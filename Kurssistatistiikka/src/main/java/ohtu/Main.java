package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) {
        try {
            // ÄLÄ laita githubiin omaa opiskelijanumeroasi
            String studentNr = "012345678";
            if (args.length > 0) {
                studentNr = args[0];
            }

            String url = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";

            String bodyText = Request.Get(url).execute().returnContent().asString();

            Gson mapper = new Gson();
            Submission[] subs = mapper.fromJson(bodyText, Submission[].class);

            url = "https://studies.cs.helsinki.fi/courses/courseinfo";
            bodyText = Request.Get(url).execute().returnContent().asString();
            Submission[] total = mapper.fromJson(bodyText, Submission[].class);
            System.out.println("Opiskelijanumero: " + studentNr);
            int tehtavaa = 0;
            int tuntia = 0;

            for (Submission subTotal : total) {
                int totalExercises = subTotal.getExercises().stream().mapToInt(i -> i).sum();
                System.out.println("\n" + subTotal.getFullName() + "\n");
                tehtavaa = 0;
                tuntia = 0;
                String statsResponse = Request.Get("https://studies.cs.helsinki.fi/courses/" + subTotal.getName() + "/stats").execute().returnContent().asString();
                JsonParser parser = new JsonParser();
                JsonObject parsedData = parser.parse(statsResponse).getAsJsonObject();
                List<Integer> totals = getCourseStats(parsedData);
                for (Submission submission : subs) {
                    if (submission.getCourse().equals(subTotal.getName())) {

                        tuntia += submission.getHours();
                        tehtavaa += submission.getExercises().size();
                        int viikko = submission.getWeek();

                        System.out.println("viikko " + submission.getWeek() + ":");
                        System.out.print("tehtyjä tehtäviä " + submission.getExercises().size() + "/" + subTotal.getExercises().get(viikko)
                                + ", aikaa kului " + submission.getHours() + " tuntia. Tehdyt tehtävät: " + submission.getExercises() + "\n");

                    }
                }
                System.out.println("yhteensä: " + tehtavaa + "/" + totalExercises + " tehtävää, " + tuntia + " tuntia.");
                System.out.println("Kurssilla yhteensä " + totals.get(0) + " palautusta, palautettuja tehtäviä "
                + totals.get(1) + " kpl. Aikaa käytetty yhteensä " + totals.get(2) + " tuntia" );
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Integer> getCourseStats(JsonObject jsonObject) {
        int palautusta = 0;
        int palautettujaTehtavia = 0;
        int yhteensaTuntia = 0;
        //{"1":{"students":68,"hour_total":376,"exercise_total":723,"hours":[null,1,2,6,15,15,8,5,8,null,4,1,1],"exercises":[null,2,null,null,null,null,6,null,null,null,null,715]},
        for (int i = 1; i <= jsonObject.size(); i++) {
            JsonObject member = jsonObject.getAsJsonObject(i + "");
            palautusta += member.get("students").getAsInt();
            palautettujaTehtavia += member.get("exercise_total").getAsInt();
            yhteensaTuntia += member.get("hour_total").getAsInt();
        }
        return Arrays.asList(palautusta, palautettujaTehtavia, yhteensaTuntia);
    }
}

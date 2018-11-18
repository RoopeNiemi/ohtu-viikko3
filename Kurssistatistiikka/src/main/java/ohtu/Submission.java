package ohtu;

import java.util.List;

public class Submission {
    private int week;
    private int hours;
    private String course;
    private String fullName;
    private List<Integer> exercises;
    private String url;
    private String name;
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    
    public String getFullName() {
        return this.fullName;
    }
    
    public void setFullName(String name) {
        this.fullName = name;
    }
    public int getHours() {
        return this.hours;
    }
    
    public void setHours(int hours) {
        this.hours = hours;
    }
    
    public String getCourse() {
        return this.course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public List<Integer> getExercises() {
        return this.exercises;
    }
    
    public void setExercises(List<Integer> exercises) {
        this.exercises = exercises;
    }
    
    
    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    @Override
    public String toString() {
       return course + ", viikko " + week + ", tehtyjä tehtäviä " 
               + exercises.size() + ", aikaa kului " + hours
               + " tuntia. Tehdyt tehtävät : " + exercises + ","
               + "url: " + this.url + ", full name=" + this.fullName;
    }
    
}
package ohtu;

import ohtu.data_access.UserDao;
import ohtu.domain.User;
import org.junit.Before;
import org.junit.rules.ExternalResource;
import spark.Spark;

public class ServerRule extends ExternalResource {
    
    private final int port;

    
    public ServerRule(int port) {
        this.port = port;
    }
    
    @Before
    public void initDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Documents\\chromedriver_win32");
    }

    @Override
    protected void before() throws Throwable {
        Spark.port(port);
        UserDao dao = new UserDaoForTests();
        dao.add(new User("jukka", "akkuj"));
        Main.setDao(dao);
        Main.main(null);
    }

    @Override
    protected void after() {
        Spark.stop();
    }
    
}

package accounts;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class UserProfile {
    private final String login;
    private final String pass;
    private final long id;
    private final String purse;

    public UserProfile(long id,String login, String pass, String purse) {
        this.login = login;
        this.pass = pass;
        this.id = id;
        this.purse=purse;
    }

    public UserProfile(String login, String password, String purse) {
        this.login = login;
        this.pass = password;
        this.id = -1;
        this.purse=purse;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getPurse(){return purse;}

    public long getId() {
        return id;
    }
}

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    public final String login;

    public final String password;

    public final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom() {
        return Courier.getRandom(true, true, true);
    }

    public static Courier getRandom(boolean useLogin, boolean usePassword, boolean useFirstName) {

        String password = "";
        String login = "";
        String firstName = "";

        if (useLogin) {
            login = RandomStringUtils.randomAlphabetic(10);
        }
        if (usePassword) {
            password = RandomStringUtils.randomAlphabetic(10);
        }
        if (useFirstName) {
            firstName = RandomStringUtils.randomAlphabetic(10);
        }

        return new Courier(login, password, firstName);
    }

    @Override
    public String toString() {
        return String.format("Курьер { Имя:%s, Логин:%s, Пароль:%s }", this.firstName, this.login, this.password);
    }
}
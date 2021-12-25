public class CourierCredentials {
    public final String login;
    public final String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials form (Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    @Override
    public String toString() {
        return String.format("Курьер { Логин:%s, Пароль:%s }", this.login, this.password);
    }
}
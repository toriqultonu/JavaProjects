package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class loginData {
    private final StringProperty UserName;
    private final StringProperty Password;
    private final StringProperty Division;

    public loginData(String UserName, String Password, String Division) {
        this.UserName = new SimpleStringProperty(UserName);
        this.Password = new SimpleStringProperty(Password);
        this.Division = new SimpleStringProperty(Division);
    }

    public String getUserName() {
        return UserName.get();
    }

    public StringProperty userNameProperty() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName.set(userName);
    }

    public String getPassword() {
        return Password.get();
    }

    public StringProperty passwordProperty() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password.set(password);
    }

    public String getDevision() {
        return Division.get();
    }

    public StringProperty devisionProperty() {
        return Division;
    }

    public void setDevision(String devision) {
        this.Division.set(devision);
    }


}

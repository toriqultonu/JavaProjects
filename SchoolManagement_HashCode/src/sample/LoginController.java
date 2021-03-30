package sample;

import admin.adminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import student.studentController;

import java.net.URL;
import java.security.MessageDigest;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    LoginModel loginModel = new LoginModel();
    @FXML
    private Label dbStatus;

    @FXML
    private Button loginbtn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> comboBox;
    @FXML
    private Label loginStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  loginbtn.setDisable(true);
        if(this.loginModel.isDatabaseConnected()){
            this.dbStatus.setText("Connected");
        }
        else{
            this.dbStatus.setText("Not Connected");
        }
        this.comboBox.setItems(FXCollections.observableArrayList(option.values()));
    }

    @FXML
    public void Login(ActionEvent event) {
        try{

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getText().getBytes());

            byte[] byteData = md.digest();
            StringBuffer sb = new StringBuffer();
            for(int i =0; i<byteData.length; i++){
                sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
            }

            String hashpassword = sb.toString();

            if(this.loginModel.isLogin(this.username.getText(), hashpassword, ((option)this.comboBox.getValue()).toString())) {
                Stage stage = (Stage) this.loginbtn.getScene().getWindow();
                stage.close();
                switch (((option)this.comboBox.getValue()).toString()){
                    case "Admin" : adminLogin();
                                    break;
                    case "Student" : studentLogin();
                                    break;
                }
            }
            else{
                this.loginStatus.setText("Invalid!");
            }
        }
        catch(Exception localException){
            localException.printStackTrace();
        }
    }
    public void studentLogin(){
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/student/studentFxml.fxml").openStream());

            studentController studentController = (studentController) loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Student DataBase");
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void adminLogin(){
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/admin/adminFxml.fxml"));

           // adminController adminController = (adminController) loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin DataBase");
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

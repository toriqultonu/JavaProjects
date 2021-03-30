package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;

public class RegisterController {

    static Stage login = null;
    @FXML
     TextField usrTF;
    @FXML
     PasswordField passTF;
    @FXML
     Button bcBtn;
    @FXML
     PasswordField confTF;
    @FXML
     Button regBtn;

    @FXML
    void regBtnClicked(ActionEvent event) throws Exception{
        if(usrTF.getText().length()>0 && passTF.getText().length()>0 && passTF.getText().equals(confTF.getText())){
            String userName = new String(usrTF.getText());

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passTF.getText().getBytes());

            byte[] byteData = md.digest();
            StringBuffer sb = new StringBuffer();
            for(int i =0; i<byteData.length; i++){
                sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
            }

            String password = new String(sb.toString());
            FileWriter output = new FileWriter("password.txt",true);
            output.write(userName+" "+password+"\n");
            output.close();
            LoginController.register.close();
            Main.login.show();
        }
        else System.out.println("Wrong input");
    }
    @FXML
    void bcBtnClicked(ActionEvent event) throws IOException {
        Main.login.show();
        LoginController.register.close();
    }


}

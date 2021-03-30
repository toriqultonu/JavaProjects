package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.File;
import java.security.MessageDigest;
import java.util.Scanner;

public class LoginController {

    static Stage register = null;
    @FXML
     TextField usrTF;
    @FXML
     Button lgnBtn;
    @FXML
     PasswordField passTF;
    @FXML
     Button regBtn;
    @FXML
    private Label notybox;


    @FXML
    void lgnBtnClicked(ActionEvent event) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(passTF.getText().getBytes());
        byte[] byteData = md.digest();
        StringBuffer sb = new StringBuffer();
        for(int i =0; i<byteData.length; i++){
            sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
        }

        String match = usrTF.getText()+" "+sb.toString();
        File obj  = new File("password.txt");
        Scanner objReader = new Scanner(obj);
            while(objReader.hasNext()){
                String data = objReader.nextLine();
                if(match.equals(data)){
                    Stage editor = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml_Windows/editor.fxml"));
                    editor.setTitle("TextEditor");
                    editor.setScene(new Scene(root));
                    Main.login.close();
                    editor.show();
                    break;
                }
            }
            notybox.setText("Invalid UserName or Password!");
        }

    @FXML
    void regBtnClicked(ActionEvent event) throws Exception{
        register = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml_Windows/register.fxml"));
        register.setTitle("Register");
        register.setScene(new Scene(root));
        register.initModality(Modality.APPLICATION_MODAL);
        register.setResizable(false);
        Main.login.close();
        register.show();

    }

}

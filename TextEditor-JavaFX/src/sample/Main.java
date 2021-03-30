package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static public Stage login = null;
    @Override
    public void start(Stage primaryStage) throws Exception{
        login = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Fxml_Windows/login.fxml"));
        login.setTitle("TextEditor");
        login.setScene(new Scene(root));
        login.setResizable(false);
        login.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

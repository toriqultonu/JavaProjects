package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static BufferedReader in;
    static PrintWriter out;
    Stage primaryStage;
    void startChat()throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("logon.fxml"));
        primaryStage.setTitle("ChatApplication Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

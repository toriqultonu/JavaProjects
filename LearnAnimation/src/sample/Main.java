package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Circle circle = new Circle();
        circle.setFill(Color.BLUE);
        circle.setRadius(30);
        circle.setLayoutX(100);
        circle.setLayoutY(60);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        //transition.setByX(500);
        transition.setByY(510);
        transition.setNode(circle);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.pause();



        ScaleTransition scale = new ScaleTransition(Duration.seconds(1),circle);
        scale.setCycleCount(Animation.INDEFINITE);
        scale.setAutoReverse(true);
        scale.setToX(2);
        scale.setToY(2);

        FillTransition fill = new FillTransition(Duration.seconds(1),circle,Color.GREEN,Color.RED);
        fill.setCycleCount(Animation.INDEFINITE);
        fill.setAutoReverse(true);

        Rectangle rect1 = new Rectangle(0,300,600,1);
        rect1.setFill(Color.BLUE);
        ScaleTransition scaleRect = new ScaleTransition(Duration.seconds(3),rect1);
        scaleRect.setToY(400);
        scaleRect.setAutoReverse(true);
        scaleRect.setCycleCount(Animation.INDEFINITE);
        scaleRect.play();

        Button btn = new Button("This is a rotating button");
        btn.setLayoutX(300);
        btn.setLayoutY(300);
        RotateTransition btnrotate = new RotateTransition(Duration.seconds(5),btn);
        btnrotate.setByAngle(360);
        btnrotate.setCycleCount(Animation.INDEFINITE);
        btnrotate.setAutoReverse(true);
        btnrotate.setRate(1);
        btnrotate.play();


       /* Button button = new Button("Click");
        button.setOnAction(e-> {
            if(transition.getStatus()== Animation.Status.RUNNING){
                transition.pause();
                scale.pause();
                fill.pause();
            }
            else {transition.play(); scale.play(); fill.play();}
        });*/

        Pane root = new Pane();
        root.getChildren().addAll(btn,rect1);
        primaryStage.setTitle("Learn Animation");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

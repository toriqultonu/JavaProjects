package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private boolean turnX = true;
    private boolean playable = true;
    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();
    Pane root = new Pane();
    private Parent createBox(){

        root.setPrefSize(600,600);

        for(int i=0;i<3;i++){
            for(int j=0 ;j<3;j++){
                Tile tile = new Tile();
                tile.setTranslateX(j*200);
                tile.setTranslateY(i*200);
                root.getChildren().addAll(tile);
                board[j][i] = tile;
            }
        }
        for(int y=0;y<3;y++){
            combos.add(new Combo(board[y][0], board[y][1],board[y][2]));
        }
        for(int y=0;y<3;y++){
            combos.add(new Combo(board[0][y], board[1][y],board[2][y]));
        }
        combos.add(new Combo(board[0][0],board[1][1],board[2][2]));
        combos.add(new Combo(board[0][2],board[1][1],board[2][0]));

        return root;
    }
    private void checkState(){
        for(Combo combo: combos){
            if(combo.isComplete()){
                playable = false;
                playWinGame(combo);
                break;
            }
        }
    }
    private void playWinGame(Combo combo){
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[0].getCenterX());
        line.setEndY(combo.tiles[0].getCenterY());
        root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
                new KeyValue(line.endXProperty(),combo.tiles[2].getCenterX()),
                new KeyValue(line.endYProperty(),combo.tiles[2].getCenterY())));
        timeline.play();
    }
    private class Combo{
        private Tile[] tiles;
        public Combo(Tile... tiles){
            this.tiles = tiles;
        }
        public boolean isComplete(){
            if(tiles[0].getValue().isEmpty()) return false;
            return tiles[0].getValue().equals(tiles[1].getValue()) && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }
    private class Tile extends StackPane{
        private Text text = new Text();
        public Tile(){
            Rectangle rect = new Rectangle(200,200);
            rect.setFill(null);
            rect.setStroke(Color.BLACK);
            setAlignment(Pos.CENTER);
            getChildren().addAll(rect,text);
            setOnMouseClicked(e -> {
                if(!playable) return;
                if(e.getButton()== MouseButton.PRIMARY){
                    if(!turnX) return;
                    drawX();
                    turnX = false;
                    checkState();
                }
                else if(e.getButton() == MouseButton.SECONDARY){
                    if(turnX) return;
                    draw0();
                    turnX = true;
                    checkState();
                }
            });
        }
        public double getCenterX(){
            return getTranslateX()+100;
        }
        public double getCenterY(){
            return getTranslateY()+100;
        }
        public String getValue(){
            return text.getText();
        }
        public void drawX(){
            text.setText("X");
            text.setFont(Font.font(76));
        }
        public void draw0(){
            text.setText("O");
            text.setFont(Font.font(76));
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setScene(new Scene(createBox()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

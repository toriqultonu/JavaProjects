package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    //FXML variables:
    @FXML
    private MediaView fxMediaView;
    @FXML
    private Button fxPlayBtn;
    @FXML
    private ImageView fxPlayImage;
    @FXML
    private Slider fxSlider;
    @FXML
    private TextArea textarea;

    //Controller variable:
    private MediaPlayer mediaPlayer;
    private Media media;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //To chose the video file...
    @FXML
    void openVideoMenu(ActionEvent event) {
        try {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            media = new Media(file.toURI().toURL().toString());
            mediaPlayer = new MediaPlayer(media);
            fxMediaView.setMediaPlayer(mediaPlayer);
            //time Slider
            mediaPlayer.setOnReady(()->{
                //when player gets ready
                fxSlider.setMin(0);
                fxSlider.setMax(mediaPlayer.getMedia().getDuration().toMinutes());
                fxSlider.setValue(0);
            });
            //listener on player..
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    Duration d = mediaPlayer.getCurrentTime();
                    fxSlider.setValue(d.toMinutes());
                }
            });

            fxSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    if(fxSlider.isPressed()){
                        double val = fxSlider.getValue();
                        mediaPlayer.seek(new Duration(val *60* 1000));
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //To play the chosen file....
    @FXML
    void playVideo(ActionEvent event) throws FileNotFoundException {
        MediaPlayer.Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            fxPlayImage.setImage(new Image(new FileInputStream("src/icons/play.png")));
        }
        else {
            mediaPlayer.play();
            fxPlayImage.setImage(new Image(new FileInputStream("src/icons/pause.png")));
        }

        int duration = (int) mediaPlayer.getMedia().getDuration().toSeconds();
        System.out.println(duration);

    }
    //Play the video backward by 10 second...
    @FXML
    void prevBtnClicked(ActionEvent event) {
        double d = mediaPlayer.getCurrentTime().toSeconds();
        d = d - 10;
        mediaPlayer.seek(new Duration(d*1000));
    }
    //Play the video forward by 10 second...
    @FXML
    void nxtBtnClicked(ActionEvent event) {
        double d = mediaPlayer.getCurrentTime().toSeconds();
        d = d + 10;
        mediaPlayer.seek(new Duration(d*1000));
    }
    //To import the text file...
    @FXML
    void importText(ActionEvent event) {
        try {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            Scanner scanner = new Scanner(file);
            String ss ;
            StringBuffer sb = new StringBuffer();
            while(scanner.hasNext()) {
                ss = scanner.nextLine()+"\n";
                sb.append(ss);
            }
          textarea.setText(sb.toString());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //To split the text into line...
    @FXML
    void generateText(ActionEvent event) {

    }
}

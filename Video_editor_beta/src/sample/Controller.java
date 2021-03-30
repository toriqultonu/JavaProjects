package sample;

import com.jfoenix.controls.JFXSlider;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Controller {

    private String[] sentences, filenames;
    public static String[] media_files, selected_media_files;
    double x,y;
    public Duration[] _duration;
    public ArrayList<MediaPlayer> mediaArrayList = new ArrayList<>();
    public ArrayList<Integer> selectedmedia = new ArrayList<>();
    public String[][] seachname = new String[100][100], words = new String[100][100];

    private LinkedList<String> Medialist = new LinkedList<String>();
    public StringBuffer tsfiles = new StringBuffer();
    @FXML
    private ListView<String> fx_audio_list = new ListView<>();
    @FXML
    private ListView<String> directory_list = new ListView<String>();
    @FXML
    private TextField fx_audio_name;
    @FXML
    private JFXSlider seeker_slider;
    private FileInputStream fileInputStream = new FileInputStream("C:/IdeaProjects/Video_editor_beta/src/sample/Images/video-player.png");
    private final Image video_icon = new Image(fileInputStream);
    private FileInputStream audiofileinput = new FileInputStream("C:/IdeaProjects/Video_editor_beta/src/sample/Images/audio_icon.png");
    private final Image audio_icon = new Image(audiofileinput);
    private CategoryAxis categoryAxis = new CategoryAxis();
    private NumberAxis numberAxis = new NumberAxis();
    private double player_duration;
    @FXML
    private StackedBarChart<String, Number> time_line = new StackedBarChart<>(categoryAxis,numberAxis);


    @FXML
    private MediaView player;

    private MediaPlayer mediaPlayer;

    @FXML
    public Label fx_progressbar;

    @FXML
    private Label player_time;

    @FXML
    private TextArea text_para;

    @FXML
    private TextArea text_slide;

    public Controller() throws FileNotFoundException {
    }

    @FXML
    void slower_button(ActionEvent event) {
        mediaPlayer.setRate(0.75);
    }

    @FXML
    void play_button(ActionEvent event) {
        mediaPlayer.play();
        mediaPlayer.setRate(1.0);
    }

    @FXML
    private ListView<String> fx_selected_videos = new ListView<>();

    @FXML
    void faster_button(ActionEvent event) {
        mediaPlayer.setRate(1.5);
    }

    @FXML
    void stop_button(ActionEvent event) {
        mediaPlayer.stop();
    }

    @FXML
    void pause_button(ActionEvent event) { mediaPlayer.pause();
    }
    public void setmedia() {
        for (int i = 0; i < selected_media_files.length; i++){
            Media media = new Media(selected_media_files[i]);
            mediaArrayList.add(i,new MediaPlayer(media));
        }
    }
    public void setTime(){
        for(int i = 0; i < mediaArrayList.size(); i++){
        }
        XYChart.Series<String, Number>[] series = Stream.<XYChart.Series<String, Number>>generate(XYChart.Series::new).limit(mediaArrayList.size()).toArray(XYChart.Series[]::new);
        for (int i =0; i < mediaArrayList.size(); i++){
            series[i].getData().add(new XYChart.Data<>("1",mediaArrayList.get(i).getTotalDuration().toSeconds()));
            player_duration = mediaArrayList.get(i).getTotalDuration().toSeconds() + player_duration;
        }
        time_line.getData().addAll(series);
    }

    public void setAudio_time(){

    }

    @FXML
    void media_directory_button(ActionEvent event) throws FileNotFoundException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File media = directoryChooser.showDialog(null);
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lname = name.toLowerCase();
                return lname.endsWith(".mp4");
            }
        };
        filenames = media.list(filenameFilter);
        File[] mediafiles = media.listFiles(filenameFilter);
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < mediafiles.length; i++){
            temp.append(mediafiles[i].toURI().toString() + " ");
        }
        media_files = temp.toString().split(" ");
        filenameset();
        FileInputStream fileicon = new FileInputStream("C:/Users/nafiu/Desktop/Coding/Video_editor_beta/src/sample/Images/check.png");
        Image icon = new Image(fileicon);
        Notifications directorynotify = Notifications.create()
                .title("MESSAGE")
                .text("Video Directory Selected")
                .graphic(new ImageView(icon))
                .hideAfter(Duration.seconds(2))
                .position(Pos.TOP_LEFT);
        directorynotify.darkStyle();
        directorynotify.show();
    }

    public String time_convert(int current_time, int total_time){
        int csec,cmin = 0,tsec,tmin = 0;
        if(current_time > 60){
            cmin = current_time/60;
            csec = Math.abs(cmin*60 - current_time);
        }
        else
            csec = current_time;
        if(total_time > 60){
            tmin = total_time/60;
            tsec = Math.abs(tmin*60 - total_time);
        }
        else
            tsec = total_time;
        String string = new String(String.valueOf(cmin) + ":" + String.valueOf(csec) + "::" + String.valueOf(tmin) + ":" + String.valueOf(tsec));
        return string;
    }

    public void filenameset(){
        for(int i = 0; i < filenames.length; i++){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(filenames[i]);
            stringBuffer.delete(stringBuffer.length() - 4,stringBuffer.length());
            filenames[i] = stringBuffer.toString();
            seachname[i] = filenames[i].split(",");
        }
    }

    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }

    public void set_selected_media(){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < selectedmedia.size(); i++){
            stringBuffer.append(media_files[selectedmedia.get(i)] + " ");
        }
        selected_media_files = stringBuffer.toString().split(" ");
        directory_list.getItems().clear();
        directory_list.getItems().addAll(selected_media_files);
        directory_list.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                        imageView.setImage(video_icon);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
        directory_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void _searcher(){
        ArrayList<Integer> selected = new ArrayList<>();
        for (int i = 0; i < sentences.length; i++){
            for (int j = 0; j < words[i].length; j++){
                for (int k = 0; k < filenames.length; k++){
                    for (int l = 0; l < seachname[k].length; l++){
                        if(words[i][j].equals(seachname[k][l])){
                            selected.add(k);
                        }
                    }
                }
            }
            selectedmedia.add(mostCommon(selected));
            selected.clear();
        }
        set_selected_media();
    }

    @FXML
    void import_text_button(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.txt)","*.txt");
        fileChooser.getExtensionFilters().add(filter);
        File import_file = fileChooser.showOpenDialog(null);
        Scanner scanner = new Scanner(import_file);
        String _sentences;
        StringBuffer buff_sentences = new StringBuffer();
        while (scanner.hasNext()){
            _sentences = scanner.nextLine() + "\n";
            buff_sentences.append(_sentences);
        }
        text_para.setText(buff_sentences.toString());
        _Generator(text_para.getText());
        text_slide.setText(sentences[0]);
    }

    // This function takes string array of sentences and returns string array of words

    // This function generates Sentences out of passage
    public void _Generator(String passage) {
        sentences = passage.split("(?<=[a-z])\\.\\s+");
        for(int i = 0; i < sentences.length; i++){
            words[i] = sentences[i].split(" ");
        }
    }

    @FXML
    void generate_button(ActionEvent event) {
        _searcher();
        setmedia();

    }

    @FXML
    void add_video_ts(ActionEvent event) throws IOException {
        StringBuffer ts_temp = new StringBuffer(directory_list.getSelectionModel().getSelectedItems().toString());
        ts_temp.deleteCharAt(0);
        ts_temp.deleteCharAt(ts_temp.length()-1);
        ts_temp.append(".ts");
        tsfiles.append(ts_temp.toString() + " ");
        fx_selected_videos.getItems().addAll(ts_temp.toString());
        fx_selected_videos.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(video_icon);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
        StringBuffer temp = new StringBuffer(directory_list.getSelectionModel().getSelectedItems().toString());
        temp.deleteCharAt(0);
        temp.deleteCharAt(temp.length()-1);
        ConvertVideo.progress(temp.toString());
        fx_selected_videos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void remove_video_ts(ActionEvent event) {
        fx_selected_videos.getItems().remove(fx_selected_videos.getSelectionModel().getSelectedItem());
    }

    @FXML
    void fx_Combine_Video(ActionEvent event) {
        CombineVideo.createOutput(tsfiles.toString().split(" "));
        StringBuffer mediaPathnames = new StringBuffer();
        Media media = new Media("file:/C:/Users/nafiu/Desktop/Coding/Videos/Result/video.mp4");
        mediaPlayer = new MediaPlayer(media);
        player.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        Medialist.addAll(Arrays.asList(mediaPathnames.toString().split(" ")));
        System.out.println(Medialist.get(0));
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                seeker_slider.setValue(t1.toSeconds());
                String string = time_convert((int)t1.toSeconds(),(int)player_duration);
                player_time.setText(string);
            }
        });
        seeker_slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.seek(Duration.seconds(seeker_slider.getValue()));
            }
        });
        setTime();

    }

    @FXML
    void fxaudiosave(ActionEvent event) {
        String string = new String(fx_audio_name.getText().toString());
        File file = new File("C:/Users/nafiu/Desktop/Coding/Audios/record.wav");
        File newfile = new File("C:/Users/nafiu/Desktop/Coding/Audios/" + string + ".wav");
        file.renameTo(newfile);
        File audio = new File("C:/Users/nafiu/Desktop/Coding/Audios");
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".wav");
            }
        };
        String[] audio_names = audio.list(filenameFilter);
        fx_audio_list.getItems().clear();
        fx_audio_list.getItems().addAll(audio_names);
        fx_audio_list.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(audio_icon);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
    }

    @FXML
    void fxAudiorecord(ActionEvent event) {
        JavaSoundRecorder.record();

    }

    @FXML
    void fxAudiostop(ActionEvent event) {
        JavaSoundRecorder.targetDataLine.stop();
        JavaSoundRecorder.targetDataLine.close();
    }

    @FXML
    void fx_up_button(ActionEvent event) {
    }

    @FXML
    void fx_down_button(ActionEvent event) {

    }

    @FXML
    void fx_Export_BUtton(ActionEvent event) throws IOException {
        File file = new File("C:/Users/nafiu/Desktop/Coding/Videos/Result/video.mp4");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File target = directoryChooser.showDialog(null);
        Path move = Files.move(
                Paths.get(file.getPath()),
                Paths.get(target.getPath() + "/result.mp4")
        );

    }
    public static void message() throws FileNotFoundException {
        FileInputStream fileicon = new FileInputStream("C:/IdeaProjects/Video_editor_beta/src/sample/Images/warning.png");
        Image icon = new Image(fileicon);
        Notifications notifications = Notifications.create()
                .title("WELCOME To MovieMontageMaker")
                .text("Please Select Video Directory Before Use")
                .graphic(new ImageView(icon))
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER);
        notifications.darkStyle();
        notifications.show();
    }

    @FXML
    void fx_minimize_button(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void fx_resize_button(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
    }

    @FXML
    void fx_close_button(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void fx_mouse_drag(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void fx_mouse_pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void remove_audio_timeline(ActionEvent event) {

    }

    @FXML
    void add_audio_timeline(ActionEvent event) {
        XYChart.Series<String,Number> series = new XYChart.Series<String, Number>();
        series.getData().add(new XYChart.Data<>("2",10));
        time_line.getData().addAll(series);
    }

    @FXML
    void audiolist_refresh(ActionEvent event) {
        File audio = new File("C:/Users/nafiu/Desktop/Coding/Audios");
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".wav");
            }
        };
        String[] audio_names = audio.list(filenameFilter);
        System.out.println(audio_names[0]);
        fx_audio_list.getItems().clear();
        fx_audio_list.getItems().addAll(audio_names);
        fx_audio_list.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(audio_icon);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
    }

}

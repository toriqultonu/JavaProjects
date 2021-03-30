package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    @FXML
    private MediaView mediaView;
    @FXML
    private Label _duration;
    @FXML
    private TextArea textarea;
    @FXML
    private ListView<String> _Directory, _sentences;
    @FXML
    private ListView<String> MediaList = new ListView<String>();
    private LinkedList<File> mediaLinkedList = new LinkedList<File>();
    private LinkedList<String> _mediapathlist = new LinkedList<String>();
    private String filepath;
    private int filenum;
    private String[] sentences, words;
    private StringBuffer media_names;
    private MediaPlayer mediaPlayer;
    // This function make files name into Listview
    private void _list(String[] _names){
        _Directory.getItems().addAll(_names);
        _Directory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    // This function takes string array of sentences and returns string array of words
    public String[] divider(String[] dwords){
        StringBuffer temp = new StringBuffer();
        for (String a : dwords){
            temp.append(a + " ");
        }
        return temp.toString().split(" ");
    }
    // This function generates Sentences out of passage
    public void _Generator(String passage){
        sentences = passage.split("(?<=[a-z])\\.\\s+");
        words = divider(sentences);
        _sentences.getItems().clear();
        _sentences.getItems().addAll(sentences);
        _sentences.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    // This function Inputs words and then match them with available media file names
    public void AutoSelect(String[] swords){
        File dir = new File("C:\\Users\\nafiu\\Desktop\\Videos");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept (File dir, String name) {
                String lname = name.toLowerCase();
                if(lname.endsWith(".mp4")){
                    return true;
                }
                else
                    return false;
            }
        };

        String[] filenames = dir.list(filter);
        for(int i = 0; i < filenames.length; i++)
        {
            for (int j = 0; j < swords.length; j++)
            {
                if((swords[j] + ".mp4").equals(filenames[i]))
                {
                    filenum = i;
                    break;
                }
            }
        }
        File[] files = dir.listFiles(filter);
        String filepath = files[filenum].toURI().toString();
        if(filepath!=null) {
            Media media = new Media(filepath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
            Label label = new Label(mediaPlayer.getCurrentTime().toString());
            _duration = label;
        }
    }
    // This function and a button that opens file and play that media player
    @FXML
    void OpenFileButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)","*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filepath = file.toURI().toString();

        if(filepath!=null){
            Media media = new Media(filepath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        }
    }

    // This Functions is a button for AutoSelect function
    @FXML
    void AutoSelectButton(ActionEvent event) {

    }

    // This Function is a button that takes textfield input make them into string and send them to divider
    @FXML
    void Savebutton(ActionEvent event) {
        for(int i = 0; i < words.length; i++){
            System.out.println(words[i]);
        }
    }
    // This is Button for Generator function
    @FXML
    void generateButtonClick(ActionEvent event) {
        _Generator(textarea.getText());
    }
    // This function is a button for Directory file select
    @FXML
    void Directory_select(ActionEvent event) {

    }
    // This Function is a button that opens directory chooser then make the files of the directory into listview
    @FXML
    void OpenDirectory(ActionEvent event) {
        DirectoryChooser directorychooser = new DirectoryChooser();
        File _dir = directorychooser.showDialog(null);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        };
        String[] filenames = _dir.list(filter);
        _list(filenames);
    }

    // This is for Importing text files
    @FXML
    void _importMenuButton(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.txt)","*.txt");
        fileChooser.getExtensionFilters().add(filter);
        File import_file = fileChooser.showOpenDialog(null);
        Scanner scanner = new Scanner(import_file);
        String sentences;
        StringBuffer buff_sentences = new StringBuffer();
        while (scanner.hasNext()){
            sentences = scanner.nextLine() + "\n";
            buff_sentences.append(sentences);
        }
        textarea.setText(buff_sentences.toString());
    }
    // add media files linkedlist to list view
    @FXML
    void AddMediaButton(ActionEvent event) {
        MediaList.getItems().addAll(_mediapathlist);
    }
    // opens directory and adds media files path into a linkedlist
    @FXML
    void Select_media_directory(ActionEvent event) {
        File file = new File("C:\\Users\\nafiu\\Desktop\\Videos");
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".mp4"))
                    return true;
                else
                    return false;
            }
        };
        File mediafiles[] = file.listFiles(filenameFilter);
        Collections.addAll(mediaLinkedList, mediafiles);
        for(int i = 0; i < mediaLinkedList.size(); i++ ){
            _mediapathlist.add( i, mediaLinkedList.get(i).toString());
        }
    }
    // Adds media paths to a string buffer that can be splited into words array
    @FXML
    void Add_media_path(ActionEvent event) {
        File media = new File("C:\\Users\\nafiu\\Desktop\\Videos");
        StringBuffer mediaPathnames = new StringBuffer();
        String[] filename = media.list();
        File mediafilex[] = media.listFiles();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < filename.length;j++) {
                if((words[i] + ".mp4").equals(filename[j])) {
                    mediaPathnames.append(mediafilex[j].toURI().toString() + " ");

                }
            }
        }
        // Just need to put mediapathnames to a string array
        MediaList.getItems().addAll(mediaPathnames.toString().split(" "));
    }

}

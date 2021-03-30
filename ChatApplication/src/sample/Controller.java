package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    public TextField textBox;
    @FXML
    private TextArea TextArea;
    @FXML
    private Button sendBtn;
    private String clientName = "tonu";
    public static String text;

    @FXML
    void sendBtnClicked(ActionEvent event) {

    }


}

package toriqul.tonu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class HistroyController {

    @FXML
    private ListView historyList;

    public void initialize(ArrayList<String> calculationHistory) {
        calculationHistory.forEach(calculation -> {
            historyList.getItems().add(calculation);
        });
    }
}
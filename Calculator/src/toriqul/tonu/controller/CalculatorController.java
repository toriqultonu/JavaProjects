package toriqul.tonu.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import toriqul.tonu.Main;
import toriqul.tonu.util.EvaluateString;

import java.io.IOException;
import java.util.ArrayList;


public class CalculatorController {

    double result;

    public ArrayList<String> calculationHistroy = new ArrayList<>();

    @FXML
    private Label expression;
    @FXML
    private Label resultEx;

    public void setresultEx(double result) {
        this.resultEx.setText("= "+String.valueOf(result));
    }

    public void insertAns(String ans){
        expression.setText(expression.getText()+ans);
    }

    public void deleteLast(){
        if(!getExpression().getText().isEmpty()){
            StringBuilder text = new StringBuilder(getExpression().getText());
            text.deleteCharAt(text.length()-1);
            getExpression().setText(text.toString());
        }
    }

    public Label getExpression(){
        return expression;
    }

    public void insertOperator(String operator){
        expression.setText(expression.getText()+" "+operator+" ");
    }

    public void insertClr(){
        expression.setText("");
    }

    public void insertNumber(String number){
        expression.setText(expression.getText() + number);
    }

    public void addCalculation(String expression, String result){
        this.calculationHistroy.add(expression+" = "+result);
    }

    public void openHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/toriqul/tonu/resources/History.fxml"));
            Parent root = loader.load();

            Main.getHistoryStage().setScene(new Scene(root));
            HistroyController histroyController = loader.getController();
            histroyController.initialize(calculationHistroy);

            Main.getHistoryStage().show();

        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }



    public void onMouseClicked(MouseEvent mouseEvent) {
        Button btn = (Button) mouseEvent.getSource();
        String str = btn.getText();

        switch (str){
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
                insertNumber(str);
                break;
            case "+":
            case "-":
            case "x":
            case "/":
                insertOperator(str);
                break;
            case "Clear":
                insertClr();
                break;
            case "=":
               result = EvaluateString.evaluate(getExpression().getText());
               setresultEx(result);
               addCalculation(expression.getText(),String.valueOf(result));
               break;
            case "Ans":
                String ans = resultEx.getText();
                insertAns(String.valueOf(result));
                break;
            case "Del":
                deleteLast();
                break;
            case "Hist.":
                openHistory();
                break;

        }
    }


}

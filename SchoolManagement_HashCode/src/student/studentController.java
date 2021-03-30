package student;

import admin.studentData;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class studentController implements Initializable {

    private dbConnection dc;
    private ObservableList<admin.studentData> data;

    @FXML
    private TableColumn<admin.studentData, String> fnameColumn;
    @FXML
    private TableColumn<admin.studentData, String> dobColumn;
    @FXML
    private TableColumn<admin.studentData, String> lnameColumn;
    @FXML
    private TableView<admin.studentData> studentTable;
    @FXML
    private TableColumn<admin.studentData, String> idColumn;
    @FXML
    private TableColumn<admin.studentData, String> emailColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Connection conn =  dc.getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("select * from student");
            while(rs.next()){
                this.data.add(new studentData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        this.studentTable.setItems(this.data);
        this.idColumn.setCellValueFactory(new PropertyValueFactory<studentData, String>("ID"));
        this.fnameColumn.setCellValueFactory(new PropertyValueFactory<studentData, String>("firstName"));
        this.lnameColumn.setCellValueFactory(new PropertyValueFactory<studentData, String>("lastName"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<studentData, String>("email"));
        this.dobColumn.setCellValueFactory(new PropertyValueFactory<studentData, String>("DOB"));
    }
}

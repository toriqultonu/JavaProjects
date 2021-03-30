package admin;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.option;

import java.net.URL;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adminController implements Initializable {
    //first tab items
    @FXML
    private TextField firstname;
    @FXML
    private DatePicker dob;
    @FXML
    private TextField id;
    @FXML
    private TextField email;
    @FXML
    private TextField lastname;
    @FXML
    private TableView<studentData> studentTable;


    @FXML
    private TableColumn<studentData, String> firstnameColumn;
    @FXML
    private TableColumn<studentData, String> dobColumn;
    @FXML
    private TableColumn<studentData, String> lastnameColumn;
    @FXML
    private TableColumn<studentData, String> idColumn;
    @FXML
    private TableColumn<studentData, String> emailColumn;

    //second tab items
    @FXML
    private TextField usrName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirm;
    @FXML
    private ComboBox<option> comboBox;
    @FXML
    private Button addBtn;
    @FXML
    private Button rmBtn;
    @FXML
    private TableColumn<loginData, String> adminusername;
    @FXML
    private TableColumn<loginData, String> adminpassword;
    @FXML
    private TableColumn<loginData, String> admindivision;
    @FXML
    private TableView<loginData> adminTable;


    //controller items
    private dbConnection dc;
    private ObservableList<studentData> data;
    private ObservableList<loginData> loginData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dc = new dbConnection();
        this.comboBox.setItems(FXCollections.observableArrayList(option.values()));

        try{
            Connection conn =  dc.getConnection();
            this.loginData = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("select * from login22");
            while(rs.next()){
                this.loginData.add(new loginData(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        this.adminTable.setItems(this.loginData);
        this.adminusername.setCellValueFactory(new PropertyValueFactory<loginData, String>("UserName"));
        this.admindivision.setCellValueFactory(new PropertyValueFactory<loginData, String>("Division"));
    }

    //First tab methods
    @FXML
    private void loadData(ActionEvent event) throws SQLException{
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
        this.firstnameColumn.setCellValueFactory(new PropertyValueFactory<studentData, String>("firstName"));
        this.lastnameColumn.setCellValueFactory(new PropertyValueFactory<studentData, String>("lastName"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<studentData, String>("email"));
        this.dobColumn.setCellValueFactory(new PropertyValueFactory<studentData, String>("DOB"));
          
    }

    @FXML
    void entry(ActionEvent event)throws SQLException{
        String sql = "insert into student(id,first_name,last_name,email,dob) values(?,?,?,?,?)";
        try{
            Connection conn = dc.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,this.id.getText());
            stmt.setString(2,this.firstname.getText());
            stmt.setString(3,this.lastname.getText());
            stmt.setString(4,this.email.getText());
            stmt.setString(5,this.dob.getEditor().getText());
            stmt.execute();
            this.data.add(new studentData(this.id.getText(),this.firstname.getText(),this.lastname.getText(),this.email.getText(),this.dob.getEditor().getText()));
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
           }

        }
    @FXML
    void clearField(ActionEvent event) {
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dob.setValue(null);

    }
    @FXML
    void removeData(ActionEvent event) {
        String sql = "delete from student where id = ?";
        String del = studentTable.getSelectionModel().getSelectedItem().getID();
       try {
           Connection conn = dc.getConnection();
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1,del);
           stmt.execute();
       }
       catch (SQLException e){
           e.printStackTrace();
       }
        this.data.remove(studentTable.getSelectionModel().getSelectedItem());
    }

    //Second tab methods
    @FXML
    void addMember(ActionEvent event) throws Exception{
        if(usrName.getText().length()>0 && password.getText().length()>0 && password.getText().equals(confirm.getText())){
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getText().getBytes());

            byte[] byteData = md.digest();
            StringBuffer sb = new StringBuffer();
            for(int i =0; i<byteData.length; i++){
                sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
            }

            String hashpassword = new String(sb.toString());
            String sql = "insert into login22(UserName,Password,Division) values(?,?,?)";
            try{
                Connection conn = dc.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,this.usrName.getText());
                stmt.setString(2,hashpassword);
                stmt.setString(3,((option)this.comboBox.getValue()).toString());
                stmt.execute();
                this.loginData.add(new loginData(this.usrName.getText(),hashpassword,((option)this.comboBox.getValue()).toString()));
                conn.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @FXML
    void rmMember(ActionEvent event){
        String sql = "delete from login22 where UserName = ?";
        String del = adminTable.getSelectionModel().getSelectedItem().getUserName();
        try {
            Connection conn = dc.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,del);
            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        this.loginData.remove(adminTable.getSelectionModel().getSelectedItem());

    }

}
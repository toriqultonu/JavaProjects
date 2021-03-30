import java.sql.*;

public class DataBase {
    public static void main(String[] args) throws Exception{
        DataBase dataBase = new DataBase();
        dataBase.createConnection();
    }
    void createConnection() throws Exception{

            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_store","root","nxdpranto*2*2");
            Statement stmt = con.createStatement();
            stmt.execute("insert into customers\n" +
                    "value(default,'Nafis', 'Walid', '2000-01-01', '5973497', 'Malotinager', 'Bogroa', 'CA', 5295)");
            stmt.execute("delete from customers where customer_id > 10");
            ResultSet resultSet = stmt.executeQuery("select * from customers");


            while(resultSet.next()){
                String name = resultSet.getString("last_name");
                int point = resultSet.getInt("points");
                System.out.println(name+"           point: "+point);
        }stmt.close();
    }
}

package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static final String USERNAME = "root";
    private static final String PASS = "nxdpranto*2*2";
    private static final String CONN = "jdbc:mysql://localhost/school";

    public static Connection getConnection() throws SQLException{
        try{
            return DriverManager.getConnection(CONN,USERNAME,PASS);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}

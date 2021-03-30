package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbUtil.dbConnection;

public class LoginModel {
    Connection connection;

    public LoginModel(){
        try{
            this.connection = dbConnection.getConnection();
        }
        catch (Exception ex){ 
            ex.printStackTrace();
        }
        if(this.connection == null){
            System.exit(1);
        }
    }
    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String user, String pass, String opt) throws Exception{
        PreparedStatement prestmt = null;
        ResultSet rs = null;
        String sql = "select * from login where UserName = ? and Password = ? and Division = ?";
        try{
            prestmt = this.connection.prepareStatement(sql);
            prestmt.setString(1,user);
            prestmt.setString(2,pass);
            prestmt.setString(3,opt);
            rs = prestmt.executeQuery();
            boolean boll1;
            if(rs.next()){
                return true;
            }
            return false;
        }
        catch(Exception ex){
            return false;
        }
        finally {
            prestmt.close();
            rs.close();
        }
    }
}

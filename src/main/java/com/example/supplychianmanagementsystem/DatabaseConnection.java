package com.example.supplychianmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String databaseURL="jdbc:mysql://localhost:3306/supply_chain_dec";
    private static final String username="root";
    private static final String password="anish@123";

    public Statement getStatement(){
        Statement statement=null;
        Connection conn;
        try {
            conn = DriverManager.getConnection(databaseURL, username, password);
            statement = conn.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return statement;
    }
    public ResultSet getQueuryTable(String query){
        Statement statement=getStatement();
        try{
            return statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    public int executeUpdateQuery(String query) {
        Statement statement = getStatement();
        try {
            return statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String args[]){
        DatabaseConnection databaseConnection=new DatabaseConnection();
        ResultSet rs=databaseConnection.getQueuryTable("SELECT * FROM CUSTOMER");
        try{
            while(rs.next()){
                System.out.println(rs.getString("customer_id")+" "+rs.getString("email")+" "+rs.getString("first_name")+" "+rs.getString("last_name")+" "+rs.getString("mobile")+" "+rs.getString("address"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

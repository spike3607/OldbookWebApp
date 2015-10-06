/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mschoenauer1
 */
public class MySqlDbStrategy implements DBStrategy {
    private Connection conn;
    
    @Override
    public void openConnection(String driverClass, String url, String userName, String password) throws Exception {
        
        Class.forName (driverClass);
	conn = DriverManager.getConnection(url, userName, password);
        
    }
    
    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }
    
    @Override
    public List<Map<String,Object>> findAllRecords(String tableName) throws SQLException {
        List<Map<String,Object>> records = new ArrayList<>();
        
        String sql = "SELECT * FROM " + tableName;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        while(rs.next()) {
            Map<String,Object> record = new HashMap<>();
            for(int i=1; i <= columnCount; i++) {
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            records.add(record);
        }
        return records;
    }
    
    @Override
    public int deleteRecordByPrimaryKey(Object key, String keyIdentifier, String tableName) throws SQLException {
        
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM " + tableName + " WHERE " + keyIdentifier + " = ?";
        
        pstmt = conn.prepareStatement(sql);
        
        if (key instanceof String) {
            pstmt.setString(1, key.toString());
        }
        else {
            pstmt.setInt(1, (int)key);
        }
        
        int updateCount = pstmt.executeUpdate();
        
        return updateCount;
    }
    
    @Override
    public int insertRecordIntoTable(String tableName, List<String> columnNames, List<Object> values) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO " + tableName + "("; //first_name,last_name)" + " VALUES('Billy','Carter')";
        
        for (int i = 0; i < columnNames.size(); i++) {
            if (i == (columnNames.size()-1)) {
               sql += columnNames.get(i) + ") VALUES(";
            }
            else {
               sql += columnNames.get(i) + ",";               
            }
        }
        
        for (int i = 0; i < values.size(); i++) {
            if (i == (values.size()-1)) {
               sql += " ? )";
            }
            else {
               sql += " ? ,";
            }
        }
        
        System.out.println(sql);
        
        pstmt = conn.prepareStatement(sql);
        
        for (int i = 0; i < values.size(); i++) {
            pstmt.setObject(i+1, values.get(i));
        }
        
        int updateCount = pstmt.executeUpdate();
        
        return updateCount;
    }
    
    @Override
    public int updateRecordByPrimaryKey(String tableName, String columnName, Object newValue, String keyIdentifier, Object key) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE " + keyIdentifier + " = ?"; 
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, newValue);
        pstmt.setObject(2, key);
        
        int updateCount = pstmt.executeUpdate();
        
        return updateCount;
    }
    
    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        
        List<Map<String,Object>> records = db.findAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }
        
        ArrayList<String> newRecordColumns = new ArrayList<>();
        newRecordColumns.add("author_name");
        newRecordColumns.add("date_created");
        
        ArrayList<Object> newRecordValues = new ArrayList<>();
        newRecordValues.add("Mike Schoenauer");
        newRecordValues.add(new Date(15,4,19));
        
        int updateCount = db.insertRecordIntoTable("author",newRecordColumns,newRecordValues);
        System.out.println("Added " + updateCount + " record(s)");
        
        records = db.findAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }
        
        updateCount = db.deleteRecordByPrimaryKey(5, "author_id", "author");
        System.out.println("Deleted " + updateCount + " record(s)");
        
        records = db.findAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }
        
        db.closeConnection();
    }
}

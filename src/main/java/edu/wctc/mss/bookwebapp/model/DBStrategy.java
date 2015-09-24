/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mschoenauer1
 */
public interface DBStrategy {

    void closeConnection() throws SQLException;

    int deleteRecordByPrimaryKey(Object key, String keyIdentifier, String tableName) throws SQLException;

    List<Map<String, Object>> findAllRecords(String tableName) throws SQLException;

    int insertRecordIntoTable(String tableName, List<String> columnNames, List<Object> values) throws SQLException;

    void openConnection(String driverClass, String url, String userName, String password) throws Exception;

    int updateRecordByPrimaryKey(String tableName, String columnName, Object newValue, String keyIdentifier, Object key) throws SQLException;
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mschoenauer1
 */
public class AuthorDao implements AuthorDaoStrategy {
    private DBStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    
    public AuthorDao(DBStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    
    @Override
    public final List<Author> getAllAuthors() throws Exception {
        db.openConnection(driverClass, url, userName, password);
        List<Author> records = new ArrayList<>();

        List<Map<String,Object>> rawData = db.findAllRecords("author");
        for(Map rawRec : rawData) {
            Author author = new Author();
            Object obj = rawRec.get("author_id");
            author.setAuthorId(Integer.parseInt(obj.toString()));
            
            String name = rawRec.get("author_name") == null ? "" : rawRec.get("author_name").toString();
            author.setAuthorName(name);
            
            obj = rawRec.get("date_added");
            Date dateAdded = (obj == null) ? new Date() : (Date)rawRec.get("date_added");
            author.setDateAdded(dateAdded);
            records.add(author);
        }
        
        // Actually closes connection
        db.closeConnection();
        
        return records;
        
    }
    
    @Override
    public final void addAuthor(String name, Date date) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        ArrayList columns = new ArrayList();
        columns.add("author_name");
        columns.add("date_created");
        
        ArrayList values = new ArrayList();
        values.add(name);
        values.add(date);
        
        db.insertRecordIntoTable("author", columns, values);
        
        db.closeConnection();
    }
    
    @Override
    public final void updateAuthor(Object key, String columnName, Object newObject) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        db.updateRecordByPrimaryKey("author", columnName, newObject, "author_id", key);
        
        db.closeConnection();
    }
    
    @Override
    public final void deleteAuthor(Object key) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        db.deleteRecordByPrimaryKey(key, "author_id", "author");
        
        db.closeConnection();
    }
    
    // Test harness - not used in production
    public static void main(String[] args) throws Exception {
        AuthorDao dao = new AuthorDao(new MySqlDbStrategy(),"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
    
        List<Author> authors = dao.getAllAuthors();
        for(Author a : authors) {
            System.out.println(a);
        }
    }
}

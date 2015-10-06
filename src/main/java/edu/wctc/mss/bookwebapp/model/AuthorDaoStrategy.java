/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author mschoenauer1
 */
public interface AuthorDaoStrategy {
    List<Author> getAllAuthors() throws Exception;
    void addAuthor(String name, Date dateCreated) throws Exception;
    void updateAuthor(Object key, String columnName, Object newObject) throws Exception;
    void deleteAuthor(Object key) throws Exception;
}

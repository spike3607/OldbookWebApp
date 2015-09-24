/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.sql.Date;

/**
 *
 * @author mschoenauer1
 */
public class Author {
    private String firstName;
    private String lastName;
    private Date dateAdded;

    public Author() {
    }

    public Author(String firstName, String lastName, Date dateAdded) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateAdded = dateAdded;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}

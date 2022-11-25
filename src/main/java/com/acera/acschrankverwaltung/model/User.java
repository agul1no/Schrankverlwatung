package com.acera.acschrankverwaltung.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Diese Klasse stellt einen User dar.
 * Jeder User-Objekt hat eine Id, einen User Name und ein Passwort.
 */
public class User {

    //region Konstanten
    private static final String STANDARD_STRING_VALUE = ">NoValueSetYet<";
    //endregion

    //region Attribute
    private int id;
    private StringProperty userName;
    private StringProperty userPasswort;
    //endregion

    //region Konstruktor
    public User() {
        this.userName = new SimpleStringProperty(STANDARD_STRING_VALUE);
        this.userPasswort = new SimpleStringProperty(STANDARD_STRING_VALUE);
    }

    public User(String userName, String userPasswort) {
        this.userName = new SimpleStringProperty(userName);
        this.userPasswort = new SimpleStringProperty(userPasswort);
    }
    //endregion

    //region Methoden

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getUserPasswort() {
        return userPasswort.get();
    }

    public StringProperty userPasswortProperty() {
        return userPasswort;
    }

    public void setUserPasswort(String userPasswort) {
        this.userPasswort.set(userPasswort);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName=" + userName +
                ", userPasswort=" + userPasswort +
                '}';
    }

    //endregion

}

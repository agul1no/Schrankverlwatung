package com.acera.acschrankverwaltung.logic;

import com.acera.acschrankverwaltung.logic.db.DbManager;
import com.acera.acschrankverwaltung.model.User;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Klasse, welche alle Users als ObservableList bereitstellt.
 */
public class UserHolder {

    //region Konstanten
    //endregion

    //region Attribute
    private static UserHolder instance;
    private ObservableList<User> observableListUsers;
    private User loggedUser;
    //endregion

    //region Konstruktor
    private UserHolder() {
        observableListUsers = FXCollections.observableArrayList(users ->
                new Observable[]{users.userNameProperty(), users.userPasswortProperty()
                });
        observableListUsers.addAll((List<User>) DbManager.getInstance().readAllDataRecords(DbManager.USER_TYPE));
        observableListUsers.addListener(new ListChangeListener<User>() {
            @Override
            public void onChanged(Change<? extends User> change) {
                System.out.println(change);
                while (change.next()) {
                    if (change.wasAdded()) {
                        User userToInsert = change.getAddedSubList().get(0);
                        DbManager.getInstance().insertDataRecord(userToInsert);
                    } else if (change.wasRemoved()) {
                        User userToDelete = change.getRemoved().get(0);
                        DbManager.getInstance().deleteDataRecord(userToDelete);
                    } else if (change.wasUpdated()) {
                        int updateIndex = change.getFrom();
                        User userToUpdate = change.getList().get(updateIndex);
                        DbManager.getInstance().updateDataRecord(userToUpdate);
                    }
                }
            }
        });
    }
    //endregion

    //region Methoden

    public static synchronized UserHolder getInstance() {
        if (instance == null) return instance = new UserHolder();
        return instance;
    }

    public ObservableList<User> getObservableListUsers() {
        return observableListUsers;
    }

    /**
     * Methode, welche überprüft, ob der Benutzername in der Liste enthalten ist
     * @param userName :  {@link String} Benutzername
     * @return {@link Boolean} gibt true oder false, wenn der User in der Liste enthalten ist oder nicht.
     */
    public boolean containsUserName(String userName) {
        for (int i = 0; i < observableListUsers.size(); i++) {
            if (observableListUsers.get(i).getUserName().equals(userName)) return true;
        }
        return false;
    }

    /**
     * Methode, welche überprüft, ob der Benutzername und das Passwort in der Liste enthalten sind.
     * @param userName :  {@link String} Benutzername.
     * @param hashedUserPassword: {@link String} Hashed Passwort.
     * @return {@link Boolean} gibt true oder false, wenn der User in der Liste enthalten ist oder nicht.
     */
    public boolean containsUserNameAndPassword(String userName, String hashedUserPassword) {
        for (int i = 0; i < observableListUsers.size(); i++) {
            if (observableListUsers.get(i).getUserName().equals(userName) && observableListUsers.get(i).getUserPasswort().equals(hashedUserPassword)) return true;
        }
        return false;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    //endregion

}

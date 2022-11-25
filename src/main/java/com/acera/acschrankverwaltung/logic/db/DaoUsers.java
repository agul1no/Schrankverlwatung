package com.acera.acschrankverwaltung.logic.db;

import com.acera.acschrankverwaltung.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Objekt für Benutzer.
 * Stellt Methoden zur Verfügung zum Erstellen, Auslesen,
 * Bearbeiten, Löschen von Benutzern in/aus der Datenbank.
 */
public class DaoUsers implements Dao<User> {

    //region Konstanten
    private final String TABLE_NAME = "users";

    private final String COLUMN_USER_ID = "pk_user_id";
    private final String COLUMN_USER_NAME = "user_name";
    private final String COLUMN_USER_PASSWORD = "user_password";

    private final String STATEMENT_INSERT_USER = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_USER_NAME + ", " +
            COLUMN_USER_PASSWORD + ") VALUES (?,?)";
    private final String STATEMENT_SELECT_ALL_USERS = "SELECT * FROM " + TABLE_NAME + ";";
    private final String STATEMENT_SELECT_ONE_USER_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = ?;";
    private final String STATEMENT_SELECT_ONE_USER_BY_NAME_AND_PASSWORD = "SELECT * FROM " + TABLE_NAME + " WHERE " +
            COLUMN_USER_NAME + " = ? AND " + COLUMN_USER_PASSWORD + " = ?;";
    private final String STATEMENT_UPDATE_ONE_USER = "UPDATE " + TABLE_NAME + " SET " + COLUMN_USER_NAME + " = ?, " +
            COLUMN_USER_PASSWORD + " = ? WHERE " + COLUMN_USER_ID + " = ?;";
    private final String STATEMENT_DELETE_ONE_USER = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = ?;";

    private final int FIRST_INDEX = 1;
    private final int SECOND_INDEX = 2;
    private final int THIRD_INDEX = 3;
    //endregion

    //region Methoden
    @Override
    public void create(Connection dbConnection, User user) {
        PreparedStatement statement = null;

        try {
            statement = dbConnection.prepareStatement(STATEMENT_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(FIRST_INDEX, user.getUserName());
            statement.setString(SECOND_INDEX, user.getUserPasswort());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            int insertId;

            if (resultSet.next()) {
                insertId = resultSet.getInt("insert_id");
                user.setId(insertId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Methode, um alle Users auszulesen. Bspw. für Adminzwecke.
     */
    @Override
    public List<User> readAll(Connection dbConnection) {
        List<User> users = new ArrayList<>();

        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(STATEMENT_SELECT_ALL_USERS);

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString(COLUMN_USER_NAME),
                        resultSet.getString(COLUMN_USER_PASSWORD)
                );
                user.setId(resultSet.getInt(COLUMN_USER_ID));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public User readById(Connection dbConnection, int id) {
        User user = null;

        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement(STATEMENT_SELECT_ONE_USER_BY_ID, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(FIRST_INDEX, id);
            ResultSet resultSet = statement.executeQuery(STATEMENT_SELECT_ONE_USER_BY_ID);

            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(COLUMN_USER_NAME),
                        resultSet.getString(COLUMN_USER_PASSWORD)
                );
                user.setId(resultSet.getInt(COLUMN_USER_ID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    /**
     * Diese Methode sucht nach einem User mit dem eingegebenen Benutzernamen und hashed Passwort und gibt diesen zurück.
     *
     * @param dbConnection       : {@link Connection} : Verbindung zur Datenbank.
     * @param userName           : {@link String} : Benutzername
     * @param hashedUserPassword : {@link String} : Hashed Password
     * @return user: {@link User} : gesuchten User aus der Datenbank
     */
    public User readUserByNameAndPassword(Connection dbConnection, String userName, String hashedUserPassword) {
        User user = null;
        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement(STATEMENT_SELECT_ONE_USER_BY_NAME_AND_PASSWORD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(FIRST_INDEX, userName);
            statement.setString(SECOND_INDEX, hashedUserPassword);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(COLUMN_USER_NAME),
                        resultSet.getString(COLUMN_USER_PASSWORD)
                );
                user.setId(resultSet.getInt(COLUMN_USER_ID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public void update(Connection dbConnection, User user) {
        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement(STATEMENT_UPDATE_ONE_USER);
            statement.setString(FIRST_INDEX, user.getUserName());
            statement.setString(SECOND_INDEX, user.getUserPasswort());
            statement.setDouble(THIRD_INDEX, user.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Connection dbConnection, User user) {
        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement(STATEMENT_DELETE_ONE_USER);
            statement.setInt(FIRST_INDEX, user.getId());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //endregion

}

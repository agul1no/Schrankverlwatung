package com.acera.acschrankverwaltung.logic.db;

import com.acera.acschrankverwaltung.logic.UserHolder;
import com.acera.acschrankverwaltung.model.Garment;
import com.acera.acschrankverwaltung.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Objekt für Kleidungsstücke.
 * Stellt Methoden zur Verfügung zum Erstellen, Auslesen,
 * Bearbeiten, Löschen von Kleidungsstücken in/aus der Datenbank.
 */
public class DaoGarments implements Dao<Garment> {

    //region Konstanten
    private final String TABLE_NAME = "garments";

    private final String COLUMN_GARMENT_ID = "pk_garment_id";
    private final String COLUMN_TYPE = "garment_type";
    private final String COLUMN_BRAND = "garment_brand";
    private final String COLUMN_PRICE = "garment_price";
    private final String COLUMN_COLOR = "garment_color";
    private final String COLUMN_DATE = "garment_date_purchase";
    private final String COLUMN_USER_ID = "fk_user_id";

    private final String STATEMENT_INSERT_GARMENT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TYPE + ", " +
            COLUMN_BRAND + ", " + COLUMN_PRICE + ", " + COLUMN_COLOR + ", " + COLUMN_DATE + ", " +
            COLUMN_USER_ID + ") VALUES (?,?,?,?,?,?)";
    private final String STATEMENT_SELECT_ALL_GARMENTS_FOR_USER = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = ?;";
    private final String STATEMENT_SELECT_ALL_GARMENTS = "SELECT * FROM " + TABLE_NAME + ";";
    private final String STATEMENT_SELECT_ALL_GARMENTS_FOR_USER_SEARCH_BUTTON = "SELECT * FROM " + TABLE_NAME + " WHERE " +
            COLUMN_USER_ID + " = ? AND (" + COLUMN_TYPE + " LIKE ? OR " + COLUMN_BRAND + " LIKE ? OR " + COLUMN_PRICE +
            " LIKE ? OR " + COLUMN_COLOR + " LIKE ? OR " +  COLUMN_DATE + " LIKE ?);";
    private final String STATEMENT_SELECT_ONE_GARMENT_FOR_USER = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_GARMENT_ID
            + " = ? AND WHERE " + COLUMN_USER_ID + " = ?;";
    private final String STATEMENT_UPDATE_GARMENT = "UPDATE " + TABLE_NAME + " SET " + COLUMN_TYPE + " = ?, " +
            COLUMN_BRAND + " = ?, " + COLUMN_PRICE + " = ?, " + COLUMN_COLOR + " = ?, " + COLUMN_DATE + " = ?, "
            + COLUMN_USER_ID + " = ? WHERE " + COLUMN_GARMENT_ID + " = ?;";
    private final String STATEMENT_DELETE_GARMENT = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_GARMENT_ID
            + " = ? AND " + COLUMN_USER_ID + " = ?;";
    private final String STATEMENT_DELETE_ALL_GARMENTS_ONE_USER = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = ?;";

    private final int FIRST_INDEX = 1;
    private final int SECOND_INDEX = 2;
    private final int THIRD_INDEX = 3;
    private final int FOURTH_INDEX = 4;
    private final int FIFTH_INDEX = 5;
    private final int SIXTH_INDEX = 6;
    private final int SEVENTH_INDEX = 7;
    //endregion

    //region Attribute
    //endregion

    //region Konstruktor
    //endregion

    //region Methoden
    @Override
    public void create(Connection dbConnection, Garment garment) {
        PreparedStatement statement = null;

        try {
            statement = dbConnection.prepareStatement(STATEMENT_INSERT_GARMENT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(FIRST_INDEX, garment.getGarmentType());
            statement.setString(SECOND_INDEX, garment.getGarmentBrand());
            statement.setDouble(THIRD_INDEX, garment.getGarmentPrice());
            statement.setString(FOURTH_INDEX, garment.getGarmentColor());
            statement.setLong(FIFTH_INDEX, garment.getDateOfPurchase());
            statement.setInt(SIXTH_INDEX, UserHolder.getInstance().getLoggedUser().getId());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            int insertId;

            if (resultSet.next()) {
                insertId = resultSet.getInt("insert_id");
                garment.setId(insertId);
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
     * Methode, um alle Kleidungsstücke auszulesen. Bspw. für Adminzwecke.
     */
    @Override
    public List<Garment> readAll(Connection dbConnection) {
        List<Garment> garments = new ArrayList<>();

        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(STATEMENT_SELECT_ALL_GARMENTS);

            while (resultSet.next()) {
                Garment garment = new Garment(
                        resultSet.getString(COLUMN_TYPE),
                        resultSet.getString(COLUMN_BRAND),
                        resultSet.getDouble(COLUMN_PRICE),
                        resultSet.getString(COLUMN_COLOR),
                        resultSet.getLong(COLUMN_DATE),
                        resultSet.getInt(COLUMN_USER_ID)
                );
                garment.setId(resultSet.getInt(COLUMN_GARMENT_ID));
                garments.add(garment);
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
        return garments;
    }

    /**
     * Methode, welche die Kleidungsstücke von einem bestimmten User aus der Datenbank ausliest und zurückgibt.
     * @param dbConnection : {@link Connection} : Verbindung zur Datenbank.
     * @param user : {@link User} eingeloggter User
     * @return Gibt die Liste der Kleidungsstücke für den passenden User zurück.
     */
    public List<Garment> readAllGarmentsUser(Connection dbConnection, User user) {
        List<Garment> garments = new ArrayList<>();

        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement(STATEMENT_SELECT_ALL_GARMENTS_FOR_USER);
            statement.setInt(FIRST_INDEX, user.getId());
            System.out.println("DaoGarments - readAllGarmentsUser - logged user id: " + user.getId());
            System.out.println("DaoGarments - readAllGarmentsUser - statement: " + statement);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Garment garment = new Garment(
                        resultSet.getString(COLUMN_TYPE),
                        resultSet.getString(COLUMN_BRAND),
                        resultSet.getDouble(COLUMN_PRICE),
                        resultSet.getString(COLUMN_COLOR),
                        resultSet.getLong(COLUMN_DATE),
                        resultSet.getInt(COLUMN_USER_ID)
                );
                garment.setId(resultSet.getInt(COLUMN_GARMENT_ID));
                garments.add(garment);
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
        return garments;
    }

    /**
     * Gibt die Liste von einem bestimmten User für ein bestimmtes Suchkriterium zurück.
     * @param dbConnection : {@link Connection} : Verbindung zur Datenbank.
     * @param user : {@link User} eingeloggter User
     * @param searchCriteria : {@link String} eingegebenes Suchkriterium im Suchfeld.
     * @return
     */
    public List<Garment> readAllGarmentsForUserBySearchCriteria(Connection dbConnection, User user, String searchCriteria) {
        List<Garment> garments = new ArrayList<>();

        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement(STATEMENT_SELECT_ALL_GARMENTS_FOR_USER_SEARCH_BUTTON);
            statement.setInt(FIRST_INDEX, user.getId());
            statement.setString(SECOND_INDEX, searchCriteria+"%");
            statement.setString(THIRD_INDEX, searchCriteria+"%");
            statement.setString(FOURTH_INDEX, searchCriteria+"%");
            statement.setString(FIFTH_INDEX, searchCriteria+"%");
            statement.setString(SIXTH_INDEX, searchCriteria+"%");
            System.out.println("DaoGarments - readByAllCriteria - logged user id: " + user.getId());
            System.out.println("DaoGarments - readByAllCriteria - statement: " + statement);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Garment garment = new Garment(
                        resultSet.getString(COLUMN_TYPE),
                        resultSet.getString(COLUMN_BRAND),
                        resultSet.getDouble(COLUMN_PRICE),
                        resultSet.getString(COLUMN_COLOR),
                        resultSet.getLong(COLUMN_DATE),
                        resultSet.getInt(COLUMN_USER_ID)
                );
                garment.setId(resultSet.getInt(COLUMN_GARMENT_ID));
                garments.add(garment);
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
        return garments;
    }

    @Override
    public Garment readById(Connection dbConnection, int id) {
        Garment garment = null;

        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement(STATEMENT_SELECT_ONE_GARMENT_FOR_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(FIRST_INDEX, id);
            ResultSet resultSet = statement.executeQuery(STATEMENT_SELECT_ONE_GARMENT_FOR_USER);

            if (resultSet.next()) {
                garment = new Garment(
                        resultSet.getString(COLUMN_TYPE),
                        resultSet.getString(COLUMN_BRAND),
                        resultSet.getDouble(COLUMN_PRICE),
                        resultSet.getString(COLUMN_COLOR),
                        resultSet.getLong(COLUMN_DATE),
                        resultSet.getInt(COLUMN_USER_ID)
                );
                garment.setId(resultSet.getInt(COLUMN_GARMENT_ID));
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

        return garment;
    }

    @Override
    public void update(Connection dbConnection, Garment garment) {
        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement(STATEMENT_UPDATE_GARMENT);
            statement.setString(FIRST_INDEX, garment.getGarmentType());
            statement.setString(SECOND_INDEX, garment.getGarmentBrand());
            statement.setDouble(THIRD_INDEX, garment.getGarmentPrice());
            statement.setString(FOURTH_INDEX, garment.getGarmentColor());
            statement.setLong(FIFTH_INDEX, garment.getDateOfPurchase());
            statement.setInt(SIXTH_INDEX, garment.getUserId());
            statement.setInt(SEVENTH_INDEX, garment.getId());

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
    public void delete(Connection dbConnection, Garment garment) {
        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement(STATEMENT_DELETE_GARMENT);
            statement.setInt(FIRST_INDEX, garment.getId());
            statement.setInt(SECOND_INDEX, garment.getUserId());

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

//    /**
//     * Löscht den User-Account und alle zu dem User gehörende Kleidungsstücke aus der Datenbank.
//     * @param dbConnection : {@link Connection} : Verbindung zur Datenbank.
//     * @param user : {@link User} eingeloggter User
//     */
//    public void deleteAllGarmentsFromUser(Connection dbConnection, User user) {
//        PreparedStatement statement = null;
//        try {
//            statement = dbConnection.prepareStatement(STATEMENT_DELETE_ALL_GARMENTS_ONE_USER);
//            statement.setInt(FIRST_INDEX, user.getId());
//            statement.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) statement.close();
//                dbConnection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    //endregion

}

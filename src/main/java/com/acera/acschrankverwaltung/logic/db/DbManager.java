package com.acera.acschrankverwaltung.logic.db;

import com.acera.acschrankverwaltung.logic.UserHolder;
import com.acera.acschrankverwaltung.model.Garment;
import com.acera.acschrankverwaltung.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Baut Verbindungen zu Datenbanken auf
 * und stellt die jeweiligen Dao-Objekte Bereit um
 * auf einzelne Tabellen zuzugreifen
 */
public class DbManager {

    //region Konstanten
    public static final int GARMENT_TYPE = 0;
    public static final int USER_TYPE = 1;
    private static final String SERVER_IP = "localhost";
    private static final String DATABASE_NAME = "schrankverwaltung";
    private static final String DB_USER_NAME = "agulicious";
    private static final String DB_USER_PW = "Sicherheit1337";

    private static final String CONNECTION_URL = "jdbc:mariadb://" + SERVER_IP + "/" + DATABASE_NAME;
    //endregion

    //region Attribute
    private static DbManager instance;
    private final DaoGarments daoGarments;
    private final DaoUsers daoUsers;
    //endregion

    //region Konstruktor
    private DbManager() {
        daoGarments = new DaoGarments();
        daoUsers = new DaoUsers();
    }
    //endregion

    //region Methoden
    public static synchronized DbManager getInstance() {
        if (instance == null) return instance = new DbManager();
        return instance;
    }

    /**
     * Baut eine Verbindung zur Datenbank auf und gibt diese zurück
     *
     * @return {@link Connection} : Verbindung zur Datenbank
     */
    public Connection getConnection() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(CONNECTION_URL, DB_USER_NAME, DB_USER_PW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

    /**
     * leitet das Einfügen eines Datensatzes für das übergebene Object ein.
     * Dafür wird geprüft, um welche Instanz es sich genau handelt.
     *
     * @param objectToInsert : {@link Object} Das einzufügende Objekt.
     */
    public void insertDataRecord(Object objectToInsert) {
        if (objectToInsert instanceof Garment garment) daoGarments.create(getConnection(), garment);
        else if (objectToInsert instanceof User user) daoUsers.create(getConnection(), user);
        else System.out.println("DbManager - insertDataRecord - Objekt konnte nicht hinzugefügt werden");
    }

    /**
     * Liest alle Einträge aus der Datenbank nach Type aus und gibt sie als Liste zurück
     *
     * @param dataType Integer, um den Datentyp zu erkennen.
     * @return : {@link List <Object>} Liste von Objekten, die aus der DB ausgelesen wird.
     */
    public List<?> readAllDataRecords(int dataType) {
        List<?> objectList = new ArrayList<>();
        switch (dataType) {
            case GARMENT_TYPE ->
                    objectList = daoGarments.readAllGarmentsUser(getConnection(), UserHolder.getInstance().getLoggedUser());
            case USER_TYPE -> objectList = daoUsers.readAll(getConnection());
            default -> System.out.println("DbManager - readAllDataRecord - Falscher Datentyp");
        }
        return objectList;
    }

    /**
     * Methode, welche die Liste von einem bestimmten User für ein bestimmtes Suchkriterium zurückgibt.
     * @param searchCriteria : {@link String} : Suchkriterium
     * @return List<Garment> : {@link List<Garment>} : Die gewünschten Objekte als Liste
     */
    public List<Garment> readAllGarmentsForUserBySearchCriteria(String searchCriteria) {
        List<Garment> garmentList;
        garmentList = daoGarments.readAllGarmentsForUserBySearchCriteria(getConnection(), UserHolder.getInstance().getLoggedUser(), searchCriteria);
        return garmentList;
    }

    /**
     * Diese Methode sucht nach einem User mit dem eingegebenen Benutzernamen und hashed Passwort und gibt diesen zurück.
     * @param userName : {@link String} : Benutzername
     * @param hashedUserPassword : {@link String} : Hashed Password
     * @return user: {@link User} : gesuchten User aus der Datenbank
     */
    public User readUserByNameAndPassword(String userName, String hashedUserPassword) {
        User user;
        user = daoUsers.readUserByNameAndPassword(getConnection(), userName, hashedUserPassword);
        return user;
    }

    /**
     * Leitet das Aktualisieren eines Datensatzes für das übergebene Objekt ein.
     * Dafür wird geprüft, um welche Instanz es sich genau handelt.
     */
    public void updateDataRecord(Object object) {
        if (object instanceof Garment garment) daoGarments.update(getConnection(), garment);
        else if (object instanceof User user) daoUsers.update(getConnection(), user);
        else System.out.printf("DbManager - updateDataRecord - Fehler beim Update");
    }

    /**
     * Leitet das Löschen eines Datensatzes für das übergebene Objekt ein.
     * Dafür wird geprüft, um welche Instanz es sich genau handelt. Wenn ein User gelöscht wird, so werden seine ganzen
     *  Kleidungsstücke mitgelöscht anhand des Constraints in der Datenbank definiert.
     */
    public void deleteDataRecord(Object objectToDelete) {
        if (objectToDelete instanceof Garment garment) daoGarments.delete(getConnection(), garment);
        else if (objectToDelete instanceof User user) {
//            daoGarments.deleteAllGarmentsFromUser(getConnection(), user);
            daoUsers.delete(getConnection(), user);
        } else System.out.println("DbManager - deleteDataRecord - Fehler beim Löschen");
    }
    //endregion

}

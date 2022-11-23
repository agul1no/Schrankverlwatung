package com.acera.acschrankverwaltung.logic.db;

import java.sql.Connection;
import java.util.List;

/**
 * Interface, welches die Methoden für die CRUD Operationen vordefiniert.
 */
public interface Dao<T> {

    /**
     * Methode zum Einfügen eines Objektes in die Datenbank
     *
     * @param dbConnection : {@link Connection} : Verbindung zur Datenbank
     * @param objectToInsert : T : Zu erstellendes Objekt einer bestimmten Klasse
     */
    void create(Connection dbConnection, T objectToInsert);

    /**
     * Methode zum Auslesen aller Objekte aus der Datenbank
     * @param dbConnection : {@link Connection} : Verbindung zur Datenbank
     * @return {@link List<T>} : Liste von Objekten aus der Datenbank
     */
    List<T> readAll(Connection dbConnection);

    /**
     * Methode zum Auslesen eines Objektes aus der Datenbank anhand der ID.
     *
     * @param dbConnection : {@link Connection} : Verbindung zur Datenbank
     * @param id : int : Id des auszulesenden Objektes
     * @return
     */
    T readById(Connection dbConnection, int id);

    /**
     * Methode zum Aktualisieren eines bestimmten Objektes in der Datenbank
     *
     * @param dbConnection : {@link Connection} : Verbindung zur Datenbank
     * @param objectToUpdate : T : Zu aktualisierendes Objekt einer bestimmten Klasse
     */
    void update(Connection dbConnection, T objectToUpdate);

    /**
     * Methode zum Löschen eines bestimmten Objektes aus der Datenbank
     *
     * @param dbConnection : {@link Connection} : Verbindung zur Datenbank
     * @param objectToDelete : T : Zu löschendes Objekt einer bestimmten Klasse
     */
    void delete(Connection dbConnection, T objectToDelete);
}

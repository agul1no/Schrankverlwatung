package com.acera.acschrankverwaltung.settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Diese Klasse speichert das Format, in welchem das Datum nach dem Auslesen aus der Datenbank ausgegeben wird
 * und stellt die Methode für die Umwandlung zur Verfügung.
 */
public class DateFormatter {

    //region Konstanten
    public static String DAY_MONTH_YEAR_FORMAT = "dd-MM-yyyy";
    //endregion

    //region Konstruktor
    private DateFormatter() {
    }
    //endregion

    //region Methoden
    public static String transformDateMillisToDateString(long dateMillis) {
        SimpleDateFormat formatter = new SimpleDateFormat(DAY_MONTH_YEAR_FORMAT);
        return formatter.format(dateMillis);
    }

    public static long transformDateStringToMillis(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DAY_MONTH_YEAR_FORMAT);
        long dateInMillis = 0;
        try {
            dateInMillis = formatter.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateInMillis;
    }
    //endregion

}
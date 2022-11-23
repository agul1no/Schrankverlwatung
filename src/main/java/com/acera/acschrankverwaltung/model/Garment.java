package com.acera.acschrankverwaltung.model;

import javafx.beans.property.*;

/**
 * Diese Klasse stellt ein Kleidungsstück der realen Welt dar.
 * Jeder Kleindungsstück-Objekt hat eine Id, einen Typ, eine Marke, einen Preis, eine Farbe und das Kaufdatum.
 */

public class Garment {

    //region Konstanten
    private static final String STANDARD_STRING_VALUE = ">NoValueSetYet<";
    private static final int STANDARD_INT_VALUE = -1;
    private static final long STANDARD_LONG_VALUE = -1;
    private static final double STANDARD_DOUBLE_VALUE = -1.0;
    //endregion

    //region Attribute
    private int id;
    private StringProperty garmentType;
    private StringProperty garmentBrand;
    private DoubleProperty garmentPrice;
    private StringProperty garmentColor;
    private LongProperty dateOfPurchase;
    private int userId;
    //endregion

    //region Konstruktor
    public Garment() {
        this.garmentType = new SimpleStringProperty(STANDARD_STRING_VALUE);
        this.garmentBrand = new SimpleStringProperty(STANDARD_STRING_VALUE);
        this.garmentPrice = new SimpleDoubleProperty(STANDARD_DOUBLE_VALUE);
        this.garmentColor = new SimpleStringProperty(STANDARD_STRING_VALUE);
        this.dateOfPurchase = new SimpleLongProperty(STANDARD_LONG_VALUE);
        this.userId = STANDARD_INT_VALUE;
    }

    public Garment(String garmentType, String garmentBrand, Double garmentPrice, String garmentColor, long dateOfPurchase, int foreignKeyUserId) {
        this.garmentType = new SimpleStringProperty(garmentType);
        this.garmentBrand = new SimpleStringProperty(garmentBrand);
        this.garmentPrice = new SimpleDoubleProperty(garmentPrice);
        this.garmentColor = new SimpleStringProperty(garmentColor);
        this.dateOfPurchase = new SimpleLongProperty(dateOfPurchase);
        this.userId = foreignKeyUserId;
    }

    //endregion

    //region Methoden
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGarmentType() {
        return garmentType.get();
    }

    public StringProperty garmentTypeProperty() {
        return garmentType;
    }

    public void setGarmentType(String garmentType) {
        this.garmentType.set(garmentType);
    }

    public String getGarmentBrand() {
        return garmentBrand.get();
    }

    public StringProperty garmentBrandProperty() {
        return garmentBrand;
    }

    public void setGarmentBrand(String garmentBrand) {
        this.garmentBrand.set(garmentBrand);
    }

    public double getGarmentPrice() {
        return garmentPrice.get();
    }

    public DoubleProperty garmentPriceProperty() {
        return garmentPrice;
    }

    public void setGarmentPrice(double garmentPrice) {
        this.garmentPrice.set(garmentPrice);
    }

    public String getGarmentColor() {
        return garmentColor.get();
    }

    public StringProperty garmentColorProperty() {
        return garmentColor;
    }

    public void setGarmentColor(String garmentColor) {
        this.garmentColor.set(garmentColor);
    }

    public long getDateOfPurchase() {
        return dateOfPurchase.get();
    }

    public LongProperty dateOfPurchaseProperty() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(long dateOfPurchase) {
        this.dateOfPurchase.set(dateOfPurchase);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //endregion

}

package com.acera.acschrankverwaltung.gui;

import com.acera.acschrankverwaltung.logic.GarmentHolder;
import com.acera.acschrankverwaltung.logic.UserHolder;
import com.acera.acschrankverwaltung.model.Garment;
import com.acera.acschrankverwaltung.settings.AppConstants;
import com.acera.acschrankverwaltung.settings.DateFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 *  Der DetailController enthält die Steuerlogik für die DetailScene
 */
public class DetailController implements Initializable {

    //region Konstanten
    //endregion

    //region Attribute
    private Garment selectedGarment;
    @FXML
    private TextField garmentTypeTextField;
    @FXML
    private TextField garmentBrandTextField;
    @FXML
    private TextField garmentPriceTextField;
    @FXML
    private TextField garmentColorTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label errorTextGarmentType;
    @FXML
    private Label errorTextGarmentBrand;
    @FXML
    private Label errorTextGarmentPrice;
    @FXML
    private Label errorTextGarmentColor;
    @FXML
    private Label errorTextGarmentPurchaseDate;
    //endregion

    //region Konstruktor
    //endregion

    /**
     * Die Methode wird bei jedem Aufruf der DetailScene aufgerufen.
     * @param url : {@link URL} : Ort um relative Pfade für das Root-Objekt aufzulösen, order null, wenn der Ort nicht bekannt ist
     * @param resourceBundle : {@link ResourceBundle} : Resource-Bundle zum Lokalisieren des Objektes, oder null, wenn
     * das Objekt nicht lokalisiert ist
     */
    //region Methoden
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setErrorTextsInvisible();
    }

    /**
     * Setzt die Visibilität der Fehlermeldungen auf false.
     */
    private void setErrorTextsInvisible() {
        errorTextGarmentType.setVisible(false);
        errorTextGarmentBrand.setVisible(false);
        errorTextGarmentPrice.setVisible(false);
        errorTextGarmentColor.setVisible(false);
        errorTextGarmentPurchaseDate.setVisible(false);
    }

    /**
     * Setzt das selektierte Kleidungsstück und füllt die Textfelder mit den Werten seiner Eigenschaften.
     * @param garment : {@link Garment} : In der Übersicht selektiertes Kleidungsstück.
     */
    public void setSelectedGarmentDetail(Garment garment) {
        this.selectedGarment = garment;

        if (garment != null) {
            garmentTypeTextField.setText(garment.getGarmentType());
            garmentBrandTextField.setText(garment.getGarmentBrand());
            garmentPriceTextField.setText(Double.toString(garment.getGarmentPrice()));
            garmentColorTextField.setText(garment.getGarmentColor());
            datePicker.setValue(Instant.ofEpochMilli(garment.getDateOfPurchase()).atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    /**
     * Prüft, ob ein Kleidungsstück der Klasse weitergegeben wurde. Wenn ja, wird die Update-Logik angeleitet.
     * Wenn nein, dann handelt es sich um ein neues Kleidungsstück, das zur Liste hinzugefügt werden soll.
     *  In beiden Fällen wird geprüft, ob der Inhalt der Textfelder den Voraussetzungen entspricht.
     */
    @FXML
    private void saveButtonClicked() {
        setErrorTextsInvisible();
        if (selectedGarment != null) {
            //bearbeiten
            checkTextFieldInputIsValidForUpdating();
        } else {
            //speichern
            checkTextFieldInputIsValidForSaving();
        }
    }

    /**
     * Prüft, ob der Inhalt der Textfelder den Voraussetzungen entspricht und wenn ja, aktualisiert das
     * ausgewählte Kleidungsstück.
     */
    private void checkTextFieldInputIsValidForUpdating() {
        if (garmentTypeTextField.getText().isEmpty() || garmentTypeTextField.getText().isBlank() ||
                garmentTypeTextField.getText().length() < AppConstants.MIN_STRING_LENGTH ||
                garmentTypeTextField.getText().length() > AppConstants.MAX_LENGTH_TYPE)
            errorTextGarmentType.setVisible(true);
        else if (garmentBrandTextField.getText().isEmpty() || garmentBrandTextField.getText().isBlank() ||
                garmentBrandTextField.getText().length() < AppConstants.MIN_STRING_LENGTH ||
                garmentBrandTextField.getText().length() > AppConstants.MAX_LENGTH_BRAND)
            errorTextGarmentBrand.setVisible(true);
        else if (garmentPriceTextField.getText().isEmpty() || garmentPriceTextField.getText().isBlank() ||
                !hasTextFieldPositiveDouble(garmentPriceTextField) ||
                garmentPriceTextField.getText().length() > AppConstants.MAX_DOUBLE_LENGTH_PRICE)
            errorTextGarmentPrice.setVisible(true);
        else if (garmentColorTextField.getText().isEmpty() || garmentColorTextField.getText().isBlank() ||
                garmentColorTextField.getText().length() < AppConstants.MIN_STRING_LENGTH ||
                garmentColorTextField.getText().length() > AppConstants.MAX_LENGTH_COLOR)
            errorTextGarmentColor.setVisible(true);
        else if (datePicker.getValue() == null) errorTextGarmentPurchaseDate.setVisible(true);
        else {
            //user input is right
            selectedGarment.setGarmentType(garmentTypeTextField.getText());
            selectedGarment.setGarmentBrand(garmentBrandTextField.getText());
            selectedGarment.setGarmentPrice(Double.parseDouble(garmentPriceTextField.getText()));
            selectedGarment.setGarmentColor(garmentColorTextField.getText());
            selectedGarment.setDateOfPurchase(DateFormatter.transformDateStringToMillis(datePicker.getValue()
                    .format(DateTimeFormatter.ofPattern(DateFormatter.DAY_MONTH_YEAR_FORMAT))));

            switchToOverviewScene();
        }
    }

    /**
     * Prüft, ob der Inhalt der Textfelder den Voraussetzungen entspricht und wenn ja, speichert das
     * eingegebene Kleidungsstück.
     */
    private void checkTextFieldInputIsValidForSaving() {
        if (garmentTypeTextField.getText().isEmpty() || garmentTypeTextField.getText().isBlank() ||
                garmentTypeTextField.getText().length() < AppConstants.MIN_STRING_LENGTH ||
                garmentTypeTextField.getText().length() > AppConstants.MAX_LENGTH_TYPE)
            errorTextGarmentType.setVisible(true);
        else if (garmentBrandTextField.getText().isEmpty() || garmentBrandTextField.getText().isBlank() ||
                garmentBrandTextField.getText().length() < AppConstants.MIN_STRING_LENGTH ||
                garmentBrandTextField.getText().length() > AppConstants.MAX_LENGTH_BRAND)
            errorTextGarmentBrand.setVisible(true);
        else if (garmentPriceTextField.getText().isEmpty() || garmentPriceTextField.getText().isBlank() ||
                !hasTextFieldPositiveDouble(garmentPriceTextField) ||
                garmentPriceTextField.getText().length() > AppConstants.MAX_DOUBLE_LENGTH_PRICE)
            errorTextGarmentPrice.setVisible(true);
        else if (garmentColorTextField.getText().isEmpty() || garmentColorTextField.getText().isBlank() ||
                garmentColorTextField.getText().length() < AppConstants.MIN_STRING_LENGTH ||
                garmentColorTextField.getText().length() > AppConstants.MAX_LENGTH_COLOR)
            errorTextGarmentColor.setVisible(true);
        else if (datePicker.getValue() == null) errorTextGarmentPurchaseDate.setVisible(true);
        else {
            //user input is right
            Garment currentGarmentToInsert = new Garment(garmentTypeTextField.getText(), garmentBrandTextField.getText(),
                    Double.parseDouble(garmentPriceTextField.getText()), garmentColorTextField.getText(),
                    DateFormatter.transformDateStringToMillis(datePicker.getValue().format(DateTimeFormatter.ofPattern(DateFormatter.DAY_MONTH_YEAR_FORMAT))),
                    UserHolder.getInstance().getLoggedUser().getId());
            GarmentHolder.getInstance().getObservableListGarments().add(currentGarmentToInsert);
            switchToOverviewScene();
        }
    }

    /**
     * Prüft, ob das weitergegeben TextField eine positive Kommazahl enthält
     * @param textField : {@link TextField} : TextField, der geprüft werden soll.
     * @return boolean : {@link Boolean} : gibt true oder false zurück.
     */
    private boolean hasTextFieldPositiveDouble(TextField textField) {
        try {
            Double.parseDouble(textField.getText());
            if (Double.parseDouble(textField.getText()) >= 0) return true;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return false;
    }

    /**
     * Prüft, ob ein Kleidungsstück ausgewählt wurde und löscht es von der Liste.
     */
    @FXML
    private void deleteGarment() {
        if (selectedGarment != null) {
            GarmentHolder.getInstance().getObservableListGarments().remove(selectedGarment);
            switchToOverviewScene();
        }
    }

    @FXML
    private void switchToOverviewScene() {
        SceneManager.getInstance().switchToOverviewScene();
    }
    //endregion

}

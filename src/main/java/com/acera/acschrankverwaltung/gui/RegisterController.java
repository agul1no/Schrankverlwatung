package com.acera.acschrankverwaltung.gui;

import com.acera.acschrankverwaltung.logic.HashGenerator;
import com.acera.acschrankverwaltung.logic.UserHolder;
import com.acera.acschrankverwaltung.logic.db.DbManager;
import com.acera.acschrankverwaltung.model.User;
import com.acera.acschrankverwaltung.settings.AppConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

/**
 * Der RegisterController enthält die Steuerlogik für die RegisterScene
 */
public class RegisterController implements Initializable {

    //region Konstanten
    //endregion

    //region Attribute
    @FXML
    private Label errorUserNameAlreadyExists;

    @FXML
    private Label errorPasswordsDontMatch;
    @FXML
    private Label errorUserNameInvalidLength;
    @FXML
    private Label errorNoServerConnection;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordFieldOne;
    @FXML
    private PasswordField passwordFieldTwo;

    private String userName;
    private String passwordOne;
    private String passwordTwo;
    private User user;
    //endregion

    //region Konstruktor
    //endregion

    //region Methoden
    /**
     * Die Methode wird bei jedem Aufruf der RegisterScene aufgerufen.
     *
     * @param url            : {@link URL} : Ort um relative Pfade für das Root-Objekt aufzulösen, order null, wenn der Ort nicht bekannt ist
     * @param resourceBundle : {@link ResourceBundle} : Resource-Bundle zum Lokalisieren des Objektes, oder null, wenn
     *                       das Objekt nicht lokalisiert ist
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setErrorFieldsInvisible();
        checkServerConnection();
    }

    /**
     * Setzt die Visibilität der Fehlermeldungen auf false.
     */
    private void setErrorFieldsInvisible() {
        errorUserNameAlreadyExists.setVisible(false);
        errorPasswordsDontMatch.setVisible(false);
        errorUserNameInvalidLength.setVisible(false);
        errorNoServerConnection.setVisible(false);
    }
    /**
     * Prüft, die Verbindung zum Server über den DbManager. Wenn es keine Verbindung zum Server besteht, zeigt
     * diese Methode die entsprechende Fehlermeldung in der LoginScene.
     */
    private void checkServerConnection() {
        if (DbManager.getInstance().getConnection() == null) errorNoServerConnection.setVisible(true);
        else errorNoServerConnection.setVisible(false);
    }

    /**
     * Prüft, ob die Eingaben vom User den Voraussetzungen entsprechen und wenn ja, wird prüft, ob der eingegebene
     * Benutzername bereits existiert. Wenn ja, wird die entsprechende Fehlermeldung angezeigt. Wenn der Benutzername
     * nicht existiert, wird geprüft, ob die Passwörter übereinstimmen und ggf. wird ein neuer User angelegt.
     */
    @FXML
    private void registerButtonClicked() {
        setErrorFieldsInvisible();
        checkServerConnection();
        if (userNameTextField.getText().isEmpty() || userNameTextField.getText().isBlank() ||
                userNameTextField.getText().length() < AppConstants.MIN_STRING_LENGTH ||
                userNameTextField.getText().length() > AppConstants.MAX_STRING_LENGTH) errorUserNameInvalidLength.setVisible(true);
        else {
            userName = userNameTextField.getText();
            if (UserHolder.getInstance().containsUserName(userName)) errorUserNameAlreadyExists.setVisible(true);
            else if (!passwordFieldOne.getText().equals(passwordFieldTwo.getText())) errorPasswordsDontMatch.setVisible(true);
            else {
                passwordOne = passwordFieldOne.getText();
                HashGenerator hashGenerator = new HashGenerator();
                String hashedPassword = null;
                try {
                    hashedPassword = hashGenerator.toHexString(hashGenerator.getSHA(passwordOne));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                user = new User(userName, hashedPassword);
                UserHolder.getInstance().getObservableListUsers().add(user);
                setErrorFieldsInvisible();
                switchToLoginScene();
            }
        }
    }

    @FXML
    private void backButtonClicked() {
        SceneManager.getInstance().switchToLoginScene();
    }

    private void switchToLoginScene() {
        SceneManager.getInstance().switchToLoginScene();
    }

    //endregion

}

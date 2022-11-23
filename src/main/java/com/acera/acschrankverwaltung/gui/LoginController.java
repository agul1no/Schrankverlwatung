package com.acera.acschrankverwaltung.gui;

import com.acera.acschrankverwaltung.logic.GarmentHolder;
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
 * Der LoginController enthält die Steuerlogik für die LoginScene
 */
public class LoginController implements Initializable {

    //region Konstanten
    //endregion

    //region Attribute
    @FXML
    private Label errorUserNameNotExist;
    @FXML
    private Label errorUserNameInvalidLength;
    @FXML
    private Label errorPasswortWrong;
    @FXML
    private Label errorNoServerConnection;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;

    private String userName;
    private String password;
    private User user;
    //endregion

    //region Konstruktor
    //endregion

    //region Methoden

    /**
     * Die Methode wird bei jedem Aufruf der LoginScene aufgerufen.
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
        errorUserNameNotExist.setVisible(false);
        errorPasswortWrong.setVisible(false);
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
     * Benutzername und Password zusammen im UserHolder enthalten sind. Wenn ja, wird der entsprechende User aus
     * der Datenbank ausgelesen und im UserHolder aus eingeloggt gespeichert.
     */
    @FXML
    private void loginButtonClicked() {
        setErrorFieldsInvisible();
        checkServerConnection();

        if (isUserNameInputValid()) errorUserNameInvalidLength.setVisible(true);
        else {
            userName = userNameTextField.getText().trim();
            if (!UserHolder.getInstance().containsUserName(userName)) errorUserNameNotExist.setVisible(true);
            else {
                password = passwordField.getText();
                HashGenerator hashGenerator = new HashGenerator();
                String hashedPassword = null;
                try {
                    hashedPassword = hashGenerator.toHexString(hashGenerator.getSHA(password));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                if (!UserHolder.getInstance().containsUserNameAndPassword(userName, hashedPassword)) {
                    errorPasswortWrong.setVisible(true);
                } else {
                    user = DbManager.getInstance().readUserByNameAndPassword(userName, hashedPassword);
                    UserHolder.getInstance().setLoggedUser(user);
                    System.out.println("logged user = " + user);
                    setErrorFieldsInvisible();
                    GarmentHolder.getInstance().loadGarmentsByUser();
                    switchToOverviewScene();
                }
            }
        }
    }

    /**
     * Prüft, ob die TextFelder richtig befüllt wurden.
     *
     * @return boolean : {@link Boolean} : gibt true oder false zurück.
     */
    private boolean isUserNameInputValid() {
        return userNameTextField.getText().isEmpty() || userNameTextField.getText().isBlank() ||
                userNameTextField.getText().length() < AppConstants.MIN_STRING_LENGTH ||
                userNameTextField.getText().length() > AppConstants.MAX_STRING_LENGTH;
    }

    @FXML
    private void switchToRegisterScene() {
        SceneManager.getInstance().switchToRegisterScene();
    }

    @FXML
    private void switchToOverviewScene() {
        SceneManager.getInstance().switchToOverviewScene();
    }
    //endregion

}

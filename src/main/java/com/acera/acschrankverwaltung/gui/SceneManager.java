package com.acera.acschrankverwaltung.gui;

import com.acera.acschrankverwaltung.Main;
import com.acera.acschrankverwaltung.model.Garment;
import com.acera.acschrankverwaltung.settings.AppConstants;
import com.acera.acschrankverwaltung.settings.AppTexts;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    //region Konstanten
    //endregion

    //region Attribute
    private static SceneManager instance;
    public Stage mainStage;
    //endregion

    //region Konstruktor

    /**
     * Singleton, welcher zuständig für das Wechseln der Szenen ist.
     */
    private SceneManager() {
    }
    //endregion

    //region Methoden
    public static synchronized SceneManager getInstance() {
        if (instance == null) instance = new SceneManager();
        return instance;
    }

    public void setMainStage(Stage stage) {
        mainStage = stage;
        mainStage.setTitle(AppTexts.APP_NAME);
        mainStage.getIcons().add(new Image("C:\\Users\\Acer R2G\\Augusto\\Programming\\Alfatraining\\Projektabschluss\\closet_icon_transparent.png"));
        switchToLoginScene();
    }

    /**
     * Lädt und wechselt zur Login-Szene
     */
    public void switchToLoginScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/login-layout.fxml"));
        try {
            Scene loginScene = new Scene(fxmlLoader.load(), AppConstants.APP_WIDTH, AppConstants.APP_HEIGHT);
            switchScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt und wechselt zur Register-Szene
     */
    public void switchToRegisterScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/register-layout.fxml"));
        try {
            Scene registerScene = new Scene(fxmlLoader.load(), AppConstants.APP_WIDTH, AppConstants.APP_HEIGHT);
            switchScene(registerScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt und wechselt zur Overview-Szene
     */
    public void switchToOverviewScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/overview-layout.fxml"));
        try {
            Scene overviewScene = new Scene(fxmlLoader.load(), AppConstants.APP_WIDTH, AppConstants.APP_HEIGHT);
            switchScene(overviewScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt und wechselt zur Detail-Szene
     */
    public void switchToDetailScene(Garment selectedGarment) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/detail-layout.fxml"));
        try {
            Scene detailScene = new Scene(fxmlLoader.load(), AppConstants.APP_WIDTH, AppConstants.APP_HEIGHT);
            DetailController detailController = fxmlLoader.getController();
            detailController.setSelectedGarmentDetail(selectedGarment);
            switchScene(detailScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchScene(Scene scene) {
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
    //endregion

}

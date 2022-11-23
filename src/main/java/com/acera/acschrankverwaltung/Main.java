package com.acera.acschrankverwaltung;

import com.acera.acschrankverwaltung.gui.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Startpunkt der Applikation -Schrankverwaltung-
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager.getInstance().setMainStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
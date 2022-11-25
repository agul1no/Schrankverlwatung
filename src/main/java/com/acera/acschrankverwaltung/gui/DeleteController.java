package com.acera.acschrankverwaltung.gui;

import com.acera.acschrankverwaltung.logic.UserHolder;
import com.acera.acschrankverwaltung.logic.db.DbManager;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Der DeleteController enthält die Steuerlogik für die DeleteScene bzw. DeleteDialog
 */
public class DeleteController {

    //region Attribute
    private Stage dialog;
    //endregion

    //region Methoden
    public void passStageDialog(Stage dialog) {
        this.dialog = dialog;
    }

    @FXML
    private void onCancelClicked() {
        dialog.close();
    }

    /**
     * Methode, welche das Löschen des Users Accounts ausführt und der damit verbundenen Kleidungsstücke.
     */
    @FXML
    private void onDeleteClicked() {
        DbManager.getInstance().deleteDataRecord(UserHolder.getInstance().getLoggedUser());
        UserHolder.getInstance().setLoggedUser(null);
        SceneManager.getInstance().switchToLoginScene();
        dialog.close();
    }
    //endregion
}
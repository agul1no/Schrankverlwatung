package com.acera.acschrankverwaltung.gui;

import com.acera.acschrankverwaltung.Main;
import com.acera.acschrankverwaltung.gui.listview.ListViewGarmentCellFactory;
import com.acera.acschrankverwaltung.logic.GarmentHolder;
import com.acera.acschrankverwaltung.logic.UserHolder;
import com.acera.acschrankverwaltung.logic.db.DbManager;
import com.acera.acschrankverwaltung.model.Garment;
import com.acera.acschrankverwaltung.model.User;
import com.acera.acschrankverwaltung.settings.AppConstants;
import com.acera.acschrankverwaltung.settings.AppTexts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Der OverviewController enthält die Steuerlogik für die OverviewScene
 */
public class OverviewController implements Initializable{

    //region Konstanten
    public static final String SORT_GARMENT_TYPE = "sortGarmentType";
    public static final String SORT_GARMENT_BRAND = "sortGarmentBrand";
    public static final String SORT_GARMENT_PRICE = "sortGarmentPrice";
    public static final String SORT_GARMENT_COLOR = "sortGarmentColor";
    public static final String SORT_GARMENT_DATE = "sortGarmentDate";
    public static final int LIST_CLICK_COUNT = 2;
    //endregion

    //region Attribute
    @FXML
    private Label welcomeMessage;
    @FXML
    private ListView<Garment> garmentListView;
    @FXML
    private TextField searchTextField;
    @FXML
    private ImageView deleteImage;

    //endregion

    //region Konstruktor
    //endregion

    //region Methoden

    /**
     * Die Methode wird bei jedem Aufruf der OverviewScene aufgerufen.
     *
     * @param url            : {@link URL} : Ort um relative Pfade für das Root-Objekt aufzulösen, order null, wenn der Ort nicht bekannt ist
     * @param resourceBundle : {@link ResourceBundle} : Resource-Bundle zum Lokalisieren des Objektes, oder null, wenn
     *                       das Objekt nicht lokalisiert ist
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeMessage.setText("Willkommen in der Schrankübersicht, " + UserHolder.getInstance().getLoggedUser().getUserName());
        garmentListView.setCellFactory(new ListViewGarmentCellFactory());
        garmentListView.setItems(GarmentHolder.getInstance().getObservableListGarments());

        setTextFieldChangeListener();
        setListViewMouseClickListener();
        setListViewKeyPressedListener();
    }

    /**
     * Setzt den Listener für das Suchfeld, damit der eingegebene Wert automatisch ausgelesen wird.
     * Wenn das Feld leer ist oder aus Leerzeichen besteht, wird die ganze Liste des Users angezeigt.
     */
    private void setTextFieldChangeListener() {
        searchTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            garmentListView.getSelectionModel().clearSelection();
            if (newValue.isBlank() || newValue.isEmpty()){
                GarmentHolder.getInstance().loadGarmentsByUser();
                garmentListView.setItems(GarmentHolder.getInstance().getObservableListGarments());
            }else {
                GarmentHolder.getInstance().loadGarmentsBySearchCriteria(newValue);
                garmentListView.setItems(GarmentHolder.getInstance().getObservableListSearchedGarments());
            }

        }));
    }

    /**
     * Setzt den MouseListener für die ListView, damit die ListView weiß, wann der User Doppelklick auf ein
     * Objekt gemacht hat.
     */
    private void setListViewMouseClickListener() {
        garmentListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == LIST_CLICK_COUNT && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    Garment selectedGarment = garmentListView.getSelectionModel().getSelectedItem();
                    searchTextField.setText(AppTexts.EMPTY_STRING);
                    SceneManager.getInstance().switchToDetailScene(selectedGarment);
                }
            }
        });
    }

    /**
     * Setzt den KeyPressedListener für die ListView, damit die ListView weiß, wann der User auf Enter gedrückt hat.
     */
    private void setListViewKeyPressedListener() {
        garmentListView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    Garment selectedGarment = garmentListView.getSelectionModel().getSelectedItem();
                    searchTextField.setText(AppTexts.EMPTY_STRING);
                    SceneManager.getInstance().switchToDetailScene(selectedGarment);
                }
            }
        });
    }

    /**
     * Setzt den Inhalt des Suchfeldes auf ein leeres String zurück.
     */
    @FXML
    private void onResetButtonClicked() {
        searchTextField.setText(AppTexts.EMPTY_STRING);
    }

    @FXML
    private void switchToDetailAnsicht() {
        SceneManager.getInstance().switchToDetailScene(null);
    }

    /**
     * Logik, wenn der User auf das Abmeldesymbol klickt. Logged User wird auf null gesetzt und die Scene wird gewechselt.
     */
    @FXML
    private void logoutImageClicked() {
        UserHolder.getInstance().setLoggedUser(null);
        System.out.println(UserHolder.getInstance().getLoggedUser());
        switchToLoginScene();
    }

    @FXML
    private void switchToLoginScene() {
        SceneManager.getInstance().switchToLoginScene();
    }

    /**
     * Sortiert die Liste der Kleidungsstücke je nach Kriterium.
     * @param actionEvent : {@link ActionEvent} : erkennt, auf welchem Knopf der User gedrückt hat.
     */
    @FXML
    private void sortGarmentList(ActionEvent actionEvent) {
        if (actionEvent.getSource() instanceof Button sortButton) {
            switch (sortButton.getId()) {
                case SORT_GARMENT_TYPE -> GarmentHolder.getInstance().sortByGarmentType();
                case SORT_GARMENT_BRAND -> GarmentHolder.getInstance().sortByGarmentBrand();
                case SORT_GARMENT_PRICE -> GarmentHolder.getInstance().sortByGarmentPrice();
                case SORT_GARMENT_COLOR -> GarmentHolder.getInstance().sortByGarmentColor();
                case SORT_GARMENT_DATE -> GarmentHolder.getInstance().sortByDateOfPurchase();
                default -> System.out.println(AppTexts.MSG_INVALID_BUTTON);
            }
        }
    }

    @FXML
    private void deleteIconClicked() {
        deleteImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(SceneManager.getInstance().mainStage);
                dialog.setResizable(false);
                dialog.setTitle(AppTexts.DELETE_DIALOG_TITLE);
                dialog.getIcons().add(new Image("C:\\Users\\Acer R2G\\Augusto\\Programming\\Alfatraining\\Projektabschluss\\remove_icon_red.png"));

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/delete-layout.fxml"));
                try {
                    Scene deleteScene = new Scene(fxmlLoader.load(), AppConstants.DIALOG_WIDTH, AppConstants.DIALOG_HEIGHT);
                    DeleteController deleteController = fxmlLoader.getController();
                    deleteController.passStageDialog(dialog);
                    dialog.setScene(deleteScene);
                    dialog.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //endregion
}
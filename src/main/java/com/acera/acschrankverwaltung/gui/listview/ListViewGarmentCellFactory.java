package com.acera.acschrankverwaltung.gui.listview;

import com.acera.acschrankverwaltung.model.Garment;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Die Klasse ListViewGarmentCellFactory stellt Zellen der Klasse ListCell<Garment> her. Diese Zellen werden
 * der ListView in der OverviewController weitergegeben. Die Klasse implementiert die Callback interface.
 */
public class ListViewGarmentCellFactory implements Callback<ListView<Garment>, ListCell<Garment>> {

    //region Methoden
    /**
     * Anhand dieser Methode werden Zellen meiner eigenen Klasse aufgebaut und and die ListView weitergegeben.
     * Die ListView wird an der GUI angezeigt.
     *
     * @param garmentListView {@link ListView<Garment>} : ListView mit der Liste der Kleidungsstücke
     * @return : {@link ListViewGarmentCell} : Objekt der eigenen Zellen-Klasse
     */
    @Override
    public ListCell<Garment> call(ListView<Garment> garmentListView) {
        return new ListViewGarmentCell();
    }
    //endregion

}

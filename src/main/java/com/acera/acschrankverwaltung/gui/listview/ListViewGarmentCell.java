package com.acera.acschrankverwaltung.gui.listview;

import com.acera.acschrankverwaltung.model.Garment;
import com.acera.acschrankverwaltung.settings.AppTexts;
import com.acera.acschrankverwaltung.settings.DateFormatter;
import javafx.scene.control.ListCell;

/**
 * Diese Klasse ist zuständig fürs Formatieren der ListView-Zellen und erbt von der Klasse ListCell.
 */

public class ListViewGarmentCell extends ListCell<Garment> {

    //region Methoden
    /**
     * Diese Methode wird von der Klasse ListCell überschrieben und formatiert den Inhalt jeder Zelle in der ListView
     *
     * @param garmentToShow {@link Garment} : Garment Objekt mit den Attributen jedes Kleidungsstückes
     * @param isEmpty       {@link Boolean} : überprüft, ob der Inhalt der Zelle ist leer.
     */
    @Override
    protected void updateItem(Garment garmentToShow, boolean isEmpty) {
        super.updateItem(garmentToShow, isEmpty);

        if (getIndex() % 2 == 1) setStyle("-fx-background-color: #f5aef7");
        else setStyle("-fx-background-color: #7171b1");

        if (isEmpty && garmentToShow == null) {
            setText(null);
            setGraphic(null);
            setStyle("-fx-background-color: #7171b1");
        } else {
            setText(String.format(AppTexts.CELL_FORMAT, garmentToShow.getGarmentType(), garmentToShow.getGarmentBrand(),
                    garmentToShow.getGarmentPrice(), garmentToShow.getGarmentColor(), DateFormatter.transformDateMillisToDateString(garmentToShow.getDateOfPurchase())));
        }
    }
    //endregion
}

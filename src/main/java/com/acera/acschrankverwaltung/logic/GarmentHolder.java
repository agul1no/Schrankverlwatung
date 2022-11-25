package com.acera.acschrankverwaltung.logic;

import com.acera.acschrankverwaltung.logic.db.DbManager;
import com.acera.acschrankverwaltung.model.Garment;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.List;

/**
 * Klasse, welche alle Kleidungsstücke als ObservableList bereitstellt.
 */
public class GarmentHolder {

    //region Attribute
    private static GarmentHolder instance;
    private ObservableList<Garment> observableListGarments;
    private ObservableList<Garment> observableListSearchedGarments;

    private ListChangeListener<Garment> listChangeListener;
    private ListChangeListener<Garment> listChangeListenerSearchedGarments;
    //endregion

    //region Konstruktor

    /**
     * Der Konstruktor enthält zwei Listen. Die observableListGarments wird immer nach dem Aufruf der OverviewScene
     * gezeigt und enthält alle Kleidungsstücke vom User. Die observableListSearchedGarments enthält alle Kleidungsstücke,
     * die dem Suchkriterium entsprechen.
     */
    private GarmentHolder() {
        initializeListListenerForUser();
        observableListGarments = FXCollections.observableArrayList(garment ->
                new Observable[]{garment.garmentTypeProperty(), garment.garmentBrandProperty(),
                        garment.garmentPriceProperty(), garment.garmentColorProperty(), garment.dateOfPurchaseProperty()
                });
        observableListSearchedGarments = FXCollections.observableArrayList(garment ->
                new Observable[]{garment.garmentTypeProperty(), garment.garmentBrandProperty(),
                        garment.garmentPriceProperty(), garment.garmentColorProperty(), garment.dateOfPurchaseProperty()
                });
    }
    //endregion

    //region Methoden
    public static synchronized GarmentHolder getInstance() {
        if (instance == null) return instance = new GarmentHolder();
        return instance;
    }

    public ObservableList<Garment> getObservableListGarments() {
        return observableListGarments;
    }

    public ObservableList<Garment> getObservableListSearchedGarments() {
        return observableListSearchedGarments;
    }

    /**
     * Methode, welche die observableListSearchedGarments ausblendet und die observableListGarments einblendet und
     * den ListChangeListener zuweist.
     */
    public void loadGarmentsByUser() {
        observableListSearchedGarments.removeAll(observableListSearchedGarments);
        observableListGarments.removeListener(listChangeListener);
        observableListGarments.removeAll(observableListGarments);
        observableListGarments.addAll((List<Garment>) DbManager.getInstance().readAllDataRecords(DbManager.GARMENT_TYPE));
        observableListGarments.addListener(listChangeListener);
    }

    /**
     * Methode, welche die observableListGarments ausblendet und die observableListSearchedGarments einblendet.
     */
    public void loadGarmentsBySearchCriteria(String searchCriteria) {
        observableListGarments.removeListener(listChangeListener);
        observableListGarments.removeAll(observableListGarments);
        observableListSearchedGarments.removeAll(observableListSearchedGarments);
        observableListSearchedGarments.addAll(DbManager.getInstance().readAllGarmentsForUserBySearchCriteria(searchCriteria));
    }

    /**
     * Methode, welche den ListChangeListener der Liste zuweist und die Änderungen observiert.
     */
    private void initializeListListenerForUser() {
        listChangeListener = new ListChangeListener<Garment>() {
            @Override
            public void onChanged(Change<? extends Garment> change) {
                System.out.println(change);

                while (change.next()) {
                    if (change.wasAdded()) {
                        Garment garmentToInsert = change.getAddedSubList().get(0);
                        DbManager.getInstance().insertDataRecord(garmentToInsert);
                    } else if (change.wasRemoved()) {
                        Garment garmentToDelete = change.getRemoved().get(0);
                        DbManager.getInstance().deleteDataRecord(garmentToDelete);
                    } else if (change.wasUpdated()) {
                        int updateIndex = change.getFrom();
                        Garment garmentToUpdate = change.getList().get(updateIndex);
                        DbManager.getInstance().updateDataRecord(garmentToUpdate);
                    }
                }
            }
        };
    }

    public void sortByGarmentType() {
        observableListGarments.sort(Comparator.comparing(Garment::getGarmentType));
    }

    public void sortByGarmentBrand() {
        observableListGarments.sort(Comparator.comparing(Garment::getGarmentBrand));
    }

    public void sortByGarmentPrice() {
        observableListGarments.sort(Comparator.comparing(Garment::getGarmentPrice));
    }

    public void sortByGarmentColor() {
        observableListGarments.sort(Comparator.comparing(Garment::getGarmentColor));
    }

    public void sortByDateOfPurchase() {
        observableListGarments.sort(Comparator.comparing(Garment::getDateOfPurchase));
    }
    //endregion

}

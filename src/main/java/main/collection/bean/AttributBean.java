package main.collection.bean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import main.collection.dao.DAOFactory;
import main.collection.metier.Attribut;

import java.util.ArrayList;
import java.util.List;

public class AttributBean {
    private ArrayList<Attribut> attributArrayList;
    private final ObservableList<Attribut> attributObservableList;
    private final FilteredList<Attribut> attributFilteredList;
    private final SortedList<Attribut> attributSortedList;

    public AttributBean() {
        attributArrayList = new ArrayList<>();
        attributArrayList = DAOFactory.getAttributDAO().getAll();
        attributObservableList = FXCollections.observableArrayList();
        attributObservableList.addAll(attributArrayList);
        attributFilteredList = new FilteredList<>(attributObservableList, null);
        attributSortedList = new SortedList<>(attributFilteredList);
    }

    public List<Attribut> getAllAttributs() {
        return new ArrayList<>(attributObservableList);
    }

    public void fiteredAttribut(String nameAttribut) {
        attributFilteredList.setPredicate(attribut -> attribut.getLibelle().contains(nameAttribut));
    }

    // Method to update the lists after data modifications
    private void updateLists() {
        attributArrayList.clear();
        attributArrayList.addAll(DAOFactory.getAttributDAO().getAll());
        attributObservableList.setAll(attributArrayList);
        fiteredAttribut(""); // Reapply any existing filters
    }


    public boolean insertAttribut(Attribut attribut) {
        boolean isInserted = DAOFactory.getAttributDAO().insert(attribut);
        if (isInserted) {
            updateLists();
        }
        return isInserted;
    }

    public boolean updateAttribut(Attribut attribut) {
        boolean isUpdated = DAOFactory.getAttributDAO().update(attribut);
        if (isUpdated) {
            updateLists();
        }
        return isUpdated;
    }

    public boolean deleteAttribut(Attribut attribut) {
        boolean isDeleted = DAOFactory.getAttributDAO().delete(attribut);
        if (isDeleted) {
            updateLists();
        }
        return isDeleted;
    }

}

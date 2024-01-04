package main.collection.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.collection.DAO.AttributDAO;
import main.collection.Metier.Attribut;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class PaneAttributModify implements Initializable {
    @FXML
    private ListView<Attribut> attributeListView;
    @FXML
    private CheckBox sortingCheckBox;
    private List<Attribut> initialOrder;
    private final AttributDAO attributDAO = new AttributDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set a custom cell factory for Attribut
        attributeListView.setCellFactory(list -> new TextFieldListCell<Attribut>(new StringConverter<Attribut>() {
            @Override
            public String toString(Attribut object) {
                return object == null ? "" : object.getLibelle();
            }

            @Override
            public Attribut fromString(String string) {
                // Assuming Attribut has a constructor that takes libelle
                return new Attribut(string);
            }
        }) {
            @Override
            public void updateItem(Attribut item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getLibelle());
                }
            }
        });

        // Handle the commit event when editing is finished
        attributeListView.setOnEditCommit(event -> {
            Attribut editedAttribut = event.getNewValue();
            attributeListView.refresh();
        });
        //show sorting
        sortingCheckBox.setOnAction(this::handleSortingCheckBox);

    }

    public void setAttributList(List<String> libelleList) {
        // Assuming there is a constructor in Attribut that takes a libelle
        List<Attribut> attributList = libelleList.stream()
                .map(Attribut::new)
                .collect(Collectors.toList());

        initialOrder = new ArrayList<>(attributList);

        attributeListView.setItems(FXCollections.observableList(attributList));

    }

    public void handleSortingCheckBox(ActionEvent actionEvent) {
        boolean isChecked = sortingCheckBox.isSelected();

        // Sort the items based on the checkbox state
        if (isChecked) {
            // Sort in ascending order
            attributeListView.getItems().sort(Comparator.comparing(Attribut::getLibelle));
        } else {
            attributeListView.getItems().setAll(initialOrder);

        }
    }

    public void addNewAttribut(ActionEvent actionEvent) {
        //create a new attribut with a defeut editable name
        Attribut newAttribut = new Attribut("Entrez le libelle");
        //add the new Attribut to the listview
        attributeListView.getItems().add(newAttribut);

        //Select the new item to make it editable
        attributeListView.getSelectionModel().select(newAttribut);
        attributeListView.edit(attributeListView.getItems().size() - 1);


    }

    public void deleteSelectedAttribut(ActionEvent actionEvent) {
        // Get the selected item from the ListView
        Attribut selectedAttribut = attributeListView.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedAttribut != null) {
            showAlert("Confirmation Dialog", "Are you sure you want to delete the selected attribute?")
                    .ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            // User clicked OK, proceed with deletion
                            attributeListView.getItems().remove(selectedAttribut);
                        }
                    });
        }
    }

    public void saveChange(ActionEvent actionEvent) {
        //Save all changes to the database
        for (Attribut attribut : attributeListView.getItems()) {
            attributDAO.update(attribut);
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private Optional<ButtonType> showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}



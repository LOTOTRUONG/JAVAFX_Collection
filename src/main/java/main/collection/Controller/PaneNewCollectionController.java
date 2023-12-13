package main.collection.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.collection.DAO.AttributDAO;
import main.collection.DAO.TypeObjectDAO;
import main.collection.Metier.Attribut;
import main.collection.Metier.TypeObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaneNewCollectionController {
    @FXML
    private ImageView newCollectionImage;
    @FXML
    private TextField newCollectionTextField;

    @FXML
    private ComboBox attributCombobox;
    @FXML
    private VBox attributPane;

    private final Map<String, HBox> attributItems = new HashMap<>();


    public void initialize() {
        populateAttributCombobox();
    }

    public void setNewCollectionImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //set extension
        FileChooser.ExtensionFilter extensionFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extensionFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        FileChooser.ExtensionFilter extensionFilterTIFF = new FileChooser.ExtensionFilter("TIFF files (*.tiff)", "*.TIFF");
        fileChooser.getExtensionFilters().addAll(extensionFilterJPG, extensionFilterPNG, extensionFilterTIFF);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                newCollectionImage.setImage(image);
                // BackgroundSize backgroundSize = new BackgroundSize(200,200,true,true,true,false);
                // BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                // Background background = new Background(backgroundImage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void insertDataIntoTable(ActionEvent actionEvent) {
        try {
            String libelle = newCollectionTextField.getText().trim().toUpperCase();
            if (!libelle.isEmpty()) {
                //Check if the libelle is already in the table
                if (!isLibelleisExits(libelle)) {
                    TypeObject typeObject = new TypeObject();
                    typeObject.setLibelle(libelle);
                    (new TypeObjectDAO()).insert(typeObject);
                    showAlert("Success", "The new collection is set");

                    //close Scene after success
                    Stage stage = (Stage) newCollectionTextField.getScene().getWindow();
                    stage.close();
                } else {
                    showAlert("ERROR", "The collection is already available");
                }
            } else {
                showAlert("ERROR", "You have to input the name of new collection");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private boolean isLibelleisExits(String libelle) {
        TypeObjectDAO typeObjectDAO = new TypeObjectDAO();
        ArrayList<TypeObject> existingObjects = typeObjectDAO.getLike(new TypeObject(libelle));
        return !existingObjects.isEmpty();
    }

    private void populateAttributCombobox() {
        AttributDAO attributDAO = new AttributDAO();
        ArrayList<Attribut> attributs = attributDAO.getAll();
        attributs.forEach(attribut -> attributCombobox.getItems().add(attribut.getLibelle()));
    }

    public void addAttributToPane(ActionEvent event) {
        String selectedAttribut = (String) attributCombobox.getValue();
        if (selectedAttribut != null && !attributItems.containsKey(selectedAttribut)) {
            HBox attributHbox = createAttributItem(selectedAttribut);
            attributPane.getChildren().add(attributHbox);
            attributItems.put(selectedAttribut, attributHbox);
        }
    }

    private HBox createAttributItem(String attributName) {
        HBox newAttributHbox = new HBox(5); // Create a new HBox for each attribut item
        newAttributHbox.getStyleClass().add("attribut-item");
        Label attributLabel = new Label(attributName);
        Button removeButton = new Button("X");
        removeButton.setOnAction(event -> removeAttributFromPane(attributName));
        HBox labelAndButtonContainer = new HBox();
        labelAndButtonContainer.setAlignment(Pos.CENTER_LEFT);
        labelAndButtonContainer.getChildren().addAll(attributLabel, removeButton);

        newAttributHbox.getChildren().add(labelAndButtonContainer);
        return newAttributHbox;
    }

    private void removeAttributFromPane(String attributName) {
        HBox attributItem = attributItems.get(attributName);
        if (attributItem != null) {
            attributPane.getChildren().remove(attributItem);
            attributItems.remove(attributName);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}

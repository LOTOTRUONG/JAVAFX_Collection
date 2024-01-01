package main.collection.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.collection.DAO.AttributDAO;
import main.collection.DAO.PhotoDAO;
import main.collection.DAO.TypeObjectDAO;
import main.collection.Metier.Attribut;
import main.collection.Metier.Photo;
import main.collection.Metier.TypeObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaneTypeObjetNewController {
    @FXML
    public ImageView newCollectionImage;
    @FXML
    public TextField newCollectionTextField;

    @FXML
    private ComboBox attributCombobox;
    @FXML
    private VBox attributVboxPane;
    private ModificationSceneCallBack paneTypeObjetModifyControllerCallback;


    private final Map<String, HBox> attributItems = new HashMap<>();


    public void initialize() {
        populateAttributCombobox();
    }

    public void setCallback(ModificationSceneCallBack callback) {
        this.paneTypeObjetModifyControllerCallback = callback;
    }

    public void setNewCollectionImage() {
        FileChooser fileChooser = new FileChooser();
        //set extension
        FileChooser.ExtensionFilter extensionFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extensionFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        FileChooser.ExtensionFilter extensionFilterTIFF = new FileChooser.ExtensionFilter("TIFF files (*.tiff)", "*.TIFF");
        FileChooser.ExtensionFilter extensionFilterAll = new FileChooser.ExtensionFilter("All files", "*.*");
        fileChooser.getExtensionFilters().addAll(extensionFilterAll, extensionFilterJPG, extensionFilterPNG, extensionFilterTIFF);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                newCollectionImage.setImage(image);

            } catch (Exception exception) {
                showAlert("Error", "Failed to load the selected image");
                exception.printStackTrace();
            }
        }
    }

    public void insertDataIntoTable() {
        try {
            String libelle = newCollectionTextField.getText().trim();
            String imagePath = newCollectionImage.getImage().getUrl().toString();
            if (libelle.isEmpty() || imagePath.isEmpty()) {
                showAlert("Error", "Please provide both the collection name and image.");
                return;
            }
            if (isLibelleisExist(libelle) || isImagePathExist(imagePath)) {
                showAlert("Error", "The collection or image path is already available.");
                return;
            }
                    TypeObject typeObject = new TypeObject();
                    typeObject.setLibelle(libelle);
            typeObject.setImagePath(imagePath);
                    (new TypeObjectDAO()).insert(typeObject);

            Photo photo = new Photo();
            photo.setImagePath(imagePath);
            (new PhotoDAO()).insert(photo);
            showAlert("Success", "The new collection has been added successfully.");

                    //close Scene after success
                    Stage stage = (Stage) newCollectionTextField.getScene().getWindow();
                    stage.close();
            // Trigger the update of the collection view in the main controller
            if (paneTypeObjetModifyControllerCallback != null) {
                paneTypeObjetModifyControllerCallback.onModificationSceneClosed();
            }

        } catch (Exception exception) {
            showAlert("Error", "An unexpected error occurred.");
            exception.printStackTrace();
        }
    }


    private boolean isLibelleisExist(String libelle) {
        TypeObjectDAO typeObjectDAO = new TypeObjectDAO();
        ArrayList<TypeObject> existingObjects = typeObjectDAO.getAll();

        //Check if any existing libelle_type(both uppercase and lowercase)
        return existingObjects.stream().anyMatch(obj -> obj.getLibelle().equalsIgnoreCase(libelle));
    }

    private boolean isImagePathExist(String imagePath) {
        PhotoDAO photoDAO = new PhotoDAO();
        ArrayList<Photo> existingObjects = photoDAO.getAll();

        //Check if any existing libelle_type(both uppercase and lowercase)
        return existingObjects.stream().anyMatch(obj -> obj.getImagePath().equalsIgnoreCase(imagePath));
    }
    private void populateAttributCombobox() {
        AttributDAO attributDAO = new AttributDAO();
        ArrayList<Attribut> attributs = attributDAO.getAll();
        attributs.forEach(attribut -> attributCombobox.getItems().add(attribut.getLibelle()));
    }

    public void addAttributToPane() {
        String selectedAttribut = (String) attributCombobox.getValue();
        if (selectedAttribut != null && !attributItems.containsKey(selectedAttribut)) {
            HBox attributHbox = createAttributItem(selectedAttribut);
            attributVboxPane.getChildren().add(attributHbox);
            attributItems.put(selectedAttribut, attributHbox);
        }
    }

    private HBox createAttributItem(String attributName) {
        HBox newAttributHbox = new HBox(); // Create a new HBox for each attribut item
        newAttributHbox.getStyleClass().add("");
        newAttributHbox.setAlignment(Pos.CENTER);
        Label attributLabel = new Label(attributName);
        Button removeButton = new Button("x");
        removeButton.setOnAction(event -> removeAttributFromPane(attributName));
        HBox labelAndButtonContainer = new HBox();
        labelAndButtonContainer.setAlignment(Pos.CENTER);
        labelAndButtonContainer.getChildren().addAll(attributLabel, removeButton);

        //Check if current row already has 3 elements
        if (newAttributHbox.getChildren().size() < 3) {
            newAttributHbox.getChildren().add(labelAndButtonContainer);
        } else {
            //Start a new row
            newAttributHbox = new HBox();
            newAttributHbox.getStyleClass().add("");
            newAttributHbox.setAlignment(Pos.CENTER);
            newAttributHbox.getChildren().add(labelAndButtonContainer);
        }
        return newAttributHbox;
    }

    private void removeAttributFromPane(String attributName) {
        HBox attributItem = attributItems.get(attributName);
        if (attributItem != null) {
            attributVboxPane.getChildren().remove(attributItem);
            attributItems.remove(attributName);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait().orElse(ButtonType.CANCEL);
    }

    public void cancelButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}

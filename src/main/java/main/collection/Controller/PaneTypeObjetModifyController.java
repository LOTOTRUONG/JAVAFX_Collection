package main.collection.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.collection.DAO.PhotoDAO;
import main.collection.DAO.TypeObjectDAO;
import main.collection.Metier.Attribut;
import main.collection.Metier.Photo;
import main.collection.Metier.TypeObject;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PaneTypeObjetModifyController implements Initializable {

    @FXML
    public ImageView modifyCollectionImage;
    @FXML
    public TextField modifyCollectionTextField;
    @FXML
    public ScrollPane scrollPane;
    private final TypeObjectDAO typeObjectDAO = new TypeObjectDAO();
    private final PhotoDAO photoDAO = new PhotoDAO();
    private TypeObject selectedTypeObject;
    private ModificationSceneCallBack paneTypeObjetModifyControllerCallback;

    @FXML
    private ListView<Attribut> attributListView;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDatafromDatabase();
    }

    public void setCallback(ModificationSceneCallBack callback) {
        this.paneTypeObjetModifyControllerCallback = callback;
    }

    public void loadDatafromDatabase() {
        try {
            scrollPane.setContent(null); // Clear existing cards
            List<TypeObject> typeObjects = typeObjectDAO.getAll();
            VBox currentColumn = new VBox();
            currentColumn.setSpacing(20);
            currentColumn.setPadding(new Insets(30, 0, 30, 30));
            for (TypeObject typeObject : typeObjects) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/collection/CardTypeObjet.fxml"));
                VBox cardbox = fxmlLoader.load();
                CardTypeObjetController cardTypeObjetController = fxmlLoader.getController();
                cardTypeObjetController.setData(typeObject);
                cardbox.setOnMouseClicked(event -> handleCardClick(typeObject));
                currentColumn.getChildren().add(cardbox);
            }
            if (!currentColumn.getChildren().isEmpty()) {
                scrollPane.setContent(currentColumn);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //click on the type object --> to see detail
    private void handleCardClick(TypeObject typeObject) {
        selectedTypeObject = typeObject;
        // Set modifyCollectionTextField to the text of the clicked card
        modifyCollectionTextField.setText(typeObject.getLibelle());
        // Set modifyCollectionImage to the image of the clicked card
        int imageId = typeObjectDAO.getImageIdByTypeId(typeObject.getId());
        if (imageId != 0) {
            PhotoDAO photoDAO = new PhotoDAO();
            String imagePath = photoDAO.getPhotoPathById(imageId);
            if (imagePath != null) {
                modifyCollectionImage.setImage(new Image(imagePath));
            }
        }
        // Load and display attributs for the selected TypeObject
        loadAttributsForTypeObject(typeObject);
    }

    //change the image of type of object
    public void setModifyCollectionImage() {
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
                modifyCollectionImage.setImage(image);
                // Save the selected image path to the database
                if (selectedTypeObject != null) {
                    int imageId = typeObjectDAO.getImageIdByTypeId(selectedTypeObject.getId());
                    if (imageId != -1) {
                        Photo photo = new Photo();
                        photo.setId(imageId);
                        photo.setImagePath(modifyCollectionImage.getImage().getUrl());
                        photoDAO.update(photo);
                    }
                }
            } catch (Exception exception) {
                showAlert("Error", "Failed to load the selected image");
                exception.printStackTrace();
            }
        }
    }

    //load list attributes linked with type of object

    private void loadAttributsForTypeObject(TypeObject typeObject) {
        attributListView.getItems().clear();
        List<String> attributs = typeObjectDAO.getAttributsByTypeObjectId(typeObject);
        for (String attribut : attributs) {
            Attribut attributObject = new Attribut(attribut); // Assuming Attribut has a constructor that takes a String
            attributListView.getItems().add(attributObject);
        }
        attributListView.setCellFactory(param -> new ListCell<Attribut>() {
            @Override
            protected void updateItem(Attribut attribut, boolean empty) {
                super.updateItem(attribut, empty);
                if (empty || attribut == null) {
                    setText(null);
                } else {
                    setText(attribut.getLibelle());
                    setTextAlignment(TextAlignment.CENTER);
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }


    //modify the list of attribut
    public void openModifyAttribut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/collection/PaneAttributModify.fxml"));
            Parent homeRoot = loader.load();
            PaneAttributModify attributModifyController = loader.getController();
            List<String> libelleList = attributListView.getItems().stream().map(Attribut::getLibelle).collect(Collectors.toList());
            attributModifyController.setAttributList(libelleList);
            Stage homeStage = new Stage();
            homeStage.setTitle("Modify Attribute Scene");
            homeStage.setScene(new Scene(homeRoot));
            homeStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void openAjouterAttribut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/collection/PaneAttributAjouter.fxml"));
            Parent homeRoot = loader.load();
            //access controller
            PaneAttributAjouter paneAttributAjouter = loader.getController();
            paneAttributAjouter.setParentController(this);
            Stage homeStage = new Stage();
            homeStage.setTitle("Ajouter Attribute Scene");
            homeStage.setScene(new Scene(homeRoot));
            homeStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addNewAttribute(Attribut newAttribut) {
        attributListView.getItems().add(newAttribut);
    }

    //button with function: save, delete and cancel
    public void saveChange() {
        if (selectedTypeObject != null) {
            // Save the changes to the libelle_type in the typeObjectDAO
            selectedTypeObject.setLibelle(modifyCollectionTextField.getText());
            typeObjectDAO.update(selectedTypeObject);
        }
        showAlert("Success", "Changes saved successfully");

        // Trigger the update of the collection view in the main controller
        if (paneTypeObjetModifyControllerCallback != null) {
            paneTypeObjetModifyControllerCallback.onModificationSceneClosed();
        }
    }

    public void deleteTypeObjet() {
        if (selectedTypeObject != null) {
            // Delete the TypeObject
            if (typeObjectDAO.delete(selectedTypeObject)) {
                showAlert("Success", "TypeObject deleted successfully");
            } else {
                showAlert("Error", "Failed to delete TypeObject");
            }

            // Delete the associated Photo
            int imageId = typeObjectDAO.getImageIdByTypeId(selectedTypeObject.getId());
            if (imageId != -1) {
                Photo photo = new Photo();
                photo.setId(imageId);
                if (photoDAO.delete(photo)) {
                    showAlert("Success", "Associated Photo deleted successfully");
                } else {
                    showAlert("Error", "Failed to delete Associated Photo");
                }
            }

            // Trigger the update of the collection view in the main controller
            if (paneTypeObjetModifyControllerCallback != null) {
                paneTypeObjetModifyControllerCallback.onModificationSceneClosed();
                Stage stage = (Stage) modifyCollectionTextField.getScene().getWindow();
                stage.close();
            }
        } else {
            showAlert("Error", "No TypeObject selected for deletion");
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait().orElse(ButtonType.CANCEL);
    }
}
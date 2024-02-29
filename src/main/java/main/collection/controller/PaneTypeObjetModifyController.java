package main.collection.controller;

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
import main.collection.dao.DAOFactory;
import main.collection.metier.Attribut;
import main.collection.metier.AttributType;
import main.collection.metier.TypeObject;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PaneTypeObjetModifyController implements Initializable {

    @FXML
    public ImageView modifyCollectionImage;
    @FXML
    public TextField modifyCollectionTextField;
    @FXML
    public ScrollPane scrollPane;
    private TypeObject selectedTypeObject;
    private ModificationSceneCallBack paneTypeObjetModifyControllerCallback;
    private static PaneTypeObjetModifyController instance;

    @FXML
    private ListView<Attribut> attributListView;
    @FXML
    private CheckBox sortingCheckBox;
    @FXML
    private Button modifyAttributButton;
    private List<Attribut> initialOrder;

    public static PaneTypeObjetModifyController getInstance() {
        if (instance == null) {
            instance = new PaneTypeObjetModifyController();
        }
        return instance;
    }

    public void setCallback(ModificationSceneCallBack callback) {
        this.paneTypeObjetModifyControllerCallback = callback;
    }

    public void loadDatafromDatabase() {
        try {
            scrollPane.setContent(null); // Clear existing cards
            List<TypeObject> typeObjects = DAOFactory.getTypeObjectDAO().getAll();
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
        String imagePath = DAOFactory.getTypeObjectDAO().getImageByTypeId(typeObject.getId());
            if (imagePath != null) {
                modifyCollectionImage.setImage(new Image(imagePath));
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
            } catch (Exception exception) {
                showAlert("Error", "Failed to load the selected image");
                exception.printStackTrace();
            }
        }
    }


    //load list attributes linked with type of object
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDatafromDatabase();
        sortingCheckBox.setOnAction(this::handleSortingCheckBox);
    }

    private void loadAttributsForTypeObject(TypeObject typeObject) {
        attributListView.getItems().clear();
        List<String> attributs = DAOFactory.getTypeObjectDAO().getAttributsByTypeObjectId(typeObject);
        //save the initial order
        initialOrder = attributs.stream().map(Attribut::new).collect(Collectors.toList());
        //sort attributs alphabetically
        //attributs.sort(Comparator.naturalOrder());
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

    //Sorting the attribut
    public void handleSortingCheckBox(ActionEvent actionEvent) {
        boolean isChecked = sortingCheckBox.isSelected();

        // Sort the items based on the checkbox state
        if (isChecked) {
            // Sort in ascending order
            attributListView.getItems().sort(Comparator.comparing(Attribut::getLibelle));
        } else {
            attributListView.getItems().setAll(initialOrder);

        }
    }

    //modify the list of attribut
    public void modifyAttribut() {
        //get the selected items from the ListView
        Attribut selectedAttribut = attributListView.getSelectionModel().getSelectedItem();
        //Check if an items is selected
        if (selectedAttribut != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/collection/PaneAttributModify.fxml"));
                Parent homeRoot = loader.load();
                //access controller
                PaneAttributModifyController paneAttributModify = loader.getController();
                paneAttributModify.setParentController(this);
                paneAttributModify.setOriginalLabel(selectedAttribut.getLibelle());
                Stage modifyStage = new Stage();
                modifyStage.setTitle("Modify Attribute Scene");
                modifyStage.setScene(new Scene(homeRoot));
                modifyStage.show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public Attribut getSelectedAttribut() {
        return attributListView.getSelectionModel().getSelectedItem();
    }

    public void updateAttributLabel(String originalLabel, String modifiedLabel) {
        for (Attribut attribut : attributListView.getItems()) {
            if (attribut.getLibelle().equals(originalLabel)) {
                attribut.setLibelle(modifiedLabel);
                DAOFactory.getAttributDAO().update(attribut);
                attributListView.refresh();
                break;
            }
        }
    }

    public void deleteSelectedAttribut() {
        Attribut selectedAttribut = attributListView.getSelectionModel().getSelectedItem();

        if (selectedAttribut != null) {
            boolean confirmed = showAlert("Confirmation Dialog", "Are you sure you want to delete the selected attribute?");
            if (confirmed) {
                attributListView.getItems().remove(selectedAttribut);
                TypeObject selectedTypeObject = this.selectedTypeObject;
                if (selectedTypeObject != null) {
                    AttributType attributTypeToDelete = new AttributType(selectedTypeObject.getId(), selectedAttribut.getId());
                    boolean deletionSuccess = DAOFactory.getAttributTypeDAO().delete(attributTypeToDelete);
                    if (deletionSuccess) {
                        System.out.println("AttributType deleted successfully from database.");
                    } else {
                        System.out.println("Failed to delete AttributType from database.");
                    }
                }
            }
        } else {
            showAlert("Error", "No attribute selected for deletion");
        }
    }


    public void addNewAttributeToType(Attribut newAttribut) {
        attributListView.getItems().add(newAttribut);
        TypeObject selectedTypeObject = this.selectedTypeObject;

        if (selectedTypeObject != null) {
            AttributType newAttributType = new AttributType(selectedTypeObject.getId(), newAttribut.getId());
            boolean inserted = DAOFactory.getAttributTypeDAO().insert(newAttributType);

            if (!inserted) {
                System.out.println("Failed to insert AttributType record into the database.");
            }
        } else {
            System.out.println("No TypeObject selected.");
        }
    }

    public void openAjouterAttribut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/collection/PaneAttributAjouter.fxml"));
            Parent homeRoot = loader.load();
            //access controller
            PaneAttributAjouterController paneAttributAjouter = loader.getController();
            paneAttributAjouter.setParentController(this);
            Stage ajouterStage = new Stage();
            ajouterStage.setTitle("Ajouter Attribute Scene");
            ajouterStage.setScene(new Scene(homeRoot));
            ajouterStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //button with function: save, delete and cancel
    public void saveChange() {
        if (selectedTypeObject != null) {
            // Save the changes to the libelle_type in the typeObjectDAO
            selectedTypeObject.setLibelle(modifyCollectionTextField.getText());
            // Check if modifyCollectionImage has a non-null image
            if (modifyCollectionImage.getImage() != null) {
                selectedTypeObject.setImagePath(modifyCollectionImage.getImage().getUrl());
            }
            DAOFactory.getTypeObjectDAO().update(selectedTypeObject);
        }
        showAlert("Success", "Changes saved successfully");

        // Reload data after saving changes
        loadDatafromDatabase();
        // Trigger the update of the collection view in the main controller
        if (paneTypeObjetModifyControllerCallback != null) {
            paneTypeObjetModifyControllerCallback.onModificationSceneClosed();
        }
    }

    public ListView<Attribut> getAttributListView() {
        return attributListView;
    }

    public void deleteTypeObjet() {
        if (selectedTypeObject != null) {
            if (DAOFactory.getTypeObjectDAO().delete(selectedTypeObject)) {
                showAlert("Success", "TypeObject deleted successfully");
            } else {
                showAlert("Error", "Failed to delete TypeObject");
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

    private boolean showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Show the alert and wait for the user's response
        Optional<ButtonType> result = alert.showAndWait();

        // Return true if the user clicked OK, false otherwise
        return result.isPresent() && result.get() == ButtonType.OK;
    }


}
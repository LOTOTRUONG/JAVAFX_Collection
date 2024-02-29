package main.collection.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.collection.dao.TypeObjectDAO;
import main.collection.metier.TypeObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PaneApplicationController implements Initializable, ModificationSceneCallBack {
    @FXML
    private AnchorPane openCollectionPane, openHomePane;
    private List<TypeObject> recentlyAdded;
    private PaneTypeObjetNewController paneNewTypeObjetController;
    private PaneTypeObjetModifyController paneTypeObjetModifyController;
    @FXML
    private VBox mainVbox;
    private final TypeObjectDAO typeObjectDAO;
    private ModificationSceneCallBack paneTypeObjetModifyControllerCallback;


    public PaneApplicationController() {
        this.typeObjectDAO = new TypeObjectDAO();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded = new ArrayList<>();
        setOpenCollectionPane();
    }

    public void setCallback(ModificationSceneCallBack callback) {
        this.paneTypeObjetModifyControllerCallback = callback;
    }
    @FXML
    private void setOpenHomePane() {
        openHomePane.setVisible(true);
        openCollectionPane.setVisible(false);

    }
    @FXML
    private void setOpenCollectionPane() {
        openHomePane.setVisible(false);
        openCollectionPane.setVisible(true);
        showRecentAddedCollection();

    }

    public void showRecentAddedCollection() {
        try {
            mainVbox.getChildren().clear(); // Clear existing cards
            List<TypeObject> typeObjects = typeObjectDAO.getAll();

            HBox currentRow = new HBox();
            currentRow.setAlignment(Pos.CENTER);
            currentRow.setSpacing(50); // Spacing between VBox nodes

            for (TypeObject typeObject : typeObjects) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/collection/CardTypeObjet.fxml"));
                VBox cardbox = fxmlLoader.load();
                CardTypeObjetController cardTypeObjetController = fxmlLoader.getController();
                cardTypeObjetController.setData(typeObject);
                cardbox.setOnMouseClicked(event -> handleVBoxClick(typeObject));
                currentRow.getChildren().add(cardbox);

                // If reach the maximum number of VBoxes per row, start a new row
                if (currentRow.getChildren().size() == 4) {
                    mainVbox.getChildren().add(currentRow);
                    currentRow = new HBox();
                    currentRow.setAlignment(Pos.CENTER);
                    currentRow.setSpacing(30);
                }
            }
            // Add any remaining VBoxes to the flowPane
            if (!currentRow.getChildren().isEmpty()) {
                mainVbox.getChildren().add(currentRow);
            }
            mainVbox.setSpacing(30);
            mainVbox.setAlignment(Pos.CENTER);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void handleVBoxClick(TypeObject typeObject) {
        if ("pièce".equalsIgnoreCase(typeObject.getLibelle()) || "pièces".equalsIgnoreCase(typeObject.getLibelle()) || "piece".equalsIgnoreCase(typeObject.getLibelle()) || "pieces".equalsIgnoreCase(typeObject.getLibelle())) {
            try {
                Parent homeRoot = (new FXMLLoader(getClass().getResource("/typeobjet/coin/CoinCollection.fxml"))).load();
                Stage homeStage = new Stage();
                homeStage.setTitle("Piece Scene");
                homeStage.setScene(new Scene(homeRoot));
                homeStage.show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if ("billet".equalsIgnoreCase(typeObject.getLibelle()) || "billets".equalsIgnoreCase(typeObject.getLibelle())) {
            try {
                Parent homeRoot = (new FXMLLoader(getClass().getResource("/typeobjet/book/BookCollection.fxml"))).load();
                Stage homeStage = new Stage();
                homeStage.setTitle("Livre Scene");
                homeStage.setScene(new Scene(homeRoot));
                homeStage.show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @FXML
    public void enterNewTypePane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/collection/PaneTypeObjetNew.fxml"));
            Parent homeRoot = loader.load();
            Stage homeStage = new Stage();
            homeStage.setTitle("New Type Scene");
            homeStage.setScene(new Scene(homeRoot));
            paneNewTypeObjetController = loader.getController();
            paneNewTypeObjetController.setCallback(this);
            homeStage.showAndWait();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @FXML
    public void enterModifyScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/collection/PaneTypeObjetModify.fxml"));
            Parent homeRoot = loader.load();
            Stage homeStage = new Stage();
            homeStage.setTitle("Modify Type Scene");
            homeStage.setScene(new Scene(homeRoot));
            paneTypeObjetModifyController = loader.getController();
            paneTypeObjetModifyController.setCallback(this); // Set the callback
            homeStage.showAndWait();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void exitApplication(MouseEvent actionEvent) {
        Node sourcenode = (Node) actionEvent.getSource();
        Scene scene = sourcenode.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();

    }

    public void onModificationSceneClosed() {
        showRecentAddedCollection();
    }
}

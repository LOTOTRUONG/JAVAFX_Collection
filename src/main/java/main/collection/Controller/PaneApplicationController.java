package main.collection.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.collection.Metier.TypeObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PaneApplicationController implements Initializable {
    @FXML
    private AnchorPane openCollectionPane, openHomePane;
    @FXML
    private HBox cardLayout;
    private List<TypeObject> recentlyAdded;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOpenHomePane();
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

    @FXML
    public void enterCollectionLivres() {
        try {
            Parent homeRoot = (new FXMLLoader(getClass().getResource("/TypeCollection/Book/BookCollection.fxml"))).load();
            Stage homeStage = new Stage();
            homeStage.setTitle("Book Scene");
            homeStage.setScene(new Scene(homeRoot));
            homeStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @FXML
    public void enterCollectionCoin() {
        try {
            Parent homeRoot = (new FXMLLoader(getClass().getResource("/TypeCollection/Coin/CoinCollection.fxml"))).load();
            Stage homeStage = new Stage();
            homeStage.setTitle("Coin Scene");
            homeStage.setScene(new Scene(homeRoot));
            homeStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void enterPersonalPane() {
        try {
            Parent homeRoot = (new FXMLLoader(getClass().getResource("/main/collection/PaneNewCollection.fxml"))).load();
            Stage homeStage = new Stage();
            homeStage.setTitle("New Collection Scene");
            homeStage.setScene(new Scene(homeRoot));
            homeStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void showRecentAddedCollection() {
        recentlyAdded = getRecentlyAdded();
        cardLayout.getChildren().clear();
        recentlyAdded = new ArrayList<>(getRecentlyAdded());
        try {
            cardLayout.getChildren().clear(); // Clear existing cards
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/collection/CardCollection.fxml"));
                VBox cardbox = fxmlLoader.load();
                CardCollectionController cardCollectionController = fxmlLoader.getController();
                cardCollectionController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(cardbox);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private List<TypeObject> getRecentlyAdded() {
        List<TypeObject> listTypeObjet = new ArrayList<>();
        TypeObject typeObject = new TypeObject();
        typeObject.setLibelle("Billet");
        typeObject.setImageSrc("/TypeCollection/Book/example/02.the warren.jpg");
        listTypeObjet.add(typeObject);
        return listTypeObjet;
    }

}

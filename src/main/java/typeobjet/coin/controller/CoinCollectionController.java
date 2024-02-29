package typeobjet.coin.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CoinCollectionController implements Initializable {
    @FXML
    private ScrollPane iconViewPane;

    @FXML
    private Pane tableViewPane;
    @FXML
    private Pane openInforPane;
    @FXML
    private Pane openMapPane;
    @FXML
    private Pane openStatisticPane;
    @FXML
    private VBox inforLayout;
    @FXML
    private TreeTableColumn<?, ?> collectionTable;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTableViewPane();
        openInforPane();

    }

    @FXML
    public void showTableViewPane() {
        tableViewPane.setVisible(true);
        iconViewPane.setVisible(false);
    }

    @FXML
    public void showIconViewPane() {
        tableViewPane.setVisible(false);
        iconViewPane.setVisible(true);
    }

    @FXML
    public void addNewCoin() {
        try {
            Parent homeRoot = (new FXMLLoader(getClass().getResource("/typeobjet/coin/Coin.fxml"))).load();
            Stage homeStage = new Stage();
            homeStage.setTitle("New Coin Scene");
            homeStage.setScene(new Scene(homeRoot));
            homeStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @FXML
    public void addNewCollection() {
        try {
            Parent homeRoot = (new FXMLLoader(getClass().getResource("/typeobjet/coin/NewCollection.fxml"))).load();
            Stage homeStage = new Stage();
            homeStage.setTitle("New Collection Scene");
            homeStage.setScene(new Scene(homeRoot));
            homeStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


    @FXML
    public void openInforPane() {
        openInforPane.setVisible(true);
        openStatisticPane.setVisible(false);
        openMapPane.setVisible(false);
    }

    @FXML
    public void openStatisticPane() {
        openInforPane.setVisible(false);
        openStatisticPane.setVisible(true);
        openMapPane.setVisible(false);
    }

    @FXML
    public void openMapPane() {
        openInforPane.setVisible(false);
        openStatisticPane.setVisible(false);
        openMapPane.setVisible(true);
    }
}

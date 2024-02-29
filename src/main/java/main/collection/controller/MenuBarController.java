package main.collection.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBarController {
    public MenuBar menuBar;
    @FXML
    private MenuItem ReturnMain;
    @FXML
    private AnchorPane mainPane;


    public void setMenuBar(Stage stage, Scene scene) {
        menuBar = (MenuBar) scene.lookup("#mainPane");

    }

    @FXML
    public void openArticleScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sdbm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Article Scene");
            stage.show();
        } catch (IOException E) {
            showErrorAlert("Error", "Unable to load Article Scene");
        }
    }

    @FXML
    public void openVendreScene() {
        try {
            FXMLLoader vendreloader = new FXMLLoader(getClass().getResource("vendre.fxml"));
            Parent vendreroot = vendreloader.load();
            Stage vendrestage = new Stage();
            vendrestage.setScene(new Scene(vendreroot));
            vendrestage.setTitle("Vendre Scene");
            vendrestage.show();
        } catch (IOException E) {
            showErrorAlert("Error", "Unable to load Vendre Scene");
        }
    }


    @FXML
    public void exitApplication() {
        Stage stage = getStage();
        if (stage != null) {
            stage.close();
        } else {
            // Handle the case where the stage reference is not available
            showErrorAlert("Unable to exit application", "The main stage reference is not available.");
        }
    }

    public void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Stage getStage() {
        return (Stage) mainPane.getScene().getWindow();
    }
}

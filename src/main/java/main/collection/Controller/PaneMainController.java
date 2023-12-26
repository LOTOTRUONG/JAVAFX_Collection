package main.collection.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.collection.CollectionApplication;

public class PaneMainController {
    @FXML
    private Button loginButton, registerButton;

    public PaneMainController() {
    }

    @FXML
    public void enterLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CollectionApplication.class.getResource("/LoginAndRegister/LoginPane.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Stage stage = new Stage();
            stage.setTitle("Login Scence!");
            stage.setScene(scene);
            stage.show();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void enterRegister() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CollectionApplication.class.getResource("/LoginAndRegister/RegisterPane.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 700);
            Stage stage = new Stage();
            stage.setTitle("Register Scence!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

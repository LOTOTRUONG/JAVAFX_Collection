package LoginAndRegister.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.collection.DAO.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class RegisterController {
    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel, confirmPasswordLabel;
    @FXML
    private PasswordField setPasswordField, confirmPasswordField;
    @FXML
    private TextField nomTextField, prenomTextField, usernomTextField, emailTextField;


    public void registerButtonAction(ActionEvent actionEvent) {
        if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
            registerUser();
            confirmPasswordLabel.setText("Matching password");
        } else {
            confirmPasswordLabel.setText("Password does not match");
        }
    }

    public void closeButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void registerUser() {
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = DatabaseConnection.getConnection();
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        String username = usernomTextField.getText();
        String email = emailTextField.getText();
        String password = setPasswordField.getText();

        String insertFields = "INSERT INTO USER_ACCOUNT (nom, prenom, username,email,password) VALUES ('";
        String insertValues = nom + "','" + prenom + "','" + username + "','" + email + "'," + password + "')";
        String insertToRegister = insertFields + insertValues;
        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("User has been registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}

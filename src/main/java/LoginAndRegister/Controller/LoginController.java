package LoginAndRegister.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.collection.dao.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameTextField, enterPasswordField;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Circle brandingImageCircle;
    @FXML
    private AnchorPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //File brandingFile = new File("/logo/logo.png");
        //Image brandingImage = new Image(brandingFile.toURI().toString());
        //brandingImageCircle.setFill(new ImagePattern(brandingImage));
    }


    public void validateLogin() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectionDB = DatabaseConnection.getConnection();
        String email = usernameTextField.getText();
        String password = enterPasswordField.getText();
        String verifyLogin = "Select email, password from users where email = '" + email + "'";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);
            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");
                if (password.equals(dbPassword)) {
                    loginMessageLabel.setText("Congratulation! Login successfully");
                    Stage stage = (Stage) mainPane.getScene().getWindow();
                    stage.close();
                    openApplicationCollectionScene();
                } else {
                    loginMessageLabel.setText("Invalid login. Please try again");
                }
            } else {
                loginMessageLabel.setText("Invalid login. Please try again");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void openApplicationCollectionScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main/collection/PaneApplication.fxml"));
            Stage applicationCollectionStage = new Stage();
            applicationCollectionStage.setScene(new Scene(root, 1280, 720));
            applicationCollectionStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void loginButtonOnAction(ActionEvent actionEvent) {
        if (!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void createAccountForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("RegisterPane.fxml"));
            Stage resgisterStage = new Stage();
            resgisterStage.initStyle(StageStyle.UNDECORATED);
            resgisterStage.setScene(new Scene(root, 600, 600));
            resgisterStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
            exception.getCause();
        }
    }
}

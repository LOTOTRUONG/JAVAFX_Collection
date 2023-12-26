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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.collection.DAO.DatabaseConnection;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //File brandingFile = new File("/logo/logo.png");
        //Image brandingImage = new Image(brandingFile.toURI().toString());
        //brandingImageCircle.setFill(new ImagePattern(brandingImage));
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

    public void validateLogin() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectionDB = DatabaseConnection.getConnection();
        String verifyLogin = "Select count(1) from USER_ACCOUNT where username = '" + usernameTextField.getText() + "'";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);
            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    loginMessageLabel.setText("Congratulation");
                    // createAccountForm();
                } else {
                    loginMessageLabel.setText("Invalid login. Please try again");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
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

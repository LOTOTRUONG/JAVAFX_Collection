package main.collection.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.collection.DAO.AttributDAO;
import main.collection.Metier.Attribut;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaneAttributAjouter implements Initializable {
    @FXML
    private TextField attributTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<Attribut> attributComboBox;
    @FXML
    private PaneTypeObjetModifyController parentController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attributComboBox.setItems(FXCollections.observableList(getExistingAttributes()));
    }

    public void setParentController(PaneTypeObjetModifyController parentController) {
        this.parentController = parentController;
    }

    public void addNewAttribut(ActionEvent actionEvent) {
        Attribut selectedAttribute = attributComboBox.getValue();
        String newAttributeName = attributTextField.getText().trim();
        if (selectedAttribute != null) {
            parentController.addNewAttribute(selectedAttribute);
        } else if (!newAttributeName.isEmpty()) {
            Attribut newAttribut = new Attribut(newAttributeName);
            parentController.addNewAttribute(newAttribut);
        }
        closeScene();
    }

    private List<Attribut> getExistingAttributes() {
        AttributDAO attributDAO = new AttributDAO();

        return attributDAO.getAll();
    }

    public void setCancelButton(ActionEvent actionEvent) {
        closeScene();
    }

    private void closeScene() {
        Stage stage = (Stage) attributComboBox.getScene().getWindow();
        stage.close();
    }
}

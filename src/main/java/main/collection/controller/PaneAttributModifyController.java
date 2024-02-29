package main.collection.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.collection.dao.AttributDAO;
import main.collection.metier.Attribut;

public class PaneAttributModifyController {
    @FXML
    private TextField modifiedAttributTextField;
    @FXML
    private PaneTypeObjetModifyController parentController;

    private String originalLabel;

    public void setOriginalLabel(String originalLabel) {
        this.originalLabel = originalLabel;
        modifiedAttributTextField.setText(originalLabel);
    }

    public void setParentController(PaneTypeObjetModifyController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void saveModifiedAttribut() {
        // Get the modified label
        String modifiedLabel = modifiedAttributTextField.getText();

        //Call the updateAtributionLabel method in the parent controller
        if (parentController != null) {
            parentController.updateAttributLabel(originalLabel, modifiedLabel);

            Attribut selectedAttribut = parentController.getSelectedAttribut();
            if (selectedAttribut != null) {
                //save the modified attribut to the database
                AttributDAO attributDAO = new AttributDAO();
                selectedAttribut.setLibelle(modifiedLabel);
                attributDAO.update(selectedAttribut);
            }
        }

        // Close the modification scene
        Stage stage = (Stage) modifiedAttributTextField.getScene().getWindow();
        stage.close();
    }

}



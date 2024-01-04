package main.collection.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.collection.DAO.TypeObjectDAO;
import main.collection.Metier.TypeObject;

public class CardTypeObjetController {
    @FXML
    private ImageView iconCollectionImage;

    @FXML
    private Label nameCollectionLabel;

    public void setData(TypeObject typeObject) {
        TypeObjectDAO typeObjectDAO = new TypeObjectDAO();
        String imagePath = typeObjectDAO.getImageByTypeId(typeObject.getId());
            if (imagePath != null) {
                Image image = new Image(imagePath);
                iconCollectionImage.setImage(image);
            }
        nameCollectionLabel.setText(typeObject.getLibelle());

    }

}

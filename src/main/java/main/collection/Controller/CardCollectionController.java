package main.collection.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.collection.Metier.TypeObject;

public class CardCollectionController {
    @FXML
    private ImageView iconCollectionImage;

    @FXML
    private Label nameCollectionLabel;

    public void setData(TypeObject typeObject) {
        Image image = new Image(getClass().getResourceAsStream(typeObject.getImageSrc()));
        iconCollectionImage.setImage(image);
        nameCollectionLabel.setText(typeObject.getLibelle());

    }

}

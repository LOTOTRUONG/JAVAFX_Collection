package TypeCollection.Coin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.collection.CollectionApplication;

import java.io.IOException;

public class TEST extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CollectionApplication.class.getResource("/TypeCollection/Coin/CoinCollection.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("TEST!");
        stage.setScene(scene);
        stage.show();
    }
}

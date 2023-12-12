package TypeCollection.Book;

import TypeCollection.Book.Model.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookCollectionController implements Initializable {
    @FXML
    private HBox cardLayout;
    private List<Book> recentlyAdded;

    @FXML
    private ScrollPane iconViewPane;

    @FXML
    private Pane tableViewPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTableViewPane();
    }

    @FXML
    public void showTableViewPane() {
        tableViewPane.setVisible(true);
        iconViewPane.setVisible(false);
    }

    @FXML
    public void showIconViewPane() {
        tableViewPane.setVisible(false);
        iconViewPane.setVisible(true);
        recentlyAdded = new ArrayList<>(recentlyAdded());
        try {
            cardLayout.getChildren().clear(); // Clear existing cards
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/TypeCollection/Book/Card.fxml"));
                HBox cardbox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(cardbox);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private List<Book> recentlyAdded() {
        List<Book> ls = new ArrayList<>();
        Book book = new Book();
        book.setName("RICH DAD\nPOOR DAD");
        book.setImageSrc("/TypeCollection/Book/example/01.rich dad.jpg");
        book.setAuthor("Robert T.Kioyasaki");
        ls.add(book);

        book = new Book();
        book.setName("THE WARREN\nBUFFET WAY");
        book.setImageSrc("/TypeCollection/Book/example/02.the warren.jpg");
        book.setAuthor("Robert G.Hagstorm");
        ls.add(book);
        return ls;
    }
}

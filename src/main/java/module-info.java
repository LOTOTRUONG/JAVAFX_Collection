module main.collection {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires lombok;
    requires com.microsoft.sqlserver.jdbc;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.controlsfx.controls;
    requires GNAvatarView;

    opens main.collection to javafx.fxml;
    exports main.collection;
    exports LoginAndRegister.Controller;
    opens LoginAndRegister.Controller to javafx.fxml;
    exports main.collection.Controller;
    opens main.collection.Controller to javafx.fxml;
    exports TypeObjet.Book;
    opens TypeObjet.Book to javafx.fxml;
    exports TypeObjet.Coin;
    opens TypeObjet.Coin to javafx.fxml;
    exports main.collection.DAO;
    opens main.collection.DAO to javafx.fxml;

}
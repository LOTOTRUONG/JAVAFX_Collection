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
    exports main.collection.controller;
    opens main.collection.controller to javafx.fxml;
    exports typeobjet.book;
    opens typeobjet.book to javafx.fxml;
    exports typeobjet.coin;
    opens typeobjet.coin to javafx.fxml;
    exports main.collection.dao;
    opens main.collection.dao to javafx.fxml;
    exports typeobjet.coin.controller;
    opens typeobjet.coin.controller to javafx.fxml;

}
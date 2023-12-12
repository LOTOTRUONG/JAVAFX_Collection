package main.collection;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TEST extends Application {

    private final ObservableList<ObjectItem> data = FXCollections.observableArrayList();
    private TableView<ObjectItem> tableView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Collection Management System");

        // Initialize data (you would load this from your CSV or database)
        data.add(new ObjectItem(1, "Item 1", 25, 2005, "Description of Item 1", "Disponible", 8, "France", "Tégestophilie"));
        data.add(new ObjectItem(2, "Item 2", 30, 2010, "Description of Item 2", "Réservé", 9, "Italie", "Tyrosémiophilie"));

        // Table
        tableView = new TableView<>();
        tableView.setItems(data);

        TableColumn<ObjectItem, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<ObjectItem, String> nameColumn = new TableColumn<>("Nom de l'objet");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<ObjectItem, Double> priceColumn = new TableColumn<>("Prix");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        TableColumn<ObjectItem, Integer> yearColumn = new TableColumn<>("Année de fabrication");
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());

        TableColumn<ObjectItem, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        TableColumn<ObjectItem, String> availabilityColumn = new TableColumn<>("Disponibilité");
        availabilityColumn.setCellValueFactory(cellData -> cellData.getValue().availabilityProperty());

        TableColumn<ObjectItem, Integer> conditionColumn = new TableColumn<>("État de conservation");
        conditionColumn.setCellValueFactory(cellData -> cellData.getValue().conditionProperty().asObject());

        TableColumn<ObjectItem, String> countryColumn = new TableColumn<>("Pays");
        countryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());

        TableColumn<ObjectItem, String> categoryColumn = new TableColumn<>("Catégorie");
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        tableView.getColumns().addAll(idColumn, nameColumn, priceColumn, yearColumn, descriptionColumn,
                availabilityColumn, conditionColumn, countryColumn, categoryColumn);

        // Buttons
        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> handleAddButton());

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(e -> handleDeleteButton());

        // Layout
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView, addButton, deleteButton);

        Scene scene = new Scene(vBox, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void handleAddButton() {
        // Implement logic to add a new item
    }

    private void handleDeleteButton() {
        // Implement logic to delete selected item
    }

    public static class ObjectItem {
        private final int id;
        private final String name;
        private final double price;
        private final int year;
        private final String description;
        private final String availability;
        private final int condition;
        private final String country;
        private final String category;

        public ObjectItem(int id, String name, double price, int year, String description,
                          String availability, int condition, String country, String category) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.year = year;
            this.description = description;
            this.availability = availability;
            this.condition = condition;
            this.country = country;
            this.category = category;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getYear() {
            return year;
        }

        public String getDescription() {
            return description;
        }

        public String getAvailability() {
            return availability;
        }

        public int getCondition() {
            return condition;
        }

        public String getCountry() {
            return country;
        }

        public String getCategory() {
            return category;
        }

        // JavaFX Properties (used for TableView)
        public javafx.beans.property.IntegerProperty idProperty() {
            return new javafx.beans.property.SimpleIntegerProperty(id);
        }

        public javafx.beans.property.StringProperty nameProperty() {
            return new javafx.beans.property.SimpleStringProperty(name);
        }

        public javafx.beans.property.DoubleProperty priceProperty() {
            return new javafx.beans.property.SimpleDoubleProperty(price);
        }

        public javafx.beans.property.IntegerProperty yearProperty() {
            return new javafx.beans.property.SimpleIntegerProperty(year);
        }

        public javafx.beans.property.StringProperty descriptionProperty() {
            return new javafx.beans.property.SimpleStringProperty(description);
        }

        public javafx.beans.property.StringProperty availabilityProperty() {
            return new javafx.beans.property.SimpleStringProperty(availability);
        }

        public javafx.beans.property.IntegerProperty conditionProperty() {
            return new javafx.beans.property.SimpleIntegerProperty(condition);
        }

        public javafx.beans.property.StringProperty countryProperty() {
            return new javafx.beans.property.SimpleStringProperty(country);
        }

        public javafx.beans.property.StringProperty categoryProperty() {
            return new javafx.beans.property.SimpleStringProperty(category);
        }
    }
}



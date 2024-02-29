package main.collection.metier;

import javafx.beans.property.*;


public class Object {
    private final int id;
    private final String libelle;
    private final double prix;
    private final int annee;
    private final String description;
    private final String etat;
    private final String pays;
    private final String categorie;

    public Object(int id, String libelle, double prix, int annee, String description, String etat, String pays, String categorie) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
        this.annee = annee;
        this.description = description;
        this.etat = etat;
        this.pays = pays;
        this.categorie = categorie;
    }

    public Integer getId() {
        return id;
    }

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public String getLibelle() {
        return libelle;
    }

    public StringProperty libelleProperty() {
        return new SimpleStringProperty(libelle);
    }

    public Double getPrix() {
        return prix;
    }

    public DoubleProperty prixProperty() {
        return new SimpleDoubleProperty(prix);
    }

    public Integer getAnnee() {
        return annee;
    }

    public IntegerProperty anneeProperty() {
        return new SimpleIntegerProperty(annee);
    }

    public String getDescription() {
        return description;
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(description);
    }

    public String getEtat() {
        return etat;
    }

    public StringProperty etatProperty() {
        return new SimpleStringProperty(etat);
    }

    public String getPays() {
        return pays;
    }

    public StringProperty paysProperty() {
        return new SimpleStringProperty(pays);
    }

    public String getCategorie() {
        return categorie;
    }

    public StringProperty categorieProperty() {
        return new SimpleStringProperty(categorie);
    }

}

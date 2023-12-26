package main.collection.Metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Attribut {

    private IntegerProperty idAttribut;

    private StringProperty libelle_attribut;

    public Attribut(int idAttribut, String libelle_attribut){

        this.idAttribut = new SimpleIntegerProperty(idAttribut);
        this.libelle_attribut = new SimpleStringProperty(libelle_attribut);

    }

    /* NO EMPTY CONSTRUCTOR
     */

    public IntegerProperty getIdAttribut(){
        return idAttribut;
    }

    public StringProperty getLibelleattribut(){
        return libelle_attribut;
    }

    public void setLibelleAttribut(String libelle_attribut){
        this.libelle_attribut.set(libelle_attribut);
    }




}

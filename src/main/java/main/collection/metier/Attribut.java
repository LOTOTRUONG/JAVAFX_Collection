package main.collection.metier;


public class Attribut {

    private Integer id;
    private String libelle;

    public Attribut(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Attribut(String libelle) {
        this.libelle = libelle;
    }

    public Attribut() {

    }


    public Integer getId() {
        return id != null ? id : 0;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {

        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    @Override
    public String toString() {
        return libelle; // Return the attribute name for meaningful representation
    }

}

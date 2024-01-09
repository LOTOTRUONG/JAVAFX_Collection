package main.collection.Metier;


public class Attribut {

    private Integer id;
    private String libelle;
    private TypeObject typeObject;

    public Attribut(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Attribut(String libelle) {
        this.libelle = libelle;
    }

    public Attribut() {

    }


    public Attribut(String libelle, TypeObject typeObject) {
        this.libelle = libelle;
        this.typeObject = typeObject;
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

    public TypeObject getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(TypeObject typeObject) {
        this.typeObject = typeObject;
    }

    @Override
    public String toString() {
        return libelle; // Return the attribute name for meaningful representation
    }

}

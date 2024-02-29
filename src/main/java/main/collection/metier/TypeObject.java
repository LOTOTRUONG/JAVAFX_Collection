package main.collection.metier;

public class TypeObject {

    private Integer id;
    private String libelle;
    private String imagePath;



    public TypeObject(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public TypeObject(Integer id, String libelle, String imagePath) {
        this.id = id;
        this.libelle = libelle;
        this.imagePath = imagePath;
    }

    public TypeObject(String libelle) {
        this.libelle = libelle;
    }

    public TypeObject() {

    }

    public Integer getId() {
        return id;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "type_objet{" +
                "id=" + id +
                ", libelle_type=" + libelle +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}

package main.collection.Metier;

public class TypeObject {

    private Integer id;
    private String libelle;
    private String imageSrc;


    public TypeObject(Integer id, String libelle) {
        this.id = id;
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

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}

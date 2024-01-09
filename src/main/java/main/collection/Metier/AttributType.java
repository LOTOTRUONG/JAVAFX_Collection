package main.collection.Metier;

public class AttributType {
    private int idType;
    private int idAttributType;
    private boolean commentaire;
    private boolean tableDeDonnee;
    private int idUnite;
    private int idAttribut;

    public AttributType(int idType, int idAttributType, boolean commentaire, boolean tableDeDonnee, int idUnite, int idAttribut) {
        this.idType = idType;
        this.idAttributType = idAttributType;
        this.commentaire = commentaire;
        this.tableDeDonnee = tableDeDonnee;
        this.idUnite = idUnite;
        this.idAttribut = idAttribut;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdAttributType() {
        return idAttributType;
    }

    public void setIdAttributType(int idAttributType) {
        this.idAttributType = idAttributType;
    }

    public boolean isCommentaire() {
        return commentaire;
    }

    public void setCommentaire(boolean commentaire) {
        this.commentaire = commentaire;
    }

    public boolean isTableDeDonnee() {
        return tableDeDonnee;
    }

    public void setTableDeDonnee(boolean tableDeDonnee) {
        this.tableDeDonnee = tableDeDonnee;
    }

    public int getIdUnite() {
        return idUnite;
    }

    public void setIdUnite(int idUnite) {
        this.idUnite = idUnite;
    }

    public int getIdAttribut() {
        return idAttribut;
    }

    public void setIdAttribut(int idAttribut) {
        this.idAttribut = idAttribut;
    }


}

package main.collection.metier;

import lombok.Getter;
import lombok.Setter;

public class AttributType {
    @Getter
    @Setter
    private TypeObject type;
    @Getter
    @Setter
    private Integer idAttributType;
    @Getter
    @Setter
    private boolean commentaire;
    @Getter
    @Setter
    private boolean tableDeDonnee;
    @Getter
    @Setter
    private Unit unit;
    @Getter
    @Setter
    private Attribut attribut;

    public AttributType() {
        type = new TypeObject();
        idAttributType = 0;
        commentaire = false;
        tableDeDonnee = false;
        unit = new Unit();
        attribut = new Attribut();
    }

    public AttributType(Integer idType, Integer idAttribut) {
        this.type = new TypeObject();
        this.type.setId(idType);

        this.attribut = new Attribut();
        this.attribut.setId(idAttribut);
    }


    public AttributType(TypeObject type, int idAttributType, boolean commentaire, boolean tableDeDonnee, Unit unit, Attribut attribut) {
        this.type = type;
        this.idAttributType = idAttributType;
        this.commentaire = commentaire;
        this.tableDeDonnee = tableDeDonnee;
        this.unit = unit;
        this.attribut = attribut;
    }


    @Override
    public String toString() {
        return attribut.getLibelle();
    }


}

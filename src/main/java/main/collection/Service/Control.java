package main.collection.Service;

import java.util.ArrayList;

public class Control {


    ArrayList <String> arrayList;


    public static ArrayList <String> convertir(ArrayList <String> arrayList){
        ArrayList <String> resultat = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); ++i){
            String nouveau_libelle;
            nouveau_libelle = arrayList.get(i).replaceAll("[^a-zA-Z]", "").toLowerCase();
            resultat.set(i,nouveau_libelle);
        }

        return resultat;
    }

    public static boolean isIn(String element_comparatif, ArrayList<String> compare){
        boolean comparateur = false;
        String traduce;
        traduce = element_comparatif.replaceAll("[^a-zA-Z]", "").toLowerCase();
        for (String cherche_compare : compare) {
            if (cherche_compare.contains(traduce)) {
                comparateur = true;
            }
        }
        return comparateur;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }



}

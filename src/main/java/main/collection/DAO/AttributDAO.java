package main.collection.DAO;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import main.collection.Metier.Attribut;
import main.collection.SDBMconnect.SDBMconnect1;

public class AttributDAO extends DAO<Attribut, AttributSearch, Integer> {

    @Override
    public Attribut getById(Integer integer){
        return null;
    }

    @Override
    public ArrayList<Attribut> getAll(){
        ArrayList<Attribut> liste = new ArrayList<>();
        String sqlRequestg = "SELECT * from ATTRIBUT";
        try (Statement statement = connexion.createStatement()) {
            ResultSet rs = statement.executeQuery(sqlRequest);
            while (rs.next()) {
                liste.add(new Attribut(rs.getInt(1), rs.getString(2), rs.getInt(4),rs.getInt(5)));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public ArrayList<Attribut> getLike(AttributSearch attributSearch) {
        ResultSet rs;
        ArrayList<Attribut> liste = new ArrayList<>();
        /* CREATE VIEW  + PROCEDURE STOCKEE
    }
}

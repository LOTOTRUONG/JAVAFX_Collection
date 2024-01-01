package main.collection.DAO;

import main.collection.Metier.Attribut;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AttributDAO extends DAO<Attribut, Attribut, Integer> {

    @Override
    public Attribut getByID(Integer integer) {
        return null;
    }

    public Attribut getByLibelle(String libelle) {
        String sqlRequest = "Select * from attribut where libelle_attribut = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, libelle);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return new Attribut(resultSet.getInt(1), resultSet.getString(2));
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Attribut> getAll() {
        ArrayList<Attribut> listAttribut = new ArrayList<>();
        String sqlRequest = "Select * from attribut";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                listAttribut.add(new Attribut(resultSet.getInt(1), resultSet.getString(2)));
            }
            resultSet.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return listAttribut;
    }

    @Override
    public ArrayList<Attribut> getLike(Attribut object) {
        return null;
    }

    @Override
    public boolean insert(Attribut object) {
        return false;
    }

    @Override
    public boolean update(Attribut object) {
        return false;
    }

    @Override
    public boolean delete(Attribut object) {
        String deleteRequest = "DELETE FROM attribut WHERE id_attribut = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRequest)) {
            preparedStatement.setInt(1, object.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}

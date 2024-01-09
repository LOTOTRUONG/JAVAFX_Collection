package main.collection.DAO;

import main.collection.Metier.Attribut;
import main.collection.Service.AttributSearch;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AttributDAO extends DAO<Attribut, AttributSearch, Integer> {

    @Override
    public Attribut getByID(Integer id) {
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
    public ArrayList<Attribut> getLike(AttributSearch object) {
        ResultSet rs;
        ArrayList<Attribut> liste = new ArrayList<>();
        // CREATE VIEW  + PROCEDURE STOCKEE
        return null;
    }

    @Override
    public boolean insert(Attribut object) {
        //Check if attribut already exist in the table
        String selectRequest = "SELECT id_attribut FROM attribut WHERE libelle_attribut = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectRequest)) {
            selectStatement.setString(1, object.getLibelle());
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                //if the libelle_attribut is existed
                int existingId = resultSet.getInt("id_attribut");
                object.setId(existingId);
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        //if the libelle_attribut does not exist, insert the new record
        String insertRequest = "INSERT INTO attribut (libelle_attribut) VALUES (?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertRequest, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, object.getLibelle());
            int rowAffected = insertStatement.executeUpdate();
            if (rowAffected > 0) {
                try (ResultSet generateKey = insertStatement.getGeneratedKeys()) {
                    if (generateKey.next()) {
                        int generatedId = generateKey.getInt(1);
                        object.setId(generatedId);
                    }
                }
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Attribut object) {
        String sqlRequest = "update attribut set libelle_attribut = ? WHERE id_attribut = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getLibelle());
            preparedStatement.setInt(2, object.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Attribut object) {
        String deleteQuery = "DELETE FROM attribut WHERE id_attribut = ? " +
                "DBCC CHECKIDENT('attribut', reseed)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, object.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }


}

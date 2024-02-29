package main.collection.dao;

import main.collection.metier.Attribut;
import main.collection.metier.AttributType;
import main.collection.metier.TypeObject;
import main.collection.metier.Unit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AttributTypeDAO extends DAO<AttributType, AttributType, Integer> {
    @Override
    public AttributType getByID(Integer id) {
        String sqlRequest = "Select * from attribut_type where id_attributType = " + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                AttributType attributType = new AttributType();
                Attribut attribut = new Attribut();
                attribut.setId(resultSet.getInt("id_attribut"));
                Unit unit = new Unit();
                unit.setId(resultSet.getInt("id_unit"));
                TypeObject typeObject = new TypeObject();
                typeObject.setId(resultSet.getInt("id_type"));

                attributType.setType(typeObject);
                attributType.setIdAttributType(resultSet.getInt("id_attributType"));
                attributType.setCommentaire(resultSet.getBoolean("commentaire"));
                attributType.setTableDeDonnee(resultSet.getBoolean("table_de_donnee"));
                attributType.setUnit(unit);
                attributType.setAttribut(attribut);
                return attributType;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<AttributType> getAll() {
        ArrayList<AttributType> listAttributType = new ArrayList<>();
        String sqlRequest = "SELECT * FROM attribut_type";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                AttributType attributType = new AttributType();
                attributType.setIdAttributType(resultSet.getInt("id_attributType"));
                attributType.setCommentaire(resultSet.getBoolean("commentaire"));
                attributType.setTableDeDonnee(resultSet.getBoolean("table_de_donnee"));

                TypeObject typeObject = new TypeObject();
                typeObject.setId(resultSet.getInt("id_type"));
                attributType.setType(typeObject);
                Unit unit = new Unit();
                unit.setId(resultSet.getInt("id_unit"));
                attributType.setUnit(unit);
                Attribut attribut = new Attribut();
                attribut.setId(resultSet.getInt("id_attribut"));
                attributType.setAttribut(attribut);

                listAttributType.add(attributType);

            }
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return listAttributType;
    }

    @Override
    public ArrayList<AttributType> getLike(AttributType object) {
        return null;
    }

    @Override
    public boolean insert(AttributType object) {
        String insertQuery = "INSERT INTO attribut_type (id_type, commentaire, table_de_donnee, id_attribut) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, object.getType().getId());
            preparedStatement.setBoolean(2, object.isCommentaire());
            preparedStatement.setBoolean(3, object.isTableDeDonnee());
            preparedStatement.setInt(4, object.getAttribut().getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idAttributType = generatedKeys.getInt(1);
                    object.setIdAttributType(idAttributType);
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(AttributType object) {
        return false;
    }

    @Override
    public boolean delete(AttributType object) {
        String deleteQuery = "DELETE FROM attribut_type WHERE id_type = ? AND id_attribut = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, object.getType().getId());
            preparedStatement.setInt(2, object.getAttribut().getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

}

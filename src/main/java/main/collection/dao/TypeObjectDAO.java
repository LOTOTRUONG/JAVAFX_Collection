package main.collection.dao;

import main.collection.metier.TypeObject;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypeObjectDAO extends DAO<TypeObject, TypeObject, Integer> {


    @Override
    public TypeObject getByID(Integer id) {
        String sqlRequest = "Select * from type_objet where id_type = " + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            if (resultSet.next())
                return new TypeObject(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String getImageByTypeId(Integer id) {
        String sqlRequest = "SELECT imagePath FROM type_objet WHERE id_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("imagePath");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public List<String> getAttributsByTypeObjectId(TypeObject typeObject) {
        List<String> attributs = new ArrayList<>();
        if (typeObject != null) {
            int idType = typeObject.getId(); // Assuming you have a method getId() in your TypeObject class
            String sqlRequest = "SELECT attribut.libelle_attribut FROM type_objet " +
                    "FULL JOIN attribut_type ON attribut_type.id_type = type_objet.id_type " +
                    "FULL JOIN attribut ON attribut.id_attribut = attribut_type.id_attribut " +
                    "WHERE type_objet.id_type = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
                preparedStatement.setInt(1, idType);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    attributs.add(resultSet.getString("libelle_attribut"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return attributs;
    }


    @Override
    public ArrayList<TypeObject> getAll() {
        ArrayList<TypeObject> listTypeObject = new ArrayList<>();
        String sqlRequest = "Select * from type_objet";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                listTypeObject.add(new TypeObject(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            resultSet.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return listTypeObject;
    }

    @Override
    public ArrayList<TypeObject> getLike(TypeObject object) {
        String sqlRequest = "Select *from type_objet where id_type like '%" + object.getLibelle() + "%'";
        ArrayList<TypeObject> listTypeObject = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                listTypeObject.add(new TypeObject(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            resultSet.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return listTypeObject;
    }

    @Override
    public boolean insert(TypeObject typeObject) {
        String insertRequest = "INSERT INTO type_objet (libelle_type, imagePath) VALUES (?, ?) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, typeObject.getLibelle());
            preparedStatement.setString(2, typeObject.getImagePath().replace("/", File.separator));
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        typeObject.setId(generatedKeys.getInt(1));
                        return true;
                    } else {
                        throw new SQLException("Insertion failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean update(TypeObject object) {
        String updateRequest = "UPDATE type_objet SET libelle_type = ?, imagePath = ? WHERE id_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateRequest)) {
            preparedStatement.setString(1, object.getLibelle());
            preparedStatement.setString(2, object.getImagePath().replace("/", File.separator));
            preparedStatement.setInt(3, object.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(TypeObject object) {
        String deleteRequest = "DELETE FROM type_objet WHERE id_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRequest)) {
            preparedStatement.setInt(1, object.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }


    public List<String> deteteAttributsByTypeObjectId(TypeObject typeObject) {
        List<String> deletedAttributs = new ArrayList<>();
        if (typeObject != null) {
            int idType = typeObject.getId(); // Assuming you have a method getId() in your TypeObject class
            String sqlRequest = "SELECT attribut.libelle_attribut FROM type_objet " +
                    "FULL JOIN attribut_type ON attribut_type.id_type = type_objet.id_type " +
                    "FULL JOIN attribut ON attribut.id_attribut = attribut_type.id_attribut " +
                    "WHERE type_objet.id_type = ?";

            String deleteRequest = "DELETE FROM attribut_type Where id_attribut = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
                 PreparedStatement deleteStatement = connection.prepareStatement(deleteRequest)) {
                //Select attribut before deletion
                preparedStatement.setInt(1, idType);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    deletedAttributs.add(resultSet.getString("libelle_attribut"));
                }

                //delete attributs based on the id_type and id_attribut
                deleteStatement.setInt(1, idType);
                deleteStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return deletedAttributs;
    }
}

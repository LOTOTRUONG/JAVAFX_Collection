package main.collection.DAO;

import main.collection.Metier.TypeObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TypeObjectDAO extends DAO<TypeObject, TypeObject, Integer> {


    @Override
    public TypeObject getByID(Integer id) {
        String sqlRequest = "Select id_type, libelle_type from type_objet where id_type = " + id;
        TypeObject typeObject;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            if (resultSet.next()) return new TypeObject(resultSet.getInt(1), resultSet.getString(2));
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


    @Override
    public ArrayList<TypeObject> getAll() {
        ArrayList<TypeObject> listTypeObject = new ArrayList<>();
        String sqlRequest = "Select * from type_objet";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                listTypeObject.add(new TypeObject(resultSet.getInt(1), resultSet.getString(2)));
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
                listTypeObject.add(new TypeObject(resultSet.getInt(1), resultSet.getString(2)));
            }
            resultSet.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return listTypeObject;
    }

    @Override
    public boolean insert(TypeObject typeObject) {
        String insertRequest = "INSERT INTO type_objet (libelle_type) VALUES (?) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertRequest)) {
            preparedStatement.setString(1, typeObject.getLibelle());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(TypeObject object) {
        String sqlRequest = "update type_objet set libelle_type = ? WHERE id_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getLibelle());
            preparedStatement.setInt(2, object.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(TypeObject object) {
        return false;
    }
}

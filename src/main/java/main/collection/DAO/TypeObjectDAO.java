package main.collection.DAO;

import main.collection.Metier.TypeObject;

import java.sql.ResultSet;
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
    public boolean insert(TypeObject object) {
        return false;
    }

    @Override
    public boolean update(TypeObject object) {
        return false;
    }

    @Override
    public boolean delete(TypeObject object) {
        return false;
    }
}

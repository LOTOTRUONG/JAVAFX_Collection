package main.collection.DAO;

import main.collection.Metier.AttributType;

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
            if (resultSet.next())
                return new AttributType(resultSet.getInt(1), resultSet.getInt(2), resultSet.getBoolean(3),
                        resultSet.getBoolean(4), resultSet.getInt(5), resultSet.getInt(6));
            return null;
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
                // Assuming the columns in the table match the constructor parameters
                listAttributType.add(new AttributType(resultSet.getInt("id_type"), resultSet.getInt("id_attributType"),
                        resultSet.getBoolean("commentaire"), resultSet.getBoolean("table_de_donnee"),
                        resultSet.getInt("id_unite"), resultSet.getInt("id_attribut")));
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
        return false;
    }

    @Override
    public boolean update(AttributType object) {
        return false;
    }

    @Override
    public boolean delete(AttributType object) {
        return false;
    }
}

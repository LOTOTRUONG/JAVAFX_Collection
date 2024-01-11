package main.collection.DAO;

import main.collection.Metier.Photo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PhotoDAO extends DAO<Photo, Photo, Integer> {

    @Override
    public Photo getByID(Integer id) {
        String sqlRequest = "Select *from image where id_image = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Photo photo = new Photo();
                photo.setId(resultSet.getInt("id_image"));
                photo.setImagePath(resultSet.getString("imagePath"));
                return photo;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String getPhotoPathById(int imageId) {
        String sqlRequest = "SELECT imagePath FROM image WHERE id_image = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, imageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("imagePath");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Photo> getAll() {
        ArrayList<Photo> photoArrayList = new ArrayList<>();
        String sqlRequest = "Select *from image";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Photo photo = new Photo();
                photo.setId(resultSet.getInt("id_image"));
                photo.setImagePath(resultSet.getString("imagePath"));
                photoArrayList.add(photo);

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return photoArrayList;
    }

    @Override
    public ArrayList<Photo> getLike(Photo object) {
        return null;
    }

    @Override
    public boolean insert(Photo photo) {
        // Check if imagePath already exists in the table
        String selectRequest = "SELECT id_image FROM image WHERE imagePath = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectRequest)) {
            selectStatement.setString(1, photo.getImagePath());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // If imagePath exists, use the existing id_image
                int existingId = resultSet.getInt("id_image");
                photo.setId(existingId);
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }

        // If imagePath doesn't exist, insert a new record
        String insertRequest = "INSERT INTO image (imagePath) VALUES (?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertRequest, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, photo.getImagePath());

            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                // Retrieve the generated ID if needed
                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        photo.setId(generatedId);
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
    public boolean update(Photo object) {
        String sqlRequest = "update image set imagePath = ? WHERE id_image = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getImagePath());
            preparedStatement.setInt(2, object.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Photo object) {
        String deleteQuery = "DELETE FROM image WHERE id_image = ? " +
                "DBCC CHECKIDENT('image', reseed)";
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

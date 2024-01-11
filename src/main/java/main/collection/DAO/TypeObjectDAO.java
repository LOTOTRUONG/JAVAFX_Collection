package main.collection.DAO;

import main.collection.Metier.Photo;
import main.collection.Metier.TypeObject;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public int getImageIdByTypeId(int typeId) {
        String sqlRequest = "SELECT id_image FROM type_objet WHERE id_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, typeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_image");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
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
        String insertRequest = "INSERT INTO type_objet (libelle_type, id_image) VALUES (?, (SELECT id_image FROM image WHERE imagePath = ?)) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, typeObject.getLibelle());
            preparedStatement.setString(2, typeObject.getImagePath().replace("/", File.separator));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idType = generatedKeys.getInt(1);
                        PhotoDAO photoDAO = new PhotoDAO();
                        Photo photo = new Photo();
                        photo.setImagePath(typeObject.getImagePath());
                        if (photoDAO.insert(photo)) {
                            int idImage = photo.getId();
                            updateTypeObjectWithPhotoId(idType, idImage);
                            typeObject.setId(idType);
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private void updateTypeObjectWithPhotoId(int typeId, int photoId) {
        String updateRequest = "UPDATE type_objet SET id_image = ? WHERE id_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateRequest)) {
            preparedStatement.setInt(1, photoId);
            preparedStatement.setInt(2, typeId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean update(TypeObject object) {
        String selectImagePathRequest = "SELECT imagePath FROM image WHERE id_image = ?";
        String updateRequest = "UPDATE type_objet SET libelle_type = ?, id_image = (SELECT id_image FROM image WHERE imagePath = ?) WHERE id_type = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectImagePathRequest)) {
            // Get the current imagePath associated with the object's id_image
            selectStatement.setInt(1, object.getId());
            ResultSet resultSet = selectStatement.executeQuery();
            String currentImagePath = resultSet.next() ? resultSet.getString("imagePath") : null;

            // Check if imagePath is the same or does not exist in the image table
            if (Objects.equals(object.getImagePath(), currentImagePath) || !imageExists(object.getImagePath())) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateRequest)) {
                    preparedStatement.setString(1, object.getLibelle());
                    preparedStatement.setString(2, currentImagePath); // Keep the same imagePath
                    preparedStatement.setInt(3, object.getId());
                    preparedStatement.executeUpdate();
                    return true;
                }
            } else {
                // Update imagePath and id_image if the new imagePath exists in the image table
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateRequest, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, object.getLibelle());
                    preparedStatement.setString(2, object.getImagePath()); // Use the new imagePath
                    preparedStatement.setInt(3, object.getId());
                    preparedStatement.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    private boolean imageExists(String imagePath) throws SQLException {
        String selectImageRequest = "SELECT id_image FROM image WHERE imagePath = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectImageRequest)) {
            selectStatement.setString(1, imagePath);
            ResultSet resultSet = selectStatement.executeQuery();
            return resultSet.next();
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

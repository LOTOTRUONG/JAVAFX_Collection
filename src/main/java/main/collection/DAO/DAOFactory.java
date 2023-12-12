package main.collection.DAO;

public class DAOFactory {
    public static TypeObjectDAO getTypeObjectDAO() {
        return new TypeObjectDAO();
    }
}

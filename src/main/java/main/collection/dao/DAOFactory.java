package main.collection.dao;

public class DAOFactory {
    public static TypeObjectDAO getTypeObjectDAO() {
        return new TypeObjectDAO();
    }

    public static AttributDAO getAttributDAO() {
        return new AttributDAO();
    }

    public static AttributTypeDAO getAttributTypeDAO() {
        return new AttributTypeDAO();
    }

    public static ValeurDAO getValeurDAO() {
        return new ValeurDAO();
    }
}

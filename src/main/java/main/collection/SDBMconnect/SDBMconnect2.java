package main.collection.SDBMconnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class SDBMconnect2 {

    private static Connection connexion;

    private SDBMconnect2(){

    }

    public static Connection getInstance() {
        if (connexion == null) {
            try {
                String dbURL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=SLM;encrypt=false";
                String user = "sa";
                String pass = "sml@123456";
                connexion = DriverManager.getConnection(dbURL, user, pass);
            }

            // Handle any errors that may have occurred.
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connexion;
    }
}
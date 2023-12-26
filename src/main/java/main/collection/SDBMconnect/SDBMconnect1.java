package main.collection.SDBMconnect;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;

public class SDBMconnect1 {



    private static Connection connexion;

    private SDBMconnect1(){

    }

    public static Connection getInstance(){
        if( connexion == null ){

            try{
                SQLServerDataSource ds = new SQLServerDataSource();
                ds.setServerName("127.0.0.1");
                ds.setPortNumber(1433);
                ds.setDatabaseName("SLM");
                ds.setIntegratedSecurity(false);
                ds.setEncrypt(false);
                ds.setUser("sa");
                ds.setPassword("slm@123456");
                connexion = ds.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return connexion;
    }
}

package jnu.mcl.scheduler.connector;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Kim on 2015-11-25.
 */
public class DBConnector {

    private static DBConnector newInstance;
    private DBConnector(){
    }
    public static DBConnector getInstance(){
        if(newInstance == null){
            newInstance = new DBConnector();
        }
        return newInstance;
    }

    private String ip = "211.108.147.18:1433";
    private String className = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "labweb";
    private String userName = "sa";
    private String password = "root123";

    public Connection getConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        try {
            Class.forName(className).newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+"/"+db, userName, password);
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return connection;
    }
}
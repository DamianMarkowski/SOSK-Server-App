package pl.sosk;

import com.mysql.jdbc.Connection;
import java.sql.*;
import java.sql.DriverManager;

public final class DatabaseManager {
    public Connection conn;
    private Statement statement;
    public static DatabaseManager db;
    private DatabaseManager() {
        String url= "";
        String dbName = "sosk?autoReconnect=true";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "";
        String password = "";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public static synchronized DatabaseManager getDbCon() {
        if ( db == null ) {
            db = new DatabaseManager();
        }
        return db;
 
    }
 
    public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
 
}
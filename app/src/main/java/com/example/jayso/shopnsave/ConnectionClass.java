package com.example.jayso.shopnsave;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionClass {

    Connection conn = null;
    Statement stmt = null;
    String server = "solanki.database.windows.net";
    String database = "ShopnSave";
    String username = "solanki46";
    String password = "Sisterbro46@123";

    public ConnectionClass() {}

    public Statement getConnection() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver).newInstance();
            String connString = "jdbc:jtds:sqlserver://"+ server + ";databaseName=" + database + ";user="+ username +";password="+ password +";";
            conn = DriverManager.getConnection(connString);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stmt;
    }

    public void connectionClose() throws SQLException {
        conn.close();
    }

}

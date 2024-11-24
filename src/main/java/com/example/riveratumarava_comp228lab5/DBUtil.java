package com.example.riveratumarava_comp228lab5;
import java.sql.*;

public class DBUtil {
    private static Connection connection = null;
    private static Statement statement = null;
    public static void dbConnect(){
        String dbURL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
        String username = "COMP228_F24_sha_**"; // replace with username
        String password = "password"; // replace with password
        try {
            connection = DriverManager.getConnection(dbURL,username,password);
            statement = connection.createStatement();
            System.out.println("DB is connected!");
        } catch (SQLException e) {
            System.out.println("DB is not connected!");
            System.out.println(e.getMessage());
        }
    }
    public static void dbDisconnect() throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
            System.out.println("DB is disconnected!");
        }
    }

    public static void createOwnerTable() throws SQLException {
        dbConnect();
        String sql = "CREATE TABLE Owner (ownerID INT PRIMARY KEY, name VARCHAR(100), address VARCHAR(200), phone VARCHAR(100), email VARCHAR(100))";
        statement.execute(sql);
        System.out.println("Owner table is created!:");
        if  (statement != null) statement.close();
        dbDisconnect();
    }

    public static void createCarTable() throws SQLException {
        dbConnect();
        String sql = "CREATE TABLE Car (carID INT PRIMARY KEY, make VARCHAR(100), model VARCHAR(100), vin INT, buildYear INT, type VARCHAR(100))";
        statement.execute(sql);
        System.out.println("Car table is created!:");
        if  (statement != null) statement.close();
        dbDisconnect();
    }

    // MISSING AUTO INCREMENT for repairID
    public static void createRepairTable() throws SQLException {
        dbConnect();
        String sql = "CREATE TABLE Repair (repairID INT PRIMARY KEY, ownerID INT, carID INT, repairDate DATE, description VARCHAR(200), cost INT, CONSTRAINT fk_owner FOREIGN KEY (ownerID) REFERENCES Owner(ownerID), CONSTRAINT fk_car FOREIGN KEY (carID) REFERENCES Car(carID))";
        statement.execute(sql);
        System.out.println("Repair table is created!");
        if (statement != null) statement.close();
        dbDisconnect();
    }

    public static void dropTables() throws SQLException {
        dbConnect();
        String sql1 = "DROP TABLE Repair";
        String sql2 = "DROP TABLE Car";
        String sql3 = "DROP TABLE Owner";
        statement.execute(sql1);
        statement.execute(sql2);
        statement.execute(sql3);
        System.out.println("Tables Repair, Car, and Owner have been dropped!");
        if (statement != null) statement.close();
        dbDisconnect();
    }


    // Test DBUtil methods
    public static void main(String[] args) throws SQLException {
        DBUtil.dbConnect();
        DBUtil.dbDisconnect();
        DBUtil.dropTables();
        DBUtil.createOwnerTable();
        DBUtil.createCarTable();
        DBUtil.createRepairTable();
    }
}

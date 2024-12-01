package com.example.riveratumarava_comp228lab5;
import java.sql.*;

public class DBUtil {
    public static Connection connection = null;
    public static Statement statement = null;
    public static void dbConnect(){
        String dbURL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
        String username = "COMP228_F24_sha_28"; // replace with username
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
        String sql = "CREATE TABLE Owner (ownerID NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 CACHE 1000) PRIMARY KEY, name VARCHAR(100) NOT NULL, address VARCHAR(200) NOT NULL, phone VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL)";
        statement.execute(sql);
        System.out.println("Owner table is created!:");
        if  (statement != null) statement.close();
        dbDisconnect();
    }

    public static void createCarTable() throws SQLException {
        dbConnect();
        String sql = "CREATE TABLE Car (carID NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 CACHE 1000) PRIMARY KEY, make VARCHAR(100) NOT NULL, model VARCHAR(100) NOT NULL, vin VARCHAR(100) NOT NULL, buildYear INT NOT NULL, type VARCHAR(100) NOT NULL)";
        statement.execute(sql);
        System.out.println("Car table is created!:");
        if  (statement != null) statement.close();
        dbDisconnect();
    }

    // MISSING AUTO INCREMENT for repairID
    public static void createRepairTable() throws SQLException {
        dbConnect();
        String sql = "CREATE TABLE Repair (repairID NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 CACHE 1000) PRIMARY KEY, ownerID INT NOT NULL, carID INT NOT NULL, repairDate DATE NOT NULL, description VARCHAR(200) NOT NULL, cost INT NOT NULL, CONSTRAINT fk_repair_owner FOREIGN KEY (ownerID) REFERENCES Owner(ownerID), CONSTRAINT fk_repair_car FOREIGN KEY (carID) REFERENCES Car(carID))";
        statement.execute(sql);
        System.out.println("Repair table is created!");
        if (statement != null) statement.close();
        dbDisconnect();
    }

    public static void insertData() throws SQLException {
        String[] ownerData = {
                "INSERT INTO Owner (name, address, phone, email) VALUES ('Alice Johnson', '123 Elm Street', '555-1234', 'alice@ex.com')",
                "INSERT INTO Owner (name, address, phone, email) VALUES ('Bob Smith', '456 Oak Avenue', '555-5678', 'bob@ex.com')",
                "INSERT INTO Owner (name, address, phone, email) VALUES ('Carol Lee', '789 Pine Road', '555-8765', 'carol@ex.com')",
                "INSERT INTO Owner (name, address, phone, email) VALUES ('David King', '321 Maple Lane', '555-4321', 'david@ex.com')",
                "INSERT INTO Owner (name, address, phone, email) VALUES ('Eve Adams', '654 Birch Blvd', '555-6789', 'eve@ex.com')"
        };

        String[] carData = {
                "INSERT INTO Car (make, model, vin, buildYear, type) VALUES ('Toyota', 'Corolla', '123456789', 2010, 'Sedan')",
                "INSERT INTO Car (make, model, vin, buildYear, type) VALUES ('Ford', 'Focus', '987654321', 2015, 'SUV')",
                "INSERT INTO Car (make, model, vin, buildYear, type) VALUES ('Honda', 'Civic', '456789123', 2018, 'Truck')",
                "INSERT INTO Car (make, model, vin, buildYear, type) VALUES ('Chevrolet', 'Malibu', '321654987', 2020, 'Sedan')",
                "INSERT INTO Car (make, model, vin, buildYear, type) VALUES ('Nissan', 'Altima', '654321987', 2022, 'SUV')"
        };

        String[] repairData = {
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (1, 1, TO_DATE('2023-01-15', 'YYYY-MM-DD'), 'Brake replacement', 300)",
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (2, 2, TO_DATE('2023-02-10', 'YYYY-MM-DD'), 'Oil change', 50)",
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (3, 3, TO_DATE('2023-03-20', 'YYYY-MM-DD'), 'Tire rotation', 100)",
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (4, 4, TO_DATE('2023-04-25', 'YYYY-MM-DD'), 'Battery replacement', 200)",
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (5, 5, TO_DATE('2023-05-05', 'YYYY-MM-DD'), 'Transmission repair', 1200)",
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (1, 2, TO_DATE('2023-06-10', 'YYYY-MM-DD'), 'Wheel alignment', 150)",
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (2, 3, TO_DATE('2023-07-18', 'YYYY-MM-DD'), 'Air conditioning repair', 400)",
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (3, 4, TO_DATE('2023-08-22', 'YYYY-MM-DD'), 'Suspension check', 500)",
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (4, 5, TO_DATE('2023-09-10', 'YYYY-MM-DD'), 'Exhaust system repair', 350)",
                "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (5, 1, TO_DATE('2023-10-15', 'YYYY-MM-DD'), 'Paint job', 800)"
        };

        dbConnect();

        for (String sql: ownerData) {
            statement.execute(sql);
        }
        System.out.println("Owner data is populated!");

        for (String sql: carData) {
            statement.execute(sql);
        }
        System.out.println("Car data is populated!");

        for (String sql: repairData) {
            statement.execute(sql);
        }
        System.out.println("Repair data is populated!");

        if (statement != null) statement.close();
        dbDisconnect();
    }

    public static void dropTables() throws SQLException {
        dbConnect();
        String sql1 = "DROP TABLE Repair";
        String sql2 = "DROP TABLE Car";
        String sql3 = "DROP TABLE Owner";
        try {
            statement.execute(sql1);
            statement.execute(sql2);
            statement.execute(sql3);
        } catch (SQLException err) {
            System.out.println("Tables Repair, Car, and Owner cannot be dropped!");
            System.out.println(err.getMessage());
            return;
        }
        finally {
            if (statement != null) statement.close();
            dbDisconnect();

        }
        System.out.println("Tables Repair, Car, and Owner have been dropped!");

    }


    // Test DBUtil methods
    public static void main(String[] args) throws SQLException {
        DBUtil.dbConnect();
        DBUtil.dbDisconnect();
        DBUtil.dropTables();
        DBUtil.createOwnerTable();
        DBUtil.createCarTable();
        DBUtil.createRepairTable();
        insertData();
    }
}

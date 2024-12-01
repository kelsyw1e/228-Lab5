package com.example.riveratumarava_comp228lab5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class HelloController {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfMake;
    @FXML
    private TextField tfModel;
    @FXML
    private TextField tfVIN;
    @FXML
    private TextField tfBuild;
    @FXML
    private ComboBox<String> comboType;
    @FXML
    private Button addOwnerCarButton;
    @FXML
    private DatePicker dpRepair;
    @FXML
    private ComboBox<Owner> cbRepairOwner;
    @FXML
    private TextField tfRepairDesc;
    @FXML
    private ComboBox<Car> cbRepairCar;
    @FXML
    private TextField tfRepairCost;
    @FXML
    private Button addRepairButton;
    @FXML
    private ComboBox<Owner> cbSearchOwner;
    @FXML
    private ComboBox<Car> cbSearchCar;
    @FXML
    private DatePicker dpStart;
    @FXML
    private DatePicker dpEnd;
    @FXML
    private Button queryButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private String defaultValue = "Select car type";

    @FXML
    private void typeComboBox() {
    }

    private void populateTypeComboBox() {
        String[] types = {
                "Sedan",
                "SUV",
                "Van",
                "Truck"
        };
        comboType.getItems().addAll(types);
        comboType.setPromptText(defaultValue);

    }

    private void loadOwners() throws SQLException {
        String sqlOwner = "Select ownerID, Name From Owner";

        cbRepairOwner.getItems().clear();
        cbSearchOwner.getItems().clear();

        DBUtil.dbConnect();
        ResultSet rs = DBUtil.statement.executeQuery(sqlOwner);
        while (rs.next()) {
            String name = rs.getString("Name");
            String id = rs.getString("ownerID");

            cbRepairOwner.getItems().add(new Owner(id, name));
            cbSearchOwner.getItems().add(new Owner(id, name));
        }

        if  (DBUtil.statement != null) DBUtil.statement.close();
        DBUtil.dbDisconnect();
    }

    private void loadCars() throws SQLException {
        String sqlOwner = "Select carID, make, model, buildYear From Car";

        cbRepairCar.getItems().clear();
        cbSearchCar.getItems().clear();

        DBUtil.dbConnect();
        ResultSet rs = DBUtil.statement.executeQuery(sqlOwner);
        while (rs.next()) {
            String make = rs.getString("make");
            String model = rs.getString("model");
            String year = rs.getString("buildYear");
            String id = rs.getString("carID");

            cbRepairCar.getItems().add(new Car(id, make, model, year));
            cbSearchCar.getItems().add(new Car(id, make, model, year));
        }

        if  (DBUtil.statement != null) DBUtil.statement.close();
        DBUtil.dbDisconnect();
    }

    public void addRepairAction(ActionEvent actionEvent) throws SQLException {
        String errors = validateRepairData();
        if (!errors.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Form validation error");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }

        String sqlRepairForm = "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (%s, %s, TO_DATE('%s', 'YYYY-MM-DD'), '%s', %s)";
        String sqlRepair = String.format(sqlRepairForm, cbRepairOwner.getValue().getId(), cbRepairCar.getValue().getId(), dpRepair.getValue(), tfRepairDesc.getText(), tfRepairCost.getText());

        DBUtil.dbConnect();
        DBUtil.statement.execute(sqlRepair);
        if  (DBUtil.statement != null) DBUtil.statement.close();
        DBUtil.dbDisconnect();

        resetForms();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Repair was added");
        alert.showAndWait();
    }

    private String validateRepairData() {
        String errors = "";
        if (dpRepair.getValue() == null) {
            errors += "Repair date is required.\n";
        }
        if (tfRepairDesc.getText().isBlank()) {
            errors += "Description is required.\n";
        }
        if (cbRepairOwner.getValue() == null) {
            errors += "Owner is required.\n";
        }
        if (cbRepairCar.getValue() == null) {
            errors += "Car is required.\n";
        }
        String cost = tfRepairCost.getText();
        if (cost.isBlank()) {
            errors += "Cost is required.\n";
        } else {
            try {
                Integer.parseInt(cost);
            } catch (Exception e) {
                errors += "Cost should be a number.\n";
            }
        }

        return errors;
    }

    private String validateCarOwnerData() {
        String errors = "";
        if (tfName.getText().isBlank()) {
            errors += "Owner's name is required.\n";
        }
        if (tfAddress.getText().isBlank()) {
            errors += "Address is required.\n";
        }
        if (tfPhone.getText().isBlank()) {
            errors += "Phone is required.\n";
        }
        if (tfEmail.getText().isBlank()) {
            errors += "Email is required.\n";
        }
        if (tfModel.getText().isBlank()) {
            errors += "Car model is required.\n";
        }
        if (tfMake.getText().isBlank()) {
            errors += "Car make is required.\n";
        }
        String year = tfBuild.getText();
        if (year.isBlank()) {
            errors += "Build year is required.\n";
        } else {
            try {
                Integer.parseInt(year);
            } catch (Exception e) {
                errors += "Build year should be a number.\n";
            }
        }
        if (tfVIN.getText().isBlank()) {
            errors += "VIN is required.\n";
        }
        if (comboType.getValue().isBlank()) {
            errors += "Type is required is required.\n";
        }

        return errors;
    }

    private void resetForms () {
        tfName.clear();
        tfAddress.clear();
        tfPhone.clear();
        tfEmail.clear();
        tfModel.clear();
        tfMake.clear();
        tfBuild.clear();
        tfVIN.clear();
        comboType.setValue(null);
        cbRepairOwner.setValue(null);
        cbRepairCar.setValue(null);
        dpRepair.getEditor().clear();
        tfRepairDesc.clear();
        tfRepairCost.clear();
    }

    public void addOwnerCarAction(ActionEvent actionEvent) throws SQLException {
        String errors = validateCarOwnerData();
        if (!errors.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Form validation error");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }

        String sqlOwnerForm = "INSERT INTO Owner (name, address, phone, email) VALUES ('%s', '%s', '%s', '%s')";
        String sqlOwner = String.format(sqlOwnerForm, tfName.getText(), tfAddress.getText(), tfPhone.getText(), tfEmail.getText());

        String sqlCarForm = "INSERT INTO Car (make, model, vin, buildYear, type) VALUES ('%s', '%s', '%s', %s, '%s')";
        String sqlCar = String.format(sqlCarForm, tfMake.getText(), tfModel.getText(), tfVIN.getText(), tfBuild.getText(), comboType.getValue());

        DBUtil.dbConnect();
        DBUtil.statement.execute(sqlOwner);
        DBUtil.statement.execute(sqlCar);
        if  (DBUtil.statement != null) DBUtil.statement.close();
        DBUtil.dbDisconnect();

        loadOwners();
        loadCars();

        resetForms();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Owner and car was added");
        alert.showAndWait();
    }

    public void queryAction(ActionEvent actionEvent) throws SQLException {
    }

    public void updateAction(ActionEvent actionEvent) throws SQLException {
    }

    public void deleteAction(ActionEvent actionEvent) throws SQLException {
    }

    public void initialize() throws SQLException {
        populateTypeComboBox();
        loadOwners();
        loadCars();
    }
}


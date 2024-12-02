package com.example.riveratumarava_comp228lab5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;

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
    private ComboBox<Owner> cbOwner;
    @FXML
    private TextField tfRepairDesc;
    @FXML
    private ComboBox<Car> cbRepairCar;
    @FXML
    private ComboBox<Car> cbCar;
    @FXML
    private TextField tfRepairCost;
    @FXML
    private Button addRepairButton;
    @FXML
    private Button clearRepairButton;
    @FXML
    private ComboBox<Owner> cbSearchOwner;
    @FXML
    private ComboBox<Car> cbSearchCar;
    @FXML
    private DatePicker dpStart;
    @FXML
    private DatePicker dpEnd;
    @FXML
    private TableView<Repair> tvRepairTable;
    @FXML
    private TableColumn<Repair,String> tcDescription;
    @FXML
    private TableColumn<Repair,String> tcOwner;
    @FXML
    private TableColumn<Repair,String> tcCar;
    @FXML
    private TableColumn<Repair,String> tcRepairDate;
    @FXML
    private TableColumn<Repair,String> tcCost;
    @FXML
    private Button queryButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private String defaultValue = "Select car type";

    private Repair selectedRepair;
    private Owner selectedOwner;
    private Car selectedCar;

    @FXML
    private void ownerSelectAction(ActionEvent actionEvent) {
    }

    @FXML
    private void carSelectAction(ActionEvent actionEvent) {
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
        cbOwner.getItems().clear();

        cbSearchOwner.getItems().add(null);

        DBUtil.dbConnect();
        ResultSet rs = DBUtil.statement.executeQuery(sqlOwner);
        while (rs.next()) {
            String name = rs.getString("Name");
            String id = rs.getString("ownerID");

            cbRepairOwner.getItems().add(new Owner(id, name));
            cbSearchOwner.getItems().add(new Owner(id, name));
            cbOwner.getItems().add(new Owner(id, name));
        }

        if  (DBUtil.statement != null) DBUtil.statement.close();
        DBUtil.dbDisconnect();
    }

    private void loadCars() throws SQLException {
        String sqlOwner = "Select carID, make, model, buildYear From Car";

        cbRepairCar.getItems().clear();
        cbSearchCar.getItems().clear();
        cbCar.getItems().clear();

        cbSearchCar.getItems().add(null);

        DBUtil.dbConnect();
        ResultSet rs = DBUtil.statement.executeQuery(sqlOwner);
        while (rs.next()) {
            String make = rs.getString("make");
            String model = rs.getString("model");
            String year = rs.getString("buildYear");
            String id = rs.getString("carID");

            cbRepairCar.getItems().add(new Car(id, make, model, year));
            cbSearchCar.getItems().add(new Car(id, make, model, year));
            cbCar.getItems().add(new Car(id, make, model, year));
        }

        if  (DBUtil.statement != null) DBUtil.statement.close();
        DBUtil.dbDisconnect();
    }

    private String setRepair(String repairId) {
        String sqlRepairForm = "UPDATE Repair SET ownerID = %s, carID = %s, repairDate = TO_DATE('%s', 'YYYY-MM-DD'), description = '%s', cost = %s WHERE repairID = %s";
        return String.format(sqlRepairForm, cbRepairOwner.getValue().getId(), cbRepairCar.getValue().getId(), dpRepair.getValue(), tfRepairDesc.getText(), tfRepairCost.getText(), repairId);
    }

    private String addRepair() {
        String sqlRepairForm = "INSERT INTO Repair (ownerID, carID, repairDate, description, cost) VALUES (%s, %s, TO_DATE('%s', 'YYYY-MM-DD'), '%s', %s)";
        return String.format(sqlRepairForm, cbRepairOwner.getValue().getId(), cbRepairCar.getValue().getId(), dpRepair.getValue(), tfRepairDesc.getText(), tfRepairCost.getText());
    }

    public void addRepairAction(ActionEvent actionEvent) throws SQLException, ParseException {
        String errors = validateRepairData();
        if (!errors.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Form validation error");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }

        String sqlRepair = selectedRepair != null ? setRepair(selectedRepair.getId()) : addRepair();

        DBUtil.dbConnect();
        DBUtil.statement.execute(sqlRepair);
        if  (DBUtil.statement != null) DBUtil.statement.close();
        DBUtil.dbDisconnect();

        if (selectedRepair != null) {
            searchRepair();
        }

        resetForms();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Repair has been successfully saved");
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
        clearRepairForm();
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

    public void queryAction(ActionEvent actionEvent) throws SQLException, ParseException {
        searchRepair();
    }

    private void searchRepair() throws SQLException, ParseException {
        String sqlOwner = "Select * From Repair JOIN Owner ON Owner.ownerID = Repair.ownerID JOIN Car ON Car.carID = Repair.carID";
        String where = "";

        if (cbSearchCar.getValue() != null) {
            where += " Repair.carID = " + cbSearchCar.getValue().getId();
        }

        if (cbSearchOwner.getValue() != null) {
            where += (where.isBlank() ? "" : " AND ") + " Repair.ownerID = " + cbSearchOwner.getValue().getId();
        }

        if (dpStart.getValue() != null) {
            where += (where.isBlank() ? "" : " AND ") + " repairDate > TO_DATE('" + dpStart.getValue() + "', 'YYYY-MM-DD')";
        }

        if (dpEnd.getValue() != null) {
            where += (where.isBlank() ? "" : " AND ") + " repairDate < TO_DATE('" + dpEnd.getValue() + "', 'YYYY-MM-DD')";
        }

        sqlOwner += (where.isBlank() ? "" : " WHERE " + where);

        tvRepairTable.getItems().clear();
        tcDescription.setCellValueFactory(new PropertyValueFactory<Repair, String>("description"));
        tcCar.setCellValueFactory(new PropertyValueFactory<Repair, String>("car"));
        tcCost.setCellValueFactory(new PropertyValueFactory<Repair, String>("cost"));
        tcOwner.setCellValueFactory(new PropertyValueFactory<Repair, String>("owner"));
        tcRepairDate.setCellValueFactory(new PropertyValueFactory<Repair, String>("repairDate"));

        String car = "%s %s (%s)";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        DBUtil.dbConnect();
        ResultSet rs = DBUtil.statement.executeQuery(sqlOwner);
        while (rs.next()) {
            String date = rs.getString("repairDate");
            String desc = rs.getString("description");
            String cost = rs.getString("cost");
            String id = rs.getString("repairID");
            String owner = rs.getString("Name");
            String make = rs.getString("make");
            String model = rs.getString("model");
            String year = rs.getString("buildYear");
            String ownerId = rs.getString("ownerID");
            String carId = rs.getString("carID");

            tvRepairTable.getItems().add(new Repair(id, format.format(format.parse(date)), desc, cost, owner, ownerId, String.format(car, make, model, year), carId));
        }

        if  (DBUtil.statement != null) DBUtil.statement.close();
        DBUtil.dbDisconnect();
    }

    private void fillRepairForm(Repair repair) throws ParseException {
        dpRepair.setValue(LocalDate.parse(repair.getRepairDate()));
        tfRepairCost.setText(repair.getCost());
        tfRepairDesc.setText(repair.getDescription());

        var owners = cbRepairOwner.getItems();
        for (Owner owner : owners) {
            if (owner.getId().equals(repair.getOwnerId())) {
                cbRepairOwner.setValue(owner);
                break;
            }
        }

        var cars = cbRepairCar.getItems();
        for (Car car : cars) {
            if (car.getId().equals(repair.getCarId())) {
                cbRepairCar.setValue(car);
                break;
            }
        }

        addRepairButton.setText("Save Repair");
    }

    private void clearRepairForm(){
        addRepairButton.setText("Add Repair");
        selectedRepair = null;

        cbRepairOwner.setValue(null);
        cbRepairCar.setValue(null);
        dpRepair.getEditor().clear();
        tfRepairDesc.clear();
        tfRepairCost.clear();
    }

    public void clearRepairAction(ActionEvent actionEvent){
        clearRepairForm();
    }

    public void updateAction(ActionEvent actionEvent) throws SQLException,  ParseException {
        Repair repair = tvRepairTable.getSelectionModel().getSelectedItem();

        if (repair == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Form validation error");
            alert.setContentText("Select repair job to update");
            alert.showAndWait();
            return;
        }

        selectedRepair = repair;
        fillRepairForm(repair);
    }

    public void deleteAction(ActionEvent actionEvent) throws SQLException, ParseException {
        Repair repair = tvRepairTable.getSelectionModel().getSelectedItem();

        if (repair == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Form validation error");
            alert.setContentText("Select repair job to remove");
            alert.showAndWait();
            return;
        }

        String sqlDeleteRepair = "DELETE FROM Repair WHERE repairID = " + repair.getId();

        DBUtil.dbConnect();
        DBUtil.statement.execute(sqlDeleteRepair);
        if  (DBUtil.statement != null) DBUtil.statement.close();
        DBUtil.dbDisconnect();

        if (selectedRepair != null) {
            clearRepairForm();
        }

        searchRepair();
    }

    public void initialize() throws SQLException {
        populateTypeComboBox();
        loadOwners();
        loadCars();
    }
}


package com.example.riveratumarava_comp228lab5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.Date;

public class HelloController {
    @FXML
    private TextField tfOwnerID;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfCarID;
    @FXML
    private TextField tfMake;
    @FXML
    private TextField tfModel;
    @FXML
    private TextField tfVIN;
    @FXML
    private TextField tfBuild;
    @FXML
    private ComboBox comboType;
    @FXML
    private Button addOwnerCarButton;
    @FXML
    private TextField tfRepairID;
    @FXML
    private DatePicker dpRepair;
    @FXML
    private TextField tfRepairOwner;
    @FXML
    private TextField tfRepairDesc;
    @FXML
    private TextField tfRepairCar;
    @FXML
    private TextField tfRepairCost;
    @FXML
    private Button addRepairButton;
    @FXML
    private TextField tfSearchOwner;
    @FXML
    private TextField tfSearchCar;
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



    @FXML
    private void typeComboBox() {
    }

    public void addRepairAction(ActionEvent actionEvent) throws SQLException {
    }

    public void addOwnerCarAction(ActionEvent actionEvent) throws SQLException {
    }

    public void queryAction(ActionEvent actionEvent) throws SQLException {
    }

    public void updateAction(ActionEvent actionEvent) throws SQLException {
    }

    public void deleteAction(ActionEvent actionEvent) throws SQLException {
    }


    // missing populate table view method

    public void initialize() {
        typeComboBox();
    }



}


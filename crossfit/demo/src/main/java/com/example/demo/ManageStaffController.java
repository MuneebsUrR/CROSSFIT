package com.example.demo;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

public class ManageStaffController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Label namelabel;

    @FXML
    private Button btnlogout;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnmm;

    @FXML
    private Button btnms;

    @FXML
    private Button btnvm;

    @FXML
    private TableColumn<StaffManagement, String> colName;

    @FXML
    private TableColumn<StaffManagement, String> colSE;

    @FXML
    private TableColumn<StaffManagement, String> colST;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfSE;

    @FXML
    private TextField tfST;

    @FXML
    private TableView<StaffManagement> table;

    DBConnection db = DBConnection.getInstance();
    Connection con= db.getConnection();
    PreparedStatement pst;

    private void setupTableColumns() {
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colSE.setCellValueFactory(new PropertyValueFactory<>("ShiftEnd"));
        colST.setCellValueFactory(new PropertyValueFactory<>("ShiftStart"));
    }

    private void populateTable() {
        table.getItems().clear();
        try {
            String query = "select userName,shiftStart,shiftEnd from staff,users where userEmail = memberEmail;";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String name  = resultSet.getString("userName");
                String shiftstart = resultSet.getString("shiftStart");
                String shiftend = resultSet.getString("shiftEnd");


                StaffManagement staffmanage = new StaffManagement(name,  shiftstart, shiftend);
                table.getItems().add(staffmanage);

                // Debugging: Print fetched data
//                System.out.println("Fetched data: ID=" + id + ", Status=" + status + ", StartDate=" + startDate + ", EndDate=" + endDate);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    @FXML
    public void Add() {
        String email, shiftstart, shiftend;

        email = tfEmail.getText();
        shiftstart = tfST.getText();
        shiftend = tfSE.getText();

        try {
            pst = con.prepareStatement("insert into staff(memberEmail, shiftStart, shiftEnd) values(?,?,?)");
            pst.setString(1, email);
            pst.setString(2, shiftstart);
            pst.setString(3, shiftend);

            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected);
            pst.close();

            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Manage Staff");
                alert.setHeaderText("Manage Staff");
                alert.setContentText("Record Added!");
                alert.showAndWait();

                populateTable();

                tfEmail.setText("");
                tfST.setText("");
                tfSE.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Manage Staff");
                alert.setHeaderText("Manage Staff");
                alert.setContentText("Unable to add record. Please try again!");
                alert.showAndWait();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Manage Staff");
            alert.setHeaderText("Manage Staff");
            alert.setContentText("Unable to add record. Please try again!");
            alert.showAndWait();
        }
    }


    @FXML
   public void Delete() {
        String email = tfEmail.getText();

        try {

            pst = con.prepareStatement("DELETE FROM staff WHERE memberEmail=?");
            pst.setString(1, email);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Manage Staff");
                alert.setHeaderText("Manage Staff");
                alert.setContentText("Record Deleted!");
                alert.showAndWait();

                populateTable();

                tfEmail.setText("");
            } else {
                // Handle the case where no rows were deleted
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Manage Staff");
                alert.setHeaderText("Manage Staff");
                alert.setContentText("No record found with the given email!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void Update() {
        String email, shiftstart, shiftend;

        email = tfEmail.getText();
        shiftstart = tfST.getText();
        shiftend = tfSE.getText();

        try {

            pst = con.prepareStatement("UPDATE staff SET shiftStart=?, shiftEnd=? WHERE memberEmail=?");
            pst.setString(1, shiftstart);
            pst.setString(2, shiftend);
            pst.setString(3, email);


            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Manage Staff");
                alert.setHeaderText("Manage Staff");
                alert.setContentText("Record Updated!");
                alert.showAndWait();

                populateTable();

                tfEmail.setText("");
                tfST.setText("");
                tfSE.setText("");

            } else {
                // Handle the case where no rows were updated
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Manage Staff");
                alert.setHeaderText("Manage Staff");
                alert.setContentText("No record found with the given Email!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    private void handleButtonClick(ActionEvent event) {
        SwitchScreen switchscreen = new SwitchScreen((Stage) btnms.getScene().getWindow());
        switchscreen.closeScreen();

        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        if("Manage Staff".equals(buttonText)){
            switchscreen.switchtoScreen("manage-staff.fxml", "Admin - Manage staff",628,379);
        }
        if("Manage Memberships".equals(buttonText)){
            switchscreen.switchtoScreen("hello-view.fxml", "Admin - Manage Memberships",711,364);
        }
//        if("View All Members".equals(buttonText)){
//            switchscreen.switchtoScreen("manage-staff.fxml", "Admin Interface");
//        }
    }
    UserCredentials uc = UserCredentials.getInstance("","");
    public void GetName(){

        String name = uc.getName();
        namelabel.setText("Welcome,"+name);
    }
    @FXML
    public void  handleLogout(){
        SwitchScreen switchscreen = new SwitchScreen((Stage) btnlogout.getScene().getWindow());
        switchscreen.closeScreen();
        switchscreen.switchtoScreen("log-in.fxml", "Crossfit - log in",626,508);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupTableColumns();
        populateTable();
        GetName();
    }
}

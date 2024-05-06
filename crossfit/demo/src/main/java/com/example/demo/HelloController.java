package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

public class HelloController implements Initializable {

    @FXML
    private DatePicker EndDate;
    @FXML
    private Button btnlogout;

    @FXML
    private DatePicker StartDate;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label namelabel;

    @FXML
    private TableView<Membership> table;

    @FXML
    private TableColumn<Membership, Integer> idCol;

    @FXML
    private TableColumn<Membership, String> nameCol;

    @FXML
    private TableColumn<Membership, String> statusCol;

    @FXML
    private TableColumn<Membership, Date> startdateCol;

    @FXML
    private TableColumn<Membership, Date> enddateCol;


    @FXML
    private TextField tfID;

    @FXML
    private ChoiceBox<String> tfStatus;

    @FXML
    private Button btnmm;

    @FXML
    private Button btnms;

    @FXML
    private Button btnvm;


    DBConnection db = DBConnection.getInstance();
    Connection con= db.getConnection();
    PreparedStatement pst;

    @FXML
    protected void Add() {
        String id, status, startdate, enddate;

        id = tfID.getText();
        status = tfStatus.getValue();
        startdate = StartDate.getValue().toString();
        enddate = EndDate.getValue().toString();
        try {

            pst = con.prepareStatement("insert into memberships(memberID, isactive,startDate,endDate) values(?,?,?,?)");
            pst.setString(1,id);
            pst.setString(2,status);
            pst.setString(3,startdate);
            pst.setString(4,enddate);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Manage Memberships");
            alert.setHeaderText("Manage Memberships");
            alert.setContentText("Record Added!");
            alert.showAndWait();

            populateTable();

            tfID.setText("");
            StartDate.setValue(null);
            EndDate.setValue(null);

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

    }




    @FXML

    protected void Delete() {
        String id = tfID.getText();

        try {
            // Assuming your table has a primary key named 'primaryKey'
            pst = con.prepareStatement("DELETE FROM memberships WHERE memberID=?");
            pst.setString(1, id);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Manage Memberships");
                alert.setHeaderText("Manage Memberships");
                alert.setContentText("Record Deleted!");
                alert.showAndWait();

                populateTable();

                tfID.setText("");
            } else {
                // Handle the case where no rows were deleted
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Manage Memberships");
                alert.setHeaderText("Manage Memberships");
                alert.setContentText("No record found with the given ID!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    protected void Update() {
        String id, status, startdate, enddate;

        id = tfID.getText();
        status = tfStatus.getValue();
        startdate = StartDate.getValue().toString();
        enddate = EndDate.getValue().toString();

        try {

            pst = con.prepareStatement("UPDATE memberships SET isactive=?, startDate=?, endDate=? WHERE memberID=?");
            pst.setString(1, status);
            pst.setString(2, startdate);
            pst.setString(3, enddate);
            pst.setString(4, id);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Manage Memberships");
                alert.setHeaderText("Manage Memberships");
                alert.setContentText("Record Updated!");
                alert.showAndWait();

                populateTable();

                tfID.setText("");
                StartDate.setValue(null);
                EndDate.setValue(null);

            } else {
                // Handle the case where no rows were updated
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Manage Memberships");
                alert.setHeaderText("Manage Memberships");
                alert.setContentText("No record found with the given ID!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void setupTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        startdateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        enddateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    private void populateTable() {
        table.getItems().clear();
        try {
            String query = "select userName, memberID, startDate,endDate,isactive from memberships , users where userID = memberID";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("memberID");

                String name  = resultSet.getString("userName");
                String status = resultSet.getString("isactive");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");

                Membership membership = new Membership(id,name,  status, startDate, endDate);
                table.getItems().add(membership);

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
    void handleButtonClick(ActionEvent event) {
        SwitchScreen switchscreen = new SwitchScreen((Stage) btnmm.getScene().getWindow());
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

        tfStatus.getItems().addAll("true","false");

        setupTableColumns();
        populateTable();
        GetName();


    }
}
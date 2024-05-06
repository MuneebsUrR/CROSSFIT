package com.example.demo;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ScheduleWorkoutController implements Initializable {

    @FXML
    private Button btnadd;

    @FXML
    private Button btnlogout;
    @FXML
    private Label namelabel;

    @FXML
    private Button btnsw;

    @FXML
    private Button btnvpp;

    @FXML

    private TableColumn<String, ScheduleWorkoutSession> coldate;

    @FXML
    private TableColumn<String, ScheduleWorkoutSession> coltime;

    @FXML
    private TableColumn<String, ScheduleWorkoutSession> coltype;

    @FXML
    private TextField date;

    @FXML
    private TableView<ScheduleWorkoutSession> table;

    @FXML
    private TextField time;

    @FXML
    private ChoiceBox<String> type;

    private void setupTableColumns() {
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
    DBConnection db = DBConnection.getInstance();
    Connection con= db.getConnection();
    PreparedStatement pst;
    private void populateTable() {
        table.getItems().clear();
        try {
            String query = "select Sessiontype, SessionDate, SessionTime from scheduleSession where memberEmail = 'jane@example.com' ";


            PreparedStatement statement = con.prepareStatement(query);
//            statement.setString(1,email.getText());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {


                String type  = resultSet.getString("Sessiontype");
                String date1 = resultSet.getString("SessionDate");
                String time1 = resultSet.getString("SessionTime");

                ScheduleWorkoutSession SW;
                SW = new ScheduleWorkoutSession(type,time1,date1);
                table.getItems().add(SW);


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
    protected void Add() {
        String Type;

        String Date = date.getText();

        String Time = time.getText();

        Type = type.getValue();

        try {

            pst = con.prepareStatement("insert into scheduleSession( Sessiontype, SessionDate,SessionTime,memberEmail) values(?,?,?,?)");

            pst.setString(1,Type);
            pst.setString(2, Date);
            pst.setString(3, Time);
            pst.setString(4,"jane@example.com");
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Schedule Workout ");
            alert.setHeaderText("Schedule Workout ");
            alert.setContentText("Record Added!");
            alert.showAndWait();

            populateTable();

            date.setText("");
            time.setText("");
            type.setValue(null);


        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

    }

    UserCredentials uc = UserCredentials.getInstance("","");
    @FXML
    private void handleButtonClick(ActionEvent event) {
        SwitchScreen switchscreen = new SwitchScreen((Stage) btnsw.getScene().getWindow());
        switchscreen.closeScreen();

        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        if("View Personal Progress".equals(buttonText)){
            switchscreen.switchtoScreen("View-MemberProgress.fxml", "Trainer - View Member Progress",737,400);
        }
        if("Schedule Workout".equals(buttonText)){
            switchscreen.switchtoScreen("Schedule-WorkoutSession.fxml", "Trainer - Schedule Workout",600,400);
        }

    }

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
        type.getItems().addAll("Muscle gain","Weight Loss");

        setupTableColumns();
        populateTable();
        GetName();

    }
}

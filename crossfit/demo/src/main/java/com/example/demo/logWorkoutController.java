package com.example.demo;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class logWorkoutController implements Initializable {

    @FXML
    private Button BtnAdd;

    @FXML
    private Button btnlogout;

    @FXML
    private Button BtnDel;

    @FXML
    private Button btnlc;



    @FXML
    private Label namelabel;

    @FXML
    private Button btnlw;

    @FXML
    private Button BtnUpdate;

    @FXML
    private TableColumn<Integer, LogWorkout> colreps;

    @FXML
    private TableColumn<Integer, LogWorkout> colsets;

    @FXML
    private TableColumn<String, LogWorkout> coltype;



    @FXML
    private TextField reps;

    @FXML
    private TextField sets;

    @FXML
    private TableView<LogWorkout> table;

    @FXML
    private ChoiceBox<String> type;
    private void setupTableColumns() {
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colsets.setCellValueFactory(new PropertyValueFactory<>("sets"));
        colreps.setCellValueFactory(new PropertyValueFactory<>("reps"));
    }
    DBConnection db = DBConnection.getInstance();
    Connection con= db.getConnection();
    PreparedStatement pst;

    UserCredentials uc = UserCredentials.getInstance("","");
    private void populateTable() {
        table.getItems().clear();
        try {
            String query = "select workoutType, sets, reps from logworkout , users where userEmail = ? ";


            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,uc.getEmail());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {


                String type  = resultSet.getString("workoutType");
                int sets = resultSet.getInt("sets");
                int reps = resultSet.getInt("reps");

                LogWorkout LW = new LogWorkout(sets,reps,type);
                table.getItems().add(LW);


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
        int Reps, Sets;
        Sets = Integer.parseInt(sets.getText());
        Reps = Integer.parseInt(reps.getText());
        Type = type.getValue();

        try {

            pst = con.prepareStatement("insert into logworkout(memberEmail, workoutType, sets,reps) values(?,?,?,?)");
            pst.setString(1,uc.getEmail());
            pst.setString(2,Type);
            pst.setInt(3, Sets);
            pst.setInt(4, Reps);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("log Workout");
            alert.setHeaderText("log Workout");
            alert.setContentText("Record Added!");
            alert.showAndWait();

            populateTable();

            sets.setText("");
            reps.setText("");
            type.setValue(null);


        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        SwitchScreen switchscreen = new SwitchScreen((Stage) btnlw.getScene().getWindow());
        switchscreen.closeScreen();

        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        if("Log Calories".equals(buttonText)){
            switchscreen.switchtoScreen("log-calories.fxml", "Member - log calories",657,400);
        }
        if("Log Workout".equals(buttonText)){
            switchscreen.switchtoScreen("logworkout.fxml", "Member - log workout",703,400);
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
        type.getItems().addAll("Strength","Cardio");

        setupTableColumns();
        GetName();
//        populateTable();
    }
}
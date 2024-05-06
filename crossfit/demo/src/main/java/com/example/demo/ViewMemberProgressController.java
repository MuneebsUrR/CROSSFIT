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

public class ViewMemberProgressController implements Initializable {

    @FXML
    private Button btnsearch;

    @FXML
    private Button btnlogout;

    @FXML
    private  Label namelabel;

    @FXML
    private Button btnsw;

    @FXML
    private Button btnvpp;

    @FXML
    private TableColumn<Integer ,ViewMemberProgress> colcal;

    @FXML
    private TableColumn<String, ViewMemberProgress> colmeal;

    @FXML
    private TableColumn<Integer, ViewMemberProgress> colreps;

    @FXML
    private TableColumn<Integer, ViewMemberProgress> colsets;

    @FXML
    private TableColumn<String, ViewMemberProgress> coltype;

    @FXML
    private TextField email;

    @FXML
    private TableView<ViewMemberProgress> table;

    @FXML
    void Search() {
       




            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("member progress");
            alert.setHeaderText("member progress");
            alert.setContentText("Record Found!");
            alert.showAndWait();

            populateTable();

          email.setText("");





    }

    private void setupTableColumns() {
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colsets.setCellValueFactory(new PropertyValueFactory<>("sets"));
        colreps.setCellValueFactory(new PropertyValueFactory<>("reps"));
        colmeal.setCellValueFactory((new PropertyValueFactory<>("Meal")));
        colcal.setCellValueFactory((new PropertyValueFactory<>("Calories")));

    }

    DBConnection db = DBConnection.getInstance();
    Connection con= db.getConnection();


    private void populateTable() {
        table.getItems().clear();
        try {
            String query = "select workoutType,sets,reps, mealName, calories from logworkout cross join logcalories where logcalories.memberEmail = ? ";


            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,email.getText());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {


                String type  = resultSet.getString("workoutType");
                int sets = resultSet.getInt("sets");
                int reps = resultSet.getInt("reps");
                int cals = resultSet.getInt("calories");
                String meal = resultSet.getString("mealName");

                ViewMemberProgress VM = new ViewMemberProgress(cals,sets,reps,type,meal);
                table.getItems().add(VM);


                // Debugging: Print fetched data
//                System.out.println("Fetched data: ID=" + id + ", Status=" + status + ", StartDate=" + startDate + ", EndDate=" + endDate);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    UserCredentials uc = UserCredentials.getInstance("","");
    @FXML
    private void handleButtonClick(ActionEvent event) {
        SwitchScreen switchscreen = new SwitchScreen((Stage) btnvpp.getScene().getWindow());
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

        setupTableColumns();
//        populateTable();
        GetName();
    }
}

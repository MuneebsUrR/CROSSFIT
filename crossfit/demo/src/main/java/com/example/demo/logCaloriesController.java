package com.example.demo;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class logCaloriesController implements Initializable {

    @FXML
    private Button btnaddcalories;

    @FXML
    private Button btnlc;

    @FXML
    private Button btnlw;

    @FXML
    private Button btnlogout;

    @FXML
    private  Label namelabel;

    @FXML
    private TableView<LogCalories> table;

    @FXML
    private TableColumn<LogCalories, Integer> colCalories;

    @FXML
    private TableColumn<LogCalories, String> colMealName;

    @FXML
    private TableColumn<LogCalories, String> colTimeStamp;

    @FXML
    private TextField tfCalories;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfMealName;

    DBConnection db = DBConnection.getInstance();
    Connection con= db.getConnection();
    PreparedStatement pst;
    private void setupTableColumns() {
        colMealName.setCellValueFactory(new PropertyValueFactory<>("MealName"));
        colCalories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
        colTimeStamp.setCellValueFactory(new PropertyValueFactory<>("TimeStamp"));

    }
    UserCredentials uc = UserCredentials.getInstance("","");

    private void populateTable() {
        table.getItems().clear();
        try {

            String query = "select calories, mealName, logTimeStamp from logcalories where memberEmail = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,uc.getEmail());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int calories = resultSet.getInt("calories");

                String mealname  = resultSet.getString("mealName");
                String timestamp = resultSet.getString("logTimeStamp");


                LogCalories lc = new LogCalories(mealname, calories , timestamp);
                table.getItems().add(lc);

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
    public void ClickAddButton() {
        String calories, mealname, email;

         calories = tfCalories.getText();
         mealname = tfMealName.getText();
         email = tfEmail.getText();

        try {

            pst = con.prepareStatement("insert into logcalories(calories, mealName,memberEmail) values(?,?,?)");
            pst.setString(1,calories);
            pst.setString(2,mealname);
            pst.setString(3,uc.getEmail());

            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Log Calories");
            alert.setHeaderText("Log Calories");
            alert.setContentText("Record Added!");
            alert.showAndWait();

            populateTable();

            tfCalories.setText("");
            tfEmail.setText("");
            tfMealName.setText("");


        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void handleButtonClick(ActionEvent event) {
        SwitchScreen switchscreen = new SwitchScreen((Stage) btnlc.getScene().getWindow());
        switchscreen.closeScreen();

        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        if("Log Calories".equals(buttonText)){
            switchscreen.switchtoScreen("log-calories.fxml", "Member - log calories",626,400);
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
        GetName();
        setupTableColumns();
//        populateTable();
    }
}

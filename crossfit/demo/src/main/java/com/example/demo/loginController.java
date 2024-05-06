package com.example.demo;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.control.*;

import javafx.stage.Stage;

public class loginController implements Initializable {
    @FXML
    private Button btnlogin;

    @FXML
    private Label errorlabel;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;


    DBConnection db = DBConnection.getInstance();
    Connection con= db.getConnection();

    @FXML
    public void Login() {
        String userRole = null;

        try {
            String query = "SELECT userName, userEmail, userRole FROM users WHERE userEmail = ? AND userPassword = ?";

            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email.getText());
            statement.setString(2, password.getText());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // User found
                userRole = resultSet.getString("userRole");
                SwitchScreen switchscreen = new SwitchScreen((Stage) btnlogin.getScene().getWindow());
                switchscreen.closeScreen();

                //Setting users credentials if user is found for later use
                 UserCredentials uc = UserCredentials.getInstance(resultSet.getString("userName"), email.getText());
                 System.out.println(uc.getEmail() +"  "+ uc.getName());

                switch (userRole) {
                    case "Trainer":
                        switchscreen.switchtoScreen("Schedule-WorkoutSession.fxml", "Trainer Interface",600,400);
                        break;
                    case "Member":

                        switchscreen.switchtoScreen("log-calories.fxml", "Member - Log Calories",657,400);
                        break;
                    case "Admin":

                        switchscreen.switchtoScreen("manage-staff.fxml", "Admin - Manage Staff",711,364);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown user role");
                }


            } else {
                errorlabel.setText("User not found, try again!");
                email.setText(null);
                password.setText(null);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

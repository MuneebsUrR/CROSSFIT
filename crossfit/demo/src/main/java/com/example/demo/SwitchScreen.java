package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchScreen {
private Stage stage;

public SwitchScreen(Stage s){
    this.stage = s;
}

public void switchtoScreen(String fxmlFile , String title,int width , int length){
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    try {
        AnchorPane root = loader.load();
        Scene scene = new Scene(root,width,length);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }

}

public void closeScreen(){
    stage.close();
}


}

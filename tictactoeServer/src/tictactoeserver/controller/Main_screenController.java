
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tictactoeserver.services.NetworkAccessLayer;

/**
 * FXML Controller class
 *
 * @author AB
 */
public class Main_screenController implements Initializable {

    @FXML
    private Button playerStatusBtn;
    @FXML
    private ToggleButton serverActivationBtn;
    private NetworkAccessLayer connection = NetworkAccessLayer.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (connection.isRunning()) {
            serverActivationBtn.setText("Close Server");
            serverActivationBtn.setStyle("-fx-background-color: green");
            serverActivationBtn.setSelected(true);
        } else {
            serverActivationBtn.setText("Open Server");
            serverActivationBtn.setStyle("-fx-background-color: red");
            serverActivationBtn.setSelected(false);
        }
    }    

    @FXML
    private void playerStatusHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tictactoeserver/XML/players_status.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,620,420);
            Stage stage = (Stage) playerStatusBtn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR,"please connect to DataBase first", ButtonType.CLOSE);
            alert.show();
           
            
        }
    }

     @FXML
    private void serverActivationHandler(ActionEvent event) {
        if(serverActivationBtn.isSelected()){
            if (connection.openServer()){
                serverActivationBtn.setStyle("-fx-background-color: green;");
                serverActivationBtn.setText("Close Server");
            }else{
                serverActivationBtn.setSelected(false);
            }
        }
        else{
            serverActivationBtn.setStyle("-fx-background-color: red;");
            serverActivationBtn.setText("Open Server");
            connection.closeServer();
        }
    }
    
}


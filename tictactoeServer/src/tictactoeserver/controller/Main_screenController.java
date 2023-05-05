
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tictactoeserver.ServerNetwork;


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
    
    ServerNetwork server;
    
    public Main_screenController(){
        
    }
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void playerStatusHandler(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tictactoeserver/XML/players_status.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,620,420);
        Stage stage = (Stage) playerStatusBtn.getScene().getWindow();
        stage.setScene(scene);
    }

     @FXML
    private void serverActivationHandler(ActionEvent event) {
         
        if(serverActivationBtn.isSelected()){
            serverActivationBtn.setStyle("-fx-background-color: green;");
                server = new ServerNetwork();
        }
        else{
            serverActivationBtn.setStyle("-fx-background-color: red;");
            
            server.closeServer();
            
        }
    }

    @Override
    protected void finalize() {
        server.closeServer();
    }
    
    
}


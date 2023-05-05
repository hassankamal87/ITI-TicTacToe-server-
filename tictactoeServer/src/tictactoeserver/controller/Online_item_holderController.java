/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Mohamed Adel
 */
public class Online_item_holderController implements Initializable {

    @FXML
    private Text playerName;
    @FXML
    private Text playerStatus;
    String name;
    boolean isActive;

    
    public Online_item_holderController(String name, boolean isActive){
        this.name = name;
        this.isActive = isActive;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerName.setText(name);
        playerStatus.setText(isActive? "Online" : "Offline");
    }

    
    
}

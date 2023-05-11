/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.services;

import javafx.scene.control.Alert;

/**
 *
 * @author AB
 */
public class MyAlert {
    Alert alert;
    
    public MyAlert(Alert.AlertType type, String message){
        alert = new Alert(type, message);
    }
    
    public void show(){
        alert.show();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class TictactoeServer extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent mainRoot = FXMLLoader.load(getClass().getResource("XML/main_screen.fxml"));
        
        Scene mainScene = new Scene(mainRoot, 610, 410);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        ServerConnection.getInstance().closeServer();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

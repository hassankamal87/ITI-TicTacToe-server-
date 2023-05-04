/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AB
 */
public class Graph_screenController implements Initializable {

    @FXML
    private PieChart pieChart;
    @FXML
    private Text onlineNum;
    @FXML
    private Text offlineNum;
    @FXML
    private Text onlinePlayersText;
    @FXML
    private Text offlinePlayersText;
    @FXML
    private ImageView backBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void backButton(MouseEvent event) throws Exception{
        
        Parent mainRoot = FXMLLoader.load(getClass().getResource("/tictactoeserver/XML/main_screen.fxml"));
        Scene mainScene = new Scene(mainRoot, 610, 410);
        Stage primaryStage = (Stage) backBtn.getScene().getWindow();
        primaryStage.setScene(mainScene);
        
    }
    
}

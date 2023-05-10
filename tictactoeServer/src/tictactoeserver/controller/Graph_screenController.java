/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tictactoeserver.services.DataAccessLayer;

/**
 * FXML Controller class
 *
 * @author AB
 */
public class Graph_screenController implements Initializable  {

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
    
    private Timeline timeLine;
    
    private int onlineNo;
    private int tottalNo;
    private int offlineNo;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        drawPieChart();
        timeLine = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateData();
            }
        }));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }    

    @FXML
    private void backButton(MouseEvent event) throws Exception{
        
        Parent mainRoot = FXMLLoader.load(getClass().getResource("/tictactoeserver/XML/players_status.fxml"));
        Scene mainScene = new Scene(mainRoot, 610, 410);
        Stage primaryStage = (Stage) backBtn.getScene().getWindow();
        primaryStage.setScene(mainScene);
        
    }

    private void updateData() {
        
        try {
           tottalNo = DataAccessLayer.getInstance().getAllPlayers().size();
           onlineNo = DataAccessLayer.getInstance().getOnlinePlayers().size();
           offlineNo = tottalNo - onlineNo;
           onlineNum.setText(Integer.toString(onlineNo));
           offlineNum.setText(Integer.toString(offlineNo));
        } catch (SQLException ex) {
            Logger.getLogger(Graph_screenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
        new PieChart.Data(onlinePlayersText.getText(), onlineNo), 
        new PieChart.Data(offlinePlayersText.getText(), offlineNo));
        pieChart.setData(pieChartData);
        pieChart.setLabelsVisible(true);
    }
    private void drawPieChart(){
        try {
           tottalNo = DataAccessLayer.getInstance().getAllPlayers().size();
           onlineNo = DataAccessLayer.getInstance().getOnlinePlayers().size();
           offlineNo = tottalNo - onlineNo;
           onlineNum.setText(Integer.toString(onlineNo));
           offlineNum.setText(Integer.toString(offlineNo));
        } catch (SQLException ex) {
            Logger.getLogger(Graph_screenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
        new PieChart.Data(onlinePlayersText.getText(), onlineNo), 
        new PieChart.Data(offlinePlayersText.getText(), offlineNo));
        pieChart.setData(pieChartData);
        pieChart.setLabelsVisible(true);
    }
}

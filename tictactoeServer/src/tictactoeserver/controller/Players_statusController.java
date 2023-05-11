/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tictactoeserver.model.Player;
import tictactoeserver.services.DataAccessLayer;

/**
 * FXML Controller class
 *
 * @author Mohamed Adel
 */
public class Players_statusController implements Initializable {

    @FXML
    private ImageView backImage;
    @FXML
    private ListView<?> listItemHolder;

    DataAccessLayer dal;
    ArrayList<Player> allPlayers;
    ArrayList<Node> listOfItems;
    Alert alert;
    Timeline timeLine;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //for testing database when Player Status clicked
        getList();
        timeLine = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateData();
            }
        }));
    }

    @FXML
    private void goBack(MouseEvent event) throws IOException {
        Parent mainRoot = FXMLLoader.load(getClass().getResource("/tictactoeserver/XML/main_screen.fxml"));
        Scene mainScene = new Scene(mainRoot, 610, 410);
        Stage primaryStage = (Stage) backImage.getScene().getWindow();
        primaryStage.setScene(mainScene);

    }

    @FXML
    private void goToGraph(MouseEvent event) throws IOException {
        Parent mainRoot = FXMLLoader.load(getClass().getResource("/tictactoeserver/XML/graph_screen.fxml"));
        Scene mainScene = new Scene(mainRoot, 610, 410);
        Stage primaryStage = (Stage) backImage.getScene().getWindow();
        primaryStage.setScene(mainScene);
    }

    private void getList() {

        alert = new Alert(Alert.AlertType.ERROR, "creation error");
        allPlayers = new ArrayList<>();
        listOfItems = new ArrayList();

        try {
            dal = DataAccessLayer.getInstance();
        } catch (SQLException ex) {
            alert.show();
        }

        try {
            allPlayers = dal.getAllPlayers();
        } catch (SQLException ex) {
            alert.setContentText("error when getting All players from database");
        }

        // TODO
        for (Player p : allPlayers) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tictactoeserver/XML/online_item_holder.fxml"));
                loader.setControllerFactory(new Callback<Class<?>, Object>() {
                    @Override
                    public Object call(Class<?> clazz) {
                        if (clazz == Online_item_holderController.class) {
                            return new Online_item_holderController(p.getName(), p.isIsActive());
                        } else {
                            try {
                                return clazz.newInstance();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });

                Node node = loader.load();
                listOfItems.add(node);
            } catch (IOException ex) {
                Logger.getLogger(Players_statusController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ObservableList items = FXCollections.observableArrayList(listOfItems);
        listItemHolder.setItems(items);
    }

    private void updateData() {
        getList();
    }

}

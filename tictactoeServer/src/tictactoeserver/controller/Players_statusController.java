/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ArrayList<Node> list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tictactoeserver/XML/online_item_holder.fxml"));
                Node node = loader.load();
                list.add(node);
            } catch (IOException ex) {
                Logger.getLogger(Players_statusController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ObservableList items = FXCollections.observableArrayList(list);
        listItemHolder.setItems(items);
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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Mohamed Adel
 */
public class NetworkAccessLayer implements Runnable {

    private static NetworkAccessLayer instance = null;
    public ServerSocket server = null;
    private Thread serverThread;

    private NetworkAccessLayer() {
    }

    public static synchronized NetworkAccessLayer getInstance() {
        if (instance == null) {
            instance = new NetworkAccessLayer();
        }
        return instance;
    }

    public boolean isRunning() {
        if(server == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean openServer() {
            try {
                DataAccessLayer.getInstance().startConnection();
                server = new ServerSocket(5005);
                serverThread = new Thread(this);
                serverThread.start();
                return true;
            } catch (SQLException ex) {
                new MyAlert(Alert.AlertType.ERROR,"please connect to database first").show();
                return false;
            } catch (IOException ex) {
                new MyAlert(Alert.AlertType.ERROR,"there is another open server").show();
                return false;
            }
            
    
    }

    public void closeServer() {
        if (server != null) {
            try {
                DataAccessLayer.getInstance().closeConnection();
            } catch (SQLException ex) {
                System.out.println("Can't close db connection");
            }
            serverThread.stop();
            try{
            server.close();
            }catch(IOException ex){
                System.out.println("Can't close server");
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting ...");
                Socket client = server.accept();
                System.out.println(client.getRemoteSocketAddress().toString());
                new ClientHandler(client);
            } catch (IOException ex) {
                new MyAlert(Alert.AlertType.WARNING, "one client is down");
            }
        }
    }
}

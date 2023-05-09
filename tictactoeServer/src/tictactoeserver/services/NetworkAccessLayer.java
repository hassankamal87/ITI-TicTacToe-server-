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

/**
 *
 * @author Mohamed Adel
 */
public class NetworkAccessLayer implements Runnable {

    private static NetworkAccessLayer instance = null;
    public ServerSocket server;
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
        return server != null;
    }

    public void openServer() {
        try{
        server = new ServerSocket(5005);
        try {
            DataAccessLayer.getInstance().startConnection();
        } catch (SQLException ex) {
            System.out.println("Can't start db connection");
        }
        serverThread = new Thread(this);
        serverThread.start();
        }catch(IOException ex){
            System.out.println("Can't open server");
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
                System.out.println(client.getLocalAddress().toString());
                new ClientHandler(client);
            } catch (IOException ex) {
                Logger.getLogger(NetworkAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

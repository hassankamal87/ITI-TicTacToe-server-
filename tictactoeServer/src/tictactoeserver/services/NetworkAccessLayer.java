/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.services;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
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
    private DataInputStream dis;
    private PrintStream ps;

    private NetworkAccessLayer() {
    }

    public static NetworkAccessLayer getInstance() {
        if (instance == null) {
            instance = new NetworkAccessLayer();
        }
        return instance;
    }

    

    public void openServer() {
        try {
            server = new ServerSocket(5005);
        } catch (IOException ex) {
            Logger.getLogger(NetworkAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverThread = new Thread(this);
        serverThread.start();
    }

    public void closeServer() {
        try {
            System.out.println("Server Closed");
            
            
                
            if(server != null){
                server.close();
                serverThread.stop();
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting ...");
                Socket client = server.accept();
                System.out.println(client.getLocalAddress().toString());
                dis = new DataInputStream(client.getInputStream());
                ps = new PrintStream(client.getOutputStream());
                ps.println("Welcome From Server");
            } catch (IOException ex) {
                Logger.getLogger(NetworkAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

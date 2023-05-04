/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

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
public class ServerConnection implements Runnable {

    private static ServerConnection instance = null;
    private ServerSocket server;
    private Thread serverThread;
    private DataInputStream dis;
    private PrintStream ps;

    private ServerConnection() {
    }

    public static ServerConnection getInstance() {
        if (instance == null) {
            instance = new ServerConnection();
        }
        return instance;
    }

    

    public void openServer() {
        try {
            server = new ServerSocket(5005);
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverThread = new Thread(this);
        serverThread.start();
    }

    public void closeServer() {
        try {
            System.out.println("Server Closed");
            
            serverThread.stop();
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

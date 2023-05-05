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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author AB
 */
public class ServerNetwork {

    ServerSocket serverSocket;
    ArrayList<ClientHandler> clientsList = new ArrayList<>();
    Alert alert = new Alert(Alert.AlertType.ERROR, "server is Closed");
    boolean isRunning = true;

    public ServerNetwork() {

        try {
            serverSocket = new ServerSocket(5005);
            System.out.println("server Connected...");
            Thread th = new Thread() {
                public void run() {
                    while(isRunning){
                        try {
                            Socket client = serverSocket.accept();
                            ClientHandler clientHandler = new ClientHandler(client);
                            clientsList.add(clientHandler);
                        } catch (IOException ex) {
                            
                        }
                    }
                }
            };
            th.start();

        } catch (IOException ex) {
            
        }
    }

    public void closeServer() {
        try {
            isRunning = false;
            serverSocket.close();
            System.out.println("server Closed");
            
        } catch (IOException ex) {
            System.out.println("Thers is No Fucking Server To Close");
        }

        
    }

}

class ClientHandler extends Thread {

    Socket client;
    DataInputStream dis;
    PrintStream ps;
    


    public ClientHandler(Socket client) {
        try {
            this.client = client;
            dis = new DataInputStream(client.getInputStream());
            ps = new PrintStream(client.getOutputStream());
            start();
        } catch (IOException ex) {
            
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            while(true){
                if(dis.available()!=0){
                    String msg = dis.readLine();
                    if(msg == null)
                        break;
                    System.out.println(msg);
                    ps.println(msg + "done");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void closeClient(){
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}

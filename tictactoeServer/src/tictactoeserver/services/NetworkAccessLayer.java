/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.services;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

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

    public boolean isRunning(){
        return server != null; 
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
                new ClientHandler(client);
                ps.println("Welcome From Server");
                BufferedReader br = new BufferedReader(new InputStreamReader(dis));
                System.out.println(br);
                JSONObject signupJson= (JSONObject) new JSONParser().parse(br.readLine());
                System.out.println(signupJson);
                
            } catch (IOException ex) {
                Logger.getLogger(NetworkAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(NetworkAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}


class ClientHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    static Vector<ClientHandler> clientVector = new Vector<ClientHandler>();

    public ClientHandler(Socket s) {
        try {
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            clientVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                sendMessageAll(dis.readLine());
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    void sendMessageAll(String msg) {
//        for (int i = 0; i < clientVector.size(); i++) {
//            clientVector.get(i).ps.println(msg);
//        }
    }
    
}

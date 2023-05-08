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
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tictactoeserver.model.Player;
import utility.JsonObjectHelper;

/**
 *
 * @author Mohamed Adel
 */
class ClientHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    BufferedReader br;
    static Vector<ClientHandler> clientVector = new Vector<ClientHandler>();

    public ClientHandler(Socket s) {
        try {
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(dis));
            clientVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            JSONObject clientJson = readMessage();
            String header = (String) clientJson.get(JsonObjectHelper.HEADER);
            switch (header) {
                case JsonObjectHelper.SIGNUP:
                    //signup server logic
                    System.out.println(clientJson.toJSONString());
                     {
                        try {
                            boolean isExist;
                            Player newPlayer = new Player(clientJson.get(JsonObjectHelper.NAME).toString(),
                                    clientJson.get(JsonObjectHelper.EMAIL).toString(),
                                    clientJson.get(JsonObjectHelper.PASSWORD).toString()
                            );
                            isExist = DataAccessLayer.getInstance().checkPlayerExist(newPlayer.getEmail());
                            if (!isExist) {
                                DataAccessLayer.getInstance().insert(newPlayer);
                            }else{
                                //send duplicate email using ps
                                System.out.println("duplicate email");
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                case JsonObjectHelper.LOGIN:
                    //signup server logic
                    System.out.println(clientJson.toJSONString());
                     {
                        try {
                            boolean isSigned = false;
                            Player player = DataAccessLayer.getInstance().getPlayerByEmail(clientJson.get(JsonObjectHelper.EMAIL).toString());
                            if(player != null){
                                if(player.getPassword() == clientJson.get(JsonObjectHelper.PASSWORD).toString())
                                    isSigned = true;
                            }
                            else{
                                //send duplicate email using ps
                                System.out.println("password wrong");
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
            }
        }
    }

    JSONObject readMessage() {
        JSONObject clientJson = new JSONObject();
        try {
            if (br != null) {
                clientJson = (JSONObject) new JSONParser().parse(br.readLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientJson;
    }

}

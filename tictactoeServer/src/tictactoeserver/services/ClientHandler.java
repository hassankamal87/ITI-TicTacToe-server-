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
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
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
    JSONObject clientJson = new JSONObject();
    JSONObject responseJson = new JSONObject();
    DataAccessLayer connection;
    String email;
    String opponentEmail;

    public ClientHandler(Socket s) {
        try {
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(dis));
            connection = DataAccessLayer.getInstance();
            clientVector.add(this);
            System.out.println(clientVector.size() + " constructor");
            start();
        } catch (IOException ex) {
            //close steams
            System.out.println("client handler line 50");
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {

            System.out.println("client handler line 54");
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                clientJson = readMessage();
            } catch (NullPointerException ex) {
                System.out.println("client handler line 66");
                break;
            }

            if (clientJson != null) {
                String header = (String) clientJson.get(JsonObjectHelper.HEADER);
                switch (header) {
                    case JsonObjectHelper.SIGNUP:
                        //signup server logic
                        System.out.println(clientVector.size() + " sign up");
                        signupLogic();
                        break;
                    case JsonObjectHelper.LOGIN:
                        //login server logic
                        System.out.println(clientVector.size() + "login");
                        loginLogic();
                        break;
                    case JsonObjectHelper.SEND_INVITATION:
                        //send invitaion logic
                        System.out.println(clientVector.size() + "send invitation");
                        sendInvitaion();
                        break;
                }
            } else{


                break;
            }
        }
    }

    private JSONObject readMessage() {
        JSONObject clientJson = new JSONObject();
        try {
            clientJson = (JSONObject) new JSONParser().parse(br.readLine());
            return clientJson;
        } catch (IOException ex) {
            try {
                closeStreams();
            } catch (IOException ex1) {
                System.out.println("not closed in 109");
            }

        } catch (ParseException ex) {
            System.out.println("client handler line 115");

        } catch (NullPointerException e) {
            System.out.println("client handler 118");
            logoutLogic(email);
            
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new MyAlert(Alert.AlertType.WARNING, email + " client get Down").show();
                }
            });
            System.out.println("client handler line 113");
            try {
                closeStreams();
            } catch (IOException ex1) {
                
            }
        }
        return null;
    }

    private void signupLogic() {
        {
            try {
                boolean isExist;
                Player newPlayer = new Player(clientJson.get(JsonObjectHelper.NAME).toString(),
                        clientJson.get(JsonObjectHelper.EMAIL).toString(),
                        clientJson.get(JsonObjectHelper.PASSWORD).toString()
                );
                isExist = connection.checkPlayerExist(newPlayer.getEmail());
                if (!isExist) {
                    int result = connection.insert(newPlayer);
                    if (result > 0) {
                        //signedup success
                        responseJson.put(JsonObjectHelper.SIGNUP_STATUS, JsonObjectHelper.SIGNUP_SUCCESS);
                    }
                } else {
                    //send duplicate email using ps
                    responseJson.put(JsonObjectHelper.SIGNUP_STATUS, JsonObjectHelper.SIGNUP_FAIL_DUPLICATE);
                }
                ps.println(responseJson);

            } catch (SQLException ex) {
            }
        }
    }

    private void loginLogic() {
        ArrayList<Player> onlinePlayersList = new ArrayList<>();
        try {

            Player player = DataAccessLayer.getInstance().getPlayerByEmail(clientJson.get(JsonObjectHelper.EMAIL).toString());
            if (player != null) {
                if (player.getPassword().toString().equals(clientJson.get(JsonObjectHelper.PASSWORD).toString())) {
                    DataAccessLayer.getInstance().changeActiveStatus(player);
                    responseJson.put(JsonObjectHelper.SIGNIN_STATUS, JsonObjectHelper.SIGNIN_SUCCESS);
                    email = player.getEmail();
                } else {
                    responseJson.put(JsonObjectHelper.SIGNIN_STATUS, JsonObjectHelper.SIGNIN_FAIL);
                }
            } else {
                responseJson.put(JsonObjectHelper.SIGNIN_STATUS, JsonObjectHelper.SIGNIN_FAIL);
            }
            ps.println(responseJson);

            if (player != null) {
                onlinePlayersList = DataAccessLayer.getInstance().getOnlinePlayers();
                if (onlinePlayersList.size() > 0) {
                    for (int i = 0; i < onlinePlayersList.size(); i++) {
                        //for (Player p : onlinePlayersList) {
                        JSONObject playerJson = new JSONObject();
                        playerJson.put(JsonObjectHelper.HEADER, JsonObjectHelper.ONLINE_LIST);
                        playerJson.put(JsonObjectHelper.EMAIL, onlinePlayersList.get(i).getEmail());
                        playerJson.put(JsonObjectHelper.NAME, onlinePlayersList.get(i).getName());
                        //System.out.println(playerJson.get(JsonObjectHelper.EMAIL));
                        ps.println(playerJson);

                        //System.out.println(onlinePlayersList.get(i).toString());
                    }
                    JSONObject endJson = new JSONObject();
                    endJson.put(JsonObjectHelper.HEADER, "end");
                    ps.println(endJson);

                }

            }
            //System.out.println(responseJson.toJSONString());

        } catch (SQLException ex) {
        }
    }

    private void sendInvitaion() {

        opponentEmail = clientJson.get(JsonObjectHelper.SENDER).toString();
        for(int i=0 ; i<clientVector.size() ; i++){
        
            if (clientVector.get(i).email == opponentEmail) {
                JSONObject invitationObject = new JSONObject();
                invitationObject.put(JsonObjectHelper.SENDER, email);
                invitationObject.put(JsonObjectHelper.SENDER, email);
                clientVector.get(i).ps.println(invitationObject);
                System.out.println(clientVector.get(i).email);
            }
        }
    }
    
    private void closeStreams() throws IOException{
        System.out.println("streams closed done");
        clientVector.remove(this);
        dis.close();
        ps.close();
        br.close();
    }

    private void logoutLogic(String email) {
        try {
            DataAccessLayer.getInstance().logout(email);
            System.out.println(clientVector.size());
            for(int i=0 ; i<clientVector.size() ; i++){
                if (clientVector.get(i).email.equals(email)) {
                    clientVector.remove(clientVector.get(i));
                }
            }
            System.out.println(clientVector.size());
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

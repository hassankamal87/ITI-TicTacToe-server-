/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.derby.jdbc.ClientDriver;
import tictactoeserver.model.Player;

/**
 *
 * @author AB
 */
public class DataAccessLayer {
    
    Connection connection;
    private static DataAccessLayer instance;
    
    public DataAccessLayer ()throws SQLException{
        DriverManager.registerDriver(new ClientDriver());
        connection = DriverManager.getConnection("jdbc:derby://localhost:1527/GameDatabase","root","root");
    }
    
    public static synchronized DataAccessLayer getInstance() throws SQLException{
        if(instance == null){
            instance = new DataAccessLayer();
        }
        return instance;
    }
    
    public int insert(Player player) throws SQLException{
        int result = 0;
        PreparedStatement statment = connection.prepareStatement("INSERT INTO PLAYERS (PLAYERID,NAME, EMAIL,PASSWORD) VALUES (?,?,?,?)");
        statment.setInt(1, player.getPlayerId());
        statment.setString(2, player.getName());
        statment.setString(3,player.getEmail());
        statment.setString(4, player.getPassword());
        
        result = statment.executeUpdate();
        
        return result;
    }
    
    public Player getPlayerByID(int id) throws SQLException{
        Player player;
        PreparedStatement statment = connection.prepareStatement("SELECT * FROM PLAYERS WHERE ID = ?");
        statment.setInt(1, id);
        
        ResultSet rs = statment.executeQuery();
        
        int playerId = rs.getInt("PLAYERID");
        String name = rs.getString("NAME");
        String email = rs.getString("EMAIL");
        boolean isActive = rs.getBoolean("ISACTIVE");
        boolean isPlaying = rs.getBoolean("ISPLAYING");
        player = new Player(playerId, name, email, name, isActive, isPlaying);
        
        return player;
    }
    
    public Player getPlayerByEmail(String email) throws SQLException{
        Player player;
        PreparedStatement statment = connection.prepareStatement("SELECT * FROM PLAYERS WHERE EMAIL = ?");
        statment.setString(1, email);
        
        ResultSet rs = statment.executeQuery();
        
        int playerId = rs.getInt("PLAYERID");
        String name = rs.getString("NAME");
        String playerEmail = rs.getString("EMAIL");
        boolean isActive = rs.getBoolean("ISACTIVE");
        boolean isPlaying = rs.getBoolean("ISPLAYING");
        player = new Player(playerId, name, playerEmail, name, isActive, isPlaying);
        
        return player;
    }
    
    public ArrayList<Player> getAllPlayers() throws SQLException{
        ArrayList<Player> players = new ArrayList<>();
        
        PreparedStatement statment = connection.prepareStatement("SELECT * FROM PLAYERS");
        ResultSet rs = statment.executeQuery();
        
        while(rs.next()){
            int playerId = rs.getInt("PLAYERID");
            String name = rs.getString("NAME");
            String email = rs.getString("EMAIL");
            boolean isActive = rs.getBoolean("ISACTIVE");
            boolean isPlaying = rs.getBoolean("ISPLAYING");
            players.add(new Player(playerId, name, email, name, isActive, isPlaying));
        }
        
        return players;
    }
    
    public ArrayList<Player> getOnlinePlayers() throws SQLException{
        ArrayList<Player> players = new ArrayList<>();
        
        PreparedStatement statment = connection.prepareStatement("SELECT * FROM PLAYERS WHERE ISACTIVE = ?");
        statment.setBoolean(1, true);
        ResultSet rs = statment.executeQuery();
        
        while(rs.next()){
            int playerId = rs.getInt("PLAYERID");
            String name = rs.getString("NAME");
            String email = rs.getString("EMAIL");
            boolean isActive = rs.getBoolean("ISACTIVE");
            boolean isPlaying = rs.getBoolean("ISPLAYING");
            players.add(new Player(playerId, name, email, name, isActive, isPlaying));
        }
        
        return players;
    }
    
    public int changeActiveStatus(Player player) throws SQLException{
        int result = 0;
        PreparedStatement statment = connection.prepareStatement("UPDATE PLAYERS SET ISACTIVE = ? WHERE id = ?");
        statment.setBoolean(1, !player.isIsActive());
        statment.setInt(2, player.getPlayerId());
        result = statment.executeUpdate();
        
        return result;
    }
    
    public int changePlayStatus(Player player) throws SQLException{
        int result = 0;
        PreparedStatement statment = connection.prepareStatement("UPDATE PLAYERS SET ISPLAYING = ? WHERE id = ?");
        statment.setBoolean(1, !player.isIsPlaying());
        statment.setInt(2, player.getPlayerId());
        result = statment.executeUpdate();
        
        return result;
    }
    
    
    
    
}

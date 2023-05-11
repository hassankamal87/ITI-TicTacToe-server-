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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;
import tictactoeserver.model.Player;

/**
 *
 * @author AB
 */
public class DataAccessLayer {

    private static Connection connection;
    private static DataAccessLayer instance;

    public static synchronized DataAccessLayer getInstance() throws SQLException {
        if (instance == null) {
            instance = new DataAccessLayer();
        }
        return instance;
    }

    public void startConnection() throws SQLException {

        DriverManager.registerDriver(new ClientDriver());
        connection = DriverManager.getConnection("jdbc:derby://localhost:1527/GameDatabase", "root", "root");

    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public int insert(Player player) throws SQLException {
        int result = 0;
        PreparedStatement statment = connection.prepareStatement("INSERT INTO PLAYERS (NAME, EMAIL,PASSWORD) VALUES (?,?,?)");
        statment.setString(1, player.getName());
        statment.setString(2, player.getEmail());
        statment.setString(3, player.getPassword());

        result = statment.executeUpdate();

        return result;
    }

    public Player getPlayerByID(int id) throws SQLException {
        Player player;
        PreparedStatement statment = connection.prepareStatement("SELECT * FROM PLAYERS WHERE PLAYERID = ?");
        statment.setInt(1, id);

        ResultSet rs = statment.executeQuery();

        rs.next();
        int playerId = rs.getInt("PLAYERID");
        String name = rs.getString("NAME");
        String email = rs.getString("EMAIL");
        boolean isActive = rs.getBoolean("ISACTIVE");
        boolean isPlaying = rs.getBoolean("ISPLAYING");
        String password = rs.getString("PASSWORD");
        player = new Player(playerId, name, email, password, isActive, isPlaying);

        return player;
    }

    public Player getPlayerByEmail(String email) throws SQLException {
        Player player = null;
        PreparedStatement statment = connection.prepareStatement("SELECT * FROM PLAYERS WHERE EMAIL = ?");
        statment.setString(1, email);

        ResultSet rs = statment.executeQuery();

        if (rs.next()) {
            int playerId = rs.getInt("PLAYERID");
            String name = rs.getString("NAME");
            String playerEmail = rs.getString("EMAIL");
            boolean isActive = rs.getBoolean("ISACTIVE");
            boolean isPlaying = rs.getBoolean("ISPLAYING");
            String password = rs.getString("PASSWORD");
            player = new Player(playerId, name, playerEmail, password, isActive, isPlaying);
        }
        return player;
    }

    public boolean checkPlayerExist(String email) throws SQLException {
        boolean isExist;
        PreparedStatement statment = connection.prepareStatement("SELECT * FROM PLAYERS WHERE EMAIL = ?");
        statment.setString(1, email);

        ResultSet rs = statment.executeQuery();

        isExist = rs.next();

        return isExist;
    }

    public ArrayList<Player> getAllPlayers() throws SQLException {
        ArrayList<Player> players = new ArrayList<>();

        PreparedStatement statment = connection.prepareStatement("SELECT * FROM PLAYERS");
        ResultSet rs = statment.executeQuery();

        while (rs.next()) {
            int playerId = rs.getInt("PLAYERID");
            String name = rs.getString("NAME");
            String email = rs.getString("EMAIL");
            boolean isActive = rs.getBoolean("ISACTIVE");
            boolean isPlaying = rs.getBoolean("ISPLAYING");
            players.add(new Player(playerId, name, email, name, isActive, isPlaying));
        }

        return players;
    }

    public ArrayList<Player> getOnlinePlayers() throws SQLException {
        ArrayList<Player> players = new ArrayList<>();

        PreparedStatement statment = connection.prepareStatement("SELECT * FROM PLAYERS WHERE ISACTIVE = ?");
        statment.setBoolean(1, true);
        ResultSet rs = statment.executeQuery();

        while (rs.next()) {
            int playerId = rs.getInt("PLAYERID");
            String name = rs.getString("NAME");
            String email = rs.getString("EMAIL");
            boolean isActive = rs.getBoolean("ISACTIVE");
            boolean isPlaying = rs.getBoolean("ISPLAYING");
            String password = rs.getString("PASSWORD");
            players.add(new Player(playerId, name, email, password, isActive, isPlaying));
        }

        return players;
    }

    public int changeActiveStatus(Player player) throws SQLException {
        int result = 0;
        PreparedStatement statment = connection.prepareStatement("UPDATE PLAYERS SET ISACTIVE = ? WHERE PLAYERID = ?");
        statment.setBoolean(1, true);
        statment.setInt(2, player.getPlayerId());
        result = statment.executeUpdate();

        return result;
    }

    public int changePlayStatus(Player player) throws SQLException {
        int result = 0;
        PreparedStatement statment = connection.prepareStatement("UPDATE PLAYERS SET ISPLAYING = ? WHERE PLAYERID = ?");
        statment.setBoolean(1, !player.isIsPlaying());
        statment.setInt(2, player.getPlayerId());
        result = statment.executeUpdate();

        return result;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.model;

/**
 *
 * @author AB
 */
public class Player {
    private int playerId;
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    private boolean isPlaying;

    @Override
    public String toString() {
        return "Player{" + "playerId=" + playerId + ", name=" + name + ", email=" + email + ", password=" + password + ", isActive=" + isActive + ", isPlaying=" + isPlaying + '}';
    }

    public Player(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = false;
        this.isPlaying = false;
    }
    
    
    
    public Player(String name, String email, String password, boolean isActive, boolean isPlaying) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.isPlaying = isPlaying;
    }

    public Player(int playerId, String name, String email, String password, boolean isActive, boolean isPlaying) {
        this.playerId = playerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.isPlaying = isPlaying;
    }
  

    

    public int getPlayerId() {
        return playerId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
    
}

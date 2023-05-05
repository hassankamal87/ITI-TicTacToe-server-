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
public class Game {
    private int gameId;
    private int palyer1_id;
    private int palyer2_id;
    private int winner; // null if match is draw
    private String moves;

    public Game(int gameId, int palyer1_id, int palyer2_id, int winner, String moves) {
        this.gameId = gameId;
        this.palyer1_id = palyer1_id;
        this.palyer2_id = palyer2_id;
        this.winner = winner;
        this.moves = moves;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPalyer1_id() {
        return palyer1_id;
    }

    public void setPalyer1_id(int palyer1_id) {
        this.palyer1_id = palyer1_id;
    }

    public int getPalyer2_id() {
        return palyer2_id;
    }

    public void setPalyer2_id(int palyer2_id) {
        this.palyer2_id = palyer2_id;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public String getMoves() {
        return moves;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }
    
    
}

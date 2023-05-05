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
    private int player1_id;
    private int player2_id;
    private int winner_id; // null if game end with draw
    private String moves;

    public Game(int player1_id, int player2_id, int winner_id, String moves) {
        this.player1_id = player1_id;
        this.player2_id = player2_id;
        this.winner_id = winner_id;
        this.moves = moves;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPlayer1_id() {
        return player1_id;
    }

    public void setPlayer1_id(int player1_id) {
        this.player1_id = player1_id;
    }

    public int getPlayer2_id() {
        return player2_id;
    }

    public void setPlayer2_id(int player2_id) {
        this.player2_id = player2_id;
    }

    public int getWinner_id() {
        return winner_id;
    }

    public void setWinner_id(int winner_id) {
        this.winner_id = winner_id;
    }

    public String getMoves() {
        return moves;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }
    
    
    
}

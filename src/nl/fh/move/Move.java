/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fh.move;

import nl.fh.gamestate.GameState;


/**
 *
 * @author frank
 */
public interface Move {

    /**
     * 
     * @param state
     * @return the game state after the move is applied
     * 
     *  Throws an IllegalArgumentException is the move cannot be executed,
     *  but does not necessarily check for legality of the move. E.g. a move 
     *  that leaves the king in check does not have to throw an exception. 
     * 
     */
    public GameState applyTo(GameState state);
    
    /**
     * 
     * @return the string the represents the move in an exported PGN 
     */
    public String moveString();
    
    /**
     * @return true if the player that made this move offered draw when playing
     * this move, false otherwise
     */
    public boolean offeredDraw();
    
    /**
     * add a draw offer the this move
     */
    public void offerDraw();
    
}

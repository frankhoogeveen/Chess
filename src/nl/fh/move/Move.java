/*
 * License: GPL v3
 * 
 */
package nl.fh.move;

import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.rules.Rules;


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
     * @return the starting field of the move 
     * 
     * Calling this method on a castling move will throw an exception
     */
    public Field getTo();
    
    /**
     * 
     * @return the field where the move ends 
     * 
     * Calling this method an castling move will throw an exception
     */
    public Field getFrom();
    
    /**
     * @param state
     * @return the string the represents the move in an exported PGN 
     * 
     * The game state is needed as arguments in order
     * to:
     * - attach the indicator of the moving piece
     * - add the check(+) and mate(#) indicators
     * - to add the supplemental rank or file info when needed
     * - to add the x when capturing
     * - to add the promoted piece
     * - to add the e.p. indicator
     * 
     * The rules used are read from the game state
     */
    public String moveString(GameState state);
    
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

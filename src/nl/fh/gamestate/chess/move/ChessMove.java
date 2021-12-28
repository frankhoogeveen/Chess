/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.chess.move;

import nl.fh.gamestate.Move;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.rule.GameDriver;

/**
 * License GPL v3
 */
public abstract class ChessMove implements Move<ChessState> {
    
    
    /**
     * 
     * @return the starting field of the move 
     * 
     * Calling this method on a castling move will throw an exception
     */
    public abstract Field getTo();
    
    /**
     * 
     * @return the field where the move ends 
     * 
     * Calling this method an castling move will throw an exception
     */
    public abstract Field getFrom();
    
    /**
     * @param state the state BEFORE the move
     * @param driver
     * @return the string the represents the move in an exported PGN 
     * 
     * The game state and arbiter are needed as arguments in order
     * to:
     * - attach the indicator of the moving piece
     * - add the check(+) and mate(#) indicators
     * - to add the supplemental rank or file info when needed
     * - to add the x when capturing
     * - to add the promoted piece
     * 
     * Setting the state to a state that is not equal to the state before the
     * move (e.g. to the state after the move) leads to undefined results. 
     * No test is made to ensure that move is legal in the given state.
     * 
     */
    public abstract String formatPGN(ChessState state, GameDriver<ChessState> driver);
    
    /**
     * 
     * @param state
     * @return the move as used in the UCI protocol
     */
    public abstract String formatUCI(ChessState state);
    
    /**
     * @return true if the player that made this move offered draw when playing
     * this move, false otherwise
     */
    public abstract boolean offeredDraw();
    
    /**
     * add a draw offer the this move
     */
    public abstract void offerDraw();    
   
}

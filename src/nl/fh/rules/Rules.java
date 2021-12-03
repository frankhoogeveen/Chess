/*
 * License: GPL v3
 * 
 */
package nl.fh.rules;

import java.util.Set;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.gamereport.GameReport;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.player.Player;

/**
 * Represents the rules of the game. Different implementations may e.g. cater
 * for variants of chess. 
 */
public interface Rules {

    /**
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return the report of a time unlimited game between the two players 
     */
    public GameReport playGame(Player whitePlayer, Player blackPlayer);
    
    /**
     * 
     * @return the initial state of the game
     * The initial state does not have to be deterministic. It might be
     * random. Repeated calls to this method may return different values.
     */
    public GameState getInitialState();

    /**
     * 
     * @param move
     * @param state
     * @return true if the move is legal in the given game state 
     */
    public boolean isLegalMove(Move move, GameState state);
    
    /**
     * @param state
     * @return the set of all legal moves in the game state
     */
    public Set<Move> getAllLegalMoves(GameState state);

    /**
     * 
     * @param field
     * @param state
     * @param color
     * @return  true if the field is covered by color in the state
     */
    public boolean isCovered(Field field, GameState state, Color color);
      
}

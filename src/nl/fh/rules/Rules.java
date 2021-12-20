/*
 * License: GPL v3
 * 
 */
package nl.fh.rules;

import nl.fh.gamestate.GameState;
import java.util.Set;
import nl.fh.gamereport.GameReport;
import nl.fh.move.Move;
import nl.fh.player.Player;

/**
 * Represents the rules of the game. Different implementations may e.g. cater
 * for variants of chess. 
 * 
 * At the moment Rules is concerned with:
 * - in a given state what are the legal moves
 * - driving games, i.e. determining wins, losses and draws
 */
public interface Rules{

    /**
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return the report of a time unlimited game between the two players 
     */
    public GameReport playGame(Player whitePlayer, Player blackPlayer);
    
    /**
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @param initialState 
     * @return the report of a time unlimited game between the two players starting
     * from the initial state
     */
    public GameReport playGame(Player whitePlayer, Player blackPlayer, GameState initialState);    
    
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
     * 
     * This is an expensive method to call. It is encouraged to 
     * retrieve the legal moves from the GameState which is buffering
     * previously calculated move sets.
     */
    public Set<Move> calculateAllLegalMoves(GameState state);

    /**
     * 
     * @param state
     * @return true if the player to move is in check and cannot make a legal move
     */
    public boolean isMate(GameState state);
    
    /**
     * 
     * @param state
     * @return true if the position is stalemate
     */
    public boolean isStaleMate(GameState state);

    /**
     * @param state
     * @return true if the position is drawn
     */
    public boolean isDrawn(GameState state);
    
    /**
     * 
     * @param state
     * @return true if the player that is to move is in check
     */
    public boolean isCheck(GameState state);

    /**
     * 
     * @param state
     * @return true if this state is a >= 3 fold repetition
     */
    public boolean isThreeFoldRepetition(GameState state);
  
    
    /**
     * 
     * @param state
     * @return true is this state is a draw due to the 50 move rule
     */
    public boolean isAtFiftyMoveRule(GameState state); 
}

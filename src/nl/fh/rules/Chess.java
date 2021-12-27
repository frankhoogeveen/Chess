/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import nl.fh.gamestate.GameState;

/**
 * Defines regular chess.
 * 
 * Thing like "50" in the 50 moves rule could be pushed up here in the future
 */
public class Chess {
//TODO change into FideChess  , perhaps create a variant where capturing the king is the objective 
    public final static String initialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    
    /**
     * 
     * @return the initial state of regular chess 
     */
    public static GameState getInitialState(){
        return GameState.fromFEN(initialFEN);
    }
    
    
    /**
     * 
     * @return the game driver of regular chess
     * 
     */
    public static GameDriver getGameDriver(){
        GameState state = getInitialState();
        ChessMoveGenerator moveGenerator = new ChessMoveGenerator();
        ChessResultArbiter arbiter = new ChessResultArbiter(moveGenerator);
        
        return GameDriver.getInstance(state, moveGenerator, arbiter);
    }
    
}

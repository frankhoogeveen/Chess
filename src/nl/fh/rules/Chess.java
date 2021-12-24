/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import nl.fh.gamestate.GameState;

/**
 * Class to keep track of the main components of chess
 * 
 * Thing like "50" in the 50 moves rule could be pushed up here in the future
 */
public class Chess {
    
    public final static String initialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
 
    public final static GameState initialState = GameState.fromFEN(initialFEN);

    public final static MoveGenerator moveGenerator = new ChessMoveGenerator();       
    public final static ResultArbiter resultArbiter = new ChessResultArbiter();
    public final static GameDriver gameDriver = GameDriver.getInstance(initialState, moveGenerator, resultArbiter);
}

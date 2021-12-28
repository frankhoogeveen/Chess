/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

import nl.fh.gamestate.chess.ChessState;

/**
 * Defines regular chess according to the FIDE laws of chess( but without the 
 * time controls for the moment).
 * 
 */
public class FIDEchess {
    private final static String initialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private final static MoveGenerator<ChessState> moveGenerator = new ChessMoveGenerator();
    private final static ResultArbiter<ChessState> arbiter = new ChessResultArbiter(moveGenerator);
        
    
    
    /**
     * 
     * @return the initial state of regular chess 
     */
    public static ChessState getInitialState(){
        return ChessState.fromFEN(initialFEN);
    }
    
    /**
     * 
     * @return the game driver of regular chess
     * 
     */
    public static GameDriver<ChessState> getGameDriver(){
        ChessState state = getInitialState();    
        return GameDriver.getInstance(state, moveGenerator, arbiter);
    }
    
}

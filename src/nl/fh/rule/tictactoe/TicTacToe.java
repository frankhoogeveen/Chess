/*
 * License: GPL v3
 * 
 */

package nl.fh.rule.tictactoe;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import nl.fh.rule.GameDriver;
import nl.fh.rule.MoveGenerator;
import nl.fh.rule.ResultArbiter;

/**
 * 
 * 
 */
public class TicTacToe{
    
    public static TicTacToeState getInitialState(){
        return new TicTacToeState();
    }

    public static GameDriver<TicTacToeState> getGameDriver(){
        TicTacToeState initialState = new TicTacToeState();
        MoveGenerator<TicTacToeState> moveGenerator = new TicTacToeMoveGenerator();
        ResultArbiter<TicTacToeState> resultArbiter = new TicTacToeResultArbiter();
        
        return GameDriver.getInstance(initialState, moveGenerator, resultArbiter);
    }
}
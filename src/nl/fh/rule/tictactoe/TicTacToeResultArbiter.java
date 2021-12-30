/*
 * License: GPL v3
 * 
 */

package nl.fh.rule.tictactoe;

import java.util.Set;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.Move;
import nl.fh.gamestate.tictactoe.TicTacToeEnum;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import nl.fh.rule.ResultArbiter;

/**
 * 
 * 
 */
public class TicTacToeResultArbiter implements ResultArbiter<TicTacToeState> {

    public TicTacToeResultArbiter() {
    }

    //TODO build unit tests for the ttt arbiter
    @Override
    public GameResult determineResult(GameReport<TicTacToeState> report, Set<Move<TicTacToeState>> legalMoves) {
        
        if(report.getFinalState() == null){
            return GameResult.UNDECIDED;
        }
        
        TicTacToeState state = report.getFinalState();
        switch(state.getMover()){
            case FIRST_MOVER:
                    if(isWin(state,legalMoves)){
                        return GameResult.WIN_SECOND_MOVER;
                    } else if(isDraw(state, legalMoves, null)){
                        return GameResult.DRAW;
                    } else {
                        return GameResult.UNDECIDED;
                    }
            case SECOND_MOVER:
                    if(isWin(state,legalMoves)){
                        return GameResult.WIN_FIRST_MOVER;
                    } else if(isDraw(state, legalMoves, null)){
                        return GameResult.DRAW;
                    } else {
                         return GameResult.UNDECIDED;
                    }
            default:
                throw new IllegalStateException("This should not happen");
        }
    }

    @Override
    public boolean isWin(TicTacToeState state, Set<Move<TicTacToeState>> legalMoves) {
        TicTacToeEnum target = null;
        switch(state.getMover()){
            case FIRST_MOVER:
                target = TicTacToeEnum.SECOND;
                break;
            case SECOND_MOVER:
                target = TicTacToeEnum.FIRST;
                break;
            default:
                throw new IllegalStateException("This should not happen");
        }
        
        boolean result = false;
        result |= check(target, state.getFieldContent(0, 0), state.getFieldContent(1, 1), state.getFieldContent(2, 2));
        result |= check(target, state.getFieldContent(2, 0), state.getFieldContent(1, 1), state.getFieldContent(0, 2));
        
        result |= check(target, state.getFieldContent(0, 0), state.getFieldContent(0, 1), state.getFieldContent(0, 2));
        result |= check(target, state.getFieldContent(1, 0), state.getFieldContent(1, 1), state.getFieldContent(1, 2));
        result |= check(target, state.getFieldContent(2, 0), state.getFieldContent(2, 1), state.getFieldContent(2, 2)); 

        result |= check(target, state.getFieldContent(0, 0), state.getFieldContent(1, 0), state.getFieldContent(2, 0));
        result |= check(target, state.getFieldContent(0, 1), state.getFieldContent(1, 1), state.getFieldContent(2, 1));
        result |= check(target, state.getFieldContent(0, 2), state.getFieldContent(1, 2), state.getFieldContent(2, 2));  
        
        return result;
    }

    @Override
    public boolean isDraw(TicTacToeState state, Set<Move<TicTacToeState>> legalMoves, GameReport<TicTacToeState> report) {
        if(isWin(state, legalMoves)){
            return false;
        }
        return legalMoves.isEmpty();
    }

    @Override
    public boolean isLoss(TicTacToeState state, Set<Move<TicTacToeState>> legalMoves) {
        return false;
    }

    private boolean check(TicTacToeEnum target, TicTacToeEnum f1, TicTacToeEnum f2, TicTacToeEnum f3) {
        return (f1 == target) && (f2 == target) && (f3 == target);
    }

}
/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

import java.util.Set;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Move;

/**
 * 
 * 
 */
public interface ResultArbiter<S extends GameState>{

    /**
     * 
     * @param report
     * @return the current status of the game, based on the report 
     * The contents of report is NOT changed
     */
    public GameResult determineResult(GameReport<S> report, Set<Move<S>> legalMoves);
    
    /**
     * 
     * @return true if the player that has just made the move has legally won
     */
    public boolean isWin(S state, Set<Move<S>> legalMoves);
    
    /**
     * 
     * @return true if the outcome of the game is legally a draw
     */
    public boolean isDraw(S state, Set<Move<S>> legalMoves, GameReport<S> report);
    
    /**
     * 
     * @return true if the player that just made the move has legally lost 
     */
    public boolean isLoss(S state, Set<Move<S>> legalMoves);

}
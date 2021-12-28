/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

import java.util.Set;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.move.Move;

/**
 * 
 * 
 */
public interface ResultArbiter{

    /**
     * 
     * @param report
     * @return the current status of the game, based on the report 
     * The contents of report is NOT changed
     */
    public GameResult determineResult(GameReport report, Set<Move> legalMoves);

}
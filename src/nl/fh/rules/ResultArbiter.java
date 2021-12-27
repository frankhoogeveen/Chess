/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import java.util.Set;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.ChessGameResult;
import nl.fh.move.Move;

/**
 * 
 * 
 */
public interface ResultArbiter {

    /**
     * 
     * @param report
     * @return the current status of the game, based on the report 
     * The contents of report is NOT changed
     */
    public ChessGameResult determineResult(GameReport report, Set<Move> legalMoves);

}
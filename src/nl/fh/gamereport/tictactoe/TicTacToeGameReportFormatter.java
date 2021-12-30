/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport.tictactoe;

import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamestate.tictactoe.TicTacToeState;

/**
 * 
 * 
 */
public class TicTacToeGameReportFormatter implements GameReportFormatter<TicTacToeState> {

    
    
    public TicTacToeGameReportFormatter() {
    }

    @Override
    public String formatGame(GameReport<TicTacToeState> report) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=== report ===\n");
        
        for(int n = 0; n < report.getMoveList().size(); n++){
            sb.append(report.getMoveList().get(n));
            sb.append(" ");
            sb.append(report.getStateList().get(n+1));
            sb.append("\n");
        }
        
        sb.append("\n");
        sb.append(report.getGameResult());
        
        sb.append("\n");
        return sb.toString();
    }

}
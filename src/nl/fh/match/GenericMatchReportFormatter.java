/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

import java.util.List;
import java.util.Map;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.player.Player;

/**
 * Formats match reports by giving:
 * 1) a header with the summary
 * 2) the list of games that passed the filter
 * 
 */
public class GenericMatchReportFormatter<S extends GameState> implements MatchReportFormatter<S> {

    private final GameReportFormatter<S> gameFormatter;

    public GenericMatchReportFormatter(GameReportFormatter<S> gFormatter){
        this.gameFormatter = gFormatter;
    }
    
    @Override
    public String formatMatch(MatchReport<S> report) {
        List<GameReport<S>> gameSelection = report.getGameSelection();
        
        StringBuilder sb = new StringBuilder();
        matchHeader(sb, report);
        
        for(GameReport gReport : gameSelection){
            sb.append(gameFormatter.formatGame(gReport));
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private void matchHeader(StringBuilder sb, MatchReport report){
        
        Player player1 = report.getPlayer1();
        Player player2 = report.getPlayer2();
        int score = report.getScore();
        int count = report.getCount();
        Map<GameResult, Integer> player1White = report.getPlayer1WhiteResults();
        Map<GameResult, Integer> player2White = report.getPlayer2WhiteResults();        
        
        sb.append(";");
        sb.append("=== MatchResult ===\n");
        
        sb.append(";Players\n");
        
        sb.append(";");        
        sb.append(player1.getDescription());
        sb.append("\n");
        
        sb.append(";");          
        sb.append(player2.getDescription());
        sb.append("\n;\n");             
        
        sb.append(";");          
        sb.append(count);
        sb.append(" games played\n");
        
        sb.append(";score differential: ");
        sb.append(score);
        sb.append("\n;\n");
        
        sb.append(";");          
        sb.append(player1.getDescription());
        sb.append(" playing as white\n");
        for(GameResult g : GameResult.values()){
           sb.append(";");              
           sb.append(g.toString());
           sb.append(" : ");
           sb.append(player1White.get(g));
           sb.append("\n");
        }
        sb.append(";\n");
        sb.append(";");         
        sb.append(player2.getDescription());
        sb.append(" playing as white\n");
        for(GameResult g : GameResult.values()){
           sb.append(";");             
           sb.append(g.toString());
           sb.append(" : ");
           sb.append(player2White.get(g));
           sb.append("\n");
        }  
        
        sb.append(";\n");
    }       

}
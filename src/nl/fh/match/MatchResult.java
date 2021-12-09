/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.player.Player;

/**
 * Stores the result of a match between two players. 
 * 
 * It keeps track of the number of times a specific game result 
 * has been achieved and stores a limited number of detailed games.
 * 
 */
public class MatchResult {

    private final Player player1;
    private final Player player2;
    
    private GameFilter filter;
    private final ArrayList<GameReport> gameSelection;    
    
    private int score = 0;
    
    private final Map<GameResult, Integer> player1White;
    private final Map<GameResult, Integer> player2White; 

    private int count = 0;
    /**
     * Object to store the result of a match between two players
     * 
     * @param player1
     * @param player2 
     */
    public MatchResult(Player player1, Player player2, GameFilter filter) {
        this.player2White = new EnumMap<GameResult, Integer>(GameResult.class);
        this.player1White = new EnumMap<GameResult, Integer>(GameResult.class);
        this.player1 = player1;
        this.player2 = player2;
        
        for(GameResult result : GameResult.values()){
            player1White.put(result, 0);
            player2White.put(result, 0);
        }
        
        this.gameSelection = new ArrayList<GameReport>();
        this.filter = filter;
    }

    /**
     * Calling this method adds the outcome of a game to the overall score sheet
     * 
     * @param player1 the player playing white
     * @param player2 the player playing black
     * @param gameResult 
     */
    void add(Player player1, Player player2, GameReport report) {
        //conditionally store the full game details
        if(this.filter.retain(report)){
            this.gameSelection.add(report);
        }
        
        // update the score
        GameResult gameResult = report.getGameResult();
        
        if(player1.equals(this.player1) && player2.equals(this.player2)){
            int current = player1White.get(gameResult);
            player1White.put(gameResult, current+1);
            score += gameResult.getValue();      
            
        } else if(player1.equals(this.player2) && player2.equals(this.player1)){
            int current = player2White.get(gameResult);
            player2White.put(gameResult, current+1); 
            score += -gameResult.getValue();
            
        } else {
            throw new IllegalArgumentException("Incorrect players for this MatchResult");
        }   
        
        count += 1;
    }
    
    /**
     * 
     * @return the selected games in PGN format. The overall match data are 
     * written into pgn comment lines.
     */
    public String toPGN(){
        StringBuilder sb = new StringBuilder();
        sb.append(matchHeader());
        for(GameReport report : this.gameSelection){
            sb.append(report.toPGN());
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private String matchHeader(){
        StringBuilder sb = new StringBuilder();
        sb.append(";");
        sb.append("=== MatchResult ===\n");
        
        sb.append(";");        
        sb.append(player1);
        sb.append("\n");
        
        sb.append(";");          
        sb.append(player2);
        sb.append("\n;\n");             
        
        sb.append(";");          
        sb.append(count);
        sb.append(" games played\n");
        
        sb.append(";score differential: ");
        sb.append(score);
        sb.append("\n");
        
        sb.append(";");          
        sb.append(player1.toString());
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
        sb.append(player2.toString());
        sb.append(" playing as white\n");
        for(GameResult g : GameResult.values()){
           sb.append(";");             
           sb.append(g.toString());
           sb.append(" : ");
           sb.append(player2White.get(g));
           sb.append("\n");
        }  
        
        sb.append(";\n");
        
        return sb.toString();
    }

}
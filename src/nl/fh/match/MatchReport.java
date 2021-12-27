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
import nl.fh.gamereport.ChessGameResult;
import nl.fh.player.Player;

/**
 * Stores the result of a match between two players. 
 * 
 * It keeps track of the number of times a specific game result 
 * has been achieved and stores a limited number of detailed games.
 * 
 */
public class MatchReport {

    private final Player player1;
    private final Player player2;
    
    private final GameFilter filter;
    private final ArrayList<GameReport> gameSelection;    
    
    private int score = 0;
    
    private final Map<ChessGameResult, Integer> player1WhiteResults;
    private final Map<ChessGameResult, Integer> player2WhiteResults; 

    private int count = 0;
    /**
     * Object to store the result of a match between two players
     * 
     * @param player1
     * @param player2 
     */
    public MatchReport(Player player1, Player player2, GameFilter filter) {
        this.player2WhiteResults = new EnumMap<ChessGameResult, Integer>(ChessGameResult.class);
        this.player1WhiteResults = new EnumMap<ChessGameResult, Integer>(ChessGameResult.class);
        this.player1 = player1;
        this.player2 = player2;
        
        for(ChessGameResult result : ChessGameResult.values()){
            player1WhiteResults.put(result, 0);
            player2WhiteResults.put(result, 0);
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
        ChessGameResult gameResult = report.getGameResult();
        
        if(player1.equals(this.player1) && player2.equals(this.player2)){
            int current = player1WhiteResults.get(gameResult);
            player1WhiteResults.put(gameResult, current+1);
            score += gameResult.getValue();      
            
        } else if(player1.equals(this.player2) && player2.equals(this.player1)){
            int current = player2WhiteResults.get(gameResult);
            player2WhiteResults.put(gameResult, current+1); 
            score += -gameResult.getValue();
            
        } else {
            throw new IllegalArgumentException("Incorrect players for this MatchResult");
        }   
        
        count += 1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public ArrayList<GameReport> getGameSelection() {
        return gameSelection;
    }

    public int getScore() {
        return score;
    }

    public Map<ChessGameResult, Integer> getPlayer1WhiteResults() {
        return player1WhiteResults;
    }

    public Map<ChessGameResult, Integer> getPlayer2WhiteResults() {
        return player2WhiteResults;
    }

    public int getCount() {
        return count;
    }
    


}
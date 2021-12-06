/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

import java.util.HashMap;
import java.util.Map;
import nl.fh.gamereport.GameResult;
import nl.fh.player.Player;

/**
 * 
 * 
 */
public class MatchResult {

    private final Player player1;
    private final Player player2;
    
    private int score = 0;
    
    private Map<GameResult, Integer> player1White = new HashMap<GameResult, Integer>();
    private Map<GameResult, Integer> player2White = new HashMap<GameResult, Integer>(); 

    private int count = 0;
    /**
     * Object to store the result of a match between two players
     * 
     * @param player1
     * @param player2 
     */
    MatchResult(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        
        for(GameResult result : GameResult.values()){
            player1White.put(result, 0);
            player2White.put(result, 0);
        }
        
    }

    /**
     * Calling this method adds the outcome of a game to the overall score sheet
     * 
     * @param player1 the player playing white
     * @param player2 the player playing black
     * @param gameResult 
     */
    void add(Player player1, Player player2, GameResult gameResult) {
        if(player1.equals(this.player1) && player2.equals(this.player2)){
            int current = player1White.get(gameResult);
            player1White.put(gameResult, current+1);
            score += gameResult.getValue();      
            
        } else if(player1.equals(this.player2) && player2.equals(this.player1)){
            int current = player2White.get(gameResult);
            player2White.put(gameResult, current+1); 
            score += - gameResult.getValue();
            
        } else {
            throw new IllegalArgumentException("Incorrect players for this MatchResult");
        }   
        
        count += 1;
        
//        System.out.println(player1);
//        System.out.println(player2);
//        System.out.println(count);
//        System.out.println(gameResult);
//        System.out.println(score);
//        System.out.println("\n");
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("=== MatchResult ===\n");
        sb.append(player1);
        sb.append("\n");
        sb.append(player2);
        sb.append("\n");   
        sb.append("\n");             
        
        sb.append(count);
        sb.append(" games played\n");
        
        sb.append("score differential: ");
        sb.append(score);
        sb.append("\n");
        
        sb.append(player1.toString());
        sb.append(" playing as white\n");
        for(GameResult g : GameResult.values()){
           sb.append(g.toString());
           sb.append(" : ");
           sb.append(player1White.get(g));
           sb.append("\n");
        }
        sb.append("\n");
        sb.append(player2.toString());
        sb.append(" playing as white\n");
        for(GameResult g : GameResult.values()){
           sb.append(g.toString());
           sb.append(" : ");
           sb.append(player2White.get(g));
           sb.append("\n");
        }  
        
        return sb.toString();
    }

}
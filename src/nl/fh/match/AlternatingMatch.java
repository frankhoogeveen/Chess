/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;
import nl.fh.player.Player;
import nl.fh.rule.GameDriver;

/**
 * 
 * 
 */
public class AlternatingMatch implements Match {
    
    private final int nGames;
    private final GameDriver driver;

    public AlternatingMatch(int nGames, GameDriver driver) {
        this.nGames = nGames;
        this.driver = driver;
    }

    @Override
    public MatchReport play(Player player1, Player player2, GameFilter filter) {
        
        MatchReport result = new MatchReport(player1, player2, filter);
        GameReport report = null;
        
        for(int i = 0; i < nGames; i++){
            if((i%2)==0){
                report = driver.playGame(player1, player2);
                result.add(player1, player2, report);
            } else{
                report = driver.playGame(player2, player1);
                result.add(player2, player1, report);               
            }         
        }
        return result;
    }
}
/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

import java.util.ArrayList;
import java.util.List;
import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamereport.filter.TransparentFilter;
import nl.fh.player.Player;
import nl.fh.rules.Rules;

/**
 * 
 * 
 */
public class AlternatingMatch implements Match {

    private final Player player1;
    private final Player player2;
    
    private final int nGames;
    private final Rules rules;
    
    private GameFilter filter;
    private final ArrayList<GameReport> gameSelection;

    public AlternatingMatch(Player player1, Player player2, int nGames, Rules rules) {
        this.player1 = player1;
        this.player2 = player2;
        this.nGames = nGames;
        this.rules = rules;
        
        this.filter = new TransparentFilter();
        this.gameSelection = new ArrayList<GameReport>();
    }

    @Override
    public MatchResult play() {
        
        MatchResult result = new MatchResult(player1, player2);
        GameReport report = null;
        
        for(int i = 0; i < nGames; i++){
            if((i%2)==0){
                report = rules.playGame(player1, player2);
                GameResult gameResult = report.getGameResult();
                result.add(player1, player2, gameResult);
            } else{
                report = rules.playGame(player2, player1);
                GameResult gameResult = report.getGameResult();
                result.add(player2, player1, gameResult);               
            }
            
            conditionallyStoreReport(report);            
        }
        


        
        return result;
    }

    private void conditionallyStoreReport(GameReport report) {
        if(this.filter.retain(report)){
            this.gameSelection.add(report);        
        }
    }

    @Override
    public void setFilter(GameFilter filter) {
        this.filter = filter;
    }

    @Override
    public List<GameReport> getReports() {
        return this.gameSelection;
    }
}
/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

import nl.fh.gamereport.GameResult;
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

    public AlternatingMatch(Player player1, Player player2, int nGames, Rules rules) {
        this.player1 = player1;
        this.player2 = player2;
        this.nGames = nGames;
        this.rules = rules;
    }

    @Override
    public MatchResult play() {
        
        MatchResult result = new MatchResult(player1, player2);
        
        for(int i = 0; i < nGames; i++){
            if((i%2)==0){
                GameResult gameResult = rules.playGame(player1, player2).getGameResult();
                result.add(player1, player2, gameResult);
            } else{
                GameResult gameResult = rules.playGame(player2, player1).getGameResult();
                result.add(player2, player1, gameResult);               
            }
        }
        
        return result;
    }
}
/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.match.AlternatingMatch;
import nl.fh.match.Match;
import nl.fh.match.MatchResult;
import nl.fh.metric.Shannon;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.player.random.RandomPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 * 
 */
public class job_003_match_between_random_and_static_evaluation {
    
    public static void main(String[] args){
        Rules rules = new SimpleRules();
        
        Player playerR = new RandomPlayer();
        Player playerM = MetricPlayer.getInstance(new Shannon());
        
        int nGames = 30 * 1000;
        
        Match match = new AlternatingMatch(playerR, playerM, nGames, rules);
        MatchResult result = match.play();
        
        System.out.println(result);
    }

}
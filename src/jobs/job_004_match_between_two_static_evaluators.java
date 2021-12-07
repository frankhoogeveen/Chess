/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.filter.TransparentFilter;
import nl.fh.match.AlternatingMatch;
import nl.fh.match.Match;
import nl.fh.match.MatchResult;
import nl.fh.metric.ShannonNoise;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 * 
 */
public class job_004_match_between_two_static_evaluators {
    
    public static void main(String[] args){
        Rules rules = new SimpleRules();
        
        Player player1 = MetricPlayer.getInstance(new ShannonNoise());
        Player player2 = MetricPlayer.getInstance(new ShannonNoise());
        
        int nGames = 4;
        GameFilter filter = new TransparentFilter();
        
        Match match = new AlternatingMatch(nGames, rules);
        
        MatchResult result = match.play(player1, player2, filter);
            
        System.out.println(result.toPGN());
    }

}
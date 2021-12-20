/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.filter.TransparentFilter;
import nl.fh.gamestate.GameState;
import nl.fh.match.AlternatingMatch;
import nl.fh.match.Match;
import nl.fh.match.MatchResult;
import nl.fh.metric.MaterialCountMetric;
import nl.fh.metric.utilities.NoiseAdder;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 * 
 */
public class Job_007_match_negamax_player {
    
    public static void main(String[] args){
        Rules rules = new SimpleRules();
        
        
        Metric<GameState> baseMetric = new MaterialCountMetric();              
         
        int depth1 = 2;
        double sigma1 = 0.0;
        Metric<GameState> metric1 = new NoiseAdder(sigma1,new NegaMax(baseMetric, depth1));
        Player player1 = MetricPlayer.getInstance(metric1);
        
        int depth2 = 3;
        double sigma2 = 0.0;
        Metric<GameState> metric2 = new NoiseAdder(sigma2,new NegaMax(baseMetric, depth2));
        Player player2 = MetricPlayer.getInstance(metric2);        
        
        int nGames = 2;
        
        GameFilter filter = new TransparentFilter();
        
        Match match = new AlternatingMatch(nGames, rules);
        
        MatchResult result = match.play(player1, player2, filter);
            
        System.out.println(result.toPGN());
    }

}
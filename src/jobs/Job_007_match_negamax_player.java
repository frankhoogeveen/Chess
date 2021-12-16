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
import nl.fh.metric.ShannonMetric;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.minimax.SearchMode;
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
        
        // Metric<GameState> metric = new ShannonNoisyMetric();
        Metric<GameState> metric = new ShannonMetric();        
        int depth = 1;        
        
        //Player playerR = new RandomPlayer();
        //Player player1 = MetricPlayer.getInstance(new ShannonNoisyMetric());
        Player player1 = MetricPlayer.getInstance(new NegaMax(metric, depth, SearchMode.MINIMAX));
        Player player2 = MetricPlayer.getInstance(new NegaMax(metric, depth, SearchMode.MAXIMIN));
        
        int nGames = 1;
        
//        GameFilter filterR = new WinnerFilter(player1);
//        GameFilter filterM = new CapFilter(10, new NotFilter(filterR));
//        GameFilter filter = new OrFilter(filterR, filterM);  
        GameFilter filter = new TransparentFilter();
        
        Match match = new AlternatingMatch(nGames, rules);
        
        MatchResult result = match.play(player1, player2, filter);
            
        System.out.println(result.toPGN());
    }

}
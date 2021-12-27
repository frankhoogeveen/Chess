/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.PGNformatter;
import nl.fh.gamereport.filter.TransparentFilter;
import nl.fh.gamestate.GameState;
import nl.fh.match.AlternatingMatch;
import nl.fh.match.Match;
import nl.fh.match.MatchReport;
import nl.fh.match.MatchReportFormatter;
import nl.fh.metric.MaterialCountMetric;
import nl.fh.metric.utilities.NoiseAdder;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rules.Chess;

/**
 * 
 * 
 */
public class Job_004_match_between_two_static_evaluators {
    
    public static void main(String[] args){
        
        double sigma1 = 0.3;
        Metric<GameState> metric1 = new NoiseAdder(sigma1, MaterialCountMetric.getWrappedInstance());
        Player player1 = MetricPlayer.getInstance(metric1);
        
        double sigma2 = 1.0;
        Metric<GameState> metric2 = new NoiseAdder(sigma2, MaterialCountMetric.getWrappedInstance());
        Player player2 = MetricPlayer.getInstance(metric2);
        
        int nGames = 4;
        GameFilter filter = new TransparentFilter();
        
        Match match = new AlternatingMatch(nGames, Chess.getGameDriver());
        
        MatchReport result = match.play(player1, player2, filter);
            
        MatchReportFormatter formatter = new PGNformatter(Chess.getGameDriver());
        System.out.println(formatter.formatMatch(result));
    }

}
/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.chess.PGNreportFormatter;
import nl.fh.gamereport.filter.TransparentFilter;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.match.AlternatingMatch;
import nl.fh.match.Match;
import nl.fh.match.MatchReport;
import nl.fh.match.MatchReportFormatter;
import nl.fh.metric.chess.MaterialCountMetric;
import nl.fh.metric.utilities.NoiseAdder;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rule.chess.FIDEchess;

/**
 * 
 * 
 */
public class Job_004_match_between_two_static_evaluators {
    
    public static void main(String[] args){
        
        double sigma1 = 0.3;
        Metric<ChessState> metric1 = new NoiseAdder(sigma1, MaterialCountMetric.getWrappedInstance());
        Player player1 = MetricPlayer.getInstance(metric1);
        
        double sigma2 = 1.0;
        Metric<ChessState> metric2 = new NoiseAdder(sigma2, MaterialCountMetric.getWrappedInstance());
        Player player2 = MetricPlayer.getInstance(metric2);
        
        int nGames = 4;
        GameFilter filter = new TransparentFilter();
        
        Match match = new AlternatingMatch(nGames, FIDEchess.getGameDriver());
        
        MatchReport result = match.play(player1, player2, filter);
            
        MatchReportFormatter formatter = new PGNreportFormatter(FIDEchess.getGameDriver());
        System.out.println(formatter.formatMatch(result));
    }

}
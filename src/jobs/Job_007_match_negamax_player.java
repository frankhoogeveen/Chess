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
import nl.fh.metric.minimax.NegaMax;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rules.FIDEchess;

/**
 * 
 * 
 */
public class Job_007_match_negamax_player {
    
    public static void main(String[] args){
        
        
        Metric<GameState> baseMetric = MaterialCountMetric.getWrappedInstance();   
         
        int depth1 = 2;
        double sigma1 = 0.0;
        Metric<GameState> metric1 = new NoiseAdder(sigma1,new NegaMax(baseMetric, FIDEchess.getGameDriver().getMoveGenerator(), depth1));
        Player player1 = MetricPlayer.getInstance(metric1);
        
        int depth2 = 2;
        double sigma2 = 0.3;
        Metric<GameState> metric2 = new NoiseAdder(sigma2,new NegaMax(baseMetric, FIDEchess.getGameDriver().getMoveGenerator(), depth2));
        Player player2 = MetricPlayer.getInstance(metric2);        
        
        int nGames = 2;
        
        GameFilter filter = new TransparentFilter();
        
        Match match = new AlternatingMatch(nGames, FIDEchess.getGameDriver());
        
        MatchReport result = match.play(player1, player2, filter);
        MatchReportFormatter formatter = new PGNformatter(FIDEchess.getGameDriver());
        System.out.println(formatter.formatMatch(result));        

    }

}
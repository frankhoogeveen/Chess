/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.PGNformatter;
import nl.fh.gamereport.filter.CapFilter;
import nl.fh.gamereport.filter.NotFilter;
import nl.fh.gamereport.filter.OrFilter;
import nl.fh.gamereport.filter.WinnerFilter;
import nl.fh.match.AlternatingMatch;
import nl.fh.match.Match;
import nl.fh.match.MatchReport;
import nl.fh.match.MatchReportFormatter;
import nl.fh.metric.MaterialCountMetric;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.player.random.RandomPlayer;
import nl.fh.rules.Chess;

/**
 * 
 * 
 */
public class Job_003_match_between_random_and_static_evaluation {
    
    public static void main(String[] args){
        
        Player playerR = new RandomPlayer();
        Player playerM = MetricPlayer.getInstance(MaterialCountMetric.getWrappedInstance());
        
        int nGames = 30;
        
        GameFilter filterR = new WinnerFilter(playerR);
        GameFilter filterM = new CapFilter(10, new NotFilter(filterR));
        GameFilter filter = new OrFilter(filterR, filterM);        
        
        Match match = new AlternatingMatch(nGames, Chess.getGameDriver());
        
        MatchReport result = match.play(playerR, playerM, filter);
            
        MatchReportFormatter formatter = new PGNformatter(Chess.getGameDriver());
        System.out.println(formatter.formatMatch(result));
    }

}
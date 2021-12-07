/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.filter.CapFilter;
import nl.fh.gamereport.filter.NotFilter;
import nl.fh.gamereport.filter.OrFilter;
import nl.fh.gamereport.filter.WinnerFilter;
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
        
        int nGames = 10;
        
        GameFilter filterR = new WinnerFilter(playerR);
        GameFilter filterM = new CapFilter(20, new NotFilter(filterR));
        GameFilter filter = new OrFilter(filterR, filterM);        
        
        Match match = new AlternatingMatch(playerR, playerM, nGames, rules);
        match.setFilter(filter);
        
        MatchResult result = match.play();
        
        for(GameReport report : match.getReports()){
            System.out.println(report.toPGN(rules));
            System.out.print("\n-----------------------------------------\n");
        }        
        System.out.println(result);
    }

}
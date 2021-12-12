/*
 * License: GPL v3
 * 
 */

package jobs;

import java.util.List;
import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.filter.TransparentFilter;
import nl.fh.match.AlternatingMatch;
import nl.fh.match.Match;
import nl.fh.match.MatchResult;
import nl.fh.metric.ShannonMetric;
import nl.fh.parser.PGN_Reader;
import nl.fh.parser.TolerantReader;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.player.random.RandomPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * Follow up after an intermittent bug in MatchTest
 * 
 */
public class job_006_reproduce_and_analyze_intermittent_bug {
        
    
    public static void main(String[] args){
        Rules rules = new SimpleRules();
        
        Player playerR = new RandomPlayer();
        Player playerM = MetricPlayer.getInstance(new ShannonMetric());
        
        int nRounds = 3000;
        int nGames = 5;     
        GameFilter filter = new TransparentFilter();
        Match match = new AlternatingMatch(nGames, rules);
        
        for(int iRound = 0; iRound < nRounds; iRound++){
            
            System.out.println("Starting round " + iRound + "/" + nRounds);
            System.out.flush();
            
            for(int iGame = 0; iGame<nGames; iGame++){
                
                MatchResult result = match.play(playerR, playerM, filter);
                String pgn = result.toPGN();

                PGN_Reader reader = new TolerantReader();
                List<GameReport> result2 = reader.getGames(pgn, rules);
                
                if(result2.size() != nGames){
                    System.out.println(pgn);
                    System.out.println("\n============\n");
                    
                    for(GameReport report : result2){
                        System.out.println(report.toPGN());
                        System.out.println("\n----------------\n");
                    }
                    
                    System.exit(-1);

                }
            }
        }  
        System.exit(0);
    }

}
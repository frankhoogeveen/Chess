/*
 * License: GPL v3
 * 
 */

package jobs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import nl.fh.rules.Chess;

/**
 * Follow up after an intermittent bug in MatchTest
 * 
 * In one run with nGames = 100, the resulting pgn decoded into 99 games.
 * So somehow, under some circumstances a game can get lost. 
 * 
 * The purpose of this job is to catch enough data to analyze this intermittent
 * bug.
 * 
 */
public class Job_006_reproduce_and_analyze_intermittent_bug {
    
    private static final String filePath = "../out/job_006_out.txt";
        
    
    public static void main(String[] args){
        
        Player playerR = new RandomPlayer();
        Player playerM = MetricPlayer.getInstance(new ShannonMetric());
        
        int nRounds = 200;
        int nGames = 100;     
        GameFilter filter = new TransparentFilter();
        Match match = new AlternatingMatch(nGames, Chess.getGameDriver());
        
        for(int iRound = 0; iRound < nRounds; iRound++){
            
            System.out.println("Starting round " + iRound + "/" + nRounds);
            System.out.flush();
            
            for(int iGame = 0; iGame<nGames; iGame++){
                
                MatchResult result = match.play(playerR, playerM, filter);
                String pgn = result.toPGN();

                PGN_Reader reader = new TolerantReader();
                List<GameReport> result2 = reader.getGames(pgn, Chess.getGameDriver());
                
                if(result2.size() != nGames){
                    saveEvidence(pgn, result2);

                }
            }
        }  
        System.exit(0);
    }

    private static void saveEvidence(String pgn, List<GameReport> result2) {
        try {
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(pgn);
                file.write("=====================");
                for(GameReport report : result2){
                    file.write(report.toPGN());
                    file.write("\n----------------\n");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Job_006_reproduce_and_analyze_intermittent_bug.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.exit(-1);
    }

}
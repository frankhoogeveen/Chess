/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamereport.chess.PGNreportFormatter;
import nl.fh.gamestate.MoveFormatter;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.Color;
import nl.fh.gamestate.chess.format.ASCIIformatter;
import nl.fh.gamestate.chess.format.PGNmoveFormatter;
import nl.fh.player.Player;
import nl.fh.player.random.RandomPlayer;
import nl.fh.player.terminal.TerminalPlayer;
import nl.fh.rule.chess.FIDEchess;
import nl.fh.rule.GameDriver;

/**
 * 
 * 
 */
public class Job_002_game_between_terminal_and_random_player {
    
    public static void main(String[] args){
      Player player1 = new RandomPlayer();
      
      GameDriver<ChessState> driver = FIDEchess.getGameDriver();
      MoveFormatter<ChessState> mFormatter = new PGNmoveFormatter();
      ASCIIformatter sFormatter = new ASCIIformatter(Color.WHITE);                  
              
      Player player2 = new TerminalPlayer<ChessState>(driver, mFormatter, sFormatter);
      
      GameReport report;
      if(Math.random() > 0.5){
        sFormatter.setColor(Color.BLACK);
        report = FIDEchess.getGameDriver().playGame(player1, player2);
      } else {
        sFormatter.setColor(Color.WHITE);          
        report = FIDEchess.getGameDriver().playGame(player2, player1);          
      }
      
      GameReportFormatter formatter = new PGNreportFormatter(FIDEchess.getGameDriver());
      System.out.println(formatter.formatGame(report));
    }
}
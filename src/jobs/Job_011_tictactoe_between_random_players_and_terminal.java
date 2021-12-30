/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.tictactoe.TicTacToeGameReportFormatter;
import nl.fh.rule.tictactoe.TicTacToe;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamestate.MoveFormatter;
import nl.fh.gamestate.tictactoe.TicTacToeASCIIformatter;
import nl.fh.gamestate.tictactoe.TicTacToeFormatter;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import nl.fh.player.Player;
import nl.fh.player.random.RandomPlayer;
import nl.fh.player.terminal.TerminalPlayer;
import nl.fh.rule.GameDriver;

/**
 * 
 * 
 */
public class Job_011_tictactoe_between_random_players_and_terminal {
    
    public static void main(String[] args){
      
      // player 1 runs the terminal
      GameDriver<TicTacToeState> driver = TicTacToe.getGameDriver();
      MoveFormatter<TicTacToeState> mFormatter = new TicTacToeFormatter();
      TicTacToeASCIIformatter<TicTacToeState> sFormatter = new TicTacToeASCIIformatter();                  
              
      Player player1 = new TerminalPlayer<TicTacToeState>(driver, mFormatter, sFormatter);
      
      // player 2 is a random player 
      Player player2 = new RandomPlayer();      
      
      GameReport report;
      if(Math.random() > 0.5){
        report = TicTacToe.getGameDriver().playGame(player1, player2);
      } else {      
        report = TicTacToe.getGameDriver().playGame(player2, player1);          
      }
      
      GameReportFormatter formatter = new TicTacToeGameReportFormatter();
      System.out.println(formatter.formatGame(report));
    }
}
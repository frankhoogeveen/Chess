package nl.fh.gamestate.tictactoe;

import java.util.Set;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.Move;
import nl.fh.rule.MoveGenerator;
import nl.fh.rule.tictactoe.TicTacToeMoveGenerator;
import nl.fh.rule.tictactoe.TicTacToeResultArbiter;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * License: GPL v3
 * 
 */

/**
 * 
 * 
 */
public class TicTacToeArbiterTest {

    @Test
    public void WinningGameTest(){
        TicTacToeResultArbiter arbiter = new TicTacToeResultArbiter();
        GameReport<TicTacToeState> report = new GameReport<TicTacToeState>();
        MoveGenerator<TicTacToeState> moveGenerator = new TicTacToeMoveGenerator();
        
        TicTacToeState state = new TicTacToeState();
        
        report.addGameState(state);
        Set<Move<TicTacToeState>> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        assertEquals(GameResult.UNDECIDED, arbiter.determineResult(report, legalMoves));
        
// 1        
        TicTacToeMove move = new TicTacToeMove("a1");
        state = move.applyTo(state);
        report.addPly(move, state);
        legalMoves = moveGenerator.calculateAllLegalMoves(state);
        assertEquals(GameResult.UNDECIDED, arbiter.determineResult(report, legalMoves));           
        
        move = new TicTacToeMove("c1");
        state = move.applyTo(state);
        report.addPly(move, state);
        legalMoves = moveGenerator.calculateAllLegalMoves(state);
        assertEquals(GameResult.UNDECIDED, arbiter.determineResult(report, legalMoves));  
        
// 2        
        move = new TicTacToeMove("a2");
        state = move.applyTo(state);
        report.addPly(move, state);
        legalMoves = moveGenerator.calculateAllLegalMoves(state);
        assertEquals(GameResult.UNDECIDED, arbiter.determineResult(report, legalMoves));   
        
        move = new TicTacToeMove("c2");
        state = move.applyTo(state);
        report.addPly(move, state);
        legalMoves = moveGenerator.calculateAllLegalMoves(state);
        assertEquals(GameResult.UNDECIDED, arbiter.determineResult(report, legalMoves)); 
// 3        
        move = new TicTacToeMove("a3");
        state = move.applyTo(state);
        report.addPly(move, state);
        legalMoves = moveGenerator.calculateAllLegalMoves(state);
        
//        for(TicTacToeState s: report.getStateList()){
//            System.out.println(s);
//        }
        
        assertEquals(GameResult.WIN_FIRST_MOVER, arbiter.determineResult(report, legalMoves));              
    }
    
    @Test
    public void WinningGameTest2(){
        TicTacToeResultArbiter arbiter = new TicTacToeResultArbiter();
        GameReport<TicTacToeState> report = new GameReport<TicTacToeState>();
        MoveGenerator<TicTacToeState> moveGenerator = new TicTacToeMoveGenerator();
        
        TicTacToeState state = new TicTacToeState();
        
        report.addGameState(state);
        Set<Move<TicTacToeState>> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        
        String[] moveList = {"a1", "b1", "b2", "b3", "c3"};
        
        for(String str : moveList){
            assertEquals(GameResult.UNDECIDED, arbiter.determineResult(report, legalMoves));     
            
            TicTacToeMove m = new TicTacToeMove(str);
            state = m.applyTo(state);
            legalMoves = moveGenerator.calculateAllLegalMoves(state);
            
            report.addPly(m, state);
        }
   
        assertEquals(GameResult.WIN_FIRST_MOVER, arbiter.determineResult(report, legalMoves));                   
    }
    
    @Test
    public void DrawGameTest(){
        TicTacToeResultArbiter arbiter = new TicTacToeResultArbiter();
        GameReport<TicTacToeState> report = new GameReport<TicTacToeState>();
        MoveGenerator<TicTacToeState> moveGenerator = new TicTacToeMoveGenerator();
        
        TicTacToeState state = new TicTacToeState();
        
        report.addGameState(state);
        Set<Move<TicTacToeState>> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        
        String[] moveList = {"b2", "a1", "a2", "c2", "b3", "b1", "c1", "a3", "c3"};
        
        for(String str : moveList){
            assertEquals(GameResult.UNDECIDED, arbiter.determineResult(report, legalMoves));     
            
            TicTacToeMove m = new TicTacToeMove(str);
            state = m.applyTo(state);
            legalMoves = moveGenerator.calculateAllLegalMoves(state);
            
            report.addPly(m, state);
        }
   
        assertEquals(GameResult.DRAW, arbiter.determineResult(report, legalMoves));                   
    }    
    
    @Test
    public void LossGameTest(){
        TicTacToeResultArbiter arbiter = new TicTacToeResultArbiter();
        GameReport<TicTacToeState> report = new GameReport<TicTacToeState>();
        MoveGenerator<TicTacToeState> moveGenerator = new TicTacToeMoveGenerator();
        
        TicTacToeState state = new TicTacToeState();
        
        report.addGameState(state);
        Set<Move<TicTacToeState>> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        
        String[] moveList = {"a1", "b2", "b1", "a2", "c3", "c2"};
        
        for(String str : moveList){
            assertEquals(GameResult.UNDECIDED, arbiter.determineResult(report, legalMoves));     
            
            TicTacToeMove m = new TicTacToeMove(str);
            state = m.applyTo(state);
            legalMoves = moveGenerator.calculateAllLegalMoves(state);
            
            report.addPly(m, state);
        }
   
        assertEquals(GameResult.WIN_SECOND_MOVER, arbiter.determineResult(report, legalMoves));                   
    }        

}
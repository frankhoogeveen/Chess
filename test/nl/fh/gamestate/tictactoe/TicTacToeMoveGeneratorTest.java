/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.tictactoe;

import java.util.Set;
import nl.fh.gamestate.Move;
import nl.fh.rule.tictactoe.TicTacToeMoveGenerator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * 
 * 
 */
public class TicTacToeMoveGeneratorTest {

    @Test
    public void EmptyTest(){
        TicTacToeMoveGenerator moveGenerator = new TicTacToeMoveGenerator();
        TicTacToeState state = new TicTacToeState();
        
        Set<Move<TicTacToeState>> moves = moveGenerator.calculateAllLegalMoves(state);
        assertEquals(9, moves.size());
    }
    
    @Test
    public void OneMoveTest(){
        TicTacToeMoveGenerator moveGenerator = new TicTacToeMoveGenerator();
        TicTacToeState state = new TicTacToeState();
        
        Move<TicTacToeState> move = new TicTacToeMove(0,2);
        
        TicTacToeState state2 = move.applyTo(state);
        
        Set<Move<TicTacToeState>> moves = moveGenerator.calculateAllLegalMoves(state2);
        assertEquals(8, moves.size());   
        
        assertFalse(moves.contains(move));
        
    }
    
    @Test
    public void TwoMoveTest(){
        TicTacToeMoveGenerator moveGenerator = new TicTacToeMoveGenerator();
        TicTacToeState state = new TicTacToeState();
        
        Move<TicTacToeState> move = new TicTacToeMove(0,2);
        TicTacToeState state2 = move.applyTo(state);
        
        Move<TicTacToeState> move2 = new TicTacToeMove(1,0);
        TicTacToeState state3 = move2.applyTo(state2);        
        
        Set<Move<TicTacToeState>> moves = moveGenerator.calculateAllLegalMoves(state3);
        assertEquals(7, moves.size());   
        
        assertFalse(moves.contains(move));
        assertFalse(moves.contains(move2));            
    }    
    
    @Test
    public void AllMoveTest(){
        TicTacToeMoveGenerator moveGenerator = new TicTacToeMoveGenerator();
        TicTacToeState state = new TicTacToeState(); 
        
        Set<Move<TicTacToeState>> moves = moveGenerator.calculateAllLegalMoves(state);
        for(Move<TicTacToeState> m : moves){
            state = m.applyTo(state);
        }
        
        moves = moveGenerator.calculateAllLegalMoves(state);      
        
        assertEquals(0, moves.size());           
    }        
    
}
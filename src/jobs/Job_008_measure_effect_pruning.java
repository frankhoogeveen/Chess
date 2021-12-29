/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamestate.chess.ChessState;
import nl.fh.metric.chess.MaterialCountMetric;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.minimax.NegaMaxAlphaBeta;
import nl.fh.metric.minimax.NegaMaxGen3;
import nl.fh.metric.utilities.Counter;
import nl.fh.metric.utilities.TableBuffer;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rule.chess.FIDEchess;
import nl.fh.rule.GameDriver;
import nl.fh.rule.MoveGenerator;

/**
 * 
 */
public class Job_008_measure_effect_pruning {
    
    private static GameDriver gameDriver = FIDEchess.getGameDriver();
    private static MoveGenerator moveGenerator = gameDriver.getMoveGenerator();
    
    public static void main(String[] args){

        Metric<ChessState> baseMetric = MaterialCountMetric.getWrappedInstance();
        
        Counter<ChessState> counter = new Counter<ChessState>(baseMetric);
        NegaMax<ChessState> nega = new NegaMax<ChessState>(counter, moveGenerator, 0);
        
        Counter<ChessState> counterAB = new Counter<ChessState>(baseMetric);        
        NegaMaxAlphaBeta<ChessState> negaAB = new NegaMaxAlphaBeta<ChessState>(counterAB, moveGenerator, 0);
        
        int size2 = 10000;
        Counter<ChessState> counterAB2 = new Counter<ChessState>(baseMetric);  
        TableBuffer<ChessState> buffered = new TableBuffer<ChessState>(counterAB2, size2);        
        NegaMaxAlphaBeta<ChessState> negaAB2= new NegaMaxAlphaBeta<ChessState>(buffered, moveGenerator, 0);
        
        int size3 = 20000;
        Counter<ChessState> counterAB3 = new Counter<ChessState>(baseMetric);  
        TableBuffer<ChessState> buffered3 = new TableBuffer<ChessState>(counterAB3, size3);        
        NegaMaxGen3<ChessState> negaAB3= new NegaMaxGen3<ChessState>(buffered3, moveGenerator, 0);      

        nega.setDepth(3);
        negaAB.setDepth(3);
        negaAB2.setDepth(3); 
        negaAB3.setDepth(5);            
        
        String fen = "r2qkb1r/pppbpppp/2n5/1B1pN3/3PnB2/4P3/PPP2PPP/RN1QK2R b KQkq - 7 7";
//        String fen = "k7/3q4/8/8/8/4Q3/7K/8 w - - 0 1";
        ChessState state  = ChessState.fromFEN(fen);
        
        System.out.println(fen);
        System.out.println();
        
        counter.setCount(0);
        double value = nega.eval(state);
        System.out.println(nega.getDescription());
        System.out.println(value);
        System.out.println(counter.getCount());
        System.out.println();
        
        counterAB.setCount(0);
        double valueAB = negaAB.eval(state);
        System.out.println(negaAB.getDescription());           
        System.out.println(valueAB);
        System.out.println(counterAB.getCount());
        System.out.println();    
               
//        counterAB2.setCount(0);
//        double valueAB2= negaAB2.eval(state);
//        System.out.println(negaAB2.getDescription());            
//        System.out.println(valueAB2);
//        System.out.println(counterAB2.getCount());
//        System.out.println(); 
        
        counterAB3.setCount(0);
        double valueAB3= negaAB3.eval(state);
        System.out.println(negaAB3.getDescription());            
        System.out.println(valueAB3);
        System.out.println(counterAB3.getCount());
        System.out.println();         
        
//         OutputToFile.write("buffer", buffered.toCSV());
        
    }

}
/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.metric.ShannonMetric;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 * 
 */
public class NegaMaxPositionTest {
/**
 * 
 * The position studied here is from a depth=2 game between two 
 * NegaMax(ShannonMetric) players. 
 * 
[Event "null"]
[Site "null"]
[Date "2021.12.16 11:55:00"]
[Round "null"]
[White "nl.fh.player.evalplayer.MetricPlayer@106d69c"]
[Black "nl.fh.player.evalplayer.MetricPlayer@52e922"]
[Result "DRAW_BY_THREEFOLD_REPETITION"]

1. d3 e5 2. e4 Qh4 3. Bg5 Qxg5 4. Qh5 Qxh5 5. d4 exd4 6. Bb5 Qxb5 
7. Nf3 Qxb2 8. c4 Qxa1 9. Nxd4 Qxb1+ 10. Kd2 Qxh1 11. Kc3 Qa1+ 12. Kd3 Qd1+ 
13. Kc3 Qa1+ 14. Kd3 Qd1+ 15. Kc3 Qa1+ 
1/2-1/2
* 
* Already in move 3 we see 3.Bg5 which gets the reply 3... Qxb5. 
* This test case tries to understand why this is played and if this is correct
* 
 */
    
    
    private final double delta = 1.e-8;
    
    @Test
    public void testMoveConsidered(){
        // position from https://lichess.org/igoizkyc#23
        
        // set up the position after 2...Qh4
        String fen = "rnb1kbnr/pppp1ppp/8/4p3/4P2q/3P4/PPP2PPP/RNBQKBNR w KQkq - 1 3";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        
        Move moveBg5 = PieceMove.getInstance( Field.getInstance("c1"), Field.getInstance("g5"));
        Move movea3  = PieceMove.getInstance( Field.getInstance("a2"), Field.getInstance("a3"));
        
        Set<Move> moves = state.getLegalMoves();
        assertTrue(moves.contains(moveBg5));
        assertTrue(moves.contains(movea3));        
        
    }
    
    @Test
    public void testMoveCalculated(){
        // position from https://lichess.org/igoizkyc#23
        
        // set up the position after 2...Qh4
        String fen = "rnb1kbnr/pppp1ppp/8/4p3/4P2q/3P4/PPP2PPP/RNBQKBNR w KQkq - 1 3";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);

        Metric<GameState> shannon = new ShannonMetric();
        int depth = 2;
        NegaMax nega = new NegaMax(shannon, depth, SearchMode.MAXIMIN);
        Player player = MetricPlayer.getInstance(nega);
        
        Move calculatedMove = player.getMove(state);
        Move moveBg5 = PieceMove.getInstance( Field.getInstance("c1"), Field.getInstance("g5"));
        
        assertEquals(moveBg5, calculatedMove);  // this seems weird
        
        GameState state1 = calculatedMove.applyTo(state);
        for(Move m : state1.getLegalMoves()){
            
        }
    }  
    
    @Test
    public void testMoveCalculatedDepthOne(){
        // position from https://lichess.org/igoizkyc#23
        
        // set up the position after 2...Qh4
        String fen = "rnb1kbnr/pppp1ppp/8/4p3/4P2q/3P4/PPP2PPP/RNBQKBNR w KQkq - 1 3";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);

        Metric<GameState> shannon = new ShannonMetric();
        int depth = 1;
        NegaMax nega = new NegaMax(shannon, depth, SearchMode.MAXIMIN);
        Player player = MetricPlayer.getInstance(nega);
        
        Move calculatedMove = player.getMove(state);
        Move moveQh5 = PieceMove.getInstance( Field.getInstance("d1"), Field.getInstance("h5"));
        
        assertEquals(moveQh5, calculatedMove);  // this seems weird, even at depth one
    }  
    
    @Test
    public void testMoveCalculatedDepthZero(){
        // position from https://lichess.org/igoizkyc#23
        
        // set up the position after 2...Qh4
        String fen = "rnb1kbnr/pppp1ppp/8/4p3/4P2q/3P4/PPP2PPP/RNBQKBNR w KQkq - 1 3";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);

        Metric<GameState> shannon = new ShannonMetric();
        int depth = 0;
        NegaMax nega = new NegaMax(shannon, depth, SearchMode.MAXIMIN);
        Player player = MetricPlayer.getInstance(nega);
        
        Move calculatedMove = player.getMove(state);
        Move moveQh5 = PieceMove.getInstance( Field.getInstance("d1"), Field.getInstance("h5"));
        
        assertEquals(moveQh5, calculatedMove); 
    }
    
    @Test
    public void testMoveShannon(){
        // position from https://lichess.org/igoizkyc#23
        
        // set up the position after 2...Qh4
        String fen = "rnb1kbnr/pppp1ppp/8/4p3/4P2q/3P4/PPP2PPP/RNBQKBNR w KQkq - 1 3";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);

        Metric<GameState> shannon = new ShannonMetric();
        Player player = MetricPlayer.getInstance(shannon);
        
        Move calculatedMove = player.getMove(state);
        Move moveQh5 = PieceMove.getInstance( Field.getInstance("d1"), Field.getInstance("h5"));
        
        assertEquals(moveQh5, calculatedMove); 
    }  
    
    @Test
    public void testMoveCalculatedDepthOneManualMiniMax(){
        // position from https://lichess.org/igoizkyc#23
        
        // set up the position after 2...Qh4
        String fen = "rnb1kbnr/pppp1ppp/8/4p3/4P2q/3P4/PPP2PPP/RNBQKBNR w KQkq - 1 3";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);

        Metric<GameState> shannon = new ShannonMetric();

        Map<GameState, Double> map1  = new HashMap<GameState, Double>();
        for(GameState state1 : state.getChildren()){
            // minimize the value of the second move;
            GameState stateMin = null;
            double valueMin = + Double.MAX_VALUE;
            for(GameState s : state1.getChildren()){
                double value2 = shannon.eval(s);
                if(value2 < valueMin){
                    valueMin = value2;
                    stateMin = s;
                }                
            }
            
            map1.put(state1, valueMin);
        }
        // maximize the value of the first move
        GameState stateMax = null;
        double valueMax = - Double.MAX_VALUE;
        for(GameState s : map1.keySet()){
            if(map1.get(s) > valueMax){
                valueMax = map1.get(s);
                stateMax = s;
            }
        }
        Move moveQh5 = PieceMove.getInstance( Field.getInstance("d1"), Field.getInstance("h5"));
        GameState stateQh5 = moveQh5.applyTo(state);
        System.out.println(stateQh5.toFEN());
        
        System.out.println(stateMax.toFEN());
        System.out.println(valueMax);
        
        
        
    }    
}
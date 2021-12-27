package nl.fh.metric.negamax;

import java.util.ArrayList;
import java.util.List;
import nl.fh.gamestate.GameState;
import nl.fh.metric.MaterialCountMetric;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.minimax.NegaMaxGen3;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.FIDEchess;
import nl.fh.rules.GameDriver;
import nl.fh.rules.MoveGenerator;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/*
 * License: GPL v3
 * 
 */

/**
 * 
 * 
 */
public class NegaMaxGen3Test {
    
    private List<String> cases;
    private final double delta = 1.e-9;
    private double MATE_VALUE = 1.e6;       
    
    private GameDriver gameDriver = FIDEchess.getGameDriver();
    private MoveGenerator moveGenerator = gameDriver.getMoveGenerator();       
    
    @Before
    public void setUpTestStates(){
        this.cases = new ArrayList<String>();
        cases.add("rnbqk2r/pp1pbppp/4pn2/2p5/2P5/1P2PN2/P2P1PPP/RNBQKB1R w KQkq - 1 5");
        cases.add("1k6/ppp5/8/q5r1/3NpP2/8/3R2PP/3R2K1 b - f3 0 1"); 
        cases.add("r6Q/pbp1k1p1/5np1/2pq2B1/3p4/8/PPPN1PPP/R5K1 w - - 0 19");         
       
    }
    
    @Test
    public void testComparison(){
        int depth = 3;
        
        Metric<GameState> baseMetric = MaterialCountMetric.getWrappedInstance();
        Metric<GameState> nega = new NegaMax(baseMetric, moveGenerator, depth);
        Metric<GameState> gen3 = new NegaMaxGen3(baseMetric, moveGenerator, depth);
        
        for(String fen : cases){
            GameState state = GameState.fromFEN(fen);
            assertEquals(nega.eval(state), gen3.eval(state), delta);
            
        }
    }
}
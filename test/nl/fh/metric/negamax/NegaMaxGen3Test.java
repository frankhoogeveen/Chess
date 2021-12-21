package nl.fh.metric.negamax;

import java.util.ArrayList;
import java.util.List;
import nl.fh.gamestate.GameState;
import nl.fh.metric.MaterialCountMetric;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.minimax.NegaMaxGen3;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
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
    private final Rules rules = new SimpleRules();
    private final double delta = 1.e-9;
    
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
        
        Metric<GameState> baseMetric = new MaterialCountMetric();
        Metric<GameState> nega = new NegaMax(baseMetric, depth);
        Metric<GameState> gen3 = new NegaMaxGen3(baseMetric, depth);
        
        for(String fen : cases){
            GameState state = GameState.fromFEN(fen, rules);
            assertEquals(nega.eval(state), gen3.eval(state), delta);
            
        }
    }
}
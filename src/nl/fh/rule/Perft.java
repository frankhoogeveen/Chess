/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

import java.util.ArrayList;
import java.util.List;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.move.ChessMove;
import nl.fh.gamestate.Move;
import nl.fh.gamestate.StateFormatter;
import nl.fh.gamestate.chess.format.FENformatter;

/**
 *  Doing standard tests of move generators
 * 
 */
public class Perft {
    private static final MoveGenerator<ChessState> moveGenerator = FIDEchess.getGameDriver().getMoveGenerator();
    
    /**
     * 
     * @param state
     * @param depth
     * @return the number of descendents of state at the given depth
     */
    public static long value(ChessState state, int depth){
        if(depth <0){
            throw new IllegalArgumentException("Perft with negative depth");
        }
        
        if(depth == 0){
            return 1;
        }
        
        long result = 0;
        for(ChessState child : moveGenerator.calculateChildren(state)){
            result += value(child, depth -1);
        }
        
        return result;
    }
    
    /**
     * 
     * @param state
     * @param depth
     * @return a report giving the values of Perft at one level deeper 
     */
    public static String details(ChessState state, int depth){
        long t0 = System.currentTimeMillis();
        StateFormatter formatter = new FENformatter();
        
        StringBuilder sb = new StringBuilder();
        sb.append("===Perft===\n");
        sb.append(formatter.format(state));
        sb.append("\n");
        
        long total = 0;
        for(Move<ChessState> move : moveGenerator.calculateAllLegalMoves(state)){
            ChessState child = move.applyTo(state);
            
            long perft = Perft.value(child, depth-1);
            total += perft;
            
            sb.append(((ChessMove)move).formatUCI(state));
            sb.append(" ");
            sb.append(perft);
            sb.append("\n");
        }
        
        sb.append("\n");
        sb.append("Total: ");
        sb.append(total);
        sb.append("\n");
        
        sb.append("Time:  ");
        sb.append(System.currentTimeMillis()-t0);
        sb.append(" msec\n");
        
        return sb.toString();
    }
    
    /**
     * 
     * @return twelve canonical cases to dp Pert on
     * https://www.talkchess.com/forum3/viewtopic.php?t=47318
     * 
     * All the FEN strings are with white to move. According to the 
     * reference flipping colors should give the same perft values.
     */
    public static List<PerftCase> getCases(){
        List<PerftCase> result = new ArrayList<PerftCase>();
        
        
        result.add(new PerftCase("8/5bk1/8/2Pp4/8/1K6/8/8 w - d6 0 1",
                "avoid illegal en passant capture",
                6, 824064));
        
        result.add(new PerftCase("8/8/1k6/8/2pP4/8/5BK1/8 b - d3 0 1",
                "avoid illegal en passant capture",
                6, 824064));        
        
        result.add(new PerftCase("8/5k2/8/2Pp4/2B5/1K6/8/8 w - d6 0 1",
                "en passant capture checks opponent",
                6, 1440467));   
        
        result.add(new PerftCase("8/8/1k6/2b5/2pP4/8/5K2/8 b - d3 0 1",
                "en passant capture checks opponent",
                6, 1440467));          
        
        result.add(new PerftCase("5k2/8/8/8/8/8/8/4K2R w K - 0 1",
                "short castling gives check",
                6, 661072));   
        
        result.add(new PerftCase("4k2r/8/8/8/8/8/8/5K2 b k - 0 1",
                "short castling gives check",
                6, 661072));    
        
        result.add(new PerftCase("3k4/8/8/8/8/8/8/R3K3 w Q - 0 1",
                "long castling gives check",
                6, 803711));   
        
        result.add(new PerftCase("r3k3/8/8/8/8/8/8/3K4 b q - 0 1",
                "long castling gives check",
                6, 803711));   
        
        result.add(new PerftCase("r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq - 0 1",
                "castling (including losing cr due to rook capture)",
                4, 1274206));   
        
        result.add(new PerftCase("r3k2r/7b/8/8/8/8/1B4BQ/R3K2R b KQkq - 0 1",
                "castling (including losing cr due to rook capture)",
                4, 1274206));    

        result.add(new PerftCase("r3k2r/8/5Q2/8/8/3q4/8/R3K2R w KQkq - 0 1",
                "castling prevented",
                4, 1720476));   
        
        result.add(new PerftCase("r3k2r/8/3Q4/8/8/5q2/8/R3K2R b KQkq - 0 1",
                "castling prevented",
                4, 1720476));  
        
        result.add(new PerftCase("2K2r2/4P3/8/8/8/8/8/3k4 w - - 0 1",
                "promote out of check",
                6, 3821001));   
        
        result.add(new PerftCase("3K4/8/8/8/8/8/4p3/2k2R2 b - - 0 1",
                "promote out of check",
                6, 3821001));      
        
        result.add(new PerftCase("5K2/8/1Q6/2N5/8/1p2k3/8/8 w - - 0 1",
                "discovered check",
                5, 1004658));   
        
        result.add(new PerftCase("8/8/1P2K3/8/2n5/1q6/8/5k2 b - - 0 1",
                "discovered check",
                5, 1004658));    
        
        result.add(new PerftCase("4k3/1P6/8/8/8/8/K7/8 w - - 0 1",
                "promote to give check",
                6, 217342));   
        
        result.add(new PerftCase("8/k7/8/8/8/8/1p6/4K3 b - - 0 1",
                "promote to give check",
                6, 217342));  

        result.add(new PerftCase("8/P1k5/K7/8/8/8/8/8 w - - 0 1",
                "underpromote to check",
                6, 92683));   
        
        result.add(new PerftCase("8/8/8/8/8/k7/p1K5/8 b - - 0 1",
                "underpromote to check",
                6, 92683)); 
        
        result.add(new PerftCase("K1k5/8/P7/8/8/8/8/8 w - - 0 1",
                "self stalemate",
                6, 2217));   
        
        result.add(new PerftCase("8/8/8/8/8/p7/8/k1K5 b - - 0 1",
                "self stalemate",
                6, 2217));    

        result.add(new PerftCase("8/k1P5/8/1K6/8/8/8/8 w - - 0 1",
                "self stalemate",
                7, 567584));   
        
        result.add(new PerftCase("8/8/8/8/1k6/8/K1p5/8 b - - 0 1",
                "self stalemate",
                7, 567584));  
        
        result.add(new PerftCase("8/5k2/8/5N2/5Q2/2K5/8/8 w - - 0 1",
                "double check",
                4, 23527));   
        
        result.add(new PerftCase("8/8/2k5/5q2/5n2/8/5K2/8 b - - 0 1",
                "double check",
                4, 23527));             
        
        return result;
    }
}
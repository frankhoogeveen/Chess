/*
 * License: GPL v3
 * 
 */

package nl.fh.player.pgn_replayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.fh.chess.Color;
import nl.fh.gamereport.GameReport;
import nl.fh.gamestate.GameState;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.move.Resignation;
import nl.fh.parser.PGN_Reader;
import nl.fh.parser.TolerantReader;
import nl.fh.player.Player;
import nl.fh.rule.FIDEchess;
import nl.fh.rule.GameDriver;

/**
 * This player replays all the moves from a PGN. When all 
 * moves of the PGN are played, it resigns.
 * 
 * 
 */
public class PgnReplayer implements Player {
    
    private static PGN_Reader reader = new TolerantReader();
    private static final GameDriver driver = FIDEchess.getGameDriver();

    private int counter;
    private List<ChessMove>  moveList;

    private PgnReplayer(){
        
    }
    
    /**
     * 
     * @param pgn
     * @param color
     * @return a player that plays the first game in the pgn 
     * from the standpoint of color.
     */
    public static Player getInstance(String pgn, Color color) {
        PgnReplayer result = new PgnReplayer();
        
        if(color == Color.BLACK){
            result.counter = 1;
        } else {
            result.counter = 0;
        }
        
        List<GameReport> report = reader.getGames(pgn, driver);
        if(report.size() < 1){
            result.moveList = new ArrayList<ChessMove>();
        } else {
            result.moveList = report.get(0).getMoveList();
        }
        return result;
    }

    @Override
    public Move getMove(GameState state, Set<Move> legalMoves) {
        if(this.counter > this.moveList.size()-1){
            return Resignation.getInstance();
        } else {
            Move result = this.moveList.get(counter);      
            counter += 2;
            return result;
        }
    }

    @Override
    public String getDescription() {
        return "PGN replayer";
    }

}
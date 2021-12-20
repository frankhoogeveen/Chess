/*
 * License: GPL v3
 * 
 */

package nl.fh.metric;

import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.PieceKind;
import nl.fh.chess.PieceType;
import nl.fh.gamestate.GameState;
import nl.fh.player.evalplayer.Metric;

/**
 * 
 * This is a metric that only depends on the pawn locations.
 * It is only intended for testing purposes.When using this metric to evaluate 
 * positions, both players want to push pawns on the king side
 */
public class PawnLocationMetric implements Metric<GameState> {

    @Override
    public double eval(GameState state) {
       double sum = 0.;
       for(Field f : Field.getAll()){
           PieceType content = state.getFieldContent(f);
           if((content != PieceType.EMPTY) && (content.getKind() == PieceKind.PAWN)){
               sum += f.getY() * Math.pow(10., f.getX());
           }
       }
        
       return sum;
    }

    @Override
    public String getDescription() {
        return this.getClass().getCanonicalName();
    }

}
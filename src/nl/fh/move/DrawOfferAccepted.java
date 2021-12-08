/*
 * License: GPL v3
 * 
 */

package nl.fh.move;

import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.rules.Rules;

/**
 * copyright F. Hoogeveen
 * @author frank
 * 
 * This move represents the acceptance of a draw offer that accompanied the 
 * previous move. Nothing changes on the board
 */
public class DrawOfferAccepted implements Move {
    
    private DrawOfferAccepted(){
        
    }
    
    public static DrawOfferAccepted getInstance(){
        return new DrawOfferAccepted();
    }

    @Override
    public GameState applyTo(GameState state) {
        GameState result = state.copy();
        return result;
    }

    @Override
    public String moveString(GameState state) {
        return "";
    }

    @Override
    public boolean offeredDraw() {
        return false;
    }

    @Override
    public void offerDraw() {
        throw new UnsupportedOperationException("Cannot offer draw when accepting a drawoffer");
    }
    
    @Override
    public Field getTo() {
        throw new UnsupportedOperationException("Not defined"); 
    }

    @Override
    public Field getFrom() {
        throw new UnsupportedOperationException("Not defined"); 
    }  
    
    
}

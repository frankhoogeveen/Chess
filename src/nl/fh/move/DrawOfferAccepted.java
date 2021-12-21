/*
 * License: GPL v3
 * 
 */

package nl.fh.move;

import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;

/**
 * copyright F. Hoogeveen
 * @author frank
 * 
 * This move represents the acceptance of a draw offer that accompanied the 
 * previous move. Nothing changes on the board
 */
public class DrawOfferAccepted implements Move {
    
    private static DrawOfferAccepted instance = null;
    
    private DrawOfferAccepted(){
        
    }
    
    public static DrawOfferAccepted getInstance(){
        if(instance == null){
            instance = new DrawOfferAccepted();
        }
        return instance;
    }

    @Override
    public GameState applyTo(GameState state) {
        GameState result = state.copy();
        result.agreeDraw();
        return result;
    }

    @Override
    public String moveString(GameState state) {
        return "";
    }
    
    @Override
    public String getUCI(GameState state) {
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

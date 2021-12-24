/*
 * License: GPL v3
 * 
 */

package nl.fh.move;

import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.rules.ChessResultArbiter;
import nl.fh.rules.GameDriver;

/**
 * copyright F. Hoogeveen
 * @author frank
 * 
 * This move represents the acceptance of a draw offer that accompanied the 
 * previous move. Nothing changes on the board
 */
public class DrawOfferAccepted extends ChessMove {
    
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

    /**
     *
     * @param state
     * @param arbiter
     * @return
     */
    @Override
    public String formatPGN(GameState state, GameDriver driver) {
        return "";
    }
    
    @Override
    public String formatUCI(GameState state) {
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

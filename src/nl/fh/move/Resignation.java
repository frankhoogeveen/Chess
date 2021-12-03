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
 */
public class Resignation implements Move {

    @Override
    public GameState applyTo(GameState state) {
        return null;
    }

    @Override
    public String moveString() {
        return "resigns";
    }

    @Override
    public boolean offeredDraw() {
        return false;
    }

    @Override
    public void offerDraw() {
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

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
 */
public class Resignation implements Move {

    @Override
    public GameState applyTo(GameState state) {
        return null;
    }

    @Override
    public String moveString(GameState state, Rules rules) {
        return "";
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

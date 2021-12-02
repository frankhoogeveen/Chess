/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fh.move;

import nl.fh.gamestate.GameState;

/**
 * copyright F. Hoogeveen
 * @author frank
 * 
 * This move represents the acceptance of a draw offer that accompanied the 
 * previous move. Nothing changes on the board
 */
public class DrawOfferAccepted implements Move {

    @Override
    public GameState applyTo(GameState state) {
        GameState result = state.copy();
        return result;
    }

    @Override
    public String moveString() {
        return "draw offer accepted";
    }

    @Override
    public boolean offeredDraw() {
        return false;
    }

    @Override
    public void offerDraw() {
        throw new UnsupportedOperationException("Cannot offer draw when accepting a drawoffer");
    }
}

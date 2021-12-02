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

}

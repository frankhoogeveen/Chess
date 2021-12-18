/*
 * License: GPL v3
 * 
 */

package nl.fh.player;

import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.Resignation;

/**
 * 
 * Always resign
 */
public class Quitter implements Player{

    @Override
    public Move getMove(GameState currentState) {
        return Resignation.getInstance();
    }

    @Override
    public String getDescription() {
        return "Quitter";
    }

}
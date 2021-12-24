/*
 * License: GPL v3
 * 
 */

package nl.fh.player;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.Resignation;

/**
 * 
 * Always resign
 */
public class Quitter implements Player{

    public Move getMove(GameState currentState, Set<Move> legalMoves) {
        return Resignation.getInstance();
    }

    @Override
    public String getDescription() {
        return "Quitter";
    }

}
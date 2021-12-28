/*
 * License: GPL v3
 * 
 */

package nl.fh.player.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Move;
import nl.fh.player.Player;

/**
 * Chooses moves randomly from the set of legal moves.
 * 
 */
public class RandomPlayer<S extends GameState> implements Player<S> {

    @Override
    public Move<S> getMove(S currentState, Set<Move<S>> legalMoves) {
        
        List<Move> moves = new ArrayList<Move>(legalMoves);        
        int randomNum = ThreadLocalRandom.current().nextInt(0, moves.size());  
        return moves.get(randomNum);
    }

    @Override
    public String getDescription() {
        return "Random";
    }
}
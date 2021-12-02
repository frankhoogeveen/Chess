/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fh.player;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;

/**
 * copyright F. Hoogeveen
 * @author frank
 */
public interface Player {

    /**
     * 
     * @param currentState
     * @param legalMoves
     * @return a move  without time control
     */
    public Move getMove(GameState currentState, Set<Move> legalMoves);

}

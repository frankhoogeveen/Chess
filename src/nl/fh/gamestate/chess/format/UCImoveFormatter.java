/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.chess.format;

import nl.fh.gamestate.Move;
import nl.fh.gamestate.MoveFormatter;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.move.ChessMove;
import nl.fh.rule.GameDriver;

/**
 * 
 * 
 */
public abstract class UCImoveFormatter implements MoveFormatter<ChessState> {

    @Override
    public String format(Move<ChessState> move, ChessState state, GameDriver<ChessState> driver) {
        ChessMove m = (ChessMove) move;
        return m.formatUCI(state);
    }
}
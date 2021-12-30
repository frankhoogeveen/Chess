/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.tictactoe;

import nl.fh.gamestate.Move;
import nl.fh.gamestate.MoveFormatter;
import nl.fh.rule.GameDriver;

/**
 * 
 * 
 */
public class TicTacToeFormatter implements MoveFormatter<TicTacToeState> {
    
    private static char[] file = {'a', 'b', 'c'};
    private static char[] rank = {'1', '2', '3'};      

    public TicTacToeFormatter() {
    }

    @Override
    public String format(Move<TicTacToeState> move, TicTacToeState state, GameDriver<TicTacToeState> driver) {
        TicTacToeMove m = (TicTacToeMove) move; 
        StringBuilder sb = new StringBuilder();
        sb.append(file[m.getX()]);
        sb.append(rank[m.getY()]);
        return sb.toString();
    }

}
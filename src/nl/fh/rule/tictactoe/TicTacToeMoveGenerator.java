/*
 * License: GPL v3
 * 
 */

package nl.fh.rule.tictactoe;

import java.util.HashSet;
import java.util.Set;
import nl.fh.gamestate.Move;
import nl.fh.gamestate.tictactoe.TicTacToeEnum;
import nl.fh.gamestate.tictactoe.TicTacToeMove;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import nl.fh.rule.MoveGenerator;

/**
 * 
 * 
 */
public class TicTacToeMoveGenerator implements MoveGenerator<TicTacToeState> {

    private TicTacToeResultArbiter arbiter = new TicTacToeResultArbiter();
    
    public TicTacToeMoveGenerator() {
    }

    @Override
    public Set<Move<TicTacToeState>> calculateAllLegalMoves(TicTacToeState state) {
        Set<Move<TicTacToeState>> moves = new HashSet<Move<TicTacToeState>>();

        
        //TODO if the game is already finished, one should not generate daughter states here.....
        if(state.isFinal()){
            return moves;
        }
        
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                if(state.getFieldContent(x,y) == TicTacToeEnum.EMPTY){
                    moves.add(new TicTacToeMove(x,y));
                }
            }
        }
        return moves;
    }

    @Override
    public Set<TicTacToeState> calculateChildren(TicTacToeState state) {
        Set<TicTacToeState> children = new HashSet<TicTacToeState>();
        for(Move<TicTacToeState> move : calculateAllLegalMoves(state)){
            children.add(move.applyTo(state));
        }
        return children;
    }

}
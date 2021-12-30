/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.tictactoe;

import java.util.Arrays;
import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Mover;
import nl.fh.gamestate.StateFormatter;

/**
 * 
 * 
 */
public class TicTacToeState implements GameState {
    private static final StateFormatter<TicTacToeState> formatter = new TicTacToeASCIIformatterCompact();
    private TicTacToeEnum[][] board;
    
    /**
     * creates an empty tictactoe board
     */
    public TicTacToeState() {
        this.board = new TicTacToeEnum[3][3];
        
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                this.board[x][y] = TicTacToeEnum.EMPTY;
            }
        }
    }
    
    @Override
    public Mover getMover() {
        if(countEmtpy() % 2 == 1){
            return Mover.FIRST_MOVER;
        } 
        return Mover.SECOND_MOVER;
    }

    @Override
    public GameState copy() {
        TicTacToeState result = new TicTacToeState();
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                result.board[x][y] = this.board[x][y];
            }
        }
        return result;
    }

    /**
     * 
     * @return the number of empty squares on the board 
     */
    public int countEmtpy() {
        int result = 0;
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                if(board[x][y] == TicTacToeEnum.EMPTY){
                    result += 1;
                }
            }
        }
        return result;
    }

    public TicTacToeEnum getFieldContent(int x, int y) {
        return this.board[x][y];
    }

    void setField(int x, int y, TicTacToeEnum next) {
        board[x][y] = next;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Arrays.deepHashCode(this.board);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TicTacToeState other = (TicTacToeState) obj;
        if (!Arrays.deepEquals(this.board, other.board)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return formatter.format(this);
    }

}
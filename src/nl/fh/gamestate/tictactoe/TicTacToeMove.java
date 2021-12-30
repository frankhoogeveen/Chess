/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.tictactoe;

import nl.fh.gamestate.Move;
import nl.fh.gamestate.MoveFormatter;

/**
 * 
 * 
 */
public class TicTacToeMove implements Move<TicTacToeState> {
    
    private int x;
    private int y;
    private MoveFormatter<TicTacToeState> moveFormatter;
    
    /**
     * 
     * @param x the horizontal coordinate 0 <= x <=2, 0 is left
     * @param y the vertical coordinate 0 <= y <= 2 2 in on top
     */
    public TicTacToeMove(int x, int y){
        moveFormatter = new TicTacToeFormatter();
        create(x, y);
    }
    
    /**
     * 
     * @param s algebraic notation for the field, e.g. "b2"
     */
    public TicTacToeMove(String s){
       if(s.length() < 2){
           throw new IllegalStateException("incorrect description");
       }
       
       int x = s.charAt(0) - 'a';
       int y = s.charAt(1) - '1';
       
       create(x,y);
    }

    TicTacToeMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void create(int x1, int y1) throws IllegalStateException {
        if (outOfRange(x1, y1)) {
            throw new IllegalStateException("out of range");
        }
        this.x = x1;
        this.y = y1;
    }    
    
    @Override
    public TicTacToeState applyTo(TicTacToeState state) {
       TicTacToeEnum next = TicTacToeEnum.FIRST;
       if(state.countEmtpy()%2 == 0){
            next = TicTacToeEnum.SECOND;           
       }
       
       TicTacToeState result = (TicTacToeState) state.copy();
       result.setField(x,y, next);
       return result;
    }

    private boolean outOfRange(int x, int y) {
        return ((x<0)||(x>2)||(y<0)||(y>2));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
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
        final TicTacToeMove other = (TicTacToeMove) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return this.moveFormatter.format(this, null, null);
    }

}
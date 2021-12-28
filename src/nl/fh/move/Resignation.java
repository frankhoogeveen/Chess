/*
 * License: GPL v3
 * 
 */

package nl.fh.move;

import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.rule.GameDriver;

/**
 * copyright F. Hoogeveen
 * @author frank
 */
public class Resignation extends ChessMove {
    
    private static Resignation instance;
    
    private Resignation(){
        
    }
    
    public static Move getInstance(){
        if(instance == null){
            instance = new Resignation();
        }
        return instance;
    }

    @Override
    public GameState applyTo(GameState state) {
        return null;
    }

    @Override
    public String formatPGN(GameState state, GameDriver driver) {
        return "";
    }
    
    @Override
    public String formatUCI(GameState state) {
        return "";
    }    

    @Override
    public boolean offeredDraw() {
        return false;
    }

    @Override
    public void offerDraw() {
    }
    
    @Override
    public Field getTo() {
        throw new UnsupportedOperationException("Not defined"); 
    }

    @Override
    public Field getFrom() {
        throw new UnsupportedOperationException("Not defined"); 
    }  
    
}

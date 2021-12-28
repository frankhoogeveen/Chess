/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.chess.move;

import nl.fh.gamestate.Move;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.rule.GameDriver;

/**
 */
public class ChessResignation extends ChessMove {
    
    private static ChessResignation instance;
    
    private ChessResignation(){
        
    }
    
    public static Move getInstance(){
        if(instance == null){
            instance = new ChessResignation();
        }
        return instance;
    }

    @Override
    public ChessState applyTo(ChessState state) {
        return null;
    }

    @Override
    public String formatPGN(ChessState state, GameDriver driver) {
        return "";
    }
    
    @Override
    public String formatUCI(ChessState state) {
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
        throw new UnsupportedOperationException("Not defined for resignation"); 
    }

    @Override
    public Field getFrom() {
        throw new UnsupportedOperationException("Not defined for resignation"); 
    }  
    
}

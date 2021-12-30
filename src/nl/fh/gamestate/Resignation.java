/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate;

/**
 * Resigning move.
 * 
 * @author frank
 * @param <S> 
 */
public class Resignation<S extends GameState> implements Move<S>{
    
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
    public S applyTo(S state) {
        return null;
    }
    
}

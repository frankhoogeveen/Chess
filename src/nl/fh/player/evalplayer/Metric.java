/*
 * License: GPL v3
 * 
 */

package nl.fh.player.evalplayer;

/**
 * 
 * 
 */
public interface Metric<T> {
    
    /**
     * 
     * @param t
     * @return an evaluation of the object t from the perspective of the player
     * that makes the first move in the game.
     * 
     */
    public double eval(T t);

    public String getDescription();

}
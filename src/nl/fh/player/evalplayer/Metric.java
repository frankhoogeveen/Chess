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
     * @return an evaluation of the object t
     */
    public double eval(T t);

}
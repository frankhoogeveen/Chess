/*
 * License: GPL v3
 * 
 */

package nl.fh.player.evalplayer;

import nl.fh.chess.Color;
import nl.fh.metric.minimax.Parent;

/**
 * 
 * 
 */
public interface Metric<T extends Parent<T>> {
    
    /**
     * 
     * @param t
     * @param perspective 
     * @return an evaluation of the object t from the perspective of the player
     * that makes the first move of the game.
     * 
     */
    public double eval(T t);

    /**
     * 
     * @return a short ASCII description of thus metric for use in output 
     */
    public String getDescription();

}
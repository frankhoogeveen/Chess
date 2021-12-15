/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.Collection;

/**
 * 
 * 
 */
public interface NextStateCalculator<T> {

    /**
     * 
     * @param t
     * @return the successors of t 
     * 
     */
    public Collection<T> calculateNextStates(T t);
    
}
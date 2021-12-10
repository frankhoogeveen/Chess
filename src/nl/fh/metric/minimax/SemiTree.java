/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.Set;

/**
 * License GPL v3
 * 
 * Interface that defines part of a tree functionality.
 * 
 */
public interface SemiTree<T> {
    
    /**
     * 
     * @return the daughters of this node. 
     */
    public Set<T> getDaughters();

}

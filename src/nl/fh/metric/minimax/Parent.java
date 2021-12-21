/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.Set;

/**
 * 
 * 
 */
public interface Parent<T> {

    /**
     * 
     * @return the children of this node 
     */
    public Set<T> getChildren();
    
    /**
     * Clears the buffered memory of the children. If we call
     * set1 = getChildren();
     * forgetChildren();
     * set2 = getChildren();
     * 
     * set1 should be equal to set2. But the daughter relations will have to be
     * recalculated to create set2. 
     */
    public void forgetChildren();
    
}
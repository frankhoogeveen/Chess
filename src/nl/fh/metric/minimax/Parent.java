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

    public Set<T> getChildren();
    
}
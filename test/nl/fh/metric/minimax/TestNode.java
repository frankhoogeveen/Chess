/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.HashSet;
import java.util.Set;
import nl.fh.chess.Color;
import nl.fh.chess.Colored;

/**
 * License GPL v3
 * 
 * Interface that defines tree functionality with an eye on generating GameStates
 * on the one hand and testability with explicit setting of daughters on the 
 * other hand.
 * 
 */
public class TestNode implements Parent<TestNode>, Colored {

    private final long content;    
    
    private TestNode parent;
    private Set<TestNode> daughters;
    private Color color;
    
    /**
     *
     * @param content the payload of this tree node.
     * @param calculator is used to calculate the next generation
     * 
     * If nextGenerator is set to null, one has to add all daughters explicitely.
     * 
     */
    public TestNode(long content, Color color){
        this.content = content;
        this.parent = null;
        this.daughters = new HashSet<TestNode>();
        this.color = color;
    }
    
    /**
     * 
     * @return the contents of this node 
     */
    public long getContent(){
        return this.content;
    }
    
    /**
     * 
     * @return the parent of this node. 
     */
    public TestNode getParent(){
        return this.parent;
    }
    
    /**
     * 
     * @param parent 
     */
    public void setParent(TestNode parent){
        this.parent = parent;
    }
    
    /**
     * 
     * @param node to be added to the tree
     */
    public void addDaughter(TestNode node){
        this.daughters.add(node);
        node.parent = this;
        node.color = this.color.flip();
    }

    @Override
    public Set<TestNode> getChildren() {
        return this.daughters;
    }

    @Override
    public Color getColor() {
        return this.color;
    }
    
}

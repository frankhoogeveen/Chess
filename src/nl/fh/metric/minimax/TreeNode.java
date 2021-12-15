/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.HashSet;
import java.util.Set;
import nl.fh.player.evalplayer.Metric;

/**
 * License GPL v3
 * 
 * Interface that defines tree functionality with an eye on generating GameStates
 * on the one hand and testability with explicit setting of daughters on the 
 * other hand.
 * 
 */
public class TreeNode<T> {

    private final T content;    
    
    private TreeNode<T> parent;
    private Set<TreeNode<T>> daughters;

    private final NextStateCalculator<T> calculator;
    private boolean isDirty;
    
    /**
     *
     * @param content the payload of this tree node.
     * @param calculator is used to calculate the next generation
     * 
     * If nextGenerator is set to null, one has to add all daughters explicitely.
     * 
     */
    public TreeNode(T content,  NextStateCalculator<T> calculator){
        this.content = content;
        this.parent = null;
        this.daughters = new HashSet<TreeNode<T>>();
        this.calculator = calculator;  
        this.isDirty = true;
    }
    
    public TreeNode(T content){
        this.content = content;
        this.parent = null;
        this.daughters = new HashSet<TreeNode<T>>();
        this.calculator = null;
        this.isDirty = false;
        
    }
    
    /**
     * 
     * @return the contents of this node 
     */
    public T getContent(){
        return this.content;
    }
    
    /**
     * 
     * @return the parent of this node. 
     */
    public TreeNode<T> getParent(){
        return this.parent;
    }
    
    /**
     * 
     * @param parent 
     */
    public void setParent(TreeNode<T> parent){
        this.parent = parent;
    }
    
    
    /**
     * 
     * @return the daughters of this node. 
     * In case a calculator has been defined in the creation of this object,
     * the calculator will be invoked once to calculate the daughters.
     */
    public Set<TreeNode<T>> getDaughters(){
        if(isDirty && (this.calculator != null)){
            this.daughters = new HashSet<TreeNode<T>>();
            for(T t : this.calculator.calculateNextStates(this.content)){
                this.daughters.add(new TreeNode<T>(t, this.calculator));
            }
            this.isDirty = false;
        }
        return this.daughters;
    }
    
    /**
     * 
     * @param node to be added to the tree
     */
    public void addDaughter(TreeNode<T> node){
        this.daughters.add(node);
        node.parent = this;
    }

    /**
     * 
     * @param metric
     * @return the value of metric applied to the content of this node
     * 
     */
    public double eval(Metric<T> metric){
        return metric.eval(content);
    }
}

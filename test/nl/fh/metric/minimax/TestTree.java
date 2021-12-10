/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.HashSet;
import java.util.Set;
import nl.fh.player.evalplayer.Metric;

/**
 * 
 * 
 */
class TestTree implements SemiTree<TestTree>{

    private double content;
    private boolean visited;
    private Set<TestTree> daughters;
    private TestTree parent;
    
    TestTree(double content){
        this.content = content;
        this.visited = false;
        this.parent = null;
        this.daughters = new HashSet<TestTree>();
    }

    public void setParent(TestTree parent){
        this.parent = parent;
        this.parent.daughters.add(this);
    }
    
    public void addDaughter(TestTree daughter){
        this.daughters.add(daughter);
        daughter.parent = this;
    }
    
    public TestTree getParent(){
        return this.parent;
    }
    
    public double getContent(){
        this.visited = true;
        return this.content;
    }
    
    public boolean isVisited(){
        return this.visited;
    }
    
    @Override
    public Set<TestTree> getDaughters() {
        return this.daughters;
    }
        
}
/*
 * License: GPL v3
 * 
 */

package nl.fh.player.evalplayer;

import java.util.Comparator;

/**
 * 
 * 
 */
public class MetricComparator<T> implements Comparator<T> {

    private Metric evaluator;
    
    private MetricComparator(){
        
    }
    
    /**
     *
     * @param metric
     * @return
     */
    public MetricComparator<T> getInstance(Metric<T> metric){
        MetricComparator<T> result = new MetricComparator<T>();
        result.evaluator = metric;
        return new MetricComparator<T>();
    }
    
    public int compare(T s1, T s2) {
        double d1 = this.evaluator.eval(s1);
        double d2 = this.evaluator.eval(s2);
        
        if(d1 > d2){
            return +1;
        } else if(d1 < d2){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public Comparator<T> reversed() {
        return Comparator.super.reversed(); 
    }


}
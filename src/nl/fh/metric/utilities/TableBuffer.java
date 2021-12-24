/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import java.util.HashMap;
import java.util.Map;
import nl.fh.player.evalplayer.Metric;

/**
 * Wrapping this around a given baseMetric buffers the result of the baseMetric.
 * No recalculations will be done. 
 * 
 * When the maxSize is set, a crude size maximization method is applied. When 
 * the size of the TableBuffer grows beyond maxSize, the buffer is emptied
 * completely and started over. At the expense of additional recalculations,
 * this puts a cap on the memory usage.
 * 
 */
public class TableBuffer<T> implements Metric<T>{

    private final Metric<T> baseMetric;
    private Map<T, Double> table;
    private final int maxSize;
    
    public TableBuffer(Metric<T> baseMetric, int maxSize){
     this.baseMetric = baseMetric;
     this.table = new HashMap<T, Double>(maxSize);
     this.maxSize = maxSize;
    }

    @Override
    public double eval(T t) {
        Double stored = this.table.get(t);
        if(stored != null){
            return stored;
        } else {
            Double result =  baseMetric.eval(t);
            if(table.size() > maxSize){
                this.table = new HashMap(maxSize);
            }
            this.table.put(t, result);
            return result;
        }
    }
    
    /**
     * 
     * @return a CSV string with the contents of this table buffer 
     */
    public String toCSV(){
        StringBuilder sb = new StringBuilder();
        sb.append("Key;Value;\n");
        
        for(T t : table.keySet()){
            sb.append(t.toString());
            sb.append(";");
            sb.append(table.get(t));
            sb.append(";\n");
        }
        
        return sb.toString();
    }
    
    @Override
    public String getDescription() {
        return "/table buffer "+ Integer.toString(maxSize)+" /" + baseMetric.getDescription();
    }
}
/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import java.util.HashMap;
import java.util.Map;
import nl.fh.metric.minimax.Parent;
import nl.fh.player.evalplayer.Metric;

/**
 * Wrapping this around a given baseMetric buffers the result of the baseMetric.
 * No recalculations will be done
 * 
 */
public class TableBuffer<T extends Parent<T>> implements Metric<T>{

    private final Metric<T> baseMetric;
    private final Map<T, Double> table;
    
    public TableBuffer(Metric<T> baseMetric){
     this.baseMetric = baseMetric;
     this.table = new HashMap<T, Double>();
    }

    @Override
    public double eval(T t) {
        Double stored = this.table.get(t);
        if(stored != null){
            return stored;
        } else {
            Double result =  baseMetric.eval(t);
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
        return "/table buffer/" + baseMetric.getDescription();
    }
}
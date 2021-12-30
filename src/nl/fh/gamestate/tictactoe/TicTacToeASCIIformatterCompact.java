/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.tictactoe;

import java.util.EnumMap;
import java.util.Map;
import nl.fh.gamestate.StateFormatter;

/**
 * 
 * 
 */
public class TicTacToeASCIIformatterCompact<T> implements StateFormatter<TicTacToeState>{

    private final Map<TicTacToeEnum, String> map;
    
    public TicTacToeASCIIformatterCompact(){
        this.map = new EnumMap<TicTacToeEnum, String>(TicTacToeEnum.class);
        map.put(TicTacToeEnum.EMPTY, " ");
        map.put(TicTacToeEnum.FIRST, "X");
        map.put(TicTacToeEnum.SECOND, "O");   
    }
    
    @Override
    public String format(TicTacToeState state) {
        StringBuilder sb = new StringBuilder();
        sb.append(map.get(state.getFieldContent(0, 2)));
        sb.append(map.get(state.getFieldContent(1, 2)));
        sb.append(map.get(state.getFieldContent(2, 2)));
        sb.append("|");

        sb.append(map.get(state.getFieldContent(0, 1)));
        sb.append(map.get(state.getFieldContent(1, 1)));
        sb.append(map.get(state.getFieldContent(2, 1)));  
        
        sb.append("|");
        
        sb.append(map.get(state.getFieldContent(0, 0)));
        sb.append(map.get(state.getFieldContent(1, 0)));
        sb.append(map.get(state.getFieldContent(2, 0)));         
        
        return sb.toString();
    }

    @Override
    public String format(TicTacToeState state, int nMove) {
        return format(state);
    }

}
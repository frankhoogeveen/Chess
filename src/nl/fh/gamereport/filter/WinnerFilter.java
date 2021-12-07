/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport.filter;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;
import nl.fh.player.Player;

/**
 * Filters games with a specific winner
 * 
 */
public class WinnerFilter implements GameFilter{

    private final String player;


    public WinnerFilter(Player player){
        this.player = player.toString();
    }
    
    @Override
    public boolean retain(GameReport report) {
        String white = report.getTag("White");
        String black = report.getTag("Black");
        int result = report.getGameResult().getValue();
        
        if((result == +1) && player.equals(white)
                || (result == -1) && player.equals(black)){
            return true;
        }
        else {
            return false;
        }
    } 
}


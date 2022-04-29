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
    //TODO make similar edits to loser filter
    public boolean retain(GameReport report) {
        Player player1 = report.getPlayer1();
        Player player2 = report.getPlayer2();
        int result = report.getGameResult().getValue();
        
        if((result == +1) && player.equals(player1)
                || (result == -1) && player.equals(player2)){
            return true;
        }
        else {
            return false;
        }
    } 
}


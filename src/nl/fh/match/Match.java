/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

import nl.fh.gamereport.GameFilter;
import nl.fh.player.Player;

/**
 * 
 * 
 */
public interface Match {
    
    /**
     * 
     * @param player1
     * @param player2
     * @param filter determines which games are in detail stored in the match report
     * @return the outcome a match 
     */
    public MatchReport play(Player player1, Player player2, GameFilter filter);

}
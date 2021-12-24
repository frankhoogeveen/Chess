/*
 * License: GPL v3
 * 
 */

package nl.fh.parser;

import java.util.List;
import nl.fh.gamereport.GameReport;
import nl.fh.rules.GameDriver;

/**
 * copyright F. Hoogeveen
 * @author frank
 */
public interface PGN_Reader {

    /**
     * 
     * @param pgn a string
     * @param driver
     * @return a list of game reports as described by the string and partially
     * validated as conforming to the rules used by the game driver
     * 
     * Any PGN_Reader should be in reasonable compliance with 
     * "Standard: Portable Game Notation Specification and Implementation Guide"
     */
    public List<GameReport> getGames(String pgn, GameDriver driver);
}

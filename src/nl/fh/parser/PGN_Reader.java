/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fh.parser;

import java.util.List;
import nl.fh.gamereport.GameReport;

/**
 * copyright F. Hoogeveen
 * @author frank
 */
public interface PGN_Reader {

    /**
     * 
     * @param pgn a string
     * @return a list of game reports as described by the string
     * 
     * Any PGN_Reader should be in reasonable compliance with 
     * "Standard: Portable Game Notation Specification and Implementation Guide"
     */
    public List<GameReport> getGames(String pgn);
}

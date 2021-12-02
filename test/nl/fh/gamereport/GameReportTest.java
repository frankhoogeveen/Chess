package nl.fh.gamereport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * copyright F. Hoogeveen
 * @author frank
 */
public class GameReportTest {

    @Test
    public void EmptyReportTest(){
        GameReport r = new GameReport();
        
        assertEquals(0, r.getTags().size());
        assertEquals(0, r.getMoveList().size());
        assertEquals(0, r.getStateList().size());
        assertEquals(GameResult.UNDECIDED, r.getGameResult());
        
    }
}

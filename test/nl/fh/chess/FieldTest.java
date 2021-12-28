/*
 * License: GPL v3
 * 
 */
package nl.fh.chess;

import nl.fh.gamestate.chess.MoveRangeType;
import nl.fh.gamestate.chess.MoveRange;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.PieceType;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author frank
 */
public class FieldTest {
    
    public FieldTest() {
    }

    /**
     * Test of getInstance method, of class Field.
     */
    @Test
    public void testGetInstance() {
        int x = 2;
        int y = 5;
        Field expResult = Field.getInstance("c6");
        Field result = Field.getInstance(x, y);
        assertEquals(expResult, result);
    }

    /**
     * Test of getX method, of class Field.
     */
    @Test
    public void testGetX() {
        int x = 2;
        int y = 5;
        Field field = Field.getInstance(x,y);
        assertEquals(x, field.getX());
    }

    /**
     * Test of getY method, of class Field.
     */
    @Test
    public void testGetY() {
        int x = 7;
        int y = 3;
        Field field = Field.getInstance(x,y);
        assertEquals(y, field.getY());
    }

    /**
     * Test of getBishopMoves method, of class Field.
     */
    @Test
    public void testGetBishopMoves_a1() {
        Field field = Field.getInstance("a1");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.WHITE_BISHOP);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(7, count);
    }
    @Test
    public void testGetBishopMoves_e5() {
        Field field = Field.getInstance("e5");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_BISHOP);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(13, count);
    }    

    /**
     * Test of getRookMoves method, of class Field.
     */
    @Test
    public void testGetRookMoves() {
        Field field = Field.getInstance("c2");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_ROOK);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(14, count);
    }
    
/**
 *  Testing the queen moves
 */  
    @Test
    public void testGetQueenMoves() {
        Field field = Field.getInstance("h1");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.WHITE_QUEEN);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(21, count);
    }    
    @Test
    public void testGetQueenMoves2() {
        Field field = Field.getInstance("e4");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_QUEEN);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(27, count);
    }        

    /**
     * Test of getKnightMoves method, of class Field.
     */
    @Test
    public void testGetKnightMoves() {
        Field field = Field.getInstance("a1");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.WHITE_KNIGHT);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(2, count);
    }
    
    @Test
    public void testGetKnightMoves2() {
        Field field = Field.getInstance("d6");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_KNIGHT);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(8, count);
    }    
    
    @Test
    public void testGetKnightMoves3() {
        Field field = Field.getInstance("g8");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_KNIGHT);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(3, count);
        
        // check that the move to h6 is included
        boolean found = false;
        Field target = Field.getInstance("h6");
        for(MoveRange range : ranges){
            for(Field field2 : range.getRange()){
                found = found || (field2.equals(target));
            }
        }
        assertTrue(found);
    }       
    
    
    /**
     * Tests of the king moves
     */
    @Test
    public void testGetKingMoves() {
        Field field = Field.getInstance("a8");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_KING);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(3, count);
    }  
    @Test
    public void testGetKingMoves2() {
        Field field = Field.getInstance("e8");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_KING);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(5, count);
    }   
    @Test
    public void testGetKingMoves3() {
        Field field = Field.getInstance("b2");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.WHITE_KING);
        int count = 0;
        for(MoveRange range : ranges){
            count += range.size();
        }
        assertEquals(8, count);
    }   
    

    /**
     * Test of getWhitePawnCaptures method, of class Field.
     */
    @Test
    public void testGetWhitePawnCaptures() {
        Field field = Field.getInstance("f3");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.WHITE_PAWN);
        int moves = 0;
        int captures = 0;
        for(MoveRange range : ranges){
            if(range.getType() ==  MoveRangeType.CAPTURE_FORBIDDEN){
                moves += range.size();
            } else if (range.getType() == MoveRangeType.CAPTURE_OBLIGATORY){
                captures += range.size();
            }
        }        
        assertEquals(1, moves);
        assertEquals(2, captures);
        
    }
    
    @Test
    public void testGetWhitePawnCaptures2() {
        Field field = Field.getInstance("h3");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.WHITE_PAWN);
        int moves = 0;
        int captures = 0;
        for(MoveRange range : ranges){
            if(range.getType() ==  MoveRangeType.CAPTURE_FORBIDDEN){
                moves += range.size();
            } else if (range.getType() == MoveRangeType.CAPTURE_OBLIGATORY){
                captures += range.size();
            }
        }        
        assertEquals(1, moves);
        assertEquals(1, captures);
    }    
    
    @Test
    public void testGetWhitePawnCaptures3() {
        Field field = Field.getInstance("c8");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.WHITE_PAWN);
        int moves = 0;
        int captures = 0;
        for(MoveRange range : ranges){
            if(range.getType() ==  MoveRangeType.CAPTURE_FORBIDDEN){
                moves += range.size();
            } else if (range.getType() == MoveRangeType.CAPTURE_OBLIGATORY){
                captures += range.size();
            }
        }        
        assertEquals(0, moves);
        assertEquals(0, captures);
    }  

        @Test
    public void testGetWhitePawnCaptures4() {
        Field field = Field.getInstance("h2");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.WHITE_PAWN);
        int moves = 0;
        int captures = 0;
        for(MoveRange range : ranges){
            if(range.getType() ==  MoveRangeType.CAPTURE_FORBIDDEN){
                moves += range.size();
            } else if (range.getType() == MoveRangeType.CAPTURE_OBLIGATORY){
                captures += range.size();
            }
        }        
        assertEquals(2, moves);
        assertEquals(1, captures);
    }   
    /**
     * Test of getBlackPawnCaptures method, of class Field.
     */
    @Test
    public void testGetBlackPawnCaptures() {
        Field field = Field.getInstance("f4");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_PAWN);
        int moves = 0;
        int captures = 0;
        for(MoveRange range : ranges){
            if(range.getType() ==  MoveRangeType.CAPTURE_FORBIDDEN){
                moves += range.size();
            } else if (range.getType() == MoveRangeType.CAPTURE_OBLIGATORY){
                captures += range.size();
            }
        }        
        assertEquals(1, moves);
        assertEquals(2, captures);
    }
    
    @Test
    public void testGetBlackPawnCaptures2() {
        Field field = Field.getInstance("a3");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_PAWN);
        int moves = 0;
        int captures = 0;
        for(MoveRange range : ranges){
            if(range.getType() ==  MoveRangeType.CAPTURE_FORBIDDEN){
                moves += range.size();
            } else if (range.getType() == MoveRangeType.CAPTURE_OBLIGATORY){
                captures += range.size();
            }
        }        
        assertEquals(1, moves);
        assertEquals(1, captures);
    }    
    
    @Test
    public void testGetBlackPawnCaptures3() {
        Field field = Field.getInstance("g1");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_PAWN);
        int moves = 0;
        int captures = 0;
        for(MoveRange range : ranges){
            if(range.getType() ==  MoveRangeType.CAPTURE_FORBIDDEN){
                moves += range.size();
            } else if (range.getType() == MoveRangeType.CAPTURE_OBLIGATORY){
                captures += range.size();
            }
        }        
        assertEquals(0, moves);
        assertEquals(0, captures);
    }  
    
    @Test
    public void testGetBlackPawnCaptures4() {
        Field field = Field.getInstance("h7");
        Set<MoveRange> ranges = field.getMoveRanges(PieceType.BLACK_PAWN);
        int moves = 0;
        int captures = 0;
        for(MoveRange range : ranges){
            if(range.getType() ==  MoveRangeType.CAPTURE_FORBIDDEN){
                moves += range.size();
            } else if (range.getType() == MoveRangeType.CAPTURE_OBLIGATORY){
                captures += range.size();
            }
        }        
        assertEquals(2, moves);
        assertEquals(1, captures);
    }    
    
    @Test
    public void testGetAllFields(){
        Set<Field> set = Field.getAll();
        assertEquals(64, set.size());
    }
}

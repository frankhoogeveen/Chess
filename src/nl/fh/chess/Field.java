/*
 * License: GPL v3
 * 
 */

package nl.fh.chess;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Objects of this class represent the fields of a chessboard, including
 * - location
 * - name
 * - relations (e.g. knights jump relations)
 */
public class Field {

    private static Field[][] fields = null;
    private static Set<Field> all = null;
    private static final String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8"};
    private static final String[] cols = {"a", "b", "c", "d", "e", "f", "g", "h"};    

    /**
     * Implements a look up function
     * 
     * @param s
     * @param arr an array of string
     * @return the index where the value s can be found in the array 
     */
    private static int indexOf(String s, String[] arr) {
        for(int i = 0; i < arr.length; i++){
            if(arr[i].equals(s)){
                return i;
            }
        }
        throw new IllegalArgumentException("Element not found by indexOf");
    }
    
    private int x;
    private int y;
    private String name;
    
    // location where the potential moves are stored
    private Map<PieceType, Set<MoveRange>> map = new EnumMap<PieceType, Set<MoveRange>>(PieceType.class);
    
    /**
     * 
     */
    private Field(){
        
    }
    
    /**
     * 
     * @param x the column number from 0..7 (a..h)
     * @param y the row number from 0..7 (1..8)
     * @return 
     */
    public static Field getInstance(int x, int y) {
        if(fields == null){
            setUp();
        }
        
        return fields[x][y];
    }
    
    /**
     * 
     * @param code a letter digit combination corresponding to a field 
     *        i.e. a1... h8
     * @return the corresponding field
     */
    public static Field getInstance(String code){
        if(code.length() != 2){
            throw new IllegalArgumentException("Incorrect code for field: " + code);
        }
        
        String col = code.substring(0, 1);
        String row = code.substring(1, 2);
        int x = indexOf(col, cols);
        int y = indexOf(row, rows);
        
        return getInstance(x,y);        
    }

    /**
     * creates the fields and sets up their properties
     */
    private static void setUp() {
        fields = new Field[8][8];
        
        createFields();
        
        // fill the connections
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                setUpEmptyMoves(x,y);
                setUpBishopMoves(x, y);
                setUpRookMoves(x,y);
                setUpQueenMoves(x,y);
                setUpKingMoves(x,y);
                setUpKnightMoves(x,y);
                setUpPawnMoves(x,y);           
            }
        } 
    }
    
    private static void createFields() {
        // create the fields in the array
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                fields[x][y] = new Field();
                fields[x][y].x = x;
                fields[x][y].y = y;
                fields[x][y].name = Field.cols[x] + Field.rows[y];
            }
        }
        
        // create a set containing the fields
        Field.all = new HashSet<Field>();
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                Field.all.add(fields[x][y]);
            }
        }        

    }  
    
    /**
     * 
     * @param x
     * @param y 
     * 
     * puts an empty set of possibly moves in the map for an empty square
     */
    private static void setUpEmptyMoves(int x, int y){
        fields[x][y].map.put(PieceType.EMPTY, new HashSet<MoveRange>());
    }
    
    private static void setUpBishopMoves(int x1, int y1) {
        Set<MoveRange> set = new HashSet<MoveRange>();
        MoveRange range;        
        
        range = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        for (int i = 1; i < 8; i++) {
            if (onBoard(x1 + i, y1 + i)) {
                range.add(Field.getInstance(x1 + i, y1 + i));
            }
        }
        set.add(range);
        
        range = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        for (int i = 1; i < 8; i++) {
            if (onBoard(x1 + i, y1 - i)) {
                range.add(Field.getInstance(x1 + i, y1 - i));
            }
        }
        set.add(range);
        
        range = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        for (int i = 1; i < 8; i++) {
            if (onBoard(x1 - i, y1 + i)) {
                range.add(Field.getInstance(x1 - i, y1 + i));
            }
        }
        set.add(range);
        
        range = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        for (int i = 1; i < 8; i++) {
            if (onBoard(x1 - i, y1 - i)) {
                range.add(Field.getInstance(x1 - i, y1 - i));
            }
        }
        set.add(range);
        
        fields[x1][y1].map.put(PieceType.WHITE_BISHOP, set);
        fields[x1][y1].map.put(PieceType.BLACK_BISHOP, set);        
    }
    
    private static void setUpRookMoves(int x1, int y1) {
        Set<MoveRange> set = new HashSet<MoveRange>();
        MoveRange range;          
        
        range = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        for (int i = 1; i < 8; i++) {
            if (onBoard(x1, y1 + i)) {
                range.add(Field.getInstance(x1, y1 + i));
            }
        }
        set.add(range);
        
        range = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        for (int i = 1; i < 8; i++) {
            if (onBoard(x1, y1 - i)) {
                range.add(Field.getInstance(x1, y1 - i));
            }
        }
        set.add(range);
        
        range = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        for (int i = 1; i < 8; i++) {
            if (onBoard(x1 + i, y1)) {
                range.add(Field.getInstance(x1 + i, y1));
            }
        }
        set.add(range);
        
        range = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        for (int i = 1; i < 8; i++) {
            if (onBoard(x1 - i, y1)) {
                range.add(Field.getInstance(x1 - i, y1));
            }
        }
        set.add(range);
        
        fields[x1][y1].map.put(PieceType.WHITE_ROOK, set);
        fields[x1][y1].map.put(PieceType.BLACK_ROOK, set);           
    }    
    
    private static void setUpKnightMoves(int x, int y){
        Set<MoveRange> set = new HashSet<MoveRange>();   
        MoveRange knightMoves;        
        
        knightMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        if(onBoard(x+2, y+1)){
            knightMoves.add(Field.getInstance(x+2, y+1));
            set.add(knightMoves);
        }
        
        knightMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x-2, y+1)){
            knightMoves.add(Field.getInstance(x-2, y+1));
            set.add(knightMoves);
        }
        
        knightMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        if(onBoard(x+2, y-1)){
            knightMoves.add(Field.getInstance(x+2, y-1));
            set.add(knightMoves);
        }
        
        knightMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x-2, y-1)){
            knightMoves.add(Field.getInstance(x-2, y-1));
            set.add(knightMoves);
        }   
        
        knightMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x+1, y+2)){
            knightMoves.add(Field.getInstance(x+1, y+2));
            set.add(knightMoves);
        }
        
        knightMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x-1, y+2)){
            knightMoves.add(Field.getInstance(x-1, y+2));
            set.add(knightMoves);
        }
        
        knightMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x+1, y-2)){
            knightMoves.add(Field.getInstance(x+1, y-2));
            set.add(knightMoves);
        }
        
        knightMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x-1, y-2)){
            knightMoves.add(Field.getInstance(x-1, y-2));
            set.add(knightMoves);
        }   
        
        fields[x][y].map.put(PieceType.WHITE_KNIGHT, set);
        fields[x][y].map.put(PieceType.BLACK_KNIGHT, set);              
    }
    
    private static void setUpQueenMoves(int x, int y) {
        Set<MoveRange> set = new HashSet<MoveRange>();
        set.addAll(fields[x][y].map.get(PieceType.WHITE_BISHOP));
        set.addAll(fields[x][y].map.get(PieceType.WHITE_ROOK));
        
        fields[x][y].map.put(PieceType.WHITE_QUEEN, set);
        fields[x][y].map.put(PieceType.BLACK_QUEEN, set);        
    }

    /**
     * This method does NOT set up the castling moves
     * @param x
     * @param y 
     */
    private static void setUpKingMoves(int x, int y) {
        Set<MoveRange> set = new HashSet<MoveRange>();   
        MoveRange kingMoves;

        kingMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);
        if(onBoard(x+1, y+1)){
            kingMoves.add(Field.getInstance(x+1, y+1));
            set.add(kingMoves);
        }
        
        kingMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x, y+1)){
            kingMoves.add(Field.getInstance(x, y+1));
            set.add(kingMoves);            
        }      
        
        kingMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x-1, y+1)){
            kingMoves.add(Field.getInstance(x-1, y+1));
            set.add(kingMoves);            
        }  
        
        kingMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x+1, y)){
            kingMoves.add(Field.getInstance(x+1, y));
            set.add(kingMoves);            
        }   
        
        kingMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x-1, y)){
            kingMoves.add(Field.getInstance(x-1, y));
            set.add(kingMoves);            
        } 
        
        kingMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x+1, y-1)){
            kingMoves.add(Field.getInstance(x+1, y-1));
            set.add(kingMoves);            
        }
        
        kingMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x, y-1)){
            kingMoves.add(Field.getInstance(x, y-1));
            set.add(kingMoves);            
        }      
        
        kingMoves = new MoveRange(MoveRangeType.CAPTURE_OPTIONAL);        
        if(onBoard(x-1, y-1)){
            kingMoves.add(Field.getInstance(x-1, y-1));
            set.add(kingMoves);            
        }     
        
        fields[x][y].map.put(PieceType.WHITE_KING, set);
        fields[x][y].map.put(PieceType.BLACK_KING, set);        
    }    

    private static void setUpPawnMoves(int x, int y){
        setUpWhitePawnMoves(x, y);
        setUpBlackPawnMoves(x, y);        
    }

    private static void setUpWhitePawnMoves(int x1, int y1) {
        HashSet<MoveRange> set = new HashSet<MoveRange>();
        MoveRange whitePawnCapture;
        if (onBoard(x1 + 1, y1 + 1)) {
            whitePawnCapture = new MoveRange(MoveRangeType.CAPTURE_OBLIGATORY);
            whitePawnCapture.add(Field.getInstance(x1 + 1, y1 + 1));
            set.add(whitePawnCapture);
        }
        if (onBoard(x1 - 1, y1 + 1)) {
            whitePawnCapture = new MoveRange(MoveRangeType.CAPTURE_OBLIGATORY);
            whitePawnCapture.add(Field.getInstance(x1 - 1, y1 + 1));
            set.add(whitePawnCapture);
        }
        MoveRange whitePawnMove = new MoveRange(MoveRangeType.CAPTURE_FORBIDDEN);
        ;
        if ((y1 > 0) && onBoard(x1, y1 + 1)) {
            whitePawnMove.add(Field.getInstance(x1, y1 + 1));
        }
        if ((y1 == 1) && onBoard(x1, y1 + 2)) {
            whitePawnMove.add(Field.getInstance(x1, y1 + 2));
        }
        set.add(whitePawnMove);
        fields[x1][y1].map.put(PieceType.WHITE_PAWN, set);
    }
    
    private static void setUpBlackPawnMoves(int x1, int y1) {
        HashSet<MoveRange> set = new HashSet<MoveRange>();
        MoveRange blackPawnCapture;
        if (onBoard(x1 + 1, y1 - 1)) {
            blackPawnCapture = new MoveRange(MoveRangeType.CAPTURE_OBLIGATORY);
            blackPawnCapture.add(Field.getInstance(x1 + 1, y1 - 1));
            set.add(blackPawnCapture);
        }
        if (onBoard(x1 - 1, y1 - 1)) {
            blackPawnCapture = new MoveRange(MoveRangeType.CAPTURE_OBLIGATORY);
            blackPawnCapture.add(Field.getInstance(x1 - 1, y1 - 1));
            set.add(blackPawnCapture);
        }
        MoveRange blackPawnMove = new MoveRange(MoveRangeType.CAPTURE_FORBIDDEN);
        ;
        if ((y1 < 7) && onBoard(x1, y1 - 1)) {
            blackPawnMove.add(Field.getInstance(x1, y1 - 1));
        }
        if ((y1 == 6) && onBoard(x1, y1 - 2)) {
            blackPawnMove.add(Field.getInstance(x1, y1 - 2));
        }
        set.add(blackPawnMove);
        fields[x1][y1].map.put(PieceType.BLACK_PAWN, set);
    }    

    
    /**
     * 
     * @param x
     * @param y
     * @return true if (x,y) is within the chessboard
     */
    private static boolean onBoard(int x, int y){
        return (0<= x) && (x < 8) && (0 <= y) && (y <8);
    }

    /**
     * 
     * @return a set containing all the fields of the chessboard 
     */
    public static Set<Field> getAll(){
        if(Field.all == null){
            setUp();
        }     
        return Field.all;
    }
    
    /**
     * 
     * @return the x-coordinate if this field in the range 0..7.
     * The a-file corresponds to 0, the h-file to 7.
     */
    public int getX() {
        return x;
    }

    /**
     * 
     * @return the y coordinate of this field in the range 0..7.
     * The first rank corresponds to 0, the eighth ranks to 7.
     */
    public int getY() {
        return y;
    }

    /**
     * 
     * @param type of a piece
     * @return the set of move ranges that can be reached from this field 
     * by a piece of the given type
     */
    public Set<MoveRange> getMoveRanges(PieceType type){
        return this.map.get(type);
    }
    
    @Override
    public String toString(){
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.x;
        hash = 79 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field other = (Field) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
}

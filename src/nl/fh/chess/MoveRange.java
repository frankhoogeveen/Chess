/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fh.chess;

import java.util.ArrayList;
import java.util.List;

/**
 * copyright F. Hoogeveen
 * @author frank
 * 
 * This contains the set of fields that can be reached by 
 * consecutive moves in a single direction. 
 * A Queen has eight move ranges, a knight upto eight, a pawn three.
 */
public class MoveRange {

    private List<Field> range;
    private MoveRangeType type;

    public MoveRange(MoveRangeType type){
        this.range = new ArrayList<Field>();
        this.type = type;
    }
    
    public void add(Field field){
        this.range.add(field);
    }
    
    public List<Field> getRange() {
        return range;
    }
    
    public int size(){
        return range.size();
    }

    public MoveRangeType getType() {
        return type;
    }
 }

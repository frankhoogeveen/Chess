/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.chess;

/**
 * Discriminates between the different types of move  ranges. This accommodates
 * for the fact that pawns move forward and capture diagonally, whereas other
 * pieces move and capture in the same way.
 */
public enum MoveRangeType {
    CAPTURE_OPTIONAL,           // all pieces, but pawns
    CAPTURE_OBLIGATORY,         // diagonal pawn moves
    CAPTURE_FORBIDDEN           // straight pawn moves
}

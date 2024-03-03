package Pieces;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import ChessBoard.Board;
import ChessBoard.Coordinate;
import PieceImageCache.RenderPieceImageCacheService;


public abstract class ChessPiece {
    public Coordinate coordinate;
    public boolean isWhite;
    public boolean hasEverMoved;

    public ChessPiece (Coordinate coordinate, boolean isWhite, boolean hasEverMoved) {
        this.coordinate = coordinate;
        this.isWhite = isWhite;
        this.hasEverMoved = hasEverMoved;
    }

    public abstract List<Coordinate> getValidMoves(ArrayList<ChessPiece> chessBoard);
    public abstract ArrayList<Coordinate> getValidAttacks(ArrayList<ChessPiece> chessBoard);
    public boolean isValidMove(Coordinate coordinate, ArrayList<ChessPiece> chessBoard){
        boolean isValid = false;
        for (Coordinate validMoves : getValidMoves(chessBoard)){
            if(validMoves.equal(coordinate)){
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    public boolean isValidAttack(Coordinate coordinate, ArrayList<ChessPiece> chessBoard){
        boolean isValid = false;
        for (Coordinate validMoves : getValidAttacks(chessBoard)){
            if (validMoves.equal(coordinate)) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    public void updatePieceCoordinate(Coordinate coordinate){
        this.coordinate = new Coordinate(coordinate.row, coordinate.column);
        hasEverMoved = true;
    }

    public abstract Image GetRenderImage(RenderPieceImageCacheService renderPieceImageCacheService);

    public boolean ifPathBlockedStraightAttack (Coordinate coord, ArrayList<ChessPiece> chessBoard){
        boolean isBlocked = false;
        for (ChessPiece pieceOnBoard : chessBoard){
            if (!pieceOnBoard.coordinate.equal(coordinate)
                    // piece blocked in the same row
                    && ((coord.row == pieceOnBoard.coordinate.row
                    && isInRange(pieceOnBoard.coordinate.column, coord.column, coordinate.column)
                    && coord.column != pieceOnBoard.coordinate.column)
                    // piece is blocked in the same column
                    || (coord.column == pieceOnBoard.coordinate.column
                    && isInRange(pieceOnBoard.coordinate.row, coord.row, coordinate.row)
                    && coord.row != pieceOnBoard.coordinate.row))){
                isBlocked = true;
                break;
            }
        }
        return isBlocked;
    }


    /**
     * @param coordinate potential attack coordinate
     * @param chessBoard a list of all ChessPiece on board
     * @return if current piece is able to attack the target
     */
    public boolean ifPathBlockedDiagonalAttack (Coordinate coordinate, ArrayList<ChessPiece> chessBoard){
        boolean isBlocked = false;
        for (ChessPiece pieceOnBoard : chessBoard){
            if(!this.coordinate.equals(pieceOnBoard.coordinate)){
                if (pieceOnBoard.coordinate.coordinateInRange(coordinate, this.coordinate)
                        && Math.abs(pieceOnBoard.coordinate.row-coordinate.row)
                        == Math.abs(pieceOnBoard.coordinate.column-coordinate.column)){
                    isBlocked = true;
                    break;
                }
            }
        }
        return isBlocked;
    }

    public boolean ifPathNotBlockedStraight(Coordinate coord, ArrayList<ChessPiece> chessBoard){
        boolean isNotBlocked = true;
        for (ChessPiece pieceOnBoard : chessBoard){
            if (!pieceOnBoard.coordinate.equal(coordinate)
                    // piece blocked in the same row
                    && ((coord.row == pieceOnBoard.coordinate.row
                    && isInRange(pieceOnBoard.coordinate.column, coord.column, coordinate.column))
                    // piece is blocked in the same column
                    || (coord.column == pieceOnBoard.coordinate.column)
                    && isInRange(pieceOnBoard.coordinate.row, coord.row, coordinate.row))
                    // there is piece occupying the target coordinate
                    || coord.equal(pieceOnBoard.coordinate)){
                isNotBlocked = false;
                break;
            }
        }
        return isNotBlocked;
    }
    public boolean ifPathNotBlockedDiagonal(Coordinate coord, ArrayList<ChessPiece> chessBoard){
        boolean isBlocked = false;
        for (ChessPiece piece : chessBoard){
            if(!piece.coordinate.equal(coordinate)){
                if (isInRange(piece.coordinate.row, coordinate.row, coord.row)
                        && isInRange(piece.coordinate.column, coordinate.column, coord.column)
                        && Math.abs(piece.coordinate.row-coord.row) == Math.abs(piece.coordinate.column-coord.column)){
                    isBlocked = true;
                    break;
                }
            }
        }
        return !isBlocked;
    }

    public boolean ifOpponentPieceInRange(Coordinate coord, ArrayList<ChessPiece> chessBoard){
        boolean result = false;
        for (ChessPiece pieceOnBoard : chessBoard){
            if(isWhite != pieceOnBoard.isWhite
                    && pieceOnBoard.coordinate.equal(coord)){
                result = true;
                break;
            }
        }
        return result;
    }
    public boolean isInRange (int i, int limit1, int limit2) {
        int low = Math.min(limit1, limit2);
        int high = Math.max(limit1, limit2);
        return i <= high && i >= low;
    }
    public Boolean isInBoard (Coordinate coord){
        return coord.row >= 1 && coord.row <= 8
                && coord.column >= 1 && coord.column <= 8;
    }


}

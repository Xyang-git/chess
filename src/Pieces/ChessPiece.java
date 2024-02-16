package Pieces;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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

    public boolean isValidMove(Coordinate coordinate, ArrayList<ChessPiece> chessBoard){
        boolean isValid = false;
        for (Coordinate validMoves : getValidMoves(chessBoard)){
            if(validMoves.equals(coordinate)){
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    public abstract List<Coordinate> getValidAttacks(ArrayList<ChessPiece> chessBoard);

    public boolean isValidAttack(Coordinate coordinate, ArrayList<ChessPiece> chessBoard){
        boolean isValid = false;
        for (Coordinate validMoves : getValidAttacks(chessBoard)){
            if (validMoves.equals(coordinate)) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    public void updatePieceCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
        hasEverMoved = true;
    }

    public abstract Image GetRenderImage(RenderPieceImageCacheService renderPieceImageCacheService);

    public boolean ifPathNotBlockedStraight(Coordinate coord, ArrayList<ChessPiece> chessBoard){
        boolean isNotBlocked = true;
        for (ChessPiece piece : chessBoard){
            if(!piece.coordinate.equals(coordinate)){
                if (coord.row == coordinate.row
                        && coord.row == piece.coordinate.row
                        && isInRange(piece.coordinate.column, coord.column, coordinate.column)){
                    isNotBlocked = false;
                    break;
                } else if (coord.column == coordinate.column
                        && coord.column == piece.coordinate.column
                        && isInRange(piece.coordinate.row, coord.row, coordinate.row)) {
                    isNotBlocked = false;
                    break;
                }
            }
        }
        return isNotBlocked;
    }
    public boolean ifPathBlockedDiagonal (Coordinate coord, ArrayList<ChessPiece> chessBoard){
        boolean isBlocked = false;
        for (ChessPiece piece : chessBoard){
            if(!piece.coordinate.equals(coordinate)){
                if (isInRange(piece.coordinate.row, coordinate.row, coord.row)
                        && isInRange(piece.coordinate.column, coordinate.column, coord.column)
                        && Math.abs(piece.coordinate.row-coord.row) == Math.abs(piece.coordinate.column-coord.column)){
                    isBlocked = true;
                    break;
                }
            }
        }
        return isBlocked;
    }

    public Boolean isInBoard (Coordinate coord){
        return coord.row >= 1 && coord.row <= 8
                && coord.column >= 1 && coord.column <= 8;
    }

    public boolean isInRange (int i, int limit1, int limit2) {
        int low = Math.min(limit1, limit2);
        int high = Math.max(limit1, limit2);
        return i <= high && i >= low;
    }
}

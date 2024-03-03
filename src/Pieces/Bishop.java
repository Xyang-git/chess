package Pieces;

import ChessBoard.Board;
import ChessBoard.Coordinate;
import PieceImageCache.RenderPieceImageCacheService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(Coordinate coordinate, boolean isWhite, boolean hasEverMoved) {
        super(coordinate, isWhite, hasEverMoved);
    }

    @Override
    public List<Coordinate> getValidMoves(ArrayList<ChessPiece> chessBoard) {
        ArrayList<Coordinate> result = new ArrayList<>();
        // TODO: This could be more readable
        for (int x=1 ;x < 8; x++){
            if (isInRange(coordinate.row+x, 1, 8) && isInRange(coordinate.column+x, 1,8)
                    && ifPathNotBlockedDiagonal(coordinate.add(new Coordinate(x, x)), chessBoard)){
                result.add(coordinate.add(new Coordinate(x,x)));
            }
            if (isInRange(coordinate.row-x, 1, 8) && isInRange(coordinate.column+x, 1,8)
                    && ifPathNotBlockedDiagonal(coordinate.add(new Coordinate(-x, x)), chessBoard)){
                result.add(coordinate.add(new Coordinate(-x,x)));
            }
            if (isInRange(coordinate.row+x, 1, 8) && isInRange(coordinate.column-x, 1,8)
                    && ifPathNotBlockedDiagonal(coordinate.add(new Coordinate(x, -x)), chessBoard)) {
                result.add(coordinate.add(new Coordinate(x, -x)));
            }
            if (isInRange(coordinate.row-x, 1, 8) && isInRange(coordinate.column-x, 1,8)
                    && ifPathNotBlockedDiagonal(coordinate.add(new Coordinate(-x, -x)), chessBoard)) {
                result.add(coordinate.add(new Coordinate(-x, -x)));
            }
        }
        return result;
    }

    @Override
    public ArrayList<Coordinate> getValidAttacks(ArrayList<ChessPiece> chessBoard) {
        ArrayList<Coordinate> result = new ArrayList<>();
        for (ChessPiece pieceOnBoard : chessBoard){
            if (Math.abs(coordinate.row-pieceOnBoard.coordinate.row) == Math.abs(coordinate.column-pieceOnBoard.coordinate.column)
                    && pieceOnBoard.isWhite != isWhite
                    && !ifPathBlockedDiagonalAttack(pieceOnBoard.coordinate, chessBoard)){
                result.add(pieceOnBoard.coordinate);
            }
        }
        return result;
    }

    @Override
    public Image GetRenderImage(RenderPieceImageCacheService renderPieceImageCacheService) {
        if (isWhite){
            return renderPieceImageCacheService.getImage("bishop_white");
        }else{
            return renderPieceImageCacheService.getImage("bishop_black");
        }
    }
}

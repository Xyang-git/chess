package Pieces;

import ChessBoard.Board;
import ChessBoard.Coordinate;
import PieceImageCache.RenderPieceImageCacheService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {

    public Queen(Coordinate coordinate, boolean isWhite, boolean hasEverMoved) {
        super(coordinate, isWhite, hasEverMoved);
    }
    @Override
    public List<Coordinate> getValidMoves(ArrayList<ChessPiece> chessBoard) {
        List<Coordinate> result = new Bishop(coordinate, isWhite, hasEverMoved).getValidMoves(chessBoard);
        result.addAll(new Rook(coordinate, isWhite, hasEverMoved).getValidMoves(chessBoard));
        return result;
    }

    @Override
    public ArrayList<Coordinate> getValidAttacks(ArrayList<ChessPiece> chessBoard) {
        ArrayList<Coordinate> result = new Bishop(coordinate, isWhite, hasEverMoved).getValidAttacks(chessBoard);
        result.addAll(new Rook(coordinate, isWhite, hasEverMoved).getValidAttacks(chessBoard));
        return result;
    }

    @Override
    public Image GetRenderImage(RenderPieceImageCacheService renderPieceImageCacheService) {
        if (this.isWhite){
            return renderPieceImageCacheService.getImage("Queen_white");
        }else{
            return renderPieceImageCacheService.getImage("Queen_black");
        }
    }
}

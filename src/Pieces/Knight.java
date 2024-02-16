package Pieces;

import ChessBoard.Coordinate;
import PieceImageCache.RenderPieceImageCacheService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {
    public Knight(Coordinate coordinate, boolean isWhite, boolean hasEverMoved) {
        super(coordinate, isWhite, hasEverMoved);
    }

    @Override
    public ArrayList<Coordinate> getValidMoves(ArrayList<ChessPiece> chessBoard){
        ArrayList<Coordinate> validNextMoves = new ArrayList<>();
        Coordinate[] possibleNextMovesForKnight = {
                coordinate.add(new Coordinate(2,1)),
                coordinate.add(new Coordinate(2,-1)),
                coordinate.add(new Coordinate(1,2)),
                coordinate.add(new Coordinate(1,-2)),
                coordinate.add(new Coordinate(-1,2)),
                coordinate.add(new Coordinate(-1,-2)),
                coordinate.add(new Coordinate(-2,1)),
                coordinate.add(new Coordinate(-2,-1))};
        for (Coordinate coord : possibleNextMovesForKnight){
            if (isInBoard(coord)){
                boolean ifPieceBlocked = false;
                for(ChessPiece piece : chessBoard){
                    if (piece.coordinate.equals(coord)){
                        ifPieceBlocked = true;
                    }
                }
                if (!ifPieceBlocked){
                    validNextMoves.add(coord);
                }
            }
        }
        return validNextMoves;
    }

    @Override
    public List<Coordinate> getValidAttacks(ArrayList<ChessPiece> chessBoard) {
        return null;
    }

    @Override
    public Image GetRenderImage(RenderPieceImageCacheService renderPieceImageCacheService) {
        if (this.isWhite){
            return renderPieceImageCacheService.getImage("Knight_white");
        }else{
            return renderPieceImageCacheService.getImage("Knight_black");
        }
    }
}

package Pieces;

import ChessBoard.Board;
import ChessBoard.Coordinate;
import PieceImageCache.RenderPieceImageCacheService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {

    public Knight(Coordinate coordinate, boolean isWhite, boolean hasEverMoved) {
        super(coordinate, isWhite, hasEverMoved);
    }

    private Coordinate[] getPossibleNextMoves(){
        return new Coordinate[]{
                coordinate.add(new Coordinate(2,1)),
                coordinate.add(new Coordinate(2,-1)),
                coordinate.add(new Coordinate(1,2)),
                coordinate.add(new Coordinate(1,-2)),
                coordinate.add(new Coordinate(-1,2)),
                coordinate.add(new Coordinate(-1,-2)),
                coordinate.add(new Coordinate(-2,1)),
                coordinate.add(new Coordinate(-2,-1))};
    }

    @Override
    public ArrayList<Coordinate> getValidMoves(ArrayList<ChessPiece> chessBoard){
        ArrayList<Coordinate> validNextMoves = new ArrayList<>();
        for (Coordinate coord : getPossibleNextMoves()){
            if (isInBoard(coord)){
                boolean ifPieceBlocked = false;
                for(ChessPiece piece : chessBoard){
                    if (piece.coordinate.equal(coord)){
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
    public ArrayList<Coordinate> getValidAttacks(ArrayList<ChessPiece> chessBoard) {
        ArrayList<Coordinate> result = new ArrayList<>();
        for (Coordinate coord : getPossibleNextMoves()){
            if (ifOpponentPieceInRange(coord, chessBoard)) {
                result.add(coord);
            }
        }
        return result;
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

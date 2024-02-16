package Pieces;

import ChessBoard.Coordinate;
import PieceImageCache.RenderPieceImageCacheService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(Coordinate coordinate, boolean isWhite, boolean hasEverMoved) {
        super(coordinate, isWhite, hasEverMoved);
    }
    @Override
    public ArrayList<Coordinate> getValidMoves(ArrayList<ChessPiece> chessBoard) {
        if (isWhite){
            return pawnWhiteNextValidMoves(chessBoard);
        }else{
            return pawnBlackNextValidMoves(chessBoard);
        }
    }

    @Override
    public List<Coordinate> getValidAttacks(ArrayList<ChessPiece> chessBoard) {
        return null;
    }

    @Override
    public Image GetRenderImage(RenderPieceImageCacheService renderPieceImageCacheService) {
        if (this.isWhite){
            return renderPieceImageCacheService.getImage("Pawn_white");
        }else{
            return renderPieceImageCacheService.getImage("Pawn_black");
        }
    }

    private ArrayList<Coordinate> pawnBlackNextValidMoves (ArrayList<ChessPiece> chessBoard){
        ArrayList<Coordinate> result = new ArrayList<>();
        if (!hasEverMoved){
            Coordinate newPos = coordinate.add(new Coordinate(2,0));
            if (ifPathNotBlockedStraight(newPos, chessBoard)){
                result.add(newPos);
            }
        }
        Coordinate newPos = coordinate.add(new Coordinate(1,0));
        if (ifPathNotBlockedStraight(newPos, chessBoard) && isInBoard(newPos)) {
            result.add(newPos);
        }
        return result;
    }
    private ArrayList<Coordinate> pawnWhiteNextValidMoves (ArrayList<ChessPiece> chessBoard){
        ArrayList<Coordinate> result = new ArrayList<>();
        if (!hasEverMoved){
            Coordinate newPos = coordinate.add(new Coordinate(-2,0));
            if (ifPathNotBlockedStraight(newPos, chessBoard)){
                result.add(newPos);
            }
        }
        Coordinate newPos = coordinate.add(new Coordinate(-1,0));
        if (ifPathNotBlockedStraight(newPos, chessBoard) && isInBoard(newPos)) {
            result.add(newPos);
        }
        return result;
    }
}

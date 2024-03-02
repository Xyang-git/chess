package Pieces;

import ChessBoard.Board;
import ChessBoard.Coordinate;
import PieceImageCache.RenderPieceImageCacheService;

import java.awt.*;
import java.util.ArrayList;

public class Rook extends ChessPiece{

    public Rook(Coordinate coordinate, boolean isWhite, boolean hasEverMoved) {
        super(coordinate, isWhite, hasEverMoved);
    }

    private ArrayList<Coordinate> getPossibleNextMoves(){
        ArrayList<Coordinate> possibleNextMoves = new ArrayList<>();
        System.out.println("new:");
        System.out.println(coordinate.row+"/"+coordinate.column);
        for (int x = 1; x <= 8; x++){
            if (x != coordinate.row){
                possibleNextMoves.add(new Coordinate(x, coordinate.column));
                System.out.println(x+"/"+coordinate.column);
            }
            if(x != coordinate.column){
                possibleNextMoves.add(new Coordinate(coordinate.row, x));
                System.out.println(coordinate.row+"/"+x);
            }
        }
        return possibleNextMoves;
    }
    @Override
    public ArrayList<Coordinate> getValidMoves(ArrayList<ChessPiece> chessBoard){
        ArrayList<Coordinate> validNextMoves = new ArrayList<>();
         for (Coordinate possibleNextMove : getPossibleNextMoves()){
            if(ifPathNotBlockedStraight(possibleNextMove, chessBoard)){
                validNextMoves.add(possibleNextMove);
            }
        }
        return validNextMoves;
    }

    @Override
    public ArrayList<Coordinate> getValidAttacks(Board chessBoard) {
        ArrayList<Coordinate> result = new ArrayList<>();
        for (Coordinate possibleNextMove : getPossibleNextMoves()){
            if(!ifPathBlockedStraightAttack(possibleNextMove, chessBoard.showBoard())
                    && chessBoard.findPieceAtPosition(possibleNextMove) != null
                    && chessBoard.findPieceAtPosition(possibleNextMove).isWhite != isWhite){
                result.add(possibleNextMove);
            }
        }
        return result;
    }

    @Override
    public Image GetRenderImage(RenderPieceImageCacheService renderPieceImageCacheService) {
        if (isWhite){
            return renderPieceImageCacheService.getImage("Rook_white");
        }else{
            return renderPieceImageCacheService.getImage("Rook_black");
        }
    }
}

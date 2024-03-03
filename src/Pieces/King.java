package Pieces;

import ChessBoard.Board;
import ChessBoard.Coordinate;
import PieceImageCache.RenderPieceImageCacheService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    private Coordinate[] getPossibleNextMoves(){
        return new Coordinate[]{
                coordinate.add(new Coordinate(0,1)),
                coordinate.add(new Coordinate(0,-1)),
                coordinate.add(new Coordinate(1,-1)),
                coordinate.add(new Coordinate(1,0)),
                coordinate.add(new Coordinate(1,1)),
                coordinate.add(new Coordinate(-1,1)),
                coordinate.add(new Coordinate(-1,0)),
                coordinate.add(new Coordinate(-1,-1))};
    }
    public King(Coordinate coordinate, boolean isWhite, boolean hasEverMoved) {
        super(coordinate, isWhite, hasEverMoved);
    }
    @Override
    public List<Coordinate> getValidMoves(ArrayList<ChessPiece> chessBoard) {
        ArrayList<Coordinate> validNextMoves = new ArrayList<>();
        for (Coordinate coord : getPossibleNextMoves()){
            if (isInBoard(coord)){
                if(ifPathNotBlockedStraight(coord, chessBoard)
                        && ifPathNotBlockedDiagonal(coord, chessBoard)
                        && !ifNextKingPosInAttackRange(coord, chessBoard)){
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
            if (ifOpponentPieceInRange(coord, chessBoard)){
                //if (!ifPieceAtPosIsProtected(pos[0], pos[1], chessBoard)){
                result.add(coord);
                //}
            }
        }
        return result;
    }

    private boolean ifNextKingPosInAttackRange (Coordinate coord, ArrayList<ChessPiece> chessboard){
        ChessPiece newKingCoordinate = new King(coord, isWhite, true);
        King currentKing = this;
        chessboard.remove(currentKing);
        chessboard.add(newKingCoordinate);
        boolean result = false;
        for (ChessPiece piece : chessboard){
            if (piece.isWhite != isWhite){
                if (piece.getClass() == King.class){
                    if (isInRange(coordinate.row, piece.coordinate.row+1, piece.coordinate.row-1)
                            && isInRange(coordinate.column, piece.coordinate.column-1, piece.coordinate.column+1)){
                        result = true;
                        break;
                    }
                }else{
                    if (piece.isValidAttack(coord, chessboard)){
                        result = true;
                        break;
                    }
                }
            }
        }
        chessboard.add(currentKing);
        chessboard.remove(newKingCoordinate);
        return result;
    }

    @Override
    public Image GetRenderImage(RenderPieceImageCacheService renderPieceImageCacheService) {
        if (this.isWhite){
            return renderPieceImageCacheService.getImage("king_white");
        }else{
            return renderPieceImageCacheService.getImage("king_black");
        }
    }
}

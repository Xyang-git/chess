package Pieces;

import ChessBoard.Board;
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
        if (isWhite) {
            return pawnWhiteNextValidMoves(chessBoard);
        } else {
            return pawnBlackNextValidMoves(chessBoard);
        }
    }

    @Override
    public ArrayList<Coordinate> getValidAttacks(Board chessBoard) {
        ArrayList<Coordinate> result = new ArrayList<>() {};
        Coordinate[] possibleAttackPos;
        if (isWhite) {
            possibleAttackPos = new Coordinate[]{coordinate.add(new Coordinate(-1, 1)),
                    coordinate.add(new Coordinate(-1, -1))};
        } else {
            possibleAttackPos = new Coordinate[]{coordinate.add(new Coordinate(1, 1)),
            coordinate.add(new Coordinate(1, -1))};
        }
        for (Coordinate coord : possibleAttackPos) {
            if (ifOpponentPieceInRange(coord, chessBoard.showBoard())) {
                result.add(coord);
            }
        }
        return result;
    }

    @Override
    public Image GetRenderImage(RenderPieceImageCacheService renderPieceImageCacheService) {
        if (this.isWhite) {
            return renderPieceImageCacheService.getImage("Pawn_white");
        } else {
            return renderPieceImageCacheService.getImage("Pawn_black");
        }
    }

    private ArrayList<Coordinate> pawnBlackNextValidMoves(ArrayList<ChessPiece> chessBoard) {
        ArrayList<Coordinate> result = new ArrayList<>();
        if (!hasEverMoved) {
            Coordinate newPos = coordinate.add(new Coordinate(2, 0));
            if (ifPathNotBlockedStraight(newPos, chessBoard)) {
                result.add(newPos);
            }
        }
        Coordinate newPos = coordinate.add(new Coordinate(1, 0));
        if (ifPathNotBlockedStraight(newPos, chessBoard) && isInBoard(newPos)) {
            result.add(newPos);
        }
        return result;
    }

    private ArrayList<Coordinate> pawnWhiteNextValidMoves(ArrayList<ChessPiece> chessBoard) {
        ArrayList<Coordinate> result = new ArrayList<>();
        if (!hasEverMoved) {
            Coordinate newPos = coordinate.add(new Coordinate(-2, 0));
            if (ifPathNotBlockedStraight(newPos, chessBoard)) {
                result.add(newPos);
            }
        }
        Coordinate newPos = coordinate.add(new Coordinate(-1, 0));
        if (ifPathNotBlockedStraight(newPos, chessBoard) && isInBoard(newPos)) {
            result.add(newPos);
        }
        return result;
    }

}

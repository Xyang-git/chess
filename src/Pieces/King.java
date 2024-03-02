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
                        && !ifNextKingPosInAttackRange(coord, chessBoard)
                ){
                    validNextMoves.add(coord);
                }
            }
        }
        return validNextMoves;
    }

    @Override
    public ArrayList<Coordinate> getValidAttacks(Board chessBoard) {
        ArrayList<Coordinate> result = new ArrayList<>();
        for (Coordinate coord : getPossibleNextMoves()){
            if (ifOpponentPieceInRange(coord, chessBoard.showBoard())){
                //if (!ifPieceAtPosIsProtected(pos[0], pos[1], chessBoard)){
                result.add(coord);
                //}
            }
        }
        return result;
    }

    private boolean ifNextKingPosInAttackRange (Coordinate coord, ArrayList<ChessPiece> chessboard){
//        chessboard.remove(piecePos);
//        PiecePos newKingPos = new PiecePos(piecePos.pieceType(), row, column, true);
//        chessboard.add(newKingPos);
//        boolean result = false;
//        for (PiecePos pos : chessboard){
//            if (pos.pieceType().isWhitePiece(pos.pieceType()) != piecePos.pieceType().isWhitePiece(piecePos.pieceType())){
//                if ((pos.pieceType() == PieceType.King_black || pos.pieceType() == PieceType.King_white)){
//                    if (isInRange(row, pos.row()+1, pos.row()-1)
//                            && isInRange(column, pos.column()-1, pos.column()+1)){
//                        result = true;
//                        break;
//                    }
//                }else{
//                    ValidAttack validAttack = new ValidAttack();
//                    if (validAttack.isValidAttack(pos, row, column, chessboard)){
//                        result = true;
//                        break;
//                    }
//                }
//            }
//        }
//        chessboard.remove(newKingPos);
//        chessboard.add(piecePos);
        return false;
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

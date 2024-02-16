package ChessBoard;
import Pieces.*;


import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    // TODO: Half the number of entries here, by defining half the board, then programmically generating the other half.
    private final ArrayList<ChessPiece> chessBoard = new ArrayList<>(Arrays.asList(
            new Rook(new Coordinate(8,1),true, false),
            new Knight(new Coordinate(8,2),true, false),
            new Bishop(new Coordinate(8,3),true, false),
            new Queen(new Coordinate(8,4),true, false),
            new King(new Coordinate(8,5),true, false),
            new Bishop(new Coordinate(8,6),true, false),
            new Knight(new Coordinate(8,7),true, false),
            new Rook(new Coordinate(8,8),true, false),
            new Pawn(new Coordinate(7,1),true, false),
            new Pawn(new Coordinate(7,2),true, false),
            new Pawn(new Coordinate(7,3),true, false),
            new Pawn(new Coordinate(7,4),true, false),
            new Pawn(new Coordinate(7,5),true, false),
            new Pawn(new Coordinate(7,6),true, false),
            new Pawn(new Coordinate(7,7),true, false),
            new Pawn(new Coordinate(7,8),true, false),
            new Rook(new Coordinate(1,1),false, false),
            new Knight(new Coordinate(1,2),false, false),
            new Bishop(new Coordinate(1,3),false, false),
            new Queen(new Coordinate(1,4),false, false),
            new King(new Coordinate(1,5),false, false),
            new Bishop(new Coordinate(1,6),false, false),
            new Knight(new Coordinate(1,7),false, false),
            new Rook(new Coordinate(1,8),false, false),
            new Pawn(new Coordinate(2,1),false, false),
            new Pawn(new Coordinate(2,2),false, false),
            new Pawn(new Coordinate(2,3),false, false),
            new Pawn(new Coordinate(2,4),false, false),
            new Pawn(new Coordinate(2,5),false, false),
            new Pawn(new Coordinate(2,6),false, false),
            new Pawn(new Coordinate(2,7),false, false),
            new Pawn(new Coordinate(2,8),false, false)));

    public ChessPiece findPiecePosAtPosition(Coordinate coordinate){
        for (ChessPiece chessPiece : chessBoard){
            if (chessPiece.coordinate.equals(coordinate)){
                return chessPiece;
            }
        }
        return null;
    }

//    public boolean ifPieceBeingAttacked (PiecePos pos){
//        boolean result = false;
//        for (PiecePos piecePos : chessBoard){
//            if(piecePos.pieceType().isOpponent(piecePos.pieceType(), pos.pieceType())
//                    && validAttack.isValidAttack(piecePos, pos.row(), pos.column(), chessBoard)){
//                result = true;
//            }
//        }
//        return result;
//    }
    public boolean whiteWins (){
        return chessBoard.stream().noneMatch(piece -> piece.getClass().equals(King.class) && !piece.isWhite);
    }
    public boolean blackWins (){
        return chessBoard.stream().noneMatch(piece -> piece.getClass().equals(King.class) && piece.isWhite);
    }

    public boolean isStaleMate (boolean whiteTurn){
        boolean result = true;
        for (ChessPiece piece : chessBoard){
            if(piece.isWhite == whiteTurn
                    && /*(!piece.getValidAttacks(piece, chessBoard).isEmpty() ||*/ !piece.getValidMoves(chessBoard).isEmpty()){
                result = false;
                break;
            }
        }
        return result;
    }
//    public boolean whiteBeingChecked (){
//        if (ifPieceBeingAttacked(findChessPiece(PieceType.King_white))){
//            return true;
//        }else{
//            return false;
//        }
//    }
//    public boolean BlackBeingChecked (){
//        if (ifPieceBeingAttacked(findChessPiece(PieceType.King_black))){
//            return true;
//        }else{
//            return false;
//        }
//    }

    public void updateBoard(ChessPiece piece, Coordinate newCoord){
        chessBoard.remove(piece);
        piece.updatePieceCoordinate(newCoord);
        chessBoard.add(piece);
    }

    public void updateBoardAfterAttack(ChessPiece piece, Coordinate coordinate){
        for (ChessPiece chessPiece : chessBoard){
            if (chessPiece.coordinate.equals(coordinate)){
                updateBoard(piece, coordinate);
                chessBoard.remove(chessPiece);
                break;
            }
        }
    }

    public ArrayList<ChessPiece> showBoard(){
        return chessBoard;
    }
}

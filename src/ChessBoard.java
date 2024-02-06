import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.in;

public class ChessBoard {

    private final ArrayList<PiecePos> chessBoard = new ArrayList<>(Arrays.asList(
            new PiecePos(ChessPiece.Rook_white, 8, 1, false),
            new PiecePos(ChessPiece.Knight_white, 8, 2, false),
            new PiecePos(ChessPiece.Bishop_white, 8, 3,false),
            new PiecePos(ChessPiece.Queen_white, 8, 4, false),
            new PiecePos(ChessPiece.King_white, 8, 5, false),
            new PiecePos(ChessPiece.Bishop_white, 8, 6, false),
            new PiecePos(ChessPiece.Knight_white, 8, 7, false),
            new PiecePos(ChessPiece.Rook_white, 8, 8, false),
            new PiecePos(ChessPiece.Pawn_white, 7, 1, false),
            new PiecePos(ChessPiece.Pawn_white, 7, 2, false),
            new PiecePos(ChessPiece.Pawn_white, 7, 3, false),
            new PiecePos(ChessPiece.Pawn_white, 7, 4,false),
            new PiecePos(ChessPiece.Pawn_white, 7, 5, false),
            new PiecePos(ChessPiece.Pawn_white, 7, 6, false),
            new PiecePos(ChessPiece.Pawn_white, 7, 7, false),
            new PiecePos(ChessPiece.Pawn_white, 7, 8, false),
            new PiecePos(ChessPiece.Pawn_black, 2, 1,false),
            new PiecePos(ChessPiece.Pawn_black, 2, 2, false),
            new PiecePos(ChessPiece.Pawn_black, 2, 3, false),
            new PiecePos(ChessPiece.Pawn_black, 2, 4, false),
            new PiecePos(ChessPiece.Pawn_black, 2, 5, false),
            new PiecePos(ChessPiece.Pawn_black, 2, 6, false),
            new PiecePos(ChessPiece.Pawn_black, 2, 7, false),
            new PiecePos(ChessPiece.Pawn_black, 2, 8,false),
            new PiecePos(ChessPiece.Rook_black, 1, 1, false),
            new PiecePos(ChessPiece.Knight_black, 1, 2, false),
            new PiecePos(ChessPiece.Bishop_black, 1, 3, false),
            new PiecePos(ChessPiece.Queen_black, 1, 4, false),
            new PiecePos(ChessPiece.King_black, 1, 5, false),
            new PiecePos(ChessPiece.Bishop_black, 1, 6, false),
            new PiecePos(ChessPiece.Knight_black, 1, 7, false),
            new PiecePos(ChessPiece.Rook_black, 1, 8, false)));

    private ValidAttack validAttack = new ValidAttack();
    private ValidMove validMove = new ValidMove();


    public PiecePos findPiecePosAtPosition(int row, int column){
        for (PiecePos piecePos : chessBoard){
            if (piecePos.row() == row && piecePos.column() == column){
                return piecePos;
            }
        }
        return null;
    }
    public PiecePos findChessPiece(ChessPiece chessPiece){
        for (PiecePos piecePos : chessBoard){
            if (chessPiece == piecePos.chessPiece()){
                return piecePos;
            }
        }
        return null;
    }
    public boolean ifPieceBeingAttacked (PiecePos pos){
        boolean result = false;
        for (PiecePos piecePos : chessBoard){
            if(piecePos.chessPiece().isOpponent(piecePos.chessPiece(), pos.chessPiece())
                    && validAttack.isValidAttack(piecePos, pos.row(), pos.column(), chessBoard)){
                result = true;
            }
        }
        return result;
    }
    public boolean whiteWins (){
        return findChessPiece(ChessPiece.King_black) == null;
    }
    public boolean blackWins (){
        return findChessPiece(ChessPiece.King_white) == null;
    }

    public boolean isStaleMate (boolean whiteTurn){
        boolean result = true;
        for (PiecePos pos : chessBoard){
            if(pos.chessPiece().isWhitePiece(pos.chessPiece()) == whiteTurn
                    && (!validAttack.nextValidAttacks(pos, chessBoard).isEmpty()
                    || !validMove.nextValidMoves(pos, chessBoard).isEmpty())){
                result = false;
                break;
            }
        }
        return result;
    }
    public boolean whiteBeingChecked (){
        if (ifPieceBeingAttacked(findChessPiece(ChessPiece.King_white))){
            return true;
        }else{
            return false;
        }
    }
    public boolean BlackBeingChecked (){
        if (ifPieceBeingAttacked(findChessPiece(ChessPiece.King_black))){
            return true;
        }else{
            return false;
        }
    }

    public void updateBoard(PiecePos previousPos, PiecePos newPos){
        chessBoard.remove(previousPos);
        chessBoard.add(newPos);
    }

    public void updateBoardAfterAttack(int row, int column, PiecePos previousPos, PiecePos newPos){
        for (PiecePos pos : chessBoard){
            if (pos.row() == row && pos.column() == column){
                chessBoard.remove(pos);
                chessBoard.remove(previousPos);
                chessBoard.add(newPos);
                break;
            }
        }
    }

    public ArrayList<PiecePos> showBoard(){
        return chessBoard;
    }


}

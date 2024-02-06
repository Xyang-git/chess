import java.util.ArrayList;

public class ValidAttack {

    //private ValidMove validMove = new ValidMove();
    public boolean isValidAttack(PiecePos piece, int row, int column, ArrayList<PiecePos> chessBoard){
        boolean validAttack = false;
        if (nextValidAttacks(piece, chessBoard) != null){
            for (int a : nextValidAttacks(piece, chessBoard)){
                if (row == a/10 && column == a%10){
                    validAttack = true;
                    break;
                }
            }
        }
        return validAttack;
    }

    public ArrayList<Integer> nextValidAttacks(PiecePos piecePos, ArrayList<PiecePos> chessBoard){
        return switch (piecePos.chessPiece()) {
            case Pawn_black -> pawnBlackNextValidAttacks(piecePos, chessBoard);
            case Pawn_white -> pawnWhiteNextValidAttacks(piecePos, chessBoard);
            case Bishop_black, Bishop_white -> bishopNextValidAttacks(piecePos, chessBoard);
            case King_black, King_white -> kingNextValidAttacks(piecePos, chessBoard);
            case Knight_black, Knight_white -> knightNextValidAttacks(piecePos, chessBoard);
            case Queen_black, Queen_white -> queenNextValidAttacks(piecePos, chessBoard);
            case Rook_black, Rook_white -> rookNextValidAttacks(piecePos, chessBoard);
        };
    }

    private ArrayList<Integer> pawnBlackNextValidAttacks(PiecePos piecePos, ArrayList<PiecePos> chessBoard){
        ArrayList<Integer> result = new ArrayList<>(){};
        int[][] possibleAttackPos = {{(piecePos.row()+1), piecePos.column()+1}, {(piecePos.row()+1), piecePos.column()-1}};
        for (int[] pos : possibleAttackPos){
            if (ifOpponentPieceInRange(pos[0], pos[1], piecePos, chessBoard)) {
                result.add(pos[0]*10+pos[1]);
            }
        }
        return result;
    }
    private ArrayList<Integer> pawnWhiteNextValidAttacks(PiecePos piecePos, ArrayList<PiecePos> chessBoard){
        ArrayList<Integer> result = new ArrayList<>(){};
        int[][] possibleAttackPos = {{(piecePos.row()-1), piecePos.column()+1}, {(piecePos.row()-1), piecePos.column()-1}};
        for (int[] pos : possibleAttackPos){
            if (ifOpponentPieceInRange(pos[0], pos[1], piecePos, chessBoard)) {
                result.add(pos[0]*10+pos[1]);
            }
        }
        return result;
    }
    private ArrayList<Integer> bishopNextValidAttacks(PiecePos piecePos, ArrayList<PiecePos> chessBoard) {
        ArrayList<Integer> result = new ArrayList<>();
        for (PiecePos pos : chessBoard){
            if (Math.abs(pos.row()-piecePos.row()) == Math.abs(pos.column()-piecePos.column())
                    && piecePos.chessPiece().isWhitePiece(piecePos.chessPiece()) != pos.chessPiece().isWhitePiece(pos.chessPiece())
                    && !ifPathBlockedDiagonalAttack(piecePos, pos.row(), pos.column(), chessBoard)){
                result.add(pos.row()*10+pos.column());
            }
        }
        return result;
    }
    private ArrayList<Integer> kingNextValidAttacks(PiecePos piecePos, ArrayList<PiecePos> chessBoard) {
        ArrayList<Integer> result = new ArrayList<>();
        int[][] possibleKingNextAttackPos = {{piecePos.row(),piecePos.column()+1}, {piecePos.row(),piecePos.column()-1},
                {(piecePos.row()+1),piecePos.column()}, {(piecePos.row()+1),piecePos.column()+1},
                {(piecePos.row()+1),piecePos.column()-1}, {(piecePos.row()-1),piecePos.column()},
                {(piecePos.row()-1),piecePos.column()+1}, {(piecePos.row()-1),piecePos.column()-1}};
        for (int[] pos : possibleKingNextAttackPos){
            if (ifOpponentPieceInRange(pos[0], pos[1], piecePos, chessBoard)){
                //if (!ifPieceAtPosIsProtected(pos[0], pos[1], chessBoard)){
                    result.add(pos[0]*10+pos[1]);
                //}
            }
        }
        return result;
    }
    private ArrayList<Integer> knightNextValidAttacks(PiecePos piecePos, ArrayList<PiecePos> chessBoard) {
        ArrayList<Integer> result = new ArrayList<>();
        int[][] possibleNextAttacksForKnight = {{piecePos.row()+2,piecePos.column()+1}, {piecePos.row()+2,piecePos.column()-1},
                {(piecePos.row()+1),piecePos.column()+2}, {(piecePos.row()+1),piecePos.column()-2},
                {(piecePos.row()-1),piecePos.column()-2}, {(piecePos.row()-1),piecePos.column()+2},
                {(piecePos.row()-2),piecePos.column()+1}, {(piecePos.row()-2),piecePos.column()-1}};
        for (int[] pos : possibleNextAttacksForKnight){
            if (ifOpponentPieceInRange(pos[0], pos[1], piecePos, chessBoard)) {
                result.add(pos[0]*10+pos[1]);
            }
        }
        return result;
    }
    private ArrayList<Integer> queenNextValidAttacks(PiecePos piecePos, ArrayList<PiecePos> chessBoard) {
        ArrayList<Integer> result = new ArrayList<>(bishopNextValidAttacks(piecePos, chessBoard));
        result.addAll(rookNextValidAttacks(piecePos, chessBoard));
        return result;
    }
    private ArrayList<Integer> rookNextValidAttacks(PiecePos piecePos, ArrayList<PiecePos> chessBoard) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int x = 1; x <= 8; x++){
            if (!ifPathBlockedStraightAttack(piecePos, piecePos.row(), x, chessBoard)
                    && x != piecePos.column() && ifOpponentPieceInRange(piecePos.row(), x, piecePos, chessBoard)){
                result.add(piecePos.row()*10 + x);
            }
            if (!ifPathBlockedStraightAttack(piecePos, x, piecePos.column(), chessBoard)
                    && x != piecePos.row() && ifOpponentPieceInRange(x, piecePos.column(), piecePos, chessBoard)){
                result.add(x*10 + piecePos.column());
            }
        }
        return result;
    }




    private boolean ifOpponentPieceInRange(int row, int column, PiecePos piecePos, ArrayList<PiecePos> chessBoard){
        Boolean result = false;
        for (PiecePos pos : chessBoard){
            if(piecePos.chessPiece().isWhitePiece(piecePos.chessPiece()) != pos.chessPiece().isWhitePiece(pos.chessPiece())){
                if(pos.row() == row && pos.column() == column){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private boolean ifPathBlockedStraightAttack (PiecePos piecePos, int row, int column, ArrayList<PiecePos> chessBoard){
        boolean isBlocked = false;
        for (PiecePos pos : chessBoard){
            if(!pos.equals(piecePos)){
                if (row == piecePos.row() && row == pos.row()
                        && isInRange(pos.column(), column, piecePos.column())
                        && pos.column() != column){
                    isBlocked = true;
                    break;
                } else if (column == piecePos.column() && column == pos.column()
                        && isInRange(pos.row(), row, piecePos.row())
                        && pos.row() != row) {
                    isBlocked = true;
                    break;
                }
            }
        }
        return isBlocked;
    }


    private boolean ifPathBlockedDiagonalAttack (PiecePos piecePos, int row, int column, ArrayList<PiecePos> chessBoard){
        boolean isBlocked = false;
        for (PiecePos pos : chessBoard){
            if(!pos.equals(piecePos)){
                if (isInRange(pos.row(), piecePos.row(), row)
                        && isInRange(pos.column(), piecePos.column(), column)
                        && Math.abs(pos.row()-row) == Math.abs(pos.column()-column)
                        && pos.row() != row && pos.column() != column){
                    isBlocked = true;
                    break;
                }
            }
        }
        return isBlocked;
    }

//    private boolean ifPieceAtPosIsProtected (int row, int column, ArrayList<PiecePos> chessBoard){
//        boolean result = false;
//        PiecePos piecePos = null;
//        for (PiecePos pos : chessBoard){
//            if (pos.row() == row && pos.column() == column){
//                piecePos = pos;
//                chessBoard.remove(piecePos);
//            }
//        }
//        for (PiecePos pos : chessBoard){
//            if (piecePos.chessPiece().isWhitePiece(piecePos.chessPiece()) == pos.chessPiece().isWhitePiece(pos.chessPiece())
//                    && validMove.isValidMove(pos, row, column, chessBoard)){
//                result = true;
//                break;
//            }
//        }
//        chessBoard.add(piecePos);
//        return result;
//    }
    private boolean isInRange (int i, int limit1, int limit2) {
        int low = Math.min(limit1, limit2);
        int high = Math.max(limit1, limit2);
        if (i <= high && i >= low) {
            return true;
        } else {
            return false;
        }
    }

}

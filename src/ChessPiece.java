public enum ChessPiece {
    Bishop_black,
    Bishop_white,
    King_black,
    King_white,
    Knight_black,
    Knight_white,
    Pawn_black,
    Pawn_white,
    Queen_black,
    Queen_white,
    Rook_black,
    Rook_white;

    public boolean isWhitePiece(ChessPiece chessPiece){
        return (chessPiece == Bishop_white || chessPiece == King_white ||
                chessPiece == Knight_white || chessPiece == Pawn_white ||
                chessPiece == Queen_white || chessPiece == Rook_white);
    }

    public boolean isOpponent(ChessPiece selectedPiece, ChessPiece piece){
        return isWhitePiece(selectedPiece) != isWhitePiece(piece);
    }
}

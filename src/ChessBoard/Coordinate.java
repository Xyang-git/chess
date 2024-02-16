package ChessBoard;

public class Coordinate {
    public int row;
    public int column;

    public Coordinate(int row, int column){
        this.row = row;
        this.column = column;
    }

    public Coordinate add (Coordinate other){
        return new Coordinate(row + other.row, column + other.column);
    }
}

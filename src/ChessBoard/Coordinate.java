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

    public boolean equal (Coordinate coordinate){
        return coordinate.row == this.row && coordinate.column == this.column;
    }

    public boolean coordinateInRange (Coordinate limit1, Coordinate limit2){
        int lower_row = Math.min(limit1.row, limit2.row);
        int higher_row = Math.max(limit1.row, limit2.row);
        int lower_column = Math.min(limit1.column, limit2.column);
        int higher_column = Math.max(limit1.column, limit2.column);
        return row>lower_row && row<higher_row
                && column>lower_column && column<higher_column;
    }
}

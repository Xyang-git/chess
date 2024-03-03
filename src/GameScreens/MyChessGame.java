package GameScreens;

import ChessBoard.*;
import PieceImageCache.RenderPieceImageCacheService;
import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyChessGame extends JPanel {
    // You can define any variables you like here:
    private ChessPiece selectedPiece = null;
    private final int pieceSize = 100;
    private Coordinate selectedSpace = new Coordinate(7,4);
    private Coordinate[] previousMove;
    private boolean white_turn = true;
    private String warnings;
    Board board = new Board();
    RenderPieceImageCacheService renderImage = new RenderPieceImageCacheService();

    public MyChessGame() {
        // This is the constructor, put code in here that you want to run exactly once.;
    }

    // You shouldn't have to change this function structure. This redirects key presses from the window to our custom game implementation.
    public KeyListener GenMyKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // You can add any logic you like in here.
                char charPressed = e.getKeyChar();
                if (!board.blackWins() && !board.whiteWins() && !board.isStaleMate(white_turn)){
                    switch (charPressed){
                        case 'w':
                            if (selectedSpace.row > 1) {
                                selectedSpace.row -= 1;
                            }
                            break;
                        case 's':
                            if (selectedSpace.row < 8){
                                selectedSpace.row += 1;
                            }
                            break;
                        case 'a':
                            if (selectedSpace.column > 1) {
                                selectedSpace.column -= 1;
                            }
                            break;
                        case 'd':
                            if (selectedSpace.column < 8){
                                selectedSpace.column += 1;
                            }
                            break;
                        // Key Space is used to select a piece or place a selected piece to a new position
                        case ' ':
                            // If no piece is currently selected
                            if (selectedPiece == null &&
                                    board.findPieceAtPosition(selectedSpace) != null){
                                ChessPiece piece = board.findPieceAtPosition(selectedSpace);
                                if (piece.isWhite != white_turn){
                                    warnings = "Please select your own piece!";
                                }else if (piece.getValidMoves(board.showBoard()).isEmpty()
                                        && piece.getValidAttacks(board.showBoard()).isEmpty()){
                                    warnings = "There is no valid moves or attacks for the selected piece, please select again!";
                                }
                                else{
                                    selectedPiece = piece;
                                }
                            }
                            // If there is a piece is selected by player
                            else if (selectedPiece != null){
                                // Deselect current piece
                                if (selectedPiece.coordinate.equal(selectedSpace)){
                                    selectedPiece = null;
                                }
                                // Selected space is not accessible by current piece
                                else if (!selectedPiece.isValidMove(selectedSpace, board.showBoard())
                                        && !selectedPiece.isValidAttack(selectedSpace, board.showBoard())) {
                                    warnings = "Invalid move, please choose again!";
                                }
                                // Attack
                                else if(selectedPiece.isValidAttack(selectedSpace, board.showBoard())) {
                                    board.updateBoardAfterAttack(selectedPiece, selectedSpace);
                                    white_turn = !white_turn;
                                    previousMove = new Coordinate[]{selectedPiece.coordinate, selectedSpace};
                                    selectedPiece = null;
                                }
                                // Move
                                else{
                                    board.updateBoard(selectedPiece, selectedSpace);
                                    white_turn = !white_turn;
                                    previousMove = new Coordinate[]{selectedPiece.coordinate, selectedSpace};
                                    selectedPiece = null;
                                }
                            }
                            break;
                    }
                }
                repaint();
            }
        };
    }



    // TODO: Make some transform function to avoid all teh multiplying by 100.
    // All rendering goes in here.
    public void drawSquareOnBoard(Graphics g, Coordinate coords, Color colour, int width, int height, int stroke){
        // TODO: Would also handle the size of the piece. (100 by 100)
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(stroke));
        g2.setColor(colour);
        g2.drawRect( coords.column*pieceSize-stroke+2, coords.row*pieceSize-stroke+2,
                width*pieceSize+2*(stroke-2), height*pieceSize+2*(stroke-2));
        g2.setStroke(new BasicStroke(2));
    }
    @Override
    public void paintComponent(Graphics g) {
        // This will clear the screen.
        super.paintComponent(g);

        // Sets the colour for every successive draw call.
        g.setColor(Color.BLUE);
        // Render some text.
        g.drawString("Pawninator 5000", 10, 20);
        //Draw boarder of the chess board
        drawSquareOnBoard(g,new Coordinate(1,1),Color.BLACK, 8,8,4);
        // Draw the chess board background
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                if ((x + y) % 2 == 1) {
                    g.fillRect(x * pieceSize, y * pieceSize, pieceSize, pieceSize);
                }
            }
        }
        g.setColor(Color.orange);
        // Draw a rectangle at a position along the screen depending on how many Ws were pressed.
        g.fillRect(selectedSpace.column*pieceSize, selectedSpace.row*pieceSize, pieceSize, pieceSize);
        // Show the previous move by player
        if (previousMove != null){
            for(Coordinate coordinate : previousMove){
                drawSquareOnBoard(g, coordinate, Color.YELLOW, 1,1,2);
            }
        }

        // Show possible positions for the selected piece to move
        g.setColor(Color.GREEN);
        if (selectedPiece != null){
            for (Coordinate c : selectedPiece.getValidMoves(board.showBoard())){
                g.drawRoundRect(c.column*pieceSize,c.row*pieceSize, pieceSize, pieceSize, 40, 40);
            }
        }
        // Show possible positions for the selected piece to attack
        g.setColor(Color.RED);
        if (selectedPiece != null ){
            for (Coordinate c : selectedPiece.getValidAttacks(board.showBoard())){
                g.drawRoundRect(c.column*pieceSize,c.row*pieceSize, pieceSize, pieceSize, 40, 40);
            }
        }

        // Specify the current turn
        g.setColor(Color.BLACK);
        if (white_turn){
            g.drawString("White to move", 450, 75);
        }else{
            g.drawString("Black to move", 450, 75);
        }
        if (warnings != null){
            g.drawString(warnings, 450, 950);
            warnings = null;
        }

        // Resets the colour to something else.
        // Draw all the chess pieces on board
        // TODO: Actaulyl save the cache somewhere, so we remember the data we've loaded. (e.g. as a member at the top of this class)
        for (ChessPiece piece : board.showBoard()){
            g.drawImage(piece.GetRenderImage(renderImage),
                    piece.coordinate.column * pieceSize,
                    piece.coordinate.row * pieceSize,
                    null);
        }

        g.setColor(Color.red);
        Font font = new Font("Snell Roundhand", Font.BOLD, 100);
        g.setFont(font);
        if (board.whiteWins()){
            g.drawString("White Wins!", 250, 500);
        }
        if (board.blackWins()){
            g.drawString("Black Wins!", 250, 500);
        }
        if (board.isStaleMate(white_turn)){
            g.drawString("Stalemate!", 250, 500);
        }
//        // Some fancy-ish graphics : ] (that'll only refresh on keypress) (need all the double / float casting for silly floating point precision reasons)
//        float time = (float)((((double) System.currentTimeMillis()) / 4000.0) % 1.0);
//        g.setColor(new Color(time, 1.0f - time, 0.5f + time / 2));
//        g.fillRect(150, 915, 50, 50);
    }
}


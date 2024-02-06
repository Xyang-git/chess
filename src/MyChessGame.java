import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyChessGame extends JPanel {
    // You can define any variables you like here:
    private PiecePos selectedPiece = null;
    private int[] selectedSpace = new int[]{7,4};
    private int[][] previousMove;
    ChessBoard chessBoard = new ChessBoard();
    ValidMove validMove = new ValidMove();
    ValidAttack validAttack = new ValidAttack();
    private boolean white_turn = true;
    private String warnings;

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
                if (!chessBoard.blackWins() && !chessBoard.whiteWins() && !chessBoard.isStaleMate(white_turn)){
                    switch (charPressed){
                        case 'w':
                            if (selectedSpace[0] > 1) {
                                selectedSpace[0] -= 1;
                            }
                            break;
                        case 's':
                            if (selectedSpace[0] < 8){
                                selectedSpace[0] += 1;
                            }
                            break;
                        case 'a':
                            if (selectedSpace[1] > 1) {
                                selectedSpace[1] -= 1;
                            }
                            break;
                        case 'd':
                            if (selectedSpace[1] < 8){
                                selectedSpace[1] += 1;
                            }
                            break;
                        // Key Space is used to select a piece or place a selected piece to a new position
                        case ' ':
                            // If no piece is currently selected
                            if (selectedPiece == null &&
                                    chessBoard.findPiecePosAtPosition(selectedSpace[0],selectedSpace[1]) != null){
                                PiecePos piece = chessBoard.findPiecePosAtPosition(selectedSpace[0], selectedSpace[1]);
                                if (piece.chessPiece().isWhitePiece(piece.chessPiece()) != white_turn){
                                    warnings = "Please select your own piece!";
                                }else if (validMove.nextValidMoves(piece, chessBoard.showBoard()).isEmpty()
                                        && validAttack.nextValidAttacks(piece, chessBoard.showBoard()).isEmpty()){
                                    warnings = "There is no valid moves or attacks for the selected piece, please select again!";
                                }
                                else{
                                    selectedPiece = piece;
                                }
                            }
                            // If there is a piece is selected by player
                            else if (selectedPiece != null){
                                // Deselect current piece
                                if (selectedPiece.row() == selectedSpace[0] && selectedPiece.column() == selectedSpace[1]){
                                    selectedPiece = null;
                                }
                                // Selected space is not accessible by current piece
                                else if (!validMove.isValidMove(selectedPiece, selectedSpace[0], selectedSpace[1], chessBoard.showBoard())
                                        && !validAttack.isValidAttack(selectedPiece, selectedSpace[0], selectedSpace[1], chessBoard.showBoard())) {
                                    warnings = "Invalid move, please choose again!";
                                }
                                // Attack
                                else if(validAttack.isValidAttack(selectedPiece, selectedSpace[0], selectedSpace[1], chessBoard.showBoard())) {
                                    chessBoard.updateBoardAfterAttack(selectedSpace[0], selectedSpace[1], selectedPiece,
                                            new PiecePos(selectedPiece.chessPiece(), selectedSpace[0], selectedSpace[1], true));
                                    white_turn = !white_turn;
                                    previousMove = new int[][]{{selectedPiece.row(),selectedPiece.column()}, {selectedSpace[0], selectedSpace[1]}};
                                    selectedPiece = null;
                                }
                                // Move
                                else{
                                    chessBoard.updateBoard(selectedPiece, new PiecePos(selectedPiece.chessPiece(), selectedSpace[0], selectedSpace[1], true));
                                    white_turn = !white_turn;
                                    previousMove = new int[][]{{selectedPiece.row(),selectedPiece.column()}, {selectedSpace[0], selectedSpace[1]}};
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



    // All rendering goes in here.
    @Override
    public void paintComponent(Graphics g) {
        // This will clear the screen.
        super.paintComponent(g);

        // Sets the colour for every successive draw call.
        g.setColor(Color.BLUE);
        // Render some text.
        g.drawString("Pawninator 5000", 10, 20);
        //Draw boarder of the chess board
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.BLACK);
        g2.drawRect(98,98,804,804);
        g2.setStroke(new BasicStroke(2));

        // Draw the chess board background
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                if ((x + y) % 2 == 1) {
                    g.fillRect(x * 100, y * 100, 100, 100);
                }
            }
        }
        g.setColor(Color.orange);
        // Draw a rectangle at a position along the screen depending on how many Ws were pressed.
        g.fillRect(selectedSpace[1]*100, selectedSpace[0]*100, 100, 100);
        // Show the previous move by player
        if (previousMove != null){
            g.setColor(Color.YELLOW);
            // Draw a rectangle at a position along the screen depending on how many Ws were pressed.
            g.drawRect(previousMove[0][1]*100, previousMove[0][0]*100, 100, 100);
            g.drawRect(previousMove[1][1]*100, previousMove[1][0]*100, 100, 100);
        }

        // Show possible positions for the selected piece to move
        g.setColor(Color.GREEN);
        if (selectedPiece != null){
            for (int i : validMove.nextValidMoves(selectedPiece, chessBoard.showBoard())){
                g.drawRoundRect(i%10*100,i/10*100, 100, 100, 40, 40);
            }
        }
        // Show possible positions for the selected piece to attack
        g.setColor(Color.RED);
        if (selectedPiece != null){
            for (int i : validAttack.nextValidAttacks(selectedPiece, chessBoard.showBoard())){
                g.drawRoundRect(i%10*100,i/10*100, 100, 100, 40, 40);
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
        ReadImageFromFile read = new ReadImageFromFile();
        for (PiecePos piecePos : chessBoard.showBoard()){
            g.drawImage(read.readPieceFromFile(piecePos.chessPiece()),
                    piecePos.column() * 100,
                    piecePos.row() * 100,
                    null);
        }

        g.setColor(Color.red);
        Font font = new Font("Snell Roundhand", Font.BOLD, 100);
        g.setFont(font);
        if (chessBoard.whiteWins()){
            g.drawString("White Wins!", 250, 500);
        }
        if (chessBoard.blackWins()){
            g.drawString("Black Wins!", 250, 500);
        }
        if (chessBoard.isStaleMate(white_turn)){
            g.drawString("Stalemate!", 250, 500);
        }
//        // Some fancy-ish graphics : ] (that'll only refresh on keypress) (need all the double / float casting for silly floating point precision reasons)
//        float time = (float)((((double) System.currentTimeMillis()) / 4000.0) % 1.0);
//        g.setColor(new Color(time, 1.0f - time, 0.5f + time / 2));
//        g.fillRect(150, 915, 50, 50);
    }
}


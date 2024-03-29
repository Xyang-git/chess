import GameScreens.*;
import javax.swing.*;

public class Main {
    // I've taken this demo from here:
    // https://docs.oracle.com/javase/tutorial/uiswing/painting/refining.html
    // And simplified it a bit.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame("ChessSim 5000");
        // Sets the window size
        f.setSize(1000, 1000);
        // Make the window 'focusable' so the user can send key presses to it.
        f.setFocusable(true);
        // Make it so that when the user presses the red 'x' button, the java program finishes.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create an instantiation of our custom logic.
        MyChessGame myChessGame = new MyChessGame();
//        GameScreens.StartScreen startScreen = new GameScreens.StartScreen(this);
        // Add our custom logic to the window that the user sees.
//        f.add(startScreen);
        f.add(myChessGame);
        // Redirect key presses that the user sends to the window, to our custom game implementation.
        f.addKeyListener(myChessGame.GenMyKeyListener());
        f.setVisible(true);
    }

}













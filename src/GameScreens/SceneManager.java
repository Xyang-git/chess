package GameScreens;

import GameScreens.MyChessGame;

import javax.swing.*;

public class SceneManager {
    JFrame f;
    JPanel currentScene;

    public void Initialize(){
        f = new JFrame("ChessSim 5000");
        // Sets the window size
        f.setSize(1000, 1000);
        // Make the window 'focusable' so the user can send key presses to it.
        f.setFocusable(true);
        // Make it so that when the user presses the red 'x' button, the java program finishes.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ShowMenu();
    }
    public void ShowGame(){
        SetScene(new MyChessGame());
        // TODO: setup key listener.
    }
    public void ShowMenu(){
        SetScene(new StartScreen(this));
    }
    private void SetScene(JPanel scene){
        f.remove(currentScene);
        currentScene = scene;
        f.add(currentScene);
    }

}

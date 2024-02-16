package GameScreens;

import GameScreens.SceneManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class StartScreen extends JPanel{
    SceneManager manager;

    public StartScreen(SceneManager sceneManager){
        this.manager = sceneManager;
        JPanel title = new JPanel();
        title.setBackground(Color.BLACK);
        title.setBounds(100, 100, 500, 500);
        JLabel titleName = new JLabel("Chess");
        titleName.setForeground(Color.WHITE);
        titleName.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 90));
        title.add(titleName);
        JPanel startButtonPanel = new JPanel();
        startButtonPanel. setBounds (300, 400, 200, 100); startButtonPanel.setBackground(Color.black);
        JButton startButton = new JButton("START");
        startButton.setBackground(Color.black); startButton.setForeground(Color.white);
        startButton.setFont (new Font("TIMES NEW ROMAN", Font.PLAIN, 25));
        startButton.setFocusPainted(false);
        startButton.setMnemonic(KeyEvent.VK_D);
        startButton.setActionCommand("play");
//        startButton.addActionListener(this);
        startButtonPanel.add(startButton);
//        f.add(title);
//        f.add(startButtonPanel);
    }

    public void actionPerformed(ActionEvent e) {
        if ("play".equals(e.getActionCommand())) {
            manager.ShowGame();
        }
    }
}

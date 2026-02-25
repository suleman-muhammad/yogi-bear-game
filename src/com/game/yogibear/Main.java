package com.game.yogibear;

import com.game.yogibear.util.AssetsLoader;
import com.game.yogibear.core.BackEnd;
import com.game.yogibear.data.ScoreManager;
import com.game.yogibear.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private static JFrame window;
    private static JPanel mainContainer;
    private static CardLayout cardLayout;

    private static BackEnd back;
    private static GamePanel gamePanel;
    private static ScorePanel scorePanel;
    private static MainMenuPanel menuPanel;
    private static Timer gameWatcherTimer;
    private static HighScorePanel highScorePanel; // New field
    private static ScoreManager scoreManager;
    final static String MENU_CARD = "Menu";
    final static String GAME_CARD = "Game";
    final static String HIGH_SCORE_CARD = "HighScores";

    public static void main(String[] args) {
        window = new JFrame("Yogi Bear Game");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmAndExit();
            }
        });

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        AssetsLoader initialAssets = new AssetsLoader();
        menuPanel = new MainMenuPanel(900, 600, initialAssets,
                e -> startGame(),
                e -> showHighScores() // Changed from showHighScores for now to test exit
        );
        scoreManager = new ScoreManager();
        highScorePanel = new HighScorePanel(500,500,initialAssets,scoreManager,
                e-> goToMainMenu());
        mainContainer.add(menuPanel, MENU_CARD);
        mainContainer.add(highScorePanel,HIGH_SCORE_CARD);
        window.add(mainContainer, BorderLayout.CENTER);
        window.setSize(new Dimension(1000,600));
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        cardLayout.show(mainContainer, MENU_CARD);
    }

    private static void confirmAndExit() {

        boolean isGameRunning = (gameWatcherTimer != null && gameWatcherTimer.isRunning());


        if (isGameRunning) {
            gameWatcherTimer.stop();
            if (gamePanel != null) gamePanel.stopGameTimer();
            if (scorePanel != null) scorePanel.stopScoreTimer();
        }


        String[] options = {"Exit Game", "Cancel"};


        int choice = JOptionPane.showOptionDialog(
                window,
                "Are you sure you want to quit the game?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );


        if (choice == JOptionPane.YES_OPTION) {

            System.out.println("Exiting game.");
            System.exit(0);
        } else {

            if (isGameRunning) {
                gameWatcherTimer.start();
                if (gamePanel != null) gamePanel.startGameTimer();
                if (scorePanel != null) scorePanel.startScoreTimer();
            }
        }
    }

    private static void startGame() {
        String playerName = menuPanel.getPlayerName();
        System.out.println("Starting game for player: " + playerName);
        initializeGameState();
        cardLayout.show(mainContainer, GAME_CARD);
        gamePanel.requestFocusInWindow();
        startGameWatcher();
    }

    private static void initializeGameState() {
        back = new BackEnd();
        gamePanel = new GamePanel(back);
        scorePanel = new ScorePanel(back.assets, back, e -> goToMainMenu(), r-> restartGame());

        JPanel combinedGamePanel = new JPanel(new BorderLayout());
        combinedGamePanel.add(gamePanel, BorderLayout.CENTER);
        combinedGamePanel.add(scorePanel, BorderLayout.EAST);

        try {
            mainContainer.remove(combinedGamePanel);
        } catch (Exception _) { }
        mainContainer.add(combinedGamePanel, GAME_CARD);
    }

    private static void startGameWatcher() {
        if (gameWatcherTimer != null && gameWatcherTimer.isRunning()) {
            gameWatcherTimer.stop();
        }
        gameWatcherTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGameStatus();
            }
        });
        gameWatcherTimer.start();
    }

    private static void checkGameStatus() {
        if (!back.gameOn()) {
            gameWatcherTimer.stop();
            gamePanel.stopGameTimer();
            scorePanel.stopScoreTimer();
            showGameOverScreen();
            saveScores();
        }
    }

    private static void showGameOverScreen() {
        GameOverPanel overPanel = new GameOverPanel(
                back.assets,
                back.score,
                e -> restartGame(),
                e -> goToMainMenu()
        );

        overPanel.setOpaque(true);
        overPanel.setBackground(Color.BLACK);

        window.getContentPane().removeAll();
        window.add(overPanel, BorderLayout.CENTER);
        window.revalidate();
        window.repaint();
    }


    private static void showHighScores() {
        highScorePanel.refreshScores();
        cardLayout.show(mainContainer, HIGH_SCORE_CARD);
    }

    private static void goToMainMenu() {
        window.getContentPane().removeAll();
        window.add(mainContainer, BorderLayout.CENTER);
        cardLayout.show(mainContainer, MENU_CARD);
        window.revalidate();
        window.repaint();
    }

    private static void restartGame() {
        goToMainMenu();
    }


    private static void saveScores(){
        int scores = back.score;
        String name = menuPanel.getPlayerName();
        scoreManager.addScoreToDatabase(name,scores);
    }
}
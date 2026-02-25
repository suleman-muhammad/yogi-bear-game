package com.game.yogibear.ui;

import com.game.yogibear.util.AssetsLoader;
import com.game.yogibear.core.BackEnd;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ScorePanel extends JPanel implements ActionListener {


    private JLabel scoreLabel;
    private JPanel heartsPanel;
    private JButton mainMenuButton;
    private JLabel levelValueLabel;
    private JLabel timerValueLabel;
    private Image backgroundImage;
    private ImageIcon heartIcon;


    private int currentScore;
    private int lives;
    private Timer gameTimer;
    private int counter;
    private BackEnd back;
    private long startTime;

    private final Font SCORE_FONT = new Font("Arial", Font.BOLD, 24);
    private final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 18);
    private final Color TEXT_COLOR = Color.WHITE;
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18); // For titles like "LEVEL", "TIME"
    private final Font VALUE_FONT = new Font("Arial", Font.BOLD, 24); // For values like "5", "01:30"
    private final Color TITLE_COLOR = Color.WHITE;
    private final Color VALUE_COLOR = Color.YELLOW;

    public ScorePanel(AssetsLoader assets, BackEnd back, ActionListener mainMenu, ActionListener restartGame) {
        this.setPreferredSize(new Dimension(300, 600));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setOpaque(false);
        this.backgroundImage = assets.road;
        startTime = System.currentTimeMillis();
        addSectionTitle("LEVEL");
        levelValueLabel = createValueLabel("1");
        add(levelValueLabel);
        add(Box.createVerticalStrut(20));
        initScoreSection();
        add(Box.createVerticalStrut(30)); // Vertical spacing gap
        initLivesSection();
        add(Box.createVerticalStrut(30)); // Vertical spacing gap
        add(Box.createVerticalGlue());
        initButtonSection();
        addSectionTitle("TIME");
        timerValueLabel = createValueLabel("00:00");
        add(timerValueLabel);
        add(Box.createVerticalStrut(20));
        this.gameTimer = new Timer(16, this);
        gameTimer.start();
        counter = 0;
        this.back = back;
        this.currentScore = back.score;
        this.lives = back.bear.lives;
        this.heartIcon = new ImageIcon(assets.heart);
        mainMenuButton.addActionListener(mainMenu);
    }



    /**
     * Sets up the Score Label
     */
    private void initScoreSection() {
        JLabel titleLabel = new JLabel("SCORE");
        titleLabel.setFont(LABEL_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreLabel = new JLabel("0");
        scoreLabel.setFont(SCORE_FONT);
        scoreLabel.setForeground(Color.YELLOW); // Make score pop
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);
        add(Box.createVerticalStrut(5)); // Small gap
        add(scoreLabel);
    }

    /**
     * Sets up the Lives container panel
     */
    private void initLivesSection() {
        JLabel titleLabel = new JLabel("LIVES");
        titleLabel.setFont(LABEL_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(10));

        // heartsPanel is a sub-panel dedicated to holding heart icons horizontally
        heartsPanel = new JPanel();
        // FlowLayout centers them horizontally
        heartsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        heartsPanel.setOpaque(false); // Transparent so background shows through
        heartsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(heartsPanel);
    }

    private void addSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(TITLE_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
        add(Box.createVerticalStrut(5)); // Small gap below title
    }
    private JLabel createValueLabel(String startText) {
        JLabel label = new JLabel(startText);
        label.setFont(VALUE_FONT);
        label.setForeground(VALUE_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Sets up the bottom buttons
     */
    private void initButtonSection() {

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        mainMenuButton = new JButton("MAIN MENU");
        mainMenuButton.setFont(buttonFont);
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuButton.setMaximumSize(new Dimension(250, 50));
        mainMenuButton.setFocusable(false);
        add(Box.createVerticalStrut(15)); // Gap between buttons
        add(mainMenuButton);
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        int tileWidth = this.backgroundImage.getWidth(null);
        int tileHeight = this.backgroundImage.getHeight(null);


        if (tileWidth <= 0 || tileHeight <= 0) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        for (int y = 0; y < this.getHeight(); y += 64) {
            // Notice we increment 'x' by tileWidth, not 1
            for (int x = 0; x < this.getWidth(); x += 64) {
                // Draw the tile at the current grid position
                g2d.drawImage(this.backgroundImage, x, y, null);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        counter++;
        if (counter >= 16) {

            counter = 0;

        }
        updateScore();
        updateLives();
        updateLevel();
        updateTimer();
        repaint();
    }

    public void updateLevel() {
        levelValueLabel.setText(String.valueOf(back.level));
    }

    public void updateTimer() {
        long now = System.currentTimeMillis();
        long elapsedMillis = now - this.startTime;

        // Math to get minutes and seconds
        int totalSeconds = (int) (elapsedMillis / 1000);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        // Format string nicely with leading zeros (e.g., "02:05")
        timerValueLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }


    /**
     * Updates the numerical score display.
     */
    public void updateScore() {
        this.currentScore = back.score;
        scoreLabel.setText(String.format("%d", currentScore));
    }

    /**
     * Rebuilds the hearts panel based on the number of lives remaining.
     *
     *
     */
    public void updateLives() {

        lives = Math.max(0, back.bear.lives);


        heartsPanel.removeAll();


        for (int i = 0; i < lives; i++) {
            heartsPanel.add(new JLabel(heartIcon));
        }


        heartsPanel.revalidate();
        heartsPanel.repaint();
    }


    public JButton getMainMenuButton() {
        return mainMenuButton;
    }


    public void stopScoreTimer() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }
    }

    public void startScoreTimer() {
        if (gameTimer != null && !gameTimer.isRunning()) {
            gameTimer.start();
        }
    }
}
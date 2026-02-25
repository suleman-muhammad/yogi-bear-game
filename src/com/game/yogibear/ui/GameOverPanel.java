package com.game.yogibear.ui;

import com.game.yogibear.util.AssetsLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel {


    private AssetsLoader assets;


    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
    private final Font SCORE_FONT = new Font("Arial", Font.PLAIN, 24);
    private final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);
    private final Color TITLE_COLOR = Color.RED;
    private final Color TEXT_COLOR = Color.WHITE;

    private final Color OVERLAY_COLOR = new Color(0, 0, 0, 150);

    /**
     * Constructor for the Game Over screen.
     * @param assets        The assets.AssetsLoader instance to access images.
     * @param finalScore    The score the player achieved.
     * @param restartAction The action to perform when "Try Again" is clicked.
     * @param menuAction    The action to perform when "Main Menu" is clicked.
     */
    public GameOverPanel(AssetsLoader assets, int finalScore, ActionListener restartAction, ActionListener menuAction) {
        this.assets = assets;


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setOpaque(true);

        setBorder(new EmptyBorder(50, 50, 50, 50));


        add(Box.createVerticalGlue());

        addTitleLabel();
        add(Box.createVerticalStrut(20));

        addScoreLabel(finalScore);
        add(Box.createVerticalStrut(50));

        addButtons(restartAction, menuAction);


        add(Box.createVerticalGlue());
    }


    private void addTitleLabel() {
        JLabel title = new JLabel("GAME OVER");
        title.setFont(TITLE_FONT);
        title.setForeground(TITLE_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        title.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                g.setColor(Color.BLACK);
                g.drawString("GAME OVER", 2, title.getHeight() - 2);
                g.setColor(TITLE_COLOR);
                g.drawString("GAME OVER", 0, title.getHeight() - 4);
            }
        });
        add(title);
    }


    private void addScoreLabel(int score) {
        JLabel scoreLabel = new JLabel("Final Score: " + score);
        scoreLabel.setFont(SCORE_FONT);
        scoreLabel.setForeground(TEXT_COLOR);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scoreLabel);
    }


    private void addButtons(ActionListener restartAction, ActionListener menuAction) {

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton menuBtn = createStyledButton("Main Menu");
        menuBtn.addActionListener(menuAction);

        buttonPanel.add(menuBtn);

        add(buttonPanel);
    }

    /**
     * Helper to create consistent looking buttons
     */
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(BUTTON_FONT);
        btn.setFocusable(false);
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return btn;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();


        if (assets != null && assets.grass != null) {
            int tileWidth = assets.grass.getWidth(null);
            int tileHeight = assets.grass.getHeight(null);


            if (tileWidth > 0 && tileHeight > 0) {
                for (int y = 0; y < getHeight(); y += 64) {
                    for (int x = 0; x < getWidth(); x += 64) {
                        g2d.drawImage(assets.grass, x, y, null);
                    }
                }
            }
        }


        g2d.setColor(OVERLAY_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }
}
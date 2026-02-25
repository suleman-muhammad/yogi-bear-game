package com.game.yogibear.ui;

import com.game.yogibear.data.ScoreEntry;
import com.game.yogibear.util.AssetsLoader;
import com.game.yogibear.data.ScoreManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class HighScorePanel extends JPanel {

    private AssetsLoader assets;
    private ScoreManager scoreManager;
    private JPanel scoreContainer;

    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
    private final Font HEADER_FONT = new Font("Arial", Font.BOLD, 22);
    private final Font SCORE_FONT = new Font("Arial", Font.BOLD, 20);
    private final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);


    private final Color TEXT_COLOR = Color.WHITE;
    private final Color GOLD = new Color(255, 215, 0);
    private final Color SILVER = new Color(192, 192, 192);
    private final Color BRONZE = new Color(205, 127, 50);


    private final int SIDE_MARGIN_PCT = 15;
    private final int TOP_MARGIN_PCT = 15;

    public HighScorePanel(int width, int height, AssetsLoader assets, ScoreManager scoreManager, ActionListener backAction) {
        this.assets = assets;
        this.scoreManager = scoreManager;
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());
        this.setOpaque(false); // Important for background drawing

        int marginX = (width * SIDE_MARGIN_PCT) / 100;
        int marginY = (height * TOP_MARGIN_PCT) / 100;


        JLabel titleLabel = createStyledLabel("TOP RANGERS", TITLE_FONT, TEXT_COLOR);
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        this.add(titleLabel, BorderLayout.NORTH);



        this.scoreContainer = new JPanel();
        this.scoreContainer.setLayout(new BoxLayout(this.scoreContainer, BoxLayout.Y_AXIS));
        this.scoreContainer.setOpaque(false);
        this.scoreContainer.setBorder(new EmptyBorder(marginY/2, marginX + 20, marginY, marginX + 20));


        populateScores();

        this.add(this.scoreContainer, BorderLayout.CENTER);



        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(20,0,30,0));

        JButton backButton = createGrassyButton("BACK TO MENU");
        backButton.addActionListener(backAction);
        buttonPanel.add(backButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }


    public void refreshScores() {

        this.scoreContainer.removeAll();

        populateScores();

        this.scoreContainer.revalidate();
        this.scoreContainer.repaint();
    }


    private void populateScores() {

        this.scoreContainer.add(createHeaderRow());
        this.scoreContainer.add(Box.createVerticalStrut(10));


        List<ScoreEntry> topScores = scoreManager.getTop10Scores();

        int rank = 1;
        if (topScores.isEmpty()) {
            this.scoreContainer.add(Box.createVerticalStrut(50));
            this.scoreContainer.add(createStyledLabel("No scores yet!", SCORE_FONT, Color.LIGHT_GRAY));
        } else {
            for (ScoreEntry entry : topScores) {
                Color rankColor = TEXT_COLOR;
                if (rank == 1) rankColor = GOLD;
                else if (rank == 2) rankColor = SILVER;
                else if (rank == 3) rankColor = BRONZE;

                JPanel row = createScoreRow(rank, entry.getName(), entry.getScore(), rankColor);
                this.scoreContainer.add(row);
                this.scoreContainer.add(Box.createVerticalStrut(8)); // Gap between rows
                rank++;
            }
        }

        this.scoreContainer.add(Box.createVerticalGlue());
    }

    private JPanel createHeaderRow() {
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(900, 30));
        panel.add(createStyledLabel("RANK", HEADER_FONT, Color.LIGHT_GRAY));
        panel.add(createStyledLabel("NAME", HEADER_FONT, Color.LIGHT_GRAY));
        panel.add(createStyledLabel("SCORE", HEADER_FONT, Color.LIGHT_GRAY));
        return panel;
    }

    private JPanel createScoreRow(int rank, String name, int score, Color color) {
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.setOpaque(false);

        panel.setMaximumSize(new Dimension(900, 35));

        panel.add(createStyledLabel("#" + rank, SCORE_FONT, color));

        String displayName = (name != null) ? name.toUpperCase() : "UNKNOWN";
        panel.add(createStyledLabel(displayName, SCORE_FONT, color));
        panel.add(createStyledLabel(String.format("%,d", score), SCORE_FONT, color));
        return panel;
    }


    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        // Add shadow effect for readability
        label.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                g.setColor(Color.BLACK);
                g.drawString(text, 2, label.getHeight() - 2); // Shadow
                g.setColor(color);
                g.drawString(text, 0, label.getHeight() - 4); // com.game.yogibear.Main text
            }
        });
        return label;
    }

    private JButton createGrassyButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(BUTTON_FONT);
        btn.setFocusable(false);

        btn.setBackground(new Color(34, 139, 34));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 205, 50), 4),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return btn;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();


        if (assets.grass != null) {

            for (int y = 0; y < h; y += 64) {
                for (int x = 0; x < w; x += 64) {
                    g2d.drawImage(assets.grass, x, y, null);
                }
            }

        }


        int marginX = (w * SIDE_MARGIN_PCT) / 100;
        int marginY = (h * TOP_MARGIN_PCT) / 100;
        int roadX = marginX;
        int roadY = marginY;
        int roadW = w - (marginX * 2);
        int roadH = h - (marginY * 2);


        if (assets.road != null) {
            Shape originalClip = g2d.getClip();
            g2d.setClip(roadX, roadY, roadW, roadH);


            for (int y = roadY; y < roadY + roadH; y += 64) {
                for (int x = roadX; x < roadX + roadW; x += 64) {
                    g2d.drawImage(assets.road, x, y, null);
                }
            }


            g2d.setClip(originalClip);
        }


//        g2d.setColor(new Color(101, 67, 33));
//        g2d.setStroke(new BasicStroke(4));
//        g2d.drawRect(roadX, roadY, roadW, roadH);
    }
}
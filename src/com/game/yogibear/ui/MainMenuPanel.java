package com.game.yogibear.ui;

import com.game.yogibear.util.AssetsLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    private JTextField nameField;
    private JButton startButton;
    private JButton highScoreButton;
    private AssetsLoader assets;


    private int panelWidth;
    private int panelHeight;


    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
    private final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    private final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);

    public MainMenuPanel(int width, int height, AssetsLoader assets, ActionListener startAction, ActionListener highScoreAction) {
        this.panelWidth = width;
        this.panelHeight = height;
        this.assets = assets;


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(width, height));

        this.setBorder(new EmptyBorder(50, 50, 50, 50));


        JLabel titleLabel = createTitleLabel();
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel(startAction, highScoreAction);


        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createVerticalStrut(50)); // Gap below title
        add(inputPanel);
        add(Box.createVerticalStrut(50)); // Gap below input
        add(buttonPanel);
        add(Box.createVerticalGlue());


        updateStartButtonState();
    }

    private JLabel createTitleLabel() {
        JLabel label = new JLabel("YOGI BEAR ADVENTURE");
        label.setFont(TITLE_FONT);
        label.setForeground(Color.YELLOW);

        label.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                g2d.setColor(Color.BLACK);
                g2d.drawString(label.getText(), 3, label.getHeight() - 3);

                g2d.setColor(label.getForeground());
                g2d.drawString(label.getText(), 0, label.getHeight() - 5);
            }
        });
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("ENTER YOUR NAME:");
        nameLabel.setFont(LABEL_FONT);
        nameLabel.setForeground(Color.WHITE);

        nameField = new JTextField(15);
        nameField.setFont(LABEL_FONT);
        nameField.setHorizontalAlignment(JTextField.CENTER);


        nameField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { updateStartButtonState(); }
            public void removeUpdate(DocumentEvent e) { updateStartButtonState(); }
            public void insertUpdate(DocumentEvent e) { updateStartButtonState(); }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        return panel;
    }

    private JPanel createButtonPanel(ActionListener startAction, ActionListener highScoreAction) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 20));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.setMaximumSize(new Dimension(400, 150));

        startButton = createStyledButton("START GAME", Color.GREEN.darker());
        startButton.addActionListener(startAction);

        startButton.setEnabled(false);

        highScoreButton = createStyledButton("CHECK HIGHEST SCORE", Color.ORANGE.darker());
        highScoreButton.addActionListener(highScoreAction);

        panel.add(startButton);
        panel.add(highScoreButton);
        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(BUTTON_FONT);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusable(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return btn;
    }


    private void updateStartButtonState() {
        boolean hasText = nameField.getText().trim().length() > 0;
        startButton.setEnabled(hasText);
        if (hasText) {
            startButton.setBackground(Color.GREEN.darker());
        } else {
            startButton.setBackground(Color.GRAY);
        }
    }

    public String getPlayerName() {
        return nameField.getText().trim();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        if (assets.grass != null) {
            for (int y = 0; y < panelHeight; y += 64) {
                for (int x = 0; x < panelWidth; x += 64) {
                    g2d.drawImage(assets.grass, x, y, null);
                }
            }
        }


        if (assets.bearWalkingFront != null && assets.bearWalkingFront.length > 0) {

            g2d.drawImage(assets.bearWalkingFront[0], 100, panelHeight - 150, 128, 128, null);
        }

        if (assets.ranger != null) {

            g2d.drawImage(assets.ranger, panelWidth - 200, panelHeight - 150, 128, 128, null);
        }

        if (assets.basket != null) {

            g2d.drawImage(assets.basket, 50, 50, null);
            g2d.drawImage(assets.basket, panelWidth - 100, 100, null);
            g2d.drawImage(assets.basket, panelWidth / 2 - 32, panelHeight - 200, null);
        }
    }
}

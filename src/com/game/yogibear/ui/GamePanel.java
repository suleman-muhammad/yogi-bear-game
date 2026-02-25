package com.game.yogibear.ui;

import com.game.yogibear.core.BackEnd;
import com.game.yogibear.entities.Basket;
import com.game.yogibear.entities.Hurdle;
import com.game.yogibear.entities.Ranger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    Timer gameTimer;
    BackEnd back;
    int counter = 0;
    boolean isPressed = false;
    int keySelected = -100;

    public GamePanel(BackEnd back){
        super(new FlowLayout());
        this.setBackground(Color.GREEN);
        this.setSize(back.ScreenWidth,back.ScreenHeight);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new MyKeyAdapter());
        this.back = back;
        gameTimer = new Timer(16,this);
        gameTimer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e){
       if(back.gameOn()){
           counter++;
           if (counter >= 8){
               back.moveRangers();
               counter = 0;

           }


       }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Random rn = new Random();
        for (int y = 0; y < back.ScreenHeight; y += 64) {
            for (int x = 0; x < back.ScreenWidth; x += 64) {
                g2d.drawImage(back.assets.grass, x, y, null);
            }
        }
        for(Hurdle h :  back.hurdles){
            g2d.drawImage(h.getSprite(), h.getX(), h.getY(), null);
        }
        for(Ranger r : back.rangers){
            g2d.drawImage(r.getSpirit(), r.getX(), r.getY(), null);
        }
        for(Basket b : back.baskets){
            g2d.drawImage(b.spirit, b.x, b.y, null);
        }


        g2d.drawImage(back.bear.spirit, back.bear.getX(), back.bear.getY(),null);

    }

    private class MyKeyAdapter extends KeyAdapter {


        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if(!back.gameOn()){
                return;
            }

            if(isPressed && (key != keySelected)){
                return;
            }

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) back.moveBear("left");
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) back.moveBear("right");
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) back.moveBear("up");
            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) back.moveBear("down");

            isPressed = true;
            keySelected = key;

        }


        @Override
        public void keyReleased(KeyEvent e) {

            if(!back.gameOn()){
                return;
            }

            back.moveBear("1");
            isPressed = false;
            keySelected = -100;
        }

    }


    public void stopGameTimer() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }
    }


    public void startGameTimer() {
        if (gameTimer != null && !gameTimer.isRunning()) {
            gameTimer.start();
        }
    }
}

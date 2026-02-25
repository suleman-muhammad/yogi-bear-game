package com.game.yogibear.entities;

import com.game.yogibear.util.AssetsLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class YogiBear {

    private final int startX = 50;
    private final int startY = 50;
    private final int BEAR_WIDTH = 30;
    private final int BEAR_Height = 30;
    private final int TARGET_WIDTH = 20;
    private final int TARGET_Height = 20;




    Rectangle bearBounds;
    public BufferedImage spirit;
    private AssetsLoader assets;


    int x;
    int y;
    int dir;
    int angle;
    int state;
    public int lives;



    public YogiBear(AssetsLoader assets){
        this.dir = 1;
        this.state = 0;
        angle = 90;
        this.x = startX;
        this.y = startY;
        this.spirit = assets.bears[0];
        this.assets = assets;
        this.lives = 5;
        bearBounds = new Rectangle(x,y,BEAR_WIDTH,BEAR_Height);

    }


    public boolean isValidMove(String move){
        if (angle == 90){
            return (dir == 1 && !move.equalsIgnoreCase("up")) ||
                    (dir == -1 && !move.equalsIgnoreCase("down"));
        }else{
            return (dir == 1 && !move.equalsIgnoreCase("left")) ||
                    (dir == -1 && !move.equalsIgnoreCase("right"));
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void move(String move){

        if (angle == 0){
            switch(move){
                case "down":
                    this.spirit = assets.bears[0];
                    angle  = 90;
                    dir = 1;
                    break;
                case "up":
                    this.spirit = assets.bears[1];
                    angle = 90;
                    dir = -1;
                    break;
                default:
                    this.x += (dir * 5);
                    if (dir == 1){
                        spirit = this.assets.bearWalkingRight[state];

                    }else{
                        spirit = this.assets.bearWalkingLeft[state];

                    }
                    state = (state + 1) % 4;
                    break;
            }
        }


        if (angle == 90){
            switch(move){
                case "left":
                    this.spirit = assets.bears[2];
                    angle  = 0;
                    dir = -1;
                    break;
                case "right":
                    this.spirit = assets.bears[3];
                    angle = 0;
                    dir = 1;
                    break;
                default:
                    this.y += (dir * 5);

                    if (dir == 1){
                        spirit = this.assets.bearWalkingFront[state];

                    }else{
                        spirit = this.assets.bearWalkingBack[state];

                    }
                    state = (state + 1) % 2;
                    break;
            }
        }

        this.bearBounds.setLocation(x,y);

    }

    public void stop(){
        if (angle == 0){
            if (dir == 1) {
                this.spirit = assets.bears[3];
            }
            else
                this.spirit = assets.bears[2];
        }else{
            if (dir == 1) {
                this.spirit = assets.bears[0];
            }
            else
                this.spirit = assets.bears[1];
        }
        state = 0;
        return;
    }


    public boolean inBoundry(int x, int y){
        Rectangle target = new Rectangle(x,y,TARGET_WIDTH,TARGET_Height);
        return bearBounds.intersects(target);
    }


    public void restart(){
        this.x = startX;
        this.y = startY;
        state = 0;
        angle = 90;
        dir = 1;
    }



    public Rectangle getBound(int x,int y){
        return new Rectangle(x,y,BEAR_WIDTH,BEAR_Height);
    }
    public Rectangle getBound(){
        return new Rectangle(x,y,BEAR_WIDTH,BEAR_Height);
    }





}

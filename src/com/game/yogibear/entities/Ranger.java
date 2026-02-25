package com.game.yogibear.entities;

import com.game.yogibear.util.AssetsLoader;

import java.awt.image.BufferedImage;

public abstract class Ranger{
    protected int x;
    protected int y;
    protected int dir;
    protected int state;
    protected AssetsLoader asset;

    final int screenHeight;
    final int screenWidth;


    private final int RANGER_WIDTH = 60;
    private final int RANGER_Height = 60;
    public Ranger(int x, int y, int dir, int screenHeight, int screenWidth, AssetsLoader asset){
        this.x = x;
        this.y = y;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.asset = asset;
        this.dir = dir;
        int state = 0;
    }

    abstract public void updateState(int coordinate);
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }


    abstract public BufferedImage getSpirit();

//
}

package com.game.yogibear.entities;

import com.game.yogibear.util.AssetsLoader;

import java.awt.image.BufferedImage;

public class HorizontalRanger extends Ranger {


    public HorizontalRanger(int x, int y, int dir, int screenHeight, int screenWidth, AssetsLoader asset) {
        super(x, y,dir,screenHeight,screenWidth,asset);
    }
    @Override
    public void updateState(int coordinate){
        this.x += (dir * coordinate);

        if(this.x < 50 || this.x > screenWidth-50){
            this.dir *= -1;
        }
        state = ((state + 1) % 4);

    }

    public BufferedImage getSpirit(){
        if (dir > 0){
            return asset.rangerWalkingRight[state];
        }else{
            return asset.rangerWalkingLeft[state];
        }
    }

}

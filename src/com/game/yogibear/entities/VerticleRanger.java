package com.game.yogibear.entities;

import com.game.yogibear.util.AssetsLoader;

import java.awt.image.BufferedImage;

public class VerticleRanger extends Ranger {

    public VerticleRanger(int x, int y, int dir, int screenHeight, int screenWidth, AssetsLoader asset) {
        super(x, y,dir,screenHeight,screenWidth,asset);
    }
    @Override
    public void updateState(int coordinate){
        this.y += (dir * coordinate);

        if(this.y < 50 || this.y > screenHeight-50){
            this.dir *= -1;
        }
        state = ((state + 1) % 2);
    }

    public BufferedImage getSpirit(){
        if (dir > 0){
            return asset.rangerWalkingFront[state];
        }else{
            return asset.rangerWalkingBack[state];
        }
    }
}

package com.game.yogibear.entities;

import com.game.yogibear.util.AssetsLoader;

public class Rock extends Hurdle {

    public Rock(int x, int y, int radius, AssetsLoader assets){
        super(x,y,radius,assets);
        this.sprite = assets.smallMount;
    }




}

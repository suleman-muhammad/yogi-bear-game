package com.game.yogibear.entities;

import com.game.yogibear.util.AssetsLoader;

public class Bush extends Hurdle {


    public Bush(int x, int y, int radius, AssetsLoader assets){
        super(x,y,radius,assets);
        this.sprite = assets.bush;
    }
}

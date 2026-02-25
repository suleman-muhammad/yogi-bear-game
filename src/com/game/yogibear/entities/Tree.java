package com.game.yogibear.entities;

import com.game.yogibear.util.AssetsLoader;

public class Tree extends Hurdle {

    public Tree(int x, int y, int radius, AssetsLoader assets){
        super(x,y,radius,assets);
        this.sprite  = assets.trees[0];
    }



}

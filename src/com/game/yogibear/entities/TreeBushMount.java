package com.game.yogibear.entities;

import com.game.yogibear.util.AssetsLoader;

public class TreeBushMount extends Hurdle {

    public TreeBushMount(int x, int y, int radius, AssetsLoader assets){
        super(x,y,radius,assets);
        this.sprite = assets.collTrees;
    }
}

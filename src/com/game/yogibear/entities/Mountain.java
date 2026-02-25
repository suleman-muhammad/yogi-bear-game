package com.game.yogibear.entities;

import com.game.yogibear.util.AssetsLoader;

public class Mountain extends Hurdle {

    public Mountain(int x, int y, int radius, AssetsLoader assets) {
        super(x, y, radius, assets);
        this.sprite = assets.mountain;
    }
}

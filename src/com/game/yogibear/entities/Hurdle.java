
package com.game.yogibear.entities;
import com.game.yogibear.util.AssetsLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Hurdle {

    int width ,height;
    protected int x;
    protected int y;

    protected int radius;

    protected AssetsLoader assets;
    protected BufferedImage sprite;


    protected Hurdle(int x, int y,int radius, AssetsLoader assets){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.assets = assets;



        if (this.radius == 10) this.width = (this.height = 30);
        if (this.radius == 5) this.width = (this.height = 15);

    }

    public  Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    public int getRadius() {
        return radius;
    }
    public BufferedImage getSprite(){
        return this.sprite;
    }


}

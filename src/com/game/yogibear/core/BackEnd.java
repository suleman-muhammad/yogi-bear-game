package com.game.yogibear.core;

import com.game.yogibear.util.AssetsLoader;
import com.game.yogibear.entities.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class BackEnd {
    public AssetsLoader assets;
    public ArrayList<Ranger> rangers;
    public ArrayList<Basket> baskets;
    public YogiBear bear;
    public ArrayList<Hurdle> hurdles;
    public final int ScreenHeight = 600;
    public final int ScreenWidth = 600;
    final String[] files = {"rangers.txt","baskets.txt","hurdles.txt"};
    public int level;
    public int score;
    final int MAX_LEVEL = 10;
    boolean gameWon = false;





    public BackEnd(){
        this.level = 1;
        this.assets = new AssetsLoader();
        rangers = new ArrayList<>();
        baskets = new ArrayList<>();
        hurdles = new ArrayList<>();
        hurdles.add(new TreeBushMount(256,256,20,assets));
        bear = new YogiBear(assets);
        score = 0;
        setupLevel();
    }



    public boolean isValidMove(String move,int x,int y){
        if (!bear.isValidMove(move)){
            return false;
        }

        Rectangle bounds = bear.getBound(x,y);
        for(Hurdle h : hurdles){
            if (bounds.intersects(h.getBounds())){
                return false;
            }
        }
        return true;
    }




    public void moveBear(String move){
        if (move.equals("1")){
            bear.stop();
            return;
        }


        int x = bear.getX();
        int y = bear.getY();

        switch(move){
            case "up":
                y -= 5;
                break;
            case "down":
                y += 5;
                break;
            case "left":
                x -= 5;
                break;
            case "right":
                x += 5;
        }

        if (!isValidMove(move,x,y)){
            return;
        }

        bear.move(move);
        updateGameStatus();
    }



    public void updateGameStatus(){
        int idx = -1;
        for(int i = 0; i<baskets.size(); i++){
            Basket b = baskets.get(i);
            if (bear.inBoundry(b.x,b.y)){
                idx = i;
                break;
            }
        }

        if (idx != -1){
            baskets.remove(idx);
            score++;
        }


        if (baskets.isEmpty()) {
            level++;

            if (level > MAX_LEVEL) {

                gameWon = true;
                System.out.println("YOU WIN THE ENTIRE GAME!");

            } else {

                setupLevel();
            }

            return;
        }



        for (Ranger r : rangers){
            if(bear.inBoundry(r.getX(), r.getY())){
                bear.restart();
                this.bear.lives--;
            }
        }

    }


    public boolean gameOn(){
        return this.bear.lives > 0 && !gameWon;
    }

    public boolean loadLevel(){
        String path = "./resources/levels/level"+ level +"/";
        BufferedReader bf;
        try{
            bf = new BufferedReader(new FileReader(path + files[0]));
            String line = null;
            this.rangers.clear();
            while((line = bf.readLine()) != null){
                String[] data = line.split(",");
                if (data.length != 4){
                    continue;
                }
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                int dir = Integer.parseInt(data[3]);
                Ranger r;
                if(data[2].equalsIgnoreCase("vertical")){

                    r = new VerticleRanger(x,y,dir,ScreenHeight,ScreenWidth,this.assets);
                }else{
                    r = new HorizontalRanger(x,y,dir,ScreenHeight,ScreenWidth,this.assets);
                }

                this.rangers.add(r);

            }

            bf.close();

            bf = new BufferedReader(new FileReader(path + files[1]));
            line = null;
            this.baskets.clear();
            while((line = bf.readLine()) != null){
                String[] data = line.split(",");
                if (data.length != 2){
                    continue;
                }
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                Basket b = new Basket(x,y,this.assets.basket);
                this.baskets.add(b);
            }

            bf = new BufferedReader(new FileReader(path + files[2]));
            line = null;
            this.hurdles.clear();
            while((line = bf.readLine()) != null){
                String[] data = line.split(",");
                if (data.length != 4){
                    continue;
                }
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                int radius = Integer.parseInt(data[3]);
                Hurdle h;
                switch (data[2]){
                    case "tree":
                        h = new Tree(x,y,radius,assets);
                        break;
                    case "mountain":
                        h = new Mountain(x,y,radius,assets);
                        break;
                    case "bush":
                        h = new Bush(x,y,radius,assets);
                        break;
                    case "smallmount":
                        h = new Rock(x,y,radius,assets);
                        break;
                    default:
                        h = null;
                }

                if (h != null) this.hurdles.add(h);
            }
            bf.close();

        }catch (Exception e){
            System.out.println("Error Happened");
            return false;
        }

        return true;
    }

    private void setupLevel() {
        rangers.clear();
        baskets.clear();
        hurdles.clear();
        if(!loadLevel()){
            System.out.println("CRITICAL ERROR: Could not load level " + level);
            bear.lives = 0;
            return;
        }
        bear.restart();
        System.out.println("Level " + level + " loaded successfully!");
    }


    public void moveRangers(){
        boolean isLost = false;
        Rectangle bearRec = bear.getBound();
        for(Ranger r: rangers){
            r.updateState(5);
            Rectangle rRec = new Rectangle(r.getX(), r.getY(),20,20);

            if(bearRec.intersects(rRec)){
                System.out.println("bear is at: " + bear.getX() + "," + bear.getY());
                isLost = true;
            }
        }

        if(isLost){
            bear.restart();
            bear.lives--;
        }
    }






}

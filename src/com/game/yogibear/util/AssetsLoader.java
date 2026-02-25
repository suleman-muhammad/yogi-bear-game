package com.game.yogibear.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class AssetsLoader {
    public BufferedImage[] rangerWalkingLeft;
    public BufferedImage[] rangerWalkingRight;
    public BufferedImage[] rangerWalkingFront;
    public BufferedImage[] rangerWalkingBack;

    public BufferedImage[] bearWalkingLeft;
    public BufferedImage[] bearWalkingRight;
    public BufferedImage[] bearWalkingFront;
    public BufferedImage[] bearWalkingBack;

    public BufferedImage[] bears;
    public BufferedImage grass;
    public BufferedImage collTrees;
    public BufferedImage[] trees;
    public BufferedImage bush;
    public BufferedImage smallMount;
    public BufferedImage mountain;
    public BufferedImage basket;
    public BufferedImage road;
    public BufferedImage heart;
    public BufferedImage ranger;





    public AssetsLoader(){
        try{
            grass  = ImageIO.read(new File(".\\resources\\media\\grass1.png"));
            collTrees  = ImageIO.read(new File(".\\resources\\media\\tree4.png"));
            basket  = ImageIO.read(new File(".\\resources\\media\\basket.png"));
            rangerWalkingFront = new BufferedImage[2];
            rangerWalkingBack = new BufferedImage[2];
            rangerWalkingLeft = new BufferedImage[4];
            rangerWalkingRight = new BufferedImage[4];
            bearWalkingFront = new BufferedImage[2];
            bearWalkingBack = new BufferedImage[2];
            bearWalkingLeft = new BufferedImage[4];
            bearWalkingRight = new BufferedImage[4];
            bears = new BufferedImage[4];
            road = ImageIO.read(new File("./resources/media/road1.png"));
            heart = ImageIO.read(new File("./resources/media/heart.png"));

            loadRangers();
            loadBears();
            loadHurdles();
            ranger = ImageIO.read(new File("./resources/media/r_facing_front.png"));

        }catch (Exception e){
            System.out.println("an error happened");
        }
    }



    public void loadRangers(){
        try{
            BufferedImage[][] rangersStates = {rangerWalkingFront,rangerWalkingBack};


            String[] states = {"rfstep","rbstep"};

            for(int j = 0; j < 2; j++){
                for(int k = 1; k<=2; k++){
                    String path = "./resources/media/"+states[j]+k+".png";
                    rangersStates[j][k-1] = ImageIO.read(new File(path));
                }
            }


            rangersStates[0] = rangerWalkingLeft;
            rangersStates[1] = rangerWalkingRight;

            states[0] = "rlstep";
            states[1] = "rrstep";

            for(int j = 0; j < 2; j++){
                for(int i = 0; i<4; i++){
                    String path = "./resources/media/"+states[j]+(i+1)+".png";

                    rangersStates[j][i] = ImageIO.read(new File(path));
                }
            }

        }catch (Exception e){
            System.out.println("something wrong Happend");
        }

    }




    public void loadBears(){
        try{
            BufferedImage[][] rangersStates = {bearWalkingFront,bearWalkingBack};


            String[] states = {"bfstep","bbstep"};

            for(int j = 0; j < 2; j++){
                for(int k = 1; k<=2; k++){
                    String path = "./resources/media/"+states[j]+k+".png";

                    rangersStates[j][k-1] = ImageIO.read(new File(path));
                }
            }


            rangersStates[0] = bearWalkingLeft;
            rangersStates[1] = bearWalkingRight;

            states[0] = "blstep";
            states[1] = "brstep";

            for(int j = 0; j < 2; j++){
                for(int i = 0; i<4; i++){
                    String path = "./resources/media/"+states[j]+(i+1)+".png";

                    rangersStates[j][i] = ImageIO.read(new File(path));
                }
            }




            bears[0]  = ImageIO.read(new File(".\\resources\\media\\bff.png"));
            bears[1]  = ImageIO.read(new File(".\\resources\\media\\bfb.png"));
            bears[2]  = ImageIO.read(new File(".\\resources\\media\\bfl.png"));
            bears[3]  = ImageIO.read(new File(".\\resources\\media\\bfr.png"));

        }catch (Exception e){
            System.out.println("something wrong Happend");
        }

    }


    public void loadHurdles(){

        try{
            trees = new BufferedImage[2];
            trees[0] = ImageIO.read(new File(".\\resources\\media\\tree2.png"));
            trees[1] = ImageIO.read(new File(".\\resources\\media\\tree3.png"));
            bush = ImageIO.read(new File(".\\resources\\media\\tree1.png"));
            smallMount = ImageIO.read(new File(".\\resources\\media\\m2.png"));
            this.mountain = ImageIO.read(new File(".\\resources\\media\\m1.png"));
        }catch (Exception _){
            System.out.println("Error happened.");
        }
    }


}

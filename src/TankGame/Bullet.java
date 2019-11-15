package TankGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet {

    private int x;
    private int y;
    private int vx;
    private int vy;

    private int angle;

    private final int R = 4;


    //private BufferedImage img;


    // Constructor
    Bullet(int x, int y, int vx, int vy, int angle){

        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;

        this.angle = angle;

        //this.img = img;


    }

    // update bullet speed
    public void update(){

        vx = (int) Math.round(R* Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R* Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;

        //checkBorder();
    }


//    private void checkBorder(){
//
//        // the left side
//        if(x < 30){
//            x = 30;
//        }
//        // right side
//        if(x >= TRE.SCREEN_WIDTH -88){
//            x = TRE.SCREEN_WIDTH - 88;
//        }
//
//        // Check the top
//        if(y < 40){
//            y = 40;
//        }
//        if(y >= TRE.SCREEN_HEIGHT -80){
//            y = TRE.SCREEN_HEIGHT - 80;
//        }
//    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void drawImage(Graphics g){
        Color c = g.getColor();

        g.setColor(Color.red);
        g.fillOval(x,y,5,5);
        g.setColor(c);


    }






}

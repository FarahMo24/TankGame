package TankGame;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

public class BulletCollision {

    // Get Bullet to collide with Tanks and or Walls

    private int x;
    private int y;
    ArrayList<Rectangle> rect;
    Tank tanks;
    Tile tiles;
    Bullet bullets;


    // Constructor
    public BulletCollision(Tank t,Bullet b){
        this.tanks = t;
        this.bullets = b;
    }

    public BulletCollision(Bullet b, Tile t){
        this.bullets = b;
        this.tiles = t;
    }





    // Tiles and Bullet Collisions

    public boolean tileCol(Bullet b, Tile t){
        Rectangle bulletRect = new Rectangle(b.getX(), b.getY(), 5,5);
        t.rectWall();
        // add to arraylist
        this.rect = t.getrWall();

        for(int i = 0; i < t.getWallAmount();i++){

            Rectangle tileRect = new Rectangle(t.getrWall().get(i));

            if(bulletRect.intersects(tileRect)){
                this.x = (int)rect.get(i).getY()/20;
                this.y = (int)rect.get(i).getX()/20;

                if(t.getMap()[x][y] == 2){
                    t.setBreakAbleWall(x,y);
                }
                return true;
            }
        }
        return false;


    }


    public boolean unBreakableCol(Bullet b, Tile t){
        Rectangle bulletRect = new Rectangle(b.getX(), b.getY(), 5,5);
        t.rectWall();
        // add to arraylist
        this.rect = t.getrWall();

        for(int i = 0; i < t.getWallAmount();i++){

            Rectangle tileRect = new Rectangle(t.getrWall().get(i));

            if(bulletRect.intersects(tileRect)){
                this.x = (int)rect.get(i).getY()/20;
                this.y = (int)rect.get(i).getX()/20;

                if(t.getMap()[x][y] == 1){
                }
                return true;
            }
        }
        return false;
    }


    // Tanks and Bullet Collision

    public boolean tankBullet(Tank t, Bullet b){

        Rectangle bulletRect = new Rectangle(b.getX(),b.getY(),5,5);
        Rectangle tileRect = new Rectangle(t.getX(),t.getY(),40,40);

        if(tileRect.intersects(bulletRect)){
            return true;
        }
        return false;
    }



}

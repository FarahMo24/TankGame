package TankGame;

import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class Tile {

    // Imgs
    private BufferedImage unBreakAbleWall;
    private BufferedImage breakAbleWall;
    private BufferedImage tiles;
    // Map class
    private int[][] map;

    private ArrayList<Rectangle> rectangle = new ArrayList<Rectangle>();
    private ArrayList<Rectangle> rWall = new ArrayList<>(); //

    public Tile(){

        Map map = new Map();
        map.mapDisplay();
        this.map = map.getMap();
    }

    public void rectWall(){

        this.rWall.clear();

        for(int i = 0; i < this.map.length; i++){

            for(int j = 0; j < this.map[i].length;j++){

                int current = this.map[i][j];

                if(current == 2 || current == 1) {
                    // add to Rectangle arraylist
                    Rectangle rect = new Rectangle(j * 20, i * 20, 20, 20);
                    this.rWall.add(rect);
                }
            }
        }// end of for
    }// addWallRectangle

    public ArrayList<Rectangle> getrWall(){
        return this.rWall;
    } // =


    public int[][]getMap(){
        return this.map;
    }

    public int getWallAmount(){
        return this.rWall.size();
    } //

    public void setBreakAbleWall(int x, int y){

        this.map[x][y] = 0;
    }  //

    public BufferedImage loadT(String location){
        BufferedImage img = null;

        try{
            img = ImageIO.read(new File(location));
        }catch (IOException e){

        }
        return img;
    } //

    public void drawLayers(Graphics graphics){
        // relative path
        tiles = loadT("resources/Background.bmp");
        unBreakAbleWall = loadT("resources/non-breakable.jpg");
        breakAbleWall = loadT("resources/breakable.jpg");

        for(int i = 0; i < this.map.length;i++){

            for(int j = 0; j < this.map[i].length;j++){

                int current = this.map[i][j];

                if(current == 0){
                    graphics.drawImage(tiles,j*20,i*20,20,20,null);
                }else if(current == 1){
                    graphics.drawImage(unBreakAbleWall,j*20,i*20,20,20,null);
                }else{
                    graphics.drawImage(breakAbleWall,j*20,i*20,20,20,null);

                }
            }
        }

    }





}

package TankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExtraLife {

    private int x;
    private int y;
    BufferedImage img;

    public ExtraLife(int x, int y){
        this.x = x;
        this.y = y;

    }

    public BufferedImage loadExtraLife(String location){
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(location));
        }
        catch (IOException e)
        {

        }
        return img;
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}

    public void draw(Graphics graphics){
        img = loadExtraLife("resources/heart.png");
        graphics.drawImage(img,x,y,null);
    }
}

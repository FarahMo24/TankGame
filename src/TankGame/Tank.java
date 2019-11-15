package TankGame;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Tank {

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    TRE tre;
    private TankCollision tCol = new TankCollision();
    private Bullet b1;

    // Rotation
    private final int R = 2;
    // RotationSpeed
    private final int ROTATIONSPEED = 4;

    // Tank Health
    private int THealth = 100;


    private BufferedImage imgT1;
    private BufferedImage imgT2;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    private boolean shootPressed; // Bullet

    // private PlayerCollision

    // ArrayList Bullet object
    public ArrayList<Bullet> bullet = new ArrayList<>();


    // Constructor
//    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, BufferedImage imgB1){
//
//        this.x = x;
//        this.y = y;
//        this.vx = vx;
//        this.vy = vy;
//        this.img = img;
//        this.imgB1 = imgB1;// bullet
//        this.angle = angle;
//        this.b1 = new Bullet(this.x,this.y,this.vx,this.vy,this.angle);
//    }

    // Constructor
    Tank(int x, int y, int vx, int vy, int angle, TRE tre){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.tre = tre;
    }

    // Get x
    public int getX(){return this.x;}

    // Get y
    public int getY(){return this.y;}

    // Get Tank Health
    public int getTHealth(){
        return this.THealth;
    }

    // Get String version of Tank Health
    public String getStrHeath(){ String str = String.valueOf(this.THealth/20);
        return str;};

    // Healed
    public void healed(){this.THealth = 100;}

    // Ghost
    public boolean isDead(){
        if(this.THealth <= 0){
            return true;
        }
        return false;
    }

    // Set x
    public void setX(int x){this.x = x;}

    // Set y
    public void setY(int y){this.y = y;}
    // Set Tank Health
    public void setTHealth(){this.THealth-=20;}





    // functions for buttons pressed

    void toggleUpPressed() {
        this.UpPressed = true;
    }
    void toggleDownPressed(){
        this.DownPressed = true;
    }
    void toggleRightPressed(){
        this.RightPressed = true;
    }
    void toggleLeftPressed(){
        this.LeftPressed = true;
    }

    void toggleShootPressed(){this.shootPressed = true;} // bullet

    //-----------------------------------
    void unToggleUpPressed() {
        this.UpPressed = false;
    }
    void unToggleDownPressed(){
        this.DownPressed = false;
    }
    void unToggleRightPressed(){
        this.RightPressed = false;
    }
    void unToggleLeftPressed(){
        this.LeftPressed = false;
    }

    void unToggleShootPressed(){this.shootPressed = false;} // bullet



    // Updates tank movements
    public void update(){

        // If up is pressed, call moveForwards function
        if(this.UpPressed){
            this.moveForwards();
        }
        // if down is pressed call moveBackwards function
        if(this.DownPressed){
            this.moveBackwards();
        }
        // if right is pressed call rotateRight function
        if(this.RightPressed){
            this.rotateRight();
        }
        // if left is pressed call rotateLeft function
        if(this.LeftPressed){
            this.rotateLeft();
        }
        if(this.shootPressed){
        }
    }



    // Rotates tank, adds to the angle of the angle instance
    private void rotateRight(){
        this.angle = this.angle + this.ROTATIONSPEED;
    }

    // Rotates tank, subtracts from angle
    private void rotateLeft(){
        this.angle = this.angle - this.ROTATIONSPEED;
    }

    private void moveBackwards(){

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R* Math.sin(Math.toRadians(angle)));

        x -=vx;
        y -=vy;

        tCol.wallCol(this, tre.tile);
        checkBorder();
    }

    private void moveForwards(){
        vx = (int) Math.round(R* Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R* Math.sin(Math.toRadians(angle)));

        x = x+vx;
        y = y+vy;
        tCol.wallCol(this, tre.tile);
        checkBorder();
    }

    // set for bullets shots
    public void setShootPressed(){
        Bullet b = new Bullet(this.x+20,this.y+20, this.vx, this.vy, this.angle);
        this.bullet.add(b);
    }

    // Check if tank passed border
    private void checkBorder(){

        // the left side
        if(x < 30){
            x = 30;
        }
        // right side
        if(x >= TRE.WORLD_WIDTH -88){
            x = TRE.WORLD_WIDTH - 88;
        }

        // Check the top
        if(y < 40){
            y = 40;
        }
        if(y >= TRE.WORLD_HEIGHT -80){
            y = TRE.WORLD_HEIGHT - 80;
        }
    }


    //  Return bullets
    public ArrayList<Bullet> getBullet(){return this.bullet;}

    @Override
    public String toString(){
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public BufferedImage loadT(String location){
        BufferedImage img = null;

        try{
            img = ImageIO.read(new File(location));
        }catch (IOException e){

        }
        return img;
    }

    // Draw tank1
    void drawT1(Graphics graphics){
        imgT1 = loadT("resources/Tank1.png");
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.imgT1.getWidth() / 2.0, this.imgT1.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(imgT1, rotation, null);
    }

    // Draw tank2
    void drawT2(Graphics graphics){
        imgT2 = loadT("resources/Tank2.png");
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.imgT2.getWidth() / 2.0, this.imgT2.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(imgT2, rotation, null);
    }


//    void drawImage(Graphics g) {
//        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
//
//
//        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
//
//
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawImage(this.img, rotation, null);
//    }






}

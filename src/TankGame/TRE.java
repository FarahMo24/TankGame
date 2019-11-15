package TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static java.lang.Thread.sleep;

import static javax.imageio.ImageIO.read;

public class TRE extends JPanel {

    public static final int SCREEN_WIDTH = 950;
    public static final int SCREEN_HEIGHT = 550;
    public static final int WORLD_WIDTH = 1180;
    public static final int WORLD_HEIGHT = 800;

    // BufferedImage class is a subclass of Image class
    // it's used to handle and manipulate the image data
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jf;
    private Tank t1;
    private Tank t2; // second tank
    private Bullet bullet; // Bullet

    public Tile tile;

    private BufferedImage p1;
    private BufferedImage p2;

    private boolean isDead = false;
    private BulletCollision bulletCollision;

    public ArrayList<ExtraLife> extraLife = new ArrayList<>();

    public static void main(String[] args){

        //Thread x;
        TRE trex = new TRE();
        trex.init();
        trex.setExtraLife();


        try{

            while(true){
                trex.t1.update();
                trex.t2.update();
                trex.isDead = trex.t1.isDead();
                trex.isDead = trex.t2.isDead();
                //  trex.repaint();
                trex.setLeftPanel(trex.t1);
                trex.setRightPanel(trex.t2);


//                System.out.println(trex.t1);
//                System.out.println(trex.t2);
//                Thread.sleep(1000/144);

                // t1 bullet update
                if(trex.t1.getBullet().size() != 0){
                    for(int i = 1; i < trex.t1.getBullet().size();i++){
                        trex.t1.getBullet().get(i).update();
                    }
                }
                // t2 bullet update
                if(trex.t2.getBullet().size() != 0){
                    for(int i = 1; i < trex.t2.getBullet().size();i++){
                        trex.t2.getBullet().get(i).update();
                    }
                }


                // Handling T1 bullets hitting t2
                for(int i = 0; i <trex.t2.getBullet().size();i++){
                    trex.bullet = trex.t2.getBullet().get(i);

                    if(trex.bulletCollision.tankBullet(trex.t1,trex.bullet)){
                        if(trex.t1.isDead()){
                            trex.isDead = true;
                            trex.exit();

                        }
                        trex.t1.setTHealth();
                        trex.t2.bullet.remove(i);
                        i--;
                    }
                }

                // Handling Collisions Tank 1 bullets and Tiles
                for(int i = 0; i < trex.t1.getBullet().size();i++){
                    trex.bullet = trex.t1.getBullet().get(i);

                    if(trex.bulletCollision.tileCol(trex.t1.getBullet().get(i),trex.tile)){
                        trex.t1.bullet.remove(i);
                        i--;
                    }
                }//

                // Handling Collision Tank 1 and Unbreakable wall
                for(int i = 0; i < trex.t1.getBullet().size();i++){
                    trex.bullet = trex.t1.getBullet().get(i);

                    if(trex.bulletCollision.unBreakableCol(trex.t1.getBullet().get(i),trex.tile)){
                        trex.t1.bullet.remove(i);
                        i--;
                    }
                }//

                // Extra Life Tank 1
                for(int i = 0; i < trex.extraLife.size();i++){

                    if(trex.TExtraLife(trex.t1, trex.extraLife.get(i))){
                        if(trex.t1.getTHealth() < 100){
                            trex.t1.healed();
                            trex.extraLife.remove(i);
                            i--;
                        }
                    }
                }


                //------------------------------------------------------------------------------
                // Handling T2 bullets hitting t1
                for(int i = 0; i <trex.t1.getBullet().size();i++){
                    trex.bullet = trex.t1.getBullet().get(i);

                    if(trex.bulletCollision.tankBullet(trex.t2,trex.bullet)){
                        if(trex.t2.isDead()){
                            trex.isDead = true;
                            trex.exit();

                        }
                        trex.t2.setTHealth();
                        trex.t1.bullet.remove(i);
                        i--;
                    }
                }

                // Handling Collision Tank 2 bullets and Tiles
                for(int i = 0; i < trex.t2.getBullet().size();i++){
                    trex.bullet = trex.t2.getBullet().get(i);

                    if(trex.bulletCollision.tileCol(trex.t2.getBullet().get(i),trex.tile)){
                        trex.t2.bullet.remove(i);
                        i--;
                    }
                }//

                // Handling Collision Tank 2 and Unbreakable wall
                for(int i = 0; i < trex.t2.getBullet().size();i++){
                    trex.bullet = trex.t2.getBullet().get(i);

                    if(trex.bulletCollision.unBreakableCol(trex.t2.getBullet().get(i),trex.tile)){
                        trex.t2.bullet.remove(i);
                        i--;
                    }
                }

                // Extra Life Tank 2
                for(int i = 0; i < trex.extraLife.size();i++){

                    if(trex.TExtraLife(trex.t2, trex.extraLife.get(i))){
                        if(trex.t2.getTHealth() < 100){
                            trex.t2.healed();
                            trex.extraLife.remove(i);
                            i--;
                        }
                    }
                }

                //System.out.println(trex.t2);
                trex.repaint();
                sleep(1000/144);


            }
        } catch(InterruptedException ignored){

        }

    }// end of main

    private void init(){
        // Title of window game
        this.jf = new JFrame("Tank Game");

        this.world = new BufferedImage(WORLD_WIDTH,WORLD_HEIGHT,BufferedImage.TYPE_INT_RGB);

        this.tile = new Tile();
        //BufferedImage t1img = null, t2img = null, b1img = null;

//        try{
//            BufferedImage tmp;
//            System.out.println(System.getProperty("user.dir"));
//
//
//            t1img = read(new File("resources/Tank1.png"));
//            t2img = read(new File("resources/Tank2.png"));
//            b1img = read(new File("resources/Shell-1.png"));
//
//        }catch(IOException ex){
//
//            System.out.println(ex.getMessage());
//        }

        // Calls Tank Constructor, passes default state vals
        t1 = new Tank(200,200,0,0,0,this);
        t2 = new Tank(1050,250,0,0,0,this);

        this.bulletCollision = new BulletCollision(bullet,tile);
        this.bulletCollision = new BulletCollision(t1,bullet);
        this.bulletCollision = new BulletCollision(t2,bullet);

        // Controls for Tank1, uses up down left right keys
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);




        // Sets JFrame as a BorderLayout
        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);



        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT );
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);


    }


    public void setLeftPanel(Tank t1){
        if(t1.getX()>700 && t1.getY()>200){
            this.p1=this.world.getSubimage(680,240,500,560);
        }
        else if(t1.getX()>700){
            this.p1=this.world.getSubimage(680,t1.getY()-30,500,560);
        }
        else if(t1.getY()>200){
            this.p1=this.world.getSubimage(t1.getX()-30,240,500,560);
        }
        else if(t1.getX()<=700 && t1.getY()<=200){
            this.p1=this.world.getSubimage(t1.getX()-20,t1.getY()-20,500,560);
        }
    }
    public void setRightPanel(Tank t2){
        if(t2.getX()>700 && t2.getY()>200){
            this.p2=this.world.getSubimage(680,240,500,560);
        }
        else if(t2.getX()>700){
            this.p2=this.world.getSubimage(680,t2.getY()-30,500,600);
        }
        else if(t2.getY()>200){
            this.p2=this.world.getSubimage(t2.getX()-20,240,500,560);
        }
        else if(t2.getX()<=700 && t2.getY()<=200){
            this.p2=this.world.getSubimage(t2.getX()-20,t2.getY()-20,500,600);
        }
    }

    public void setExtraLife(){
        ExtraLife e1 = new ExtraLife(120,200);
        this.extraLife.add(e1);
        ExtraLife e2 = new ExtraLife(440,500);
        this.extraLife.add(e2);
        ExtraLife e3 = new ExtraLife(50,100);
        this.extraLife.add(e3);
    }
    public boolean TExtraLife(Tank t, ExtraLife extraLife){
        Rectangle extraLifeRect = new Rectangle(extraLife.getX(),extraLife.getY(),20,20);
        Rectangle tRect = new Rectangle(t.getX(),t.getY(),40,40);

        if(tRect.intersects(extraLifeRect) || extraLifeRect.intersects(tRect)){
            return true;
        }
        return false;
    }
    public void exit(){System.exit(-1);}
    public void getBackground(Graphics2D graphics2D){this.tile.drawLayers(graphics2D);}

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        buffer = world.createGraphics();
        this.getBackground(buffer);

//        super.paintComponent(g2);
//
//        this.t1.drawT1(buffer);
//        this.t2.drawT2(buffer);
//        g2.drawImage(world,0,0,null);


        if(this.t1.getBullet().size()!=0){
            for(int i = 1; i < this.t1.getBullet().size();i++){
                this.t1.getBullet().get(i).drawImage(buffer);
            }
        }
        if(this.t2.getBullet().size()!=0){
            for(int i = 1; i < this.t2.getBullet().size();i++){
                this.t2.getBullet().get(i).drawImage(buffer);
            }
        }

        if(this.extraLife.size() !=0){
            for(int i = 0; i < this.extraLife.size();i++){
                this.extraLife.get(i).draw(buffer);
            }
        }

        this.t1.drawT1(buffer);
        this.t2.drawT2(buffer);


        buffer.setColor(Color.gray);
        buffer.fillRect(this.t1.getX(),this.t1.getY(),10,10);

        if(this.t1.getTHealth()>50){
            buffer.setColor(Color.green);
        }else if(this.t1.getTHealth()>20 && this.t1.getTHealth() < 50){
            buffer.setColor(Color.yellow);
        }else{
            buffer.setColor(Color.red);
        }

        buffer.fillRect(this.t1.getX(),this.t1.getY()-10,this.t1.getTHealth()/2,10);
        buffer.setColor(Color.black);
        buffer.drawRect(this.t1.getX(),this.t1.getY()-10,this.t1.getTHealth()/2,10);
        buffer.drawString(this.t1.getStrHeath(),this.t1.getX()-20,this.t1.getY()-20);



        buffer.setColor(Color.gray);
        buffer.fillRect(this.t2.getX(),this.t2.getY(),10,10);

        if(this.t2.getTHealth()>50){
            buffer.setColor(Color.green);
        }else if(this.t2.getTHealth()>20 && this.t2.getTHealth() < 50){
            buffer.setColor(Color.yellow);
        }else{
            buffer.setColor(Color.red);
        }

        buffer.fillRect(this.t2.getX(),this.t2.getY()-10,this.t2.getTHealth()/2,10);
        buffer.setColor(Color.black);
        buffer.drawRect(this.t2.getX(),this.t2.getY()-10,this.t2.getTHealth()/2,10);
        buffer.drawString(this.t2.getStrHeath(),this.t2.getX()-20,this.t2.getY()-20);


        g2.drawImage(world,0,0,null);

        g2.drawImage(this.p1,0,0,null);
        g2.drawImage(this.p2,460,0,null);
        g2.drawLine(460,0,460,1200);

        g2.drawImage(this.world.getScaledInstance(255,150,world.SCALE_SMOOTH),350,370,null);
        g2.drawRect(350,370,255,150);


    }








}

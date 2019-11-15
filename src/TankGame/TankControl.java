package TankGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TankControl implements KeyListener {

    private Tank t1;
    //Controls we need
    private final int up;
    private final int down;
    private final int right;
    private final int left;;
    private final int shoot;

    //Constructor
    public TankControl(Tank t1, int up, int down, int left, int right, int shoot){

        this.t1 = t1;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.shoot = shoot;
    }


    @Override
    public void keyTyped(KeyEvent e) {

        // Not used
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        int keyPressed = ke.getKeyCode();
        // Exp: if up is pressed, keyPressed = 38
        // 38 is the keycode for "up"
        if(keyPressed == up){
            this.t1.toggleUpPressed();
        }
        if(keyPressed == down){
            this.t1.toggleDownPressed();
        }
        if(keyPressed == left){
            this.t1.toggleLeftPressed();
        }
        if(keyPressed == right){
            this.t1.toggleRightPressed();
        }

        // Bullet
        if(keyPressed == shoot){
            this.t1.toggleShootPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

        int keyReleased = ke.getKeyCode();
        if(keyReleased == up){
            this.t1.unToggleUpPressed();
        }
        if(keyReleased == down){
            this.t1.unToggleDownPressed();
        }
        if(keyReleased == left){
            this.t1.unToggleLeftPressed();
        }
        if(keyReleased == right){
            this.t1.unToggleRightPressed();
        }

        // Bullet
        if(keyReleased == shoot){
            this.t1.unToggleShootPressed();
            this.t1.setShootPressed();
        }
    }
}

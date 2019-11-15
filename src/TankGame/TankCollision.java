package TankGame;

import java.awt.*;

public class TankCollision {

    private int x;
    private int y;
    //private Rectangle r;

    public void TankCollision(){

    }

    public void wallCol(Tank tank, Tile tile){
        Rectangle tbox = new Rectangle(tank.getX(), tank.getY(), 40, 40);
        tile.rectWall();
        for(int i = 0; i < tile.getWallAmount(); i++){
            Rectangle rbox = new Rectangle(tile.getrWall().get(i));
            if(tbox.intersects(rbox)||rbox.intersects(tbox) ){
                tank.setX(this.x);
                tank.setY(this.y);
            }
        }
        for(int i = 0; i < tile.getWallAmount(); i++){
            Rectangle rbox = new Rectangle(tile.getrWall().get(i));
            if(!tbox.intersects(rbox)||!rbox.intersects(tbox) ){
                this.x = tank.getX();
                this.y = tank.getY();
            }
        }
    }

}

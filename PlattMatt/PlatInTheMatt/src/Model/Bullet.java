package Model;

import javafx.scene.image.ImageView;

/**
 * Abstract bullet class
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public abstract class Bullet extends GameObject {


    double speed;

    /**
     * parm every bullets should have
     * @param view image if the bullet
     * @param speed speed
     */
    Bullet(ImageView view, double speed){
        super(view);
        this.speed = speed;
    }

    /**
     * All bullets have to move
     */
    public abstract void move();
}
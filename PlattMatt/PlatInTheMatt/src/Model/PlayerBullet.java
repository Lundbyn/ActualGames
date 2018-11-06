package Model;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * The bulletclass to the playerbullets
 *
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public class PlayerBullet extends Bullet {



    private double x;
    private double speed;
    private boolean right;
    public AnimationTimer bulletTimer;

    /**
     * The constructor of the PlayerBullet class
     * @param imagePath link to the playerBullet Image
     * @param x x coordinate
     * @param y y coordinate
     * @param speed speed of the bullet, how much the x coordinate is changing every game-tick
     * @param right if the bullets is supposed to shoot right or left
     */
    PlayerBullet(String imagePath, double x, double y, double speed, boolean right){
        super(new ImageView(new Image(imagePath)), speed);
        this.right = right;
        this.speed = speed;

        this.getView().setTranslateX(x);
        this.getView().setTranslateY(y);
        this.x = x;

        bulletTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                move();
            }
        };
        bulletTimer.start();
    }

    /**
     * Moving the bullets either right or left
     */
    @Override
    public void move() {
        if(this.right){
            x += speed;
            getView().setTranslateX(x);
        }
        else{
            x -= speed;
            getView().setTranslateX(x);
        }
        if(right) {
            getView().setRotate(getView().getRotate() + 10);
        }
        if(!right) {
            getView().setRotate(getView().getRotate() - 10);
        }
    }
}
package Model;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The bullets the boss shoots at Mat, if Mat collides with a bullet he dies
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public class BossBullet extends Bullet{

    public AnimationTimer bossBulletTimer;
    private double angle;
    private boolean aiming;
    private boolean right;

    /**
     * Create Bossbullet at bosses location
     * @param imagePath link to the Image
     * @param x x coordinate
     * @param y y coordinate
     * @param speed bullet speed
     * @param player player Node the bullet moving towards
     * @param aiming type of bullet
     */
    BossBullet(String imagePath, double x, double y, double speed, Node player, boolean aiming) {
        super(new ImageView(new Image(imagePath)), speed);

        this.getView().setTranslateX(x);
        this.getView().setTranslateY(y);

        this.right = player.getTranslateX() > x;

        double deltaX = player.getTranslateX() - x;
        double deltaY = player.getTranslateY() - y;
        this.angle = Math.atan2(deltaY, deltaX);
        this.aiming = aiming;


        bossBulletTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                move();
            }
        };
        bossBulletTimer.start();
    }


    /**
     * basic move
     */
    @Override
    public void move() {
        if(aiming) {
            this.getView().setTranslateX(this.getXposition() + (speed * Math.cos(angle)));
            this.getView().setTranslateY(this.getYposition() + (speed * Math.sin(angle)));
        }
        else {
            if(right) {
                this.getView().setTranslateX(this.getXposition() + speed);
            }
            else {
                this.getView().setTranslateX(this.getXposition() - speed);
            }
        }
        this.getView().setRotate(this.getView().getRotate() - 5);
    }
}
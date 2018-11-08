package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class PlayerBullet extends Bullet {

    private AnimationTimer bulletTimer;
    int power;
    int tick;
    int direction;

    public PlayerBullet(int power, boolean right, double x, double y) {
        super(x + 20,y, 5,Color.GREEN);

        if(right) {
            direction = 8;
        }
        else {
            direction = -8;
        }
        this.setVelocity(new Point2D(direction,0));
        this.power = power;
        tick = 0;

        bulletTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveBullet();
                tick++;
            }
        };
        bulletTimer.start();
    }

    private void moveBullet() {
        this.getView().setTranslateX(this.getView().getTranslateX() + getVelocity().getX());
        this.getView().setTranslateY(this.getView().getTranslateY() + getVelocity().getY());


        if(tick % power == 0 && getVelocity().getY() < 12) {
            setVelocity(new Point2D(getVelocity().getX(), getVelocity().getY() + 1));
        }
    }

    public int getPower() {
        return power;
    }
}

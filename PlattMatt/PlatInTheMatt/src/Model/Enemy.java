package Model;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Enemy object in Mats world, if math collides with them he dies
 * They move around in the game, either vertically of horizontally
 * Mat can shoot at them to kill them
 *
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public class Enemy extends EnemyObject {



    private int stop, xSpeed, ySpeed;
    private boolean horizontal;
    public AnimationTimer enemyTimer;
    private Rectangle healthBar;

    /**
     * Create Enemy at specific location
     * @param x x coordinate
     * @param y y coordinate
     * @param stop stop coordinate, where it turns the other way
     * @param xSpeed how fast it moves in the x direction
     * @param ySpeed how fast it moves in the y direction
     * @param hitPoints how much hitpoint the enemy has
     * @param horizontal if is is supposed to move horizontal or not
     * @param imgPath link to the enemy image
     */
    Enemy(int x, int y, int stop, int xSpeed, int ySpeed, int hitPoints, boolean horizontal, String imgPath) {
        super(new ImageView(new Image(imgPath)), x, y, hitPoints);

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.getView().setTranslateX(x);
        this.getView().setTranslateY(y);
        this.stop = stop;
        this.horizontal = horizontal;
        this.defaultHp = hitPoints;
        this.healthBar = new Rectangle(50, 5, Color.GREEN);
        this.isAlive = true;

        enemyTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                enemyMovement((int)getXposition(), (int)getYposition());
                setVisibility();
                healthBarHandling();
            }
        };

        enemyTimer.start();
    }


    /**
     *
     * @param xPos new x position
     * @param yPos new y portion
     */
    private void enemyMovement(int xPos, int yPos) {
        if(this.horizontal) {
            if (xPos == this.x) {
                this.setVelocity(new Point2D(xSpeed, ySpeed));
            }
            if (xPos == this.stop) {
                this.setVelocity(new Point2D(-xSpeed, ySpeed));
            }
        }
        if(!this.horizontal) {
            if (yPos == this.y) {
                this.setVelocity(new Point2D(xSpeed, ySpeed));
            }
            if (yPos == this.stop) {
                this.setVelocity(new Point2D(xSpeed, -ySpeed));
            }
        }
        this.getView().setTranslateX(xPos + this.getVelocity().getX());
        this.getView().setTranslateY(yPos + this.getVelocity().getY());
    }

    /**
     * Makes the enemy visible if it's alive
     */
    private void setVisibility() {
        if(!isAlive) {
            getView().setVisible(false);
        }
        else {
            getView().setVisible(true);
        }
    }

    /**
     * Handles the healthbar of the enemy
     */
    private void healthBarHandling() {
        healthBar.setTranslateX(this.getXposition());
        healthBar.setTranslateY(this.getYposition() - 10);
        healthBar.setWidth(hitPoints*(50/defaultHp));

        if(hitPoints == defaultHp) {
            healthBar.setVisible(false);
        }
        else if(hitPoints < defaultHp && hitPoints > 0) {
            healthBar.setVisible(true);

        }
        else if(hitPoints == 0) {
            healthBar.setVisible(false);
        }
    }

    /**
     * Resets the alive-status, timer and hitpoins of the enemy
     */
    public void enemyReset(){
        this.isAlive = true;
        this.enemyTimer.start();
        this.setHitPoints(this.getDefaultHp());
    }

    /**
     * Creats enemy healthBar
     * @return healthBar over the enemy
     */
    public Rectangle getHealthBar() {
        return healthBar;
    }
}
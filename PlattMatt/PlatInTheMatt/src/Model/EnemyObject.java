package Model;

import javafx.scene.image.ImageView;

/**
 * Abstract enemyObject class the enemies in the game can extend from
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public abstract class EnemyObject extends GameObject {

    int x,y;
    int hitPoints, defaultHp;
    public boolean isAlive;

    /**
     * Parameters enemies need
     * @param view image of the enemy
     * @param x x coordinate
     * @param y y coordinate
     * @param hitPoints Amount of hitpoint
     */
    EnemyObject(ImageView view, int x, int y, int hitPoints) {
        super(view);
        this.x = x;
        this.y = y;
        this.hitPoints = hitPoints;
    }

    /**
     * Sets hitpints
     * @param hitPoints Enemy hitpoint
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getDefaultHp() {
        return defaultHp;
    }


}
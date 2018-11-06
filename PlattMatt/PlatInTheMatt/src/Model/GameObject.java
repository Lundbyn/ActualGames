package Model;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Abstract class every moving object inherits from
 */
public abstract class GameObject {

    private Node view;
    private Point2D velocity;
    private boolean isAlive = true;

    /**
     * @param view image of the object
     */
    GameObject(ImageView view) {
        this.view = view;
    }

    /**
     *
     * @param other another game object of type Gameobject
     * @return if the other object is colliding with a gameObject
     */
    public boolean isColliding(GameObject other) {
        return this.getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    public boolean isDead(){
        return !isAlive;
    }

    public Node getView() {
        return this.view;
    }

    Point2D getVelocity() {
        return this.velocity;
    }

    public boolean getAlive() {
        return this.isAlive;
    }

    public double getXposition(){
        return getView().getTranslateX();
    }

    public double getYposition(){
        return getView().getTranslateY();
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void setAlive(boolean dead) {
        this.isAlive = dead;
    }
}
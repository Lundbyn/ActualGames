package sample;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class GameObject {

    private Point2D velocity;
    private boolean active;
    private Color color;

    public GameObject(boolean active) { //Constructor
        this.active = active;
    }




    //Getters and setters
    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}

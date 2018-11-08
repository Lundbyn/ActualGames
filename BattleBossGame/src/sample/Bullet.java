package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Bullet extends GameObject {

    Circle view;

    public Bullet(double x, double y, int size, Color color) {
        super(true);
        view = new Circle(x,y,size,color);
    }

    public Node getView() {
        return view;
    }

    public void remove() {
        this.setActive(false);
        this.getView().setVisible(false);
    }
}

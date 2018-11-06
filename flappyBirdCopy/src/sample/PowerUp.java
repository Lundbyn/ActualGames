package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerUp {

    Rectangle rec;
    boolean active;
    double x, y;
    Color color = Color.ORANGE;

    public PowerUp(double x, double y) {
        this.x = x;
        this.y = y;
        active = false;
        rec = new Rectangle(20,20,color);
        rec.setTranslateX(x);
        rec.setTranslateY(y);
    }

    public Rectangle getView() {
        return rec;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

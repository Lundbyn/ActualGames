package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform {

    Rectangle rec;
    Color color;

    public Platform(int x, int y, int width, int height, Color color) {
        this.rec = new Rectangle(x,y,width,height);
        rec.setFill(color);
    }

    public Rectangle getRec() {
        return rec;
    }
}
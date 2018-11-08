package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform  {

    private Rectangle view;
    private boolean active;

    public Platform(int x, int y, int width, int height, Color color) {
        this.view = new Rectangle(x,y,width,height);
        this.view.setFill(color);
        this.active = true;
    }

    public Rectangle getView() {
        return view;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

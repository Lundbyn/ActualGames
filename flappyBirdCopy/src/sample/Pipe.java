package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Pipe {

    private final int width = 60;
    private int height;
    private final Color color = Color.GREEN;
    public Rectangle upper;
    private Rectangle lower;
    public AnimationTimer timer;
    boolean active;

    public Pipe(Pane mainPane, ArrayList<Rectangle> pipeList) {
        active = true;
        Random r = new Random();

        height = r.nextInt(500) + 50;

        upper = new Rectangle(width,height,color);
        upper.setY(0);
        upper.setTranslateX(1300);

        lower = new Rectangle(width, 800, color);
        lower.setTranslateY(height + 200);
        lower.setTranslateX(1300);

        pipeList.add(upper);
        pipeList.add(lower);
        mainPane.getChildren().addAll(upper, lower);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                move();

                if(upper.getTranslateX() < -100) {
                    timer.stop();
                    mainPane.getChildren().removeAll(upper, lower);
                    pipeList.remove(upper);
                    pipeList.remove(lower);
                }
            }
        };
        timer.start();
    }

    private void move() {
        this.upper.setTranslateX(this.upper.getTranslateX() - 2);
        this.lower.setTranslateX(this.lower.getTranslateX() - 2);

        if(this.upper.getTranslateX() < -100) {
            timer.stop();
        }
    }

    public AnimationTimer getTimer() {
        return timer;
    }

    public Rectangle getUpper() {
        return upper;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

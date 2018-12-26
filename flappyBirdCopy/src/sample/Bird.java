package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Bird {

    private Node circle;
    private final int x = 300;
    private final int y = 400;

    private int ySpeed;
    boolean canJump;
    private AnimationTimer timer;

    public Bird(Pane pane) {
        //decideColor();
        circle = new ImageView(new Image("pics/fugl.png"));
        circle.setTranslateY(y);
        circle.setTranslateX(x);
        canJump = true;

        pane.getChildren().add(circle);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                move();
                circle.setRotate(circle.getRotate() + 2);
            }
        };

    }

    /*Color picker
    public void decideColor() {
        Random r = new Random();
        int decider = r.nextInt(3);
        switch (decider) {
            case 0: color = Color.BLUE;
            break;

            case 1: color = Color.ORANGE;
            break;

            case 2: color = Color.PURPLE;
            break;
        }
    }*/

    private void move() {
        if(ySpeed < 10) {
            ySpeed++;
        }
        circle.setTranslateY(circle.getTranslateY() + ySpeed);

        if(this.circle.getTranslateY() <= 0) {
            this.ySpeed = 1;
        }
    }

    public void jump() {
        if(canJump) {
            timer.start();
            this.ySpeed = -16;
            this.circle.setRotate(-50);
        }
    }

    public void die() {
        ySpeed = 9;
        canJump = false;
        circle.setRotate(80);
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public Node getCircle() {
        return circle;
    }
}

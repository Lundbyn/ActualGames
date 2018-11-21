package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayDeque;

public class Rat {

    private Circle view;
    private AnimationTimer ratTimer;
    private ArrayDeque<Square> deque;
    private Square current;

    public Rat() {
        view = new Circle(10, 10, 10, Color.LIGHTGREEN);

        ratTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveRat();
            }
        };
    }

    public void startMovement(ArrayDeque<Square> deque) {
        this.deque = deque;
        current = deque.pollLast();
        ratTimer.start();
    }

    public Circle getView() {
        return view;
    }

    public void setPosition(double x, double y) {
        view.setTranslateX(x);
        view.setTranslateY(y);
    }

    private void moveRat() {
        if(getXpos() == current.getXpos() && getYpos() == current.getYpos()) {
            if(deque.isEmpty()) {
                ratTimer.stop();
            }
            else {
                current = deque.pollLast();
            }
        }

        else if (current.getYpos() < view.getTranslateY())
            moveUp();

        else if (current.getYpos() > view.getTranslateY())
            moveDown();

        else if(current.getXpos() > view.getTranslateX())
            moveRight();

        else if (current.getXpos() < view.getTranslateX())
            moveLeft();
    }

    private void moveUp() {
        view.setTranslateY(view.getTranslateY() - 3);
    }

    private void moveRight() {
        view.setTranslateX(view.getTranslateX() + 3);
    }

    private void moveLeft() {
        view.setTranslateX(view.getTranslateX() - 3);
    }

    private void moveDown() {
        view.setTranslateY(view.getTranslateY() + 3);
    }

    private double getXpos() {
        return view.getTranslateX();
    }

    private double getYpos() {
        return view.getTranslateY();
    }
}
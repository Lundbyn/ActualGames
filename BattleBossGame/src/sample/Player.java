package sample;


import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Player {

    public Rectangle view;
    private boolean up,left,right;
    private ArrayList<Platform> levelList;
    public AnimationTimer playerTimer;
    private Point2D velocity;
    private boolean canJump;

    public Player() {
        view = new Rectangle(40,40, Color.BLUE);
        velocity = new Point2D(0,0);

        playerTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                movePlayer();
                if(up) jump();
            }
        };
        playerTimer.start();
    }

    public void jump(){
        if(canJump) {
            this.setVelocity(new Point2D(0, -20));
            canJump = false;
        }
    }


    private void movePlayer() {
        if (right) {
            this.getView().setTranslateX(this.getXposition() + 6);
        }
        if (left) {
            this.getView().setTranslateX(this.getXposition() - 6);
        }
        for (Platform platform : levelList) {
            Bounds bounds = platform.getRec().getLayoutBounds();
            if (this.getView().getBoundsInParent().intersects(bounds)) {
                if (right) {
                    this.getView().setTranslateX(bounds.getMinX() - 40.01);
                }
                if (left) {
                    this.getView().setTranslateX(bounds.getMaxX() + 0.01);
                }
            }
        }
        if (this.getXposition() < 0) {
            this.getView().setTranslateX(0);
        }

        if (this.getVelocity().getY() < 10) {
            this.setVelocity(this.getVelocity().add(0, 1));
        }
        this.getView().setTranslateY(this.getYposition() + this.getVelocity().getY());

        for (Platform platform : levelList) {
            Bounds bounds = platform.getRec().getLayoutBounds();
            if (this.getView().getBoundsInParent().intersects(bounds)) {
                if(this.getVelocity().getY() > 0){
                    this.getView().setTranslateY(bounds.getMinY()  -40.01);
                    canJump = true;
                    this.setVelocity(new Point2D(0,0));
                }
                else if (this.getVelocity().getY() < 0){
                    this.getView().setTranslateY(bounds.getMaxY() + 0.01);
                    this.setVelocity(new Point2D(0,0));
                }
            }
        }
    }



    public void setUp(boolean up) {
        this.up = up;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLevelList(ArrayList<Platform> levelList) {
        this.levelList = levelList;
    }

    public double getXposition() {
        return view.getTranslateX();
    }

    public double getYposition() {
        return view.getTranslateY();
    }

    public Rectangle getView() {
        return view;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }
}

package sample;


import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Player extends GameObject {

    private boolean up,left,right, pointingRight = true;
    private ArrayList<Platform> levelList;
    private ArrayList<PlayerBullet> playerBullets;
    public AnimationTimer playerTimer;
    private boolean canJump;
    public int protection;
    private Rectangle view;
    private Rectangle power;

    public Player() {
        super(true);
        view = new Rectangle(40,40,Color.BLUE);
        view.setTranslateY(20);
        view.setTranslateX(20);

        this.setVelocity(new Point2D(0,0));
        this.getView().setTranslateX(10);
        this.protection = 1;
        this.power = new Rectangle(0,5,Color.ORANGERED);

        playerBullets = new ArrayList<>();

        playerTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                movePlayer();
                if(up) jump();
            }
        };
        playerTimer.start();
    }

    public void shoot(int power, Pane pane) {
        PlayerBullet pb = new PlayerBullet(power, pointingRight, getXposition(), getYposition());
        pane.getChildren().addAll(pb.view);
        getPlayerBullets().add(pb);
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
            pointingRight = true;
        }
        if (left) {
            this.getView().setTranslateX(this.getXposition() - 6);
            pointingRight = false;
        }
        for (Platform platform : levelList) {
            if(platform.isActive()) {
                Bounds bounds = platform.getView().getLayoutBounds();
                if (this.getView().getBoundsInParent().intersects(bounds)) {
                    if (right) {
                        this.getView().setTranslateX(bounds.getMinX() - 40.01);
                    }
                    if (left) {
                        this.getView().setTranslateX(bounds.getMaxX() + 0.01);
                    }
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
            if(platform.isActive()) {
                Bounds bounds = platform.getView().getLayoutBounds();
                if (this.getView().getBoundsInParent().intersects(bounds)) {
                    if (this.getVelocity().getY() > 0) {
                        this.getView().setTranslateY(bounds.getMinY() - 40.01);
                        canJump = true;
                        this.setVelocity(new Point2D(0, 0));
                    } else if (this.getVelocity().getY() < 0) {
                        this.getView().setTranslateY(bounds.getMaxY() + 0.01);
                        this.setVelocity(new Point2D(0, 0));
                    }
                }
            }
        }

        power.setTranslateX(getXposition() - 5);
        power.setTranslateY(getYposition() - 7);
    }


    public void setProtection(char e) {
        switch (e) {
            case 'q': {
                view.setFill(Color.BLUE);
                protection = 1;
            }
            break;

            case 'e': {
                view.setFill(Color.RED);
                protection = 2;
            }
        }
    }

    public void reset() {
        getView().setTranslateX(20);
        getView().setTranslateY(450);
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
        return this.getView().getTranslateX();
    }

    public double getYposition() {
        return this.getView().getTranslateY();
    }

    public Node getView() {
        return view;
    }

    public Rectangle getPower() {
        return power;
    }

    public void setPower(Rectangle power) {
        this.power = power;
    }

    public ArrayList<PlayerBullet> getPlayerBullets() {
        return playerBullets;
    }
}

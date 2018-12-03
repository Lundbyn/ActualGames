package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle {

    private Rectangle view;
    private boolean visited = false;
    private int trait;
    public Square left, right, down, up;

    public Square(char trait) {
        view = new Rectangle(20,20);
        setColor(trait);
    }

    public Rectangle getView() {
        return view;
    }

    public void setColor(int colorValue) {
        switch (colorValue) {

                        //Free square
            case '0':   view.setFill(Color.LIGHTGREY);
                        trait = 0;
                        break;

                        //Wall square
            case '1':   view.setFill(Color.DARKGRAY);
                        trait = 1;
                        break;

            //Start square
            case '2':   view.setFill(Color.LIGHTGREY);
                        trait = 2;
                        break;

                        //End square
            case '3':   view.setFill(Color.YELLOW);
                        trait = 3;
                        break;

                        //Square visited
            case '4':   view.setFill(Color.ORANGE);
                        visited = true;
                        break;

                        //When the solver moves backwards, it leaves the squares red.
            case '5':   view.setFill(Color.RED);
                        break;
        }
    }

    //Sets both x and y position with given parameters
    public void setPos(double x, double y) {
        view.setTranslateX(x);
        view.setTranslateY(y);
    }

    public double getXpos() {
        return view.getTranslateX();
    }

    public double getYpos() {
        return view.getTranslateY();
    }

    public boolean isVisited() {
        return visited;
    }

    //Getter for trait. trait can be wall, open square and end square.
    public int getTrait() {
        return trait;
    }

    //Sets square to visited. A visited square is never going back to not visited.
    public void setVisited() {
        visited = true;
    }
}

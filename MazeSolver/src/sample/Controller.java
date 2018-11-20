package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Controller {

    @FXML AnchorPane UIpane;
    @FXML Pane mazePane;
    @FXML Button btnSolveMaze;
    @FXML Button btnLoadMaze;
    @FXML Button btnPauseOrPlay;

    LevelCreator lvl;
    AnimationTimer timer;
    Square start;
    Square current;
    ArrayList<Square> squares;
    ArrayDeque<Square> deque;

    private int time;
    boolean isPaused;

    public void solveMaze() {
        isPaused = false;
        //Creating a FIFO queue
        deque = new ArrayDeque<>();

        current = start;
        deque.addFirst(current);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time++;
                if(time % 10 == 0) {

                    if (deadEnd(current)) {
                        current = deque.pollFirst();
                        current.setColor('5');

                    }

                    if (current.right != null && !current.right.isVisited()) {
                        current = current.right;
                        deque.addFirst(current);
                        current.setColor('4');
                        current.setVisited();
                    }

                    else if (current.down != null && !current.down.isVisited()) {
                        current = current.down;
                        deque.addFirst(current);
                        current.setColor('4');
                        current.setVisited();
                    }

                    else if (current.left != null && !current.left.isVisited()) {
                        current = current.left;
                        deque.addFirst(current);
                        current.setColor('4');
                        current.setVisited();
                    }

                    else if (current.up != null && !current.up.isVisited()) {
                        current = current.up;
                        deque.addFirst(current);
                        current.setColor('4');
                        current.setVisited();
                    }

                    if (current.getTrait() == 3) {
                        timer.stop();
                        System.out.println("solved!");
                        current.setColor('3');
                    }
                }
            }
        };
        timer.start();
    }

    public void loadMaze() {
        lvl = new LevelCreator(mazePane, 0);
        squares = lvl.getSquares();

        for(int i = 0; i < squares.size(); i++) {
            if(squares.get(i).getTrait() == 2) {
                start = squares.get(i);
                start.setColor('4');
                break;
            }
        }
    }

    public void pauseOrPlay() {
        boolean paused = isPaused;

        if(!paused) {
            timer.stop();
            btnPauseOrPlay.setText("Play");
            isPaused = true;
        }
        if(paused) {
            timer.start();
            btnPauseOrPlay.setText("Pause");
            isPaused = false;
        }
    }

    public boolean deadEnd(Square current) {
        return  ((current.right == null || current.right.isVisited()) &&
                (current.down == null || current.down.isVisited() ) &&
                (current.left == null || current.left.isVisited()) &&
                (current.up == null || current.up.isVisited()));

    }

}

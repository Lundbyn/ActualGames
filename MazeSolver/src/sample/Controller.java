package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Controller {

    //All FXML interface from Sample.fxml
    @FXML AnchorPane UIpane;
    @FXML Pane mazePane;
    @FXML TextField txtSelect;
    @FXML Button btnSolveMaze;
    @FXML Button btnLoadMaze;
    @FXML Button btnPauseOrPlay;
    @FXML Button btnDrawMaze;
    @FXML Rectangle background;

    public static double width = 800.00;
    public static double height = 600.00;

    //Declares references
    LevelCreator lvl;
    AnimationTimer timer;
    Square start;
    Square current;
    ArrayList<Square> squares;
    ArrayDeque<Square> deque;
    Rat rat;

    //Declares variables
    private int mazeSelector, time;
    boolean isPaused, isSolved = false, isSolving = true, isDrawing = false;
    char editSquare = '0';


    //Maze drawer (BETA)
    public void drawMaze() {

        isDrawing = true;
        loadMaze(0);


        //Relocate testing
        background.setWidth(width);
        background.setHeight(height);
        mazePane.setLayoutX((width / 2) - 217);
        mazePane.setLayoutY((height / 2) - 200);
        System.out.println(background.getWidth());



        //Gets x and y for clicked position
        mazePane.setOnMouseDragged(e -> {
            //Tests to see if clicked position contains square
            if(isDrawing) {
                for (Square square : lvl.getSquares()) {
                    if(e.getPickResult().getIntersectedNode() == square.getView()) {
                        if(square.getTrait() != 2 && square.getTrait() != 3)
                        square.setColor(editSquare);
                    }
                }
            }
        });

        //Adds listeners to 1 and 2, deciding new trait for edited squares
        mazePane.requestFocus();
        mazePane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.DIGIT1) {
                editSquare = '0';
            }
            if(e.getCode() == KeyCode.DIGIT2) {
                editSquare = '1';
            }
        });
    }


    public void solveMaze() {
        if(isSolving) return;
        isSolving = true;
        isSolved = false;
        isPaused = false;

        if(isDrawing) lvl.setAdjacents(0,squares);
        isDrawing = false;

        //Setting start and preparing deque.
        current = start;
        deque.addFirst(current);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time++;
                if(time % 2 == 0) {

                    //Checks to see if it can go right. Sets current to right-square and recolors if possible.
                    if (current.right != null && !current.right.isVisited()) {
                        current = current.right;
                        deque.addFirst(current);
                        current.setColor('4');
                        current.setVisited();
                    }

                    //Same thing as above, with all directions.
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

                    //Going backwards through the deque
                    else {
                        if(!deque.isEmpty()) {
                            current = deque.peekFirst();
                            if(deadEnd(current)) {
                                deque.pollFirst();
                                current.setColor('5');
                            }
                        }
                        //If deque is empty and goal not reached, goal is not reachable.
                        else {
                            pauseOrPlay();
                            System.out.println("Fant ikke mål");
                        }
                    }

                    //Found goal, end process
                    if (current.getTrait() == 3) {
                        runRat();
                    }
                }
            }
        };
        timer.start();
    }

    private void runRat() {
        timer.stop();
        System.out.println("solved!");
        current.setColor('3');

        if(rat == null) rat = new Rat();
        mazePane.getChildren().add(rat.getView());
        rat.setPosition(deque.peekLast().getView().getTranslateX(), deque.peekLast().getView().getTranslateY());
        rat.getView().toFront();
        rat.startMovement(deque);
    }

    public void loadMaze() {
        try {
            mazeSelector = Integer.parseInt(txtSelect.getText());
        }
        catch (Exception e) {
            mazeSelector = 1;
            System.out.println("Ikke et tall");
        }
        loadMaze(mazeSelector);
        isDrawing = false;

    }

    private void loadMaze(int maze) {
        //Resets maze and maze-pane
        isSolving = false;
        isSolved = false;
        lvl = null;
        mazePane.getChildren().clear();
        deque = new ArrayDeque<>();

        //Creates level using levelCreator.
        lvl = new LevelCreator(mazePane, maze);
        squares = lvl.getSquares();

        for(int i = 0; i < squares.size(); i++) {
            if(squares.get(i).getTrait() == 2) {
                start = squares.get(i);
                start.setColor('4');
                break;
            }
        }
    }

    //Pauses or resumes the process, based current status. Lots of bugs here
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

    //If all directions is visited or null, its a dead end.
    public boolean deadEnd(Square current) {
        return  ((current.right == null || current.right.isVisited()) &&
                (current.down == null || current.down.isVisited()) &&
                (current.left == null || current.left.isVisited()) &&
                (current.up == null || current.up.isVisited()));

    }

    public static void setWidth(Number number) {
        width = (double) number;
    }

    public static void setHeight(Number number) {
        height = (double) number;
    }

}
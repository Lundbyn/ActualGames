package sample;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class LevelCreator {

    private ArrayList<Square> squares = new ArrayList<>();

    public LevelCreator(Pane pane, int maze) {

        if(maze >= MazeData.MAZES.length) {
            return;
        }
        squares.clear();
        if(!testStringArray(maze)) {
            System.out.println("Labyrinten er ikke riktig");
            return;
        }

        //Creating squares from the map created tin MazeData class
        for (int i = 0; i < MazeData.MAZES[maze].length; i++) {
            String line = MazeData.MAZES[maze][i];
            for (int j = 0; j < line.length(); j++) {
                Square square = new Square(line.charAt(j));
                square.setPos(j*21, i*21);
                pane.getChildren().add(square.getView());
                squares.add(square);
            }
        }

        setAdjacents(maze, squares);
    }

    //Setting adjacent squares
    public void setAdjacents(int maze, ArrayList<Square> squares) {
        Square adjacent;
        int length = MazeData.MAZES[maze][0].length();
        for (int i = 0; i < squares.size(); i++) {
            Square current = squares.get(i);

            //If current square is a wall, no adjacent square is needed
            if (current.getTrait() != 1) {

                //Testing if right square exists
                if ((i % length) + 1 < length) {
                    adjacent = squares.get(i + 1);
                    //If right square is not a wall, set current.right to adjacent.
                    if (adjacent.getTrait() != 1) {
                        current.right = adjacent;
                    }
                }

                //Same thing as above with down, left and up in given order.
                if (i + length < squares.size()) {
                    adjacent = squares.get(i + length);
                    if (adjacent.getTrait() != 1) {
                        current.down = adjacent;
                    }
                }

                if ((i % length) - 1 >= 0) {
                    adjacent = squares.get(i - 1);
                    if (adjacent.getTrait() != 1) {
                        current.left = adjacent;
                    }
                }

                if (i - length >= 0) {
                    adjacent = squares.get(i - length);
                    if (adjacent.getTrait() != 1) {
                        current.up = adjacent;
                    }
                }
            }
        }
    }

    //Tests to see if all lines are the same length.
    private boolean testStringArray(int maze) {
        int lineLength = MazeData.MAZES[maze][0].length();
        for(int i = 1; i < MazeData.MAZES[maze].length; i++) {
            if(lineLength != MazeData.MAZES[maze][i].length()) {
                return false;
            }
        }
        return true;
    }


    public ArrayList<Square> getSquares() {
        return squares;
    }

}

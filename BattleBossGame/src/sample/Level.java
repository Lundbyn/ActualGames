package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class Level {

    private final int platformWidth = 60;
    private final int platformHeight = 60;
    private ArrayList<Platform> platformList = new ArrayList<>();
    Platform rec;
    Color color;


    Level(int currentLevel, Pane pane) {

        decideColor(currentLevel);

        for (int i = 0; i < LevelData.levels[currentLevel].length; i++) {
            String line = LevelData.levels[currentLevel][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;

                    case '1':
                        rec = new Platform(j* platformWidth, i * platformHeight, platformWidth, platformHeight, color);
                        pane.getChildren().add(rec.getRec());
                        platformList.add(rec);
                        break;

                }
            }
        }
    }

    private void decideColor(int currentLevel) {
        switch (currentLevel) {
            case 0: color = Color.ORANGE;
            break;

            case 1: color = Color.GREEN;
            break;
        }
    }

    public ArrayList<Platform> getPlatformList(){ return platformList; }
}
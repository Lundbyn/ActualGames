package Model;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


/**
 * This is the level object creating the layout for the different types of enemies/ platforms and where to place them
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public class Level extends Group {

    private final int platformWidth = 60;
    private final int platformHeight = 60;
    private String enemy1Img = "Pictures/enemy.png";
    private String enemy2Img = "Pictures/enemy2.png";
    private String bossImg = "Pictures/boss.png";


    private ArrayList<Platform> platformList = new ArrayList<>();
    private ArrayList<Spikes> spikeList = new ArrayList<>();
    private ArrayList<Platform> goalList = new ArrayList<>();
    private ArrayList<Enemy> enemyList = new ArrayList<>();

    public Boss boss;

    /**
     * Create a level
     * @param currentLevel current level
     * @param player player Object
     */
    Level(int currentLevel, Node player) {


        boss = new Boss(bossImg, 680,180,100, player);
        boss.setAlive(false);

        for (int i = 0; i < LevelData.LEVELS[currentLevel].length; i++) {
            String line = LevelData.LEVELS[currentLevel][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;

                    case '1':
                        Platform platform = new Platform(j* platformWidth, i * platformHeight, platformWidth, platformHeight, "Pictures/platform.png");
                        getChildren().add(platform.getImgView());
                        platformList.add(platform);
                        break;

                    case '2':
                        Spikes spike = new Spikes(j* platformWidth, i * platformHeight, platformWidth, platformHeight, "Pictures/spike.png");
                        getChildren().add(spike.getImgView());
                        spikeList.add(spike);
                        break;

                    case '3':
                        Platform goal = new Platform(j* platformWidth, i * platformHeight, platformWidth, platformHeight, "Pictures/end.png");
                        getChildren().add(goal.getImgView());
                        goalList.add(goal);
                        break;

                    case '4':
                        Enemy enemy = new Enemy(j*platformWidth, (i*platformHeight) + 10, (j * platformWidth) + 200, 2,0, 3, true, enemy1Img);
                        getChildren().addAll(enemy.getView(), enemy.getHealthBar());
                        enemyList.add(enemy);
                        break;


                    case '5':
                        Enemy verticalEnemy = new Enemy(j*platformWidth, (i*platformHeight) + 10, (i * platformHeight) + 200, 0,2, 3, false, enemy1Img);
                        getChildren().addAll(verticalEnemy.getView(), verticalEnemy.getHealthBar());
                        enemyList.add(verticalEnemy);
                        break;

                    case '6':
                        Enemy blueEnemy = new Enemy(j*platformWidth, (i*platformHeight) + 10, (j * platformWidth) + 200, 2,0, 5, true, enemy2Img);
                        getChildren().addAll(blueEnemy.getView(), blueEnemy.getHealthBar());
                        enemyList.add(blueEnemy);
                        break;

                    case '7':
                        Enemy verticalBlue = new Enemy(j*platformWidth, (i*platformHeight) + 10, (i * platformHeight) + 200, 0,2, 5, false, enemy2Img);
                        getChildren().addAll(verticalBlue.getView(), verticalBlue.getHealthBar());
                        enemyList.add(verticalBlue);
                        break;

                    case '9':
                        getChildren().add(boss.getView());
                        boss.setAlive(true);
                        break;
                }
            }
        }
    }

    public ArrayList<Platform> getPlatformList(){ return platformList; }
    public ArrayList<Spikes> getSpikeList() { return spikeList; }
    public ArrayList<Platform> getGoalList() { return goalList; }
    public ArrayList<Enemy> getEnemyList() { return enemyList; }
}
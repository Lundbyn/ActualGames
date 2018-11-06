package Model;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.file.Paths;

/**
 * The Game object
 */
public class Game {

    private int bossTimer;
    public int numberOfDeaths;
    private int saveLevel, saveDeaths;
    private int [] saveInfo = {saveLevel, saveDeaths};

    private boolean  right, left, up, shooting;

    private boolean allowedToShoot = true;

    private Scene scene;
    private Group root;

    private static int currentLevel;
    public boolean gameComplete = false;

    private Text moveTips, shootTips, redTips, blueTips, progressTips;

    public AnimationTimer timer;
    public AudioClip shootSound, deathSound, completionSound;

    public Player player;
    public Level level;

    private Rectangle bossHp;
    private ImageView hpBorder;

    /**
     * Sets the size if the game
     * @param sceneWidth Width of the screen
     * @param sceneHeight Height of the screen
     */
    public Game(int sceneWidth, int sceneHeight) {
        loadFile("saveFile.txt");
        player = new Player();
        player.setVelocity(new Point2D(0, 0));

        root = new Group();
        createTextObjects();
        root.getChildren().addAll(player.getView());
        scene = new Scene(root, sceneWidth, sceneHeight);

        numberOfDeaths = saveDeaths;
        newLevel();
        createBossHp();

        shootSound = new AudioClip(Paths.get("src/Sounds/playerShot.wav").toUri().toString());
        shootSound.setVolume(0.1);
        deathSound = new AudioClip(Paths.get("src/Sounds/deathsound.wav").toUri().toString());
        deathSound.setVolume(1);
        completionSound = new AudioClip(Paths.get("src/Sounds/completion.wav").toUri().toString());
        completionSound.setVolume(0.5);


        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                root.requestFocus();

                playerMovement();
                enemyCollision();
                levelCompletion();
                playerBulletUpdate();
                bossBulletUpdate();
                bossBattle();
            }
        };
        timer.start();



        player.getView().translateXProperty().addListener(((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();

            if (offset > 580) {
                root.setLayoutX(-(offset - 580));
                bossHp.setTranslateX(offset - 180);
                hpBorder.setTranslateX(offset - 183);
            }
        }));
    }

    /**
     * Moves the player
     */
    private void playerMovement() {
        player.updateMovement(level.getPlatformList());
        if (left) {
            player.moveLeft();
        }
        if (right) {
            player.moveRight();
        }
        if (up) {
            player.jump();
        }
        if(!right && !left) {
            player.standingStill();
        }
        if(shooting && allowedToShoot) {
            player.shootBullet();
            root.getChildren().add(player.bullet.getView());
            shootSound.play();
            allowedToShoot = false;
        }
        if (!player.fall()) {
            gameOver();
        }
        if (player.spikeCollision(level.getSpikeList())) {
            gameOver();
        }
    }

    /**
     * Tests if the player is colliding with enemies
     */
    private void enemyCollision() {
        for(Enemy enemies : level.getEnemyList()) {
            if(enemies.isAlive) {
                if(player.getView().getBoundsInParent().intersects(enemies.getView().getBoundsInParent())) {
                    gameOver();
                }
            }
        }
    }

    /**
     * Tests if the player has come ti the end point and sets the next level
     */
    private void levelCompletion() {
        for(Platform goal : level.getGoalList()) {
            if (player.getView().getBoundsInParent().intersects(goal.getBoundsInParent())) {
                currentLevel++;
                player.playerReset();
                newLevel();
                resetTextPosition();
                clearBullets();
                root.setLayoutX(0);
                completionSound.play();

                if(currentLevel == 1){
                    moveTips.setVisible(false);
                    shootTips.setVisible(false);
                    redTips.setVisible(false);
                    blueTips.setVisible(false);
                    progressTips.setVisible(false);
                }
            }
        }
    }

    /**
     * Updates the playerbullets and removes them if the are colliding with enemies, or are to far away form the level
     */
    private void playerBulletUpdate() {
        for (Bullet bullet : player.getBulletList()) {
            for(Enemy enemy :level.getEnemyList()){
                if(bullet.isColliding(enemy) && enemy.isAlive){
                    root.getChildren().remove(bullet.getView());
                    bullet.setAlive(false);
                    enemy.setHitPoints(enemy.getHitPoints()-1);
                    if(enemy.getHitPoints() == 0) {
                        enemy.isAlive = false;
                        enemy.getView().setVisible(false);
                    }
                }
            }
            for (Platform platform : level.getPlatformList()) {
                Bounds bounds = platform.getLayoutBounds();
                if (bullet.getView().getBoundsInParent().intersects(bounds)) {
                    bullet.setAlive(false);
                    root.getChildren().remove(bullet.getView());
                }
            }
            for(Spikes spike : level.getSpikeList()) {
                Bounds bounds = spike.getLayoutBounds();
                if(bullet.getView().getBoundsInParent().intersects(bounds)) {
                    bullet.setAlive(false);
                    root.getChildren().remove(bullet.getView());
                }
            }
            for(Platform goal : level.getGoalList()) {
                Bounds bounds = goal.getBoundsInParent();
                if(bullet.getView().getBoundsInParent().intersects(bounds)) {
                    bullet.setAlive(false);
                    root.getChildren().remove(bullet.getView());
                }
            }

            if(bullet.getXposition() < - 100 || bullet.getXposition() > 5000) {
                bullet.setAlive(false);
                root.getChildren().remove(bullet.getView());
            }
        }
        player.getBulletList().removeIf(Bullet::isDead);
    }

    /**
     * Updates the bossBullets and removes them if they are colliding with the player, or are to far away form the level
     */
    private void bossBulletUpdate(){
        for(Bullet bullet : level.boss.getBulletList()){
            if(bullet.isColliding(player)){
                root.getChildren().remove(bullet.getView());
                bullet.setAlive(false);
                gameOver();
            }
            if(bullet.getXposition() < - 100 || bullet.getXposition() > 5000) {
                root.getChildren().remove(bullet.getView());
                bullet.setAlive(false);
            }
        }
        level.boss.getBulletList().removeIf(Bullet::isDead);
    }


    /**
     * Updates the bossBattle
     */
    private void bossBattle() {
        if(level.boss.getAlive()) {
            bossHp.toFront();
            hpBorder.toFront();
            bossHp.setVisible(true);
            hpBorder.setVisible(true);
            if(player.getView().getBoundsInParent().intersects(level.boss.getView().getBoundsInParent())) {
                gameOver();
            }
            for(Bullet bullet : player.getBulletList()) {
                if(bullet.getView().getBoundsInParent().intersects(level.boss.getView().getBoundsInParent())) {
                    level.boss.setHitPoints(level.boss.getHitPoints() - 1);
                    bullet.setAlive(false);
                    root.getChildren().remove(bullet.getView());
                    bossHp.setWidth(level.boss.getHitPoints() * (400 / level.boss.getDefaultHp()));

                    if(level.boss.getHitPoints() < level.boss.getDefaultHp() / 2) {
                        level.boss.shootAimingBullet();
                        root.getChildren().add(level.boss.bossBullet.getView());
                    }

                    if (level.boss.getHitPoints() == 0) {
                        level.boss.setAlive(false);
                        bossHp.setVisible(false);
                        level.boss.getView().setVisible(false);
                        clearBullets();
                        gameComplete = true;
                    }
                }
            }
            bossTimer++;
            if(bossTimer % 30 == 0) {
                level.boss.shootBullet();
                root.getChildren().add(level.boss.bossBullet.getView());
            }
            for(BossBullet bossBullet : level.boss.getBulletList()) {
                for(Platform platform : level.getPlatformList()) {
                    Bounds bounds = platform.getBoundsInParent();
                    if(bossBullet.getView().getBoundsInParent().intersects(bounds)) {
                        root.getChildren().remove(bossBullet.getView());
                        bossBullet.setAlive(false);
                    }
                }
            }
        }
    }

    /**
     * Creates a new level, removing the old
     */
    private void newLevel(){
        root.getChildren().removeAll(level);
        level = null;
        level = new Level(currentLevel, player.getView());
        root.getChildren().add(level);
    }

    /**
     * Every necessary function when the player respawn
     */
    public void respawn() {
        clearBullets();
        numberOfDeaths ++;
        resetTextPosition();
        player.playerReset();
        player.setAlive(true);
        bossHp.setWidth(400);
        level.boss.bossReset();
        root.setLayoutX(0);
        timer.start();

        for(Enemy enemies : level.getEnemyList()) {
            enemies.enemyReset();
        }
    }

    /**
     * Every necessary function when the player dies
     */
    private void gameOver() {
        for(Enemy enemies : level.getEnemyList()) {
            enemies.enemyTimer.stop();
        }
        for(PlayerBullet bullet : player.getBulletList()){
            bullet.bulletTimer.stop();
        }
        for(BossBullet bullet : level.boss.getBulletList()){
            bullet.bossBulletTimer.stop();
        }
        timer.stop();
        level.boss.bossTimer.stop();
        deathSound.play();
        player.setAlive(false);
    }

    /**
     * Removes all the bullets
     */
    private void clearBullets() {
        for(Bullet playerBullet : player.getBulletList()){
            root.getChildren().remove(playerBullet.getView());
            playerBullet.setAlive(false);
        }
        for(Bullet bossBullet : level.boss.getBulletList()){
            root.getChildren().remove(bossBullet.getView());
            bossBullet.setAlive(false);
        }
    }

    /**
     * Creates the text objects in the game
     */
    private void createTextObjects() {
        if(currentLevel == 0) {
            moveTips = new Text("Move by using 'w' 'a' 'd'");
            shootTips = new Text("Shoot with 'Space'");
            progressTips = new Text("Reach the 'END' blocks to progress to the next map");
            redTips = new Text("Red enemies have 3 hp");
            blueTips = new Text("Blue enemies have 5 hp");

            moveTips.setFont(new Font(20));
            moveTips.setX(20);
            moveTips.setY(400);

            shootTips.setFont(new Font(20));
            shootTips.setX(600);
            shootTips.setY(300);

            redTips.setFont(new Font(20));
            redTips.setX(900);
            redTips.setY(300);

            blueTips.setFont(new Font(20));
            blueTips.setX(1260);
            blueTips.setY(400);

            progressTips.setFont(new Font(20));
            progressTips.setX(1340);
            progressTips.setY(300);

            root.getChildren().addAll(moveTips, shootTips, redTips, blueTips, progressTips);
        }
    }

    /**
     * Creates the boess hitpoints rectangle and border around the bar
     */
    private void createBossHp() {
        bossHp = new Rectangle(400,20, Color.RED);
        bossHp.setTranslateX(400);
        bossHp.setTranslateY(50);
        bossHp.setVisible(false);

        hpBorder = new ImageView(new Image("Pictures/hpBorder.png"));
        hpBorder.setTranslateX(397);
        hpBorder.setTranslateY(47);
        hpBorder.setVisible(false);

        root.getChildren().addAll(bossHp, hpBorder);
    }


    /**
     * Resets the position of the bosses heathBar
     */
    private void resetTextPosition() {
        bossHp.setTranslateX(400);
        hpBorder.setTranslateX(397);
    }

    /**
     * Saves CurrentLevel and number of deaths in a file
     * @param filePath name of the file with the saved data
     */
    public void saveFile(String filePath) {

        saveInfo[0] = currentLevel;
        saveInfo[1] = numberOfDeaths;

        try {
            File savedFile = new File(filePath);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(savedFile));

            for(int i = 0; i < saveInfo.length; i++) {
                fileWriter.write(saveInfo[i] + "\n");
            }

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the saved file
     * @param filepath name of the file with the saved data
     */
    private void loadFile(String filepath) {
        try {
            File savedFile = new File(filepath);
            BufferedReader fileReader = new BufferedReader(new FileReader(savedFile));

            for(int i = 0; i < saveInfo.length; i++) {
                saveInfo[i] = Integer.parseInt(fileReader.readLine());
            }

            saveLevel = saveInfo[0];
            saveDeaths = saveInfo[1];

            currentLevel = saveInfo[0];
            numberOfDeaths = saveInfo[0];


            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Group getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public void setAllowedToShoot(boolean allowedToShoot) {
        this.allowedToShoot = allowedToShoot;
    }

}
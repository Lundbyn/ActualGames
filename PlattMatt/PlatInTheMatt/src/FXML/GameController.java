package FXML;

import Model.BossBullet;
import Model.Enemy;
import Model.Game;
import Model.PlayerBullet;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML AnchorPane root;

    @FXML Button btnResume;
    @FXML Button btnSave;
    @FXML Button btnRestart;
    @FXML Button btnLoad;
    @FXML Button btnQuit;
    @FXML CheckBox muteSfxBox;
    @FXML Rectangle pausedBackground;
    @FXML Label totalDeaths;
    @FXML Label deathCounter;
    @FXML Label deathMessage;

    private boolean paused = false;
    private String filePath = "saveFile.txt";

    private Game game;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game(1200, 680);
        root.getChildren().add(game.getRoot());
        listener.start();
        totalDeaths.setVisible(false);
        uiVisibility(false);


        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.D) {
                game.setRight(true);
            }
            if (e.getCode() == KeyCode.A) {
                game.setLeft(true);
            }
            if (e.getCode() == KeyCode.W) {
                game.setUp(true);
            }
            if(e.getCode() == KeyCode.SPACE) {
                game.setShooting(true);
            }
            if(e.getCode() == KeyCode.R && !game.gameComplete) {
                game.respawn();
                uiVisibility(false);
                paused = false;
            }
            if(e.getCode() == KeyCode.ESCAPE && !game.gameComplete) {
                if(!paused) {
                    uiVisibility(true);
                    paused = true;
                    pauseTimers(paused);
                }
                else if(paused && game.player.getAlive()) {
                    uiVisibility(false);
                    paused = false;
                    pauseTimers(paused);
                }
            }
        });

        root.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.D) {
                game.setRight(false);
            }
            if (e.getCode() == KeyCode.A) {
                game.setLeft(false);
            }
            if (e.getCode() == KeyCode.W) {
                game.setUp(false);
            }
            if(e.getCode() == KeyCode.SPACE) {
                game.setShooting(false);
                game.setAllowedToShoot(true);
            }
        });
    }
    AnimationTimer listener = new AnimationTimer() {
        @Override
        public void handle(long now) {
            root.requestFocus();

            uiToFront();
            sfxControl();
            endGame();
            updateDeathCounter();
        }
    };

    public void resumeGame() {
        paused = false;
        pauseTimers(paused);
        uiVisibility(false);
    }

    public void saveGame() {
        game.saveFile(filePath);
        paused = false;
        pauseTimers(paused);
        uiVisibility(false);
    }

    public void restartGame() {
        try {
            File savedFile = new File(filePath);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(savedFile));
            fileWriter.write("0\n0");
            fileWriter.close();
            loadProgress();

        } catch (IOException e) {
            e.printStackTrace();
        }
        paused = false;
        totalDeaths.setVisible(false);
        btnRestart.setText("Restart game");
        uiVisibility(false);
    }

    public void loadProgress() {
        root.getChildren().remove(game.getRoot());
        game = new Game(1200, 680);
        root.getChildren().add(game.getRoot());
        paused = false;
        uiVisibility(false);
    }

    public void quitGame() {
        Stage stage = (Stage)btnQuit.getScene().getWindow();
        stage.close();
    }

    private void endGame() {
        totalDeaths.setText("You died a total of " + game.numberOfDeaths + " time(s).");
        if(game.gameComplete) {
            totalDeaths.setVisible(true);
            pausedBackground.setVisible(true);
            btnRestart.setVisible(true);
            btnRestart.setText("Play again");
        }
    }

    private void updateDeathCounter() {
        deathCounter.setText("Deaths: " + game.numberOfDeaths);
        if(game.player.isDead()) {
            deathMessage.setVisible(true);
        }
        else {
            deathMessage.setVisible(false);
        }
    }

    public void sfxControl() {
        if(muteSfxBox.isSelected()) {
            game.player.jumpSound.setVolume(0);
            game.shootSound.setVolume(0);
            game.completionSound.setVolume(0);
            game.deathSound.setVolume(0);
        }
        else {
            game.player.jumpSound.setVolume(0.1);
            game.shootSound.setVolume(0.1);
            game.completionSound.setVolume(0.5);
            game.deathSound.setVolume(1);
        }
    }


    private void uiVisibility(boolean visibility) {
        btnResume.setVisible(visibility);
        btnSave.setVisible(visibility);
        btnRestart.setVisible(visibility);
        btnLoad.setVisible(visibility);
        btnQuit.setVisible(visibility);
        muteSfxBox.setVisible(visibility);
        pausedBackground.setVisible(visibility);
    }

    private void uiToFront() {
        pausedBackground.toFront();
        btnResume.toFront();
        btnSave.toFront();
        btnRestart.toFront();
        btnLoad.toFront();
        btnQuit.toFront();
        muteSfxBox.toFront();
        totalDeaths.toFront();
        deathCounter.toFront();
        deathMessage.toFront();
    }

    private void pauseTimers(boolean paused) {
        if(paused) {
            for (Enemy enemy : game.level.getEnemyList()) {
                enemy.enemyTimer.stop();
            }
            for(PlayerBullet bullet : game.player.getBulletList()) {
                bullet.bulletTimer.stop();
            }
            for(BossBullet bullet : game.level.boss.getBulletList()){
                bullet.bossBulletTimer.stop();
            }
            game.level.boss.bossTimer.stop();
            game.timer.stop();
        }
        if(!paused) {
            for (Enemy enemy : game.level.getEnemyList()) {
                enemy.enemyTimer.start();
            }
            for(PlayerBullet bullet : game.player.getBulletList()) {
                bullet.bulletTimer.start();
            }
            for(BossBullet bullet : game.level.boss.getBulletList()){
                bullet.bossBulletTimer.start();
            }
            game.level.boss.bossTimer.start();
            game.timer.start();
        }
    }
}
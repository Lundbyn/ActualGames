package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Pane pane;

    AnimationTimer gameTimer;
    Player player;
    Rectangle rec;
    Level level;
    int power = 0;
    boolean space = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        level = new Level(0, pane);
        System.out.println("LEL");
        player = new Player();
        player.setLevelList(level.getPlatformList());
        pane.getChildren().addAll(player.getView(), player.getPower());
        player.getView().setTranslateX(10);
        player.getView().setTranslateY(20);



        initializeKeyListeners();
        initializeCameraListener();

        testMethod();

        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                pane.requestFocus();

                rec.setTranslateY(rec.getTranslateY() + 2);
                testMethod2();

                updatePlayerBullets();
            }
        };
        gameTimer.start();
    }

    private void testMethod() {
        rec = new Rectangle(300,30,30,30);
        pane.getChildren().add(rec);
    }

    private void testMethod2() {
        for(Platform platform : level.getPlatformList()) {
            if(platform.getView().getBoundsInParent().intersects(rec.getBoundsInParent())) {
                platform.getView().setVisible(false);
                platform.setActive(false);
            }
        }
    }

    private void initializeKeyListeners() {
        pane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.D) {
                player.setRight(true);
            }
            if (e.getCode() == KeyCode.A) {
                player.setLeft(true);
            }
            if (e.getCode() == KeyCode.W) {
                player.setUp(true);
            }
            if(e.getCode() == KeyCode.E || e.getCode() == KeyCode.Q) {
                player.setProtection(e.getCode().toString().toLowerCase().charAt(0));
            }
            if(e.getCode() == KeyCode.SPACE) {
                space = true;
            }
            if(e.getCode() == KeyCode.R) {
                player.reset();
            }
        });

        pane.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.D) {
                player.setRight(false);
            }
            if (e.getCode() == KeyCode.A) {
                player.setLeft(false);
            }
            if (e.getCode() == KeyCode.W) {
                player.setUp(false);
            }
            if(e.getCode() == KeyCode.SPACE) {
                player.shoot(power, pane);
                space = false;
            }
        });
    }

    private void initializeCameraListener() {
        player.getView().translateXProperty().addListener(((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();

            if (offset > 700) {
                pane.setLayoutX(-(offset - 700));
            }
        }));
    }

    private void updatePlayerBullets() {
        for(PlayerBullet pb : player.getPlayerBullets()) {
            for (Platform platform : level.getPlatformList()) {
                if (pb.getView().getBoundsInParent().intersects(platform.getView().getBoundsInParent())) {
                    pb.remove();
                }
            }
        }

        if(space) {
            if(power < 50) {
                power++;
                player.getPower().setWidth(power);
            }
        }
        else {
            player.getPower().setWidth(0);
            power = 0;
        }
    }
}

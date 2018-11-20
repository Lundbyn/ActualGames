package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashController implements Initializable {

    @FXML Pane dashPane;

    Player player;
    Level level;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("YAYY");

        level = new Level(0,dashPane);

        player = new Player();
        player.setLevelList(level.getPlatformList());
        dashPane.getChildren().add(player.getView());
    }
}

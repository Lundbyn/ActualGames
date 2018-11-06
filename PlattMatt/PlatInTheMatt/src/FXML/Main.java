package FXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Main class for Mat in the plat. Basic boilerplate code for launching the JavaFX
 * game application. Game is launched through this file.
 * 
 * @author Mathias Lundby, Jacob Larsen
 */
public class Main extends Application {

    @Override
    public void start (Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));

        Scene scene = new Scene(root, 1200, 680);
        stage.setTitle("PlatInTheMatt");
        stage.setScene(scene);
        stage.show();
        scene.getRoot().requestFocus();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
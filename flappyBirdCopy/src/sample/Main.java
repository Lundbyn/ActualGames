package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        scene = new Scene(root,1200,800);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
/*
        primaryStage.setMaxHeight(812);
        primaryStage.setMaxWidth(1212);

        primaryStage.setMinHeight(812);
        primaryStage.setMinWidth(1212);
        */
    }

    public static void main(String[] args) {
        launch(args);
    }
}

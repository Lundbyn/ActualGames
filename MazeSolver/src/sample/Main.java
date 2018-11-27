package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Maze solver");
        Scene scene = new Scene(root, 800,600);
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("Stage width: " + primaryStage.getWidth());
        System.out.println("Stage heigth: " + primaryStage.getHeight());
        System.out.println("scene width: " + scene.getWidth());
        System.out.println("scene height: " + scene.getHeight());

        /*
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                Controller.resize(arg2);
                System.out.println(arg1 + " " + arg2);
            }
        });
        */
    }


    public static void main(String[] args) {
        launch(args);
    }
}

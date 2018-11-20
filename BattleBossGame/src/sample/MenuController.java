package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML Pane menuPane;
    @FXML Button btnNormal;
    @FXML Button btnDash;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("LEL");
    }

    public void loadNormal() {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
            menuPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadDash() {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("Dash.fxml"));
            menuPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

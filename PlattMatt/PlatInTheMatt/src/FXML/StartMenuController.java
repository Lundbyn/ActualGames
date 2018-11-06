package FXML;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StartMenuController {

    @FXML AnchorPane root;
    @FXML Button newButton;
    @FXML Button loadButton;
    @FXML Button closeButton;

    public void newGame() throws IOException {
            File savedFile = new File("saveFile.txt");
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(savedFile));
            fileWriter.write("0\n0");
            fileWriter.close();
            loadGame();
    }

    public void loadGame() throws IOException{
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("Game.fxml"));
        root.getChildren().setAll(newPane);
    }

    public void exitGame() {
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }
}
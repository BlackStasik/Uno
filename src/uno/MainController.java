package uno;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainController {

    @FXML
    public void newGameForTwo(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("unoForTwo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.stage.setScene(new Scene(root, 1500, 850));
        Main.stage.show();
    }
    @FXML
    public void newGameForThree(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("unoForThree.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.stage.setScene(new Scene(root, 1500, 850));
        Main.stage.show();
    }
    @FXML
    public void newGameForFour(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("unoForFour.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.stage.setScene(new Scene(root, 1500, 850));
        Main.stage.show();
    }


}

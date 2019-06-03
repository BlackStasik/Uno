package uno;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Main extends Application {

    public static Stage stage;
    public Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        String path = new File("src/resource/sound.mp3")
                .getPath();


        System.out.println(path);
        Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();


        root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = primaryStage;
        stage.getIcons().add(new Image("resource/icon.png"));
        stage.setTitle("Uno");
        stage.setScene(new Scene(root, 850, 450));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

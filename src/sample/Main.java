package sample;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import javafx.util.Duration;
import java.util.Observable;

import static java.lang.StrictMath.floor;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        int height, width;
        width = 900;
        height = 300;

        Text time = new Text();
        Text fileName = new Text();


        Image PlayButtonImage = new Image(getClass().getResourceAsStream("play.jpg"), 100, 100, false, false);
        Image PauseButtonImage = new Image(getClass().getResourceAsStream("pause.jpg"), 100, 100, false, false);
        Image FilePickerButtonImage = new Image(getClass().getResourceAsStream("mp3.jpg"), 30, 30, false, false);
        ImageView imageViewPlay = new ImageView(PlayButtonImage);
        ImageView imageViewPause = new ImageView(PauseButtonImage);
        ImageView imageViewFile = new ImageView(FilePickerButtonImage);



        time.setX((width / 4) * 3);
       time.setY(height / 8);



        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("mp3 music","*.mp3"));
        File file = fc.showOpenDialog(null);
        String path = file.getAbsolutePath();
        path = path.replace("\\", "/");
        Media media = new Media(new File(path).toURI().toString());
        final MediaPlayer[] mediaPlayer = {new MediaPlayer(media)};
       MediaView mediaView = new MediaView(mediaPlayer[0]);

        Button FilePicker = new Button();
        FilePicker.setGraphic(imageViewFile);
        FilePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("mp3 music","*.mp3"));
                File file = fc.showOpenDialog(null);
                String path = file.getAbsolutePath();
                path = path.replace("\\", "/");
                Media mediaPick = new Media(new File(path).toURI().toString());
                if(mediaPlayer[0].getStatus() == MediaPlayer.Status.PLAYING){
                    mediaPlayer[0].stop();
                }
                mediaPlayer[0] = new MediaPlayer(mediaPick);
                mediaPlayer[0].setAutoPlay(true);
                mediaPlayer[0].currentTimeProperty().addListener((javafx.beans.Observable ov) -> {
                    time.setText(timeConverter(mediaPlayer[0].getCurrentTime().toMillis())+ " / " + timeConverter(mediaPlayer[0].getTotalDuration().toMillis()));
                });
            }


                });



        Button playpause = new Button();
        playpause.setGraphic(imageViewPause);
        playpause.setOnAction((new EventHandler<ActionEvent>() {




            @Override public void handle(ActionEvent e) {
                if(picked != 1) {}
                MediaPlayer.Status status = mediaPlayer[0].getStatus();
                if (status == MediaPlayer.Status.PAUSED
                        || status == MediaPlayer.Status.READY
                        || status == MediaPlayer.Status.STOPPED) {
                    mediaPlayer[0].play();
                    System.out.println("Play");

                    playpause.setGraphic(imageViewPlay);
                }
                else{
                    mediaPlayer[0].pause();
                    playpause.setGraphic(imageViewPause);
                }
            }
        }
        ));
        time.setFont(Font.font("Times New Roman", 22));
        mediaPlayer[0].currentTimeProperty().addListener((javafx.beans.Observable ov) -> {
            time.setText(timeConverter(mediaPlayer[0].getCurrentTime().toMillis())+ " / " + timeConverter(mediaPlayer[0].getTotalDuration().toMillis()));
        });



        Group Player = new Group();
        Player.getChildren().add(playpause);
        Player.getChildren().add(mediaView);
        Player.getChildren().add(time);
        Player.getChildren().add(FilePicker);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(Player, width, height));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    public String timeConverter(double millies) {
        String secondsString;

        double millis = millies;  // obtained from StopWatch
        int minutes = (int) ((millis / 1000)  / 60);
        int seconds = (int) ((millis / 1000) % 60);

        if(seconds < 10) { secondsString = "0"+seconds;}
        else{secondsString = ""+seconds;}
        return minutes + " : " + secondsString;
    }

}

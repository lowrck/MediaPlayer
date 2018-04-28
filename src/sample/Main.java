import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import javax.crypto.NullCipher;
import javax.swing.*;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        int height, width;
        width = 900;
        height = 300;

        Text time = new Text();
        Text fileName = new Text();


        Image PlayButtonImage = new Image(getClass().getResourceAsStream("/sample/play.jpg"), 200, 200, false, false);
        Image PauseButtonImage = new Image(getClass().getResourceAsStream("/sample/pause.jpg"), 200, 200, false, false);
        Image FilePickerButtonImage = new Image(getClass().getResourceAsStream("/sample/mp3.jpg"), 30, 30, false, false);
        ImageView imageViewPlay = new ImageView(PlayButtonImage);
        ImageView imageViewPause = new ImageView(PauseButtonImage);
        ImageView imageViewFile = new ImageView(FilePickerButtonImage);


        final int[] picked = {0};







        final MediaPlayer[] mediaPlayer = {null};
       MediaView mediaView = new MediaView(mediaPlayer[0]);

        Button FilePicker = new Button();
        FilePicker.setGraphic(imageViewFile);
        FilePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FileChooser fc = new FileChooser();
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("mp3 music", "*.mp3"));
                    File file = fc.showOpenDialog(null);
                    String path = file.getAbsolutePath();
                    path = path.replace("\\", "/");
                    fileName.setText(file.getName().split("\\.mp3")[0]);
                    Media mediaPick = new Media(new File(path).toURI().toString());
                   try {
                    if (mediaPlayer[0].getStatus() == MediaPlayer.Status.PLAYING) {
                        mediaPlayer[0].stop();
                    }}catch (NullPointerException nll) {}
                    mediaPlayer[0] = new MediaPlayer(mediaPick);
                    mediaPlayer[0].setAutoPlay(true);
                    mediaPlayer[0].currentTimeProperty().addListener((javafx.beans.Observable ov) -> {
                        time.setText(timeConverter(mediaPlayer[0].getCurrentTime().toMillis()) + " / " + timeConverter(mediaPlayer[0].getTotalDuration().toMillis()));
                    });
                }catch(NullPointerException nll) {
                    JOptionPane.showMessageDialog(null, "Please pick a song", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                picked[0] = 1;
            }


                });



        Button playpause = new Button();
        playpause.setGraphic(imageViewPause);
        playpause.setOnAction((new EventHandler<ActionEvent>() {




            @Override public void handle(ActionEvent e) {
                if (picked[0] == 0) {
                    JOptionPane.showMessageDialog(null, "Please pick a song", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                try {
                    MediaPlayer.Status status = mediaPlayer[0].getStatus();
                    if (status == MediaPlayer.Status.PAUSED
                            || status == MediaPlayer.Status.READY
                            || status == MediaPlayer.Status.STOPPED) {
                        mediaPlayer[0].play();

                        playpause.setGraphic(imageViewPlay);
                    } else {
                        mediaPlayer[0].pause();
                        playpause.setGraphic(imageViewPause);
                    }
                }catch(NullPointerException nll) {

                }
            }
        }
        ));
        time.setFont(Font.font("Times New Roman", 22));


        time.setX((width / 4) * 3);
        time.setY(height / 8);
        time.setText("0:00 / 0:00");
        FilePicker.setLayoutY((height / 16) * 14);
        fileName.setX((width / 2) - 100);
        fileName.setY((height / 16) * 2);


        Group Player = new Group();
        Player.getChildren().add(playpause);
        Player.getChildren().add(mediaView);
        Player.getChildren().add(time);
        Player.getChildren().add(FilePicker);
        Player.getChildren().add(fileName);

        primaryStage.setTitle("Music Player");
        Scene Stage = new Scene(Player, width, height);
        primaryStage.setScene(Stage);
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

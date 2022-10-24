package uet.oop.bomberman;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Objects;

public class Sound {

    public static String BGM = "res/audio/03MainBGM.mp3";
    public static String startStage = "res/audio/02StageStart.mp3";
    private static Clip myClip;
    private static Media media;
    private static MediaPlayer player;

    public static void play(String sound) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    media = new Media(new File(sound).toURI().toString());
                    player = new MediaPlayer(media);
                    if(player.getStatus() != MediaPlayer.Status.PLAYING) {
                        loopInf();
                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();

    }
    public static void stop() {
        player.stop();
    }

    public static void loopInf(){
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.seconds(2));
                player.play();
            }
        });
        player.play();
    }
    public static void Startstage() {
        media = new Media(new File(startStage).toURI().toString());
        player = new MediaPlayer(media);
        player.play();
        player.setVolume(0.2);
    }

}

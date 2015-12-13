package util.sounds;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.LinkedList;
import main.Options;

public class SoundManager implements Runnable {

    private static LinkedList<AudioClip> sounds;
    private static LinkedList<AudioClip> music;

    public enum Music {

        music(0);

        private final int id;

        Music(int id) {
            this.id = id;
        }
    }

    public enum Sounds {

        pickup_laser(0), pickup_shield(1), pickup_repair(2), hurt(3), laserShot(4),
        button_press(5), typing(6)
        ;

	private final int id;

        Sounds(int id) {
            this.id = id;
        }
    }

    public SoundManager() {
        new Thread(this).start();
    }

    public static void playSound(Sounds s) {
        playSound(s.id);
    }

    public static void playMusic(Music m) {
        playMusic(m.id);
    }

    public static void stopMusic(Music m) {
        music.get(m.id).stop();
    }

    public static void playSound(int id) {
        if (Options.playSounds) {
            sounds.get(id).play();
        }
    }

    public static void playMusic(int id) {
        if (Options.playMusic) {
            System.out.println("util.sounds.SoundManager.playMusic()");
            music.get(id).loop();
        }
    }

    public static void stopMusic(int id) {
        music.get(id).stop();
    }

    @SuppressWarnings("unused")
    private static AudioClip loadClip(String name) {
        try {
            return Applet.newAudioClip(SoundManager.class.getClass().getResource("/resources/" + name + ".au"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setPlayMusic(boolean playMusic) {
        if (playMusic != Options.playMusic) {
            if (playMusic) {
                Options.playMusic = playMusic;
                playMusic(0);
            } else {
                for (int i = 0; i < music.size(); i++) {
                    stopMusic(i);
                }
                Options.playMusic = playMusic;
            }
        }
    }

    @Override
    public void run() {
        sounds = new LinkedList<>();
        // sounds.add(loadClip("Test"));
        music = new LinkedList<>();
        music.add(loadClip("music"));
        sounds.add(loadClip("pickup_laser"));
        sounds.add(loadClip("pickup_shield"));
        sounds.add(loadClip("pickup_repair"));
        sounds.add(loadClip("hurt"));
        sounds.add(loadClip("laserShot"));
        sounds.add(loadClip("button_press"));
        sounds.add(loadClip("typing"));
        
        playMusic(Music.music);
    }
}

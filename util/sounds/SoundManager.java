package util.sounds;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.LinkedList;
import util.gfx.GfxLoader;

public class SoundManager implements Runnable {
	private static LinkedList<AudioClip> sounds;
	private static LinkedList<AudioClip> music;

	private static boolean playSounds;
	private static boolean playMusic;

	public enum Music {
		music(0);

		private final int id;

		Music(int id) {
			this.id = id;
		}
	}

	public enum Sounds {
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
		if (playSounds) {
			sounds.get(id).play();
		}
	}

	public static void playMusic(int id) {
		if (playMusic) {
            System.out.println("util.sounds.SoundManager.playMusic()");
			music.get(id).loop();
		}
	}

	public static void stopMusic(int id) {
		music.get(id).stop();
	}

	@SuppressWarnings("unused")
	private static AudioClip loadClip(String name) {
            try{
		return Applet.newAudioClip(SoundManager.class.getClass().getResource("/resources/" + name + ".au"));
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
	}

	public static void setPlayMusic(boolean playMusic) {
		if (playMusic) {
			SoundManager.playMusic = playMusic;
		} else {
			for (int i = 0; i < music.size(); i++) {
				stopMusic(i);
			}
			SoundManager.playMusic = playMusic;
		}
	}

	public static void setPlaySounds(boolean playSounds) {
		SoundManager.playSounds = playSounds;
	}

	public static boolean isPlayMusic() {
		return playMusic;
	}

	public static boolean isPlaySounds() {
		return playSounds;
	}

	@Override
	public void run() {
		sounds = new LinkedList<>();
		// sounds.add(loadClip("Test"));
		music = new LinkedList<>();
                music.add(loadClip("music"));
		playMusic = true;
		playSounds = true;
                playMusic(Music.music);
	}
}

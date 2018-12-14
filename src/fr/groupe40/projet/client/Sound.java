package fr.groupe40.projet.client;

import fr.groupe40.projet.util.ResourcesManager;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.media.AudioClip;

public class Sound {

	/**
	 *  contains the main theme audio clip
	 */
	private AudioClip main_theme;

	private static AudioClip quit_button_sound;
	private static AudioClip play_button_sound;
	private static AudioClip settings_button_sound;
	/**
	 * contain the sound of a ship collision with his destination
	 */
	private static AudioClip sound_ship_explosion;

	/**
	 * Instanciate the music object, and launch or not the main_theme automatically
	 * @param launch_music Auto-launch main theme if true, else false
	 */
    public Sound(boolean launch_music) {
    	this.main_theme = Sound.generateAudioClip(Resources.path_sound_main_theme, Resources.main_theme_volume);
		if(Constants.main_theme_enabled && launch_music && this.main_theme != null)
    		main_theme.play();
		
		Sound.play_button_sound = generateAudioClip(Resources.path_sound_play, 0.5);
		Sound.quit_button_sound = generateAudioClip(Resources.path_sound_quit, 0.5);
		Sound.settings_button_sound = generateAudioClip(Resources.path_sound_settings, 0.5);
		Sound.sound_ship_explosion = generateAudioClip(Resources.path_sound_explosion, Resources.ship_explosion_volume);

	}

    /**
     *  Launch the main theme, if the theme isnt playing, auto-relaunch it
     */
    public void run() {
    	if(main_theme == null || !Constants.main_theme_enabled) { 
    		System.out.println("Unable to load main-theme music");
    		return; 
    	}
    	main_theme.setVolume(Resources.main_theme_volume);
    	if(main_theme.isPlaying()){
    		//DO nothing, music is already playing
    	} else{ //Replay the music
    		main_theme.play(); 
    	}
    }
    
    /**
     *  return an audio clip from a path to the file and his volume
     * @param path	path to the media file
     * @param volume	volume wanted of this sound (between 0 and 1, usually 0.5 is ok)
     * @return the audioclip
     */
    public static AudioClip generateAudioClip(String path, double volume) {
		try {
	    	AudioClip res = new AudioClip(ResourcesManager.getRessourcePathByName(path));
	    	res.setVolume(volume);
			return res;
		} catch (NullPointerException e) {
			System.out.println("Unable to load sounds: "+ path);
			return null;
		}   	
    	
    }

	/**
	 * @return the play_button_sound
	 */
	public static AudioClip getPlay_button_sound() {
		return play_button_sound;
	}

	/**
	 * @param play_button_sound the play_button_sound to set
	 */
	public static void setPlay_button_sound(AudioClip play_button_sound) {
		Sound.play_button_sound = play_button_sound;
	}

	/**
	 * @return the quit_button_sound
	 */
	public static AudioClip getQuit_button_sound() {
		return quit_button_sound;
	}

	/**
	 * @param quit_button_sound the quit_button_sound to set
	 */
	public static void setQuit_button_sound(AudioClip quit_button_sound) {
		Sound.quit_button_sound = quit_button_sound;
	}

	/**
	 * @return the settings_button_sound
	 */
	public static AudioClip getSettings_button_sound() {
		return settings_button_sound;
	}

	/**
	 * @param settings_button_sound the settings_button_sound to set
	 */
	public static void setSettings_button_sound(AudioClip settings_button_sound) {
		Sound.settings_button_sound = settings_button_sound;
	}

	/**
	 * @return the sound_ship_explosion
	 */
	public static AudioClip getSound_ship_explosion() {
		return sound_ship_explosion;
	}

	/**
	 * @param sound_ship_explosion the sound_ship_explosion to set
	 */
	public static void setSound_ship_explosion(AudioClip sound_ship_explosion) {
		Sound.sound_ship_explosion = sound_ship_explosion;
	}
}

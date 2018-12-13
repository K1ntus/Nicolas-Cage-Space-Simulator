package fr.groupe40.projet.client;

import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.media.AudioClip;

public class Music {

	/**
	 *  contains the main theme audio clip
	 */
	private AudioClip main_theme;

	/**
	 *  instanciate the music object, and launch or not the main_theme automatically
	 * @param launch_music Auto-launch main theme if true, else false
	 */
    public Music(boolean launch_music) {
    	this.main_theme = this.generateAudioClip(Resources.path_sound_main_theme, Resources.main_theme_volume);
		if(Constants.main_theme_enabled && launch_music)
    		main_theme.play();
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
     *  return an audioclip from a path to the file and his volume
     * @param path	path to the media file
     * @param volume	volume wanted of this sound (between 0 and 1, usually 0.5 is ok)
     * @return
     */
    public AudioClip generateAudioClip(String path, double volume) {
		try {
	    	AudioClip res = new AudioClip(this.getClass().getResource(path).toExternalForm());
	    	res.setVolume(volume);
			return res;
		} catch (NullPointerException e) {
			System.out.println("Unable to load explosion sounds of ships");
			return null;
		}   	
    	
    }
}

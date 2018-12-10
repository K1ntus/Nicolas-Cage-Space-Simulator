package fr.groupe40.projet.client;

import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.media.AudioClip;

public class Music {

	private AudioClip main_theme;

    public Music() {
    	this.main_theme = this.generateAudioClip(Resources.path_sound_main_theme, Resources.main_theme_volume);
		if(Constants.main_theme_enabled)
    		main_theme.play();
	}

    public void run() {
    	if(main_theme == null || !Constants.main_theme_enabled) { 
    		System.out.println("Unable to load main-theme music");
    		return; 
    	}
    	main_theme.setVolume(Resources.main_theme_volume);
    	if(main_theme.isPlaying()){  } else{ main_theme.play(); }
    }
    
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

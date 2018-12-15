package fr.groupe40.projet.util;

import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Resources;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.media.AudioClip;


/**
 * Offers many sounds tweaks and manage the background music
 * @author Jordane Masson
 * @author Sarah Portejoie
 */
public class SoundManager {

	/**
	 *  contains the main theme audio clip
	 */
	private AudioClip main_theme;

	/**
	 * Instanciate the music object, and launch or not the main_theme automatically
	 * @param launch_music Auto-launch main theme if true, else false
	 */
    public SoundManager(boolean launch_music) {
    	this.main_theme = SoundManager.generateAudioClip(Resources.path_sound_main_theme, Resources.main_theme_volume);
		if(Constants.main_theme_enabled && launch_music && this.main_theme != null)
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
    	
    	//main_theme.setVolume(Resources.main_theme_volume);
    	
    	if(main_theme.isPlaying()){
    		//DO nothing, music is already playing
    	} else if (main_theme.isPlaying() && !Constants.main_theme_enabled) {
    		System.out.println("stopping");
    		main_theme.stop();
    	} else {
   		 //Replay the music
    		System.out.println("replay");
    		main_theme.stop();
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
	    	AudioClip res = new AudioClip(ImageManager.getRessourcePathByName(path));
	    	res.setVolume(volume);
			return res;
		} catch (NullPointerException e) {
			System.out.println("Unable to load sounds: "+ path);
			return null;
		}   	
    	
    }

	/**
	 * Will contain the result of function 'getAudioByPath_dynamic'
	 */
    private static AudioClip result = null;

	/**
	 * Load an AudioClip using another processus
	 * @param path the sound path
	 * @param the volume of the AudioClip (Should be between 0 & 1, recommend 0.5 by default)
	 * @return the image loaded
	 */
	public static AudioClip getAudioByPath_dynamic(String path, double volume) {
		final Service<AudioClip> imageLoadingService = new Service<AudioClip>(){

			  @Override
			  protected Task<AudioClip> createTask() {
			    return new Task<AudioClip>(){

			     @Override
			     protected AudioClip call() throws Exception {
			 		return generateAudioClip(path, volume);
			      }
			    };
			  }
			};

			imageLoadingService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			    @Override
			    public void handle(WorkerStateEvent t) {
			        result = imageLoadingService.getValue();
			    }
			});
			imageLoadingService.start();
			if(result != null)
				return result;
			else
		 		return generateAudioClip(path, volume);
	}
}

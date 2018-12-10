package fr.groupe40.projet.client;

import javafx.scene.media.AudioClip;

public class Music {

	private AudioClip mainMusic;

    public Music(AudioClip music_mainTheme) {
    	this.mainMusic = music_mainTheme;
    	mainMusic.setVolume(0.25);
    	mainMusic.play();
	}

    public void run() {
    	if(mainMusic == null) { 
    		System.out.println("Unable to load main-theme music");
    		return; 
    	}
    	if(mainMusic.isPlaying()){  } else{ mainMusic.play(); }
    }
}

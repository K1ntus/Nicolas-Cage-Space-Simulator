package fr.groupe40.projet.util.constants;

import fr.groupe40.projet.util.annot.WorkInProgress;

public final class Windows {

	@WorkInProgress(comment="Give name to the human user, and make something like leaderboard ?")
    public static final String form_player_name = "Pseudo: ";
	
	/**
	 * String for the play button
	 */
    public static final String button_play = 		"    Jouer    ";
    
    /**
     * String for the apply button
     */
    public static final String button_apply = 		"  Appliquer ";
    
    /**
     * String for the option button
     */
    public static final String button_settings = 	"  Options  ";
    
    /**
     * String for the how_to_play button
     */
    public static final String button_how_to_play = 	"  Comment Jouer  ";
    
    /**
     * String for the quit button
     */
    public static final String button_close = 		"   Quitter  ";
    

    /**
     * The window currently displayed
     */
	public enum WindowType {
		MAIN_MENU,
		
		SETTINGS,
		
		GAME,
		
		@WorkInProgress(comment="Loading screen with gif or javafx progressbar ?")
		LOADING
	}

}

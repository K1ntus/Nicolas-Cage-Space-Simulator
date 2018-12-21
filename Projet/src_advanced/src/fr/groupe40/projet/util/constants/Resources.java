package fr.groupe40.projet.util.constants;

import fr.groupe40.projet.util.annot.WorkInProgress;

/**
 * Contains every path to resource file that is used
 * And the volume value for few audio
 */
public final class Resources {
	
	/*	Square Planets */
	public static final String path_img_square_basic = "images/planets/square/default.png";
	public static final String path_img_square_time_night = "images/planets/square/time_square_night.png";

	/* Round Planets	*/
	public static final String path_img_round_planet1 = "images/planets/rounds/1.png";
	public static final String path_img_round_planet2 = "images/planets/rounds/2.png";
	public static final String path_img_planet_human = "images/planets/special/earth.png";

	/*	Sun Image Path	*/
	public static final String path_img_sun = "images/sun.png";
	

	/*	******************	*/
	
	/* Ships images
	 * To be edited in 'Squads' class
	 */
	public static final String path_img_human_ships = "images/ships/human.gif";
	
	//public static final String path_img_event_ships = "images/ships/salt.png";
	public static final String path_img_event_ships = "images/ships/tenders.png";
	
	public static final String path_img_event_pirate_ships = "images/ships/pirate.png";
	public static final String path_img_AI_ships = "images/ships/ai.png";
	

	/*	******************	*/
	
	/* Background	*/
	public static final String path_img_game_background = "images/background.jpg";
	public static final String path_img_menu_background = "images/main_menu.gif";
	public static final String path_img_menu_title =  "images/title.png";


	/*	******************	*/
	
	public static final String path_sound_main_theme = "sounds/musics/main_theme.mp3";
	
	public static final String path_sound_explosion = "sounds/explosion.mp3";
	public static final String path_sound_sun_explosion = "sounds/sun_explosion.mp3";

	public static final String path_sound_play = "sounds/play_sound.mp3";
	public static final String path_sound_quit = "sounds/exit_sound.mp3";
	public static final String path_sound_settings = "sounds/settings_sound.mp3";
	

	/*	******************	*/
	
	//GUI
	@WorkInProgress
	public static final String path_img_gui_play_unselected = "images/gui/play_unselected.jpg";
	@WorkInProgress
	public static final String path_img_gui_settings_unselected = "images/gui/settings_unselected.jpg";
	@WorkInProgress
	public static final String path_img_gui_exit_unselected = "images/gui/exit_unselected.jpg";

	@WorkInProgress
	public static final String path_img_gui_play_selected = "images/gui/play_selected.jpg";
	@WorkInProgress
	public static final String path_img_gui_settings_selected = "images/gui/settings_selected.jpg";
	@WorkInProgress
	public static final String path_img_gui_exit_selected = "images/gui/exit_selected.jpg";
	

	/*	******************	*/
	
	//Explosion of the ship
	@Deprecated
	@WorkInProgress(comment="not working, gif getting overwrite each tick per the background rendering")
	public static final String path_gfx_ship_explosion = "images/fx/blast.gif";
	
	
	
	/*	******************	*/
	
	public static final double main_theme_volume = 0.1; //Between 0 (cut the music) and 1
	public static final double ship_explosion_volume = 0.5;
	public static final double sun_explosion_volume = 0.25;
}

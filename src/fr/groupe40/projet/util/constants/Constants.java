package fr.groupe40.projet.util.constants;

import javafx.scene.paint.Color;

/**
 * Contains many unclassed constants to manage this project
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public final class Constants {
	/**
	 * String message to send when the player lost the current game
	 * Put as constant to be easily changed for langage
	 */
	public static final String message_game_over = "You Lose !";

	/**
	 * String message to send when the player win the current game
	 * Put as constant to be easily changed for langage
	 */
	public static final String message_winner = "You Won !";
	

	/*	file */
	//public static final String path_save = "/saves/";
	/**
	 * Path for the save file, currently in the local eclipse workspace/jar position
	 */
	public static final String path_save = "";
	
	/**
	 * The save name without the extension
	 */
	public static final String fileName_save = "01";
	
	/* Event */
	/**
	 * The troups decrementing value when a planet
	 * got the sickness event
	 */
	public static final int planet_sickness_value = 2;
	
	/**
	 * The maximum numbers of pirate ships that appears per event
	 */
	public static final int pirate_assault_max_nb_ships = 10;

	/**
	 * If we're enabling event
	 */
	public static final boolean events_enabled = true;
	
	/**
	 * If a sun is enabled
	 */
	public static final boolean sun_enabled = true;
	
	/**
	 * Enable main_theme music + auto-play at startup
	 */
	public static boolean main_theme_enabled = true;
	
	/* AI */
	public static final boolean ai_enabled = true;

	/*	Color	*/
	/**
	 * Planet color for the neutral user
	 */
	public static final Color color_neutral = Color.DARKGRAY;

	/**
	 * Planet color for the human
	 */
	public static final Color color_player = Color.LIGHTSEAGREEN;

	/**
	 * Planet color for the ai
	 */
	public static final Color color_ai = Color.RED;

	/**
	 * Default color (unused)
	 */
	public static final Color color_default = Color.WHITE;
	
}

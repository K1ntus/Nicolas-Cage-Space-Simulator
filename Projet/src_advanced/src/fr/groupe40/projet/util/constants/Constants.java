package fr.groupe40.projet.util.constants;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.util.annot.TODO;
import javafx.scene.paint.Color;

/**
 * Contains many unclassed constants to manage this project
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public final class Constants {
	public static Difficulty difficulty = Difficulty.INITIE;

	/*	Window Parameters	*/
	
	/**
	 * Window width
	 */
	public static final int width = 800;
	
	/**
	 * Window height
	 */
	public static final int height = 600;
	
	//Four margins
	public static final double top_margin_size = 50;
	public static final double left_margin_size = 50;
	public static final double bottom_margin_size = 50;
	public static final double right_margin_size = 50;

	/**
	 * Maximum numbers of planets
	 */
	public static int nb_planets_tentatives = 10;	//<-> max planets available	//Exponential complexity | Usually 10 is ok
	
	/**
	 * Minimal number of planets to be a valid game
	 */
	public static final int min_numbers_of_planets = 3;	//2 + sun
	
	/**
	 * Minimal distance between two planets in Pixels
	 */
	public static final int minimal_distance_between_planets = 150;	//Distance in pixels required between each planets

	
	
	//Nb of ai squads generated from the beginning
	//Currently for debugging and not working anymore
	//public static final int nb_squads = 50;

	/*	Graphics	*/
	
	/**
	 * Size of a ship in pixels
	 */
	public static double size_squads = 10.0;	//size in pixels of a "squad"
	
	/**
	 * Minimal size of planet in pixel
	 */
	public static double size_minimal_planets = 5*size_squads;	//minimal size of a planet in pixel ((4*size squad)
	
	/**
	 * Maximal size of planet
	 */
	public static double size_maximal_planets = 8*size_squads;
	
	/**
	 * Size of the sun
	 */
	public static double size_sun = 10*size_squads;
	
	/**
	 * Enable some print to be done to check informations
	 * Enabling it display the time used for each game board initialization
	 * Display which planet you have selected, ...
	 */
	public static final boolean DEBUG = false;
	public static final boolean DEBUG_TIMER = true;
	

	/**
	 * Maximal troop available when the game got initialized
	 */
	public static final int max_initDefense = 15;//The initial troops in a planet at the Constants of the world
	
	/**
	 * Minimal number of troups before getting ruled
	 */
	public static final int min_troups = 1;	//minimal troops in a planet
	
	/**
	 * Maximal number of troops available in a planet
	 */
	@TODO(comment="Make it variable a constant * ratio (with ratio depending of the planet width or something like that ?")
	public static final int max_troups = 100;	//minimal troops in a planet
	
	/**
	 * The troop of the sun 'planet'
	 */
	public static final int sun_troups = 100;
	


	/**
	 * Constants about players faction
	 */
	public static final int neutral_faction = -1, human_faction = 0, ai_faction = 1;

	/**
	 * Pirate id
	 */
	public static final int pirate_id = -666;
	/**
	 * event ships id
	 */
	public static final int event_id = -2;
	
	public static final int neutral_id = -1;
	
	/**
	 * sun id
	 */
	public static final int sun_id = -667;
	
	public static final int human_id = 0;

	/**
	 * Neutral user constant
	 */
	public static final User neutral_user = new User(neutral_faction, neutral_id);
	
	/**
	 * ai user constant, should be managed with collection 
	 * to handle multiple ai players
	 */
	public static final User ai_user = new User(ai_faction, 1);
	
	/**
	 * Human user constant
	 */
	public static final User human_user = new User(human_faction, human_id);
	
	public static final User pirate_user = new User(neutral_faction, pirate_id);
	public static final User event_user = new User(neutral_faction, event_id);
	public static final User sun_user = new User(neutral_faction, sun_id);
	

	
	/**
	 * The minimal ship speed in pixel, editable in the setting menu
	 */
	public static double min_ship_speed = 3;
	
	/**
	 * The maximal ship speed in pixel, editable in the setting menu
	 */
	public static double max_ship_speed = 5;
	
	/**
	 * The minimal ship power (n-power = n-fleet damage)
	 */
	public static final double min_ship_power = 1;
	/**
	 * The maximal ship power (n-power = n-fleet damage)
	 */
	public static final double max_ship_power = 2;
	
	/**
	 * Minimal number of ship produce per waves
	 */
	public static final double min_ship_produce = 1;	
	
	/**
	 * Maximal number of ship produce per waves
	 */
	public static final double max_ship_produce = 4;


	
	/**
	 * The ticks needed to produce troops of each planets
	 */
	public static final long tick_per_produce = 40;
	
	/**
	 * The ticks between each fleet lift-off
	 */
	public static final long tick_per_lift_off = 20;
	
	/**
	 * The ticks between each ai-attack check
	 */
	public static final long tick_per_ai_attack = 91;
	
	/**
	 * Tick between each ship move on board
	 */
	public static final long tick_per_squad_position_update = 1;	//original is 1, higher for better performance
	
	/**
	 * Tick between each event caster
	 */
	public static final long tick_per_events = 249;
	
	/**
	 * Tick between each check if the main theme is playing
	 */
	public static final long tick_per_main_theme_check = 500;
	
	
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
	public static final String path_save = "./saves";
	
	/**
	 * The save extension
	 */
	public static final String fileName_extension = "save";
	
	/* Event */
	/**
	 * The troops decrementing value when a planet
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
	public static final boolean main_theme_enabled = true;
	
	/* AI */
	public static final int ai_number = 5;
	
	/**
	 * Enable/Disable AI
	 */
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

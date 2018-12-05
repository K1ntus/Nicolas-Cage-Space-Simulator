package fr.groupe40.projet.util.constants;

import fr.groupe40.projet.client.User;
import javafx.scene.paint.Color;

/**
 * \brief contains every constants to manage this project
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public final class Constants {
	/**Deprecated	*/
	@Deprecated
	public static final String name_player2 = "IA 1";
	@Deprecated
	public static final double PI = 3.1415926535898;
	@Deprecated
	public static final boolean limit_ai_squads_number = true;	//If ai should have a limited number of squads at the same time
	@Deprecated
	public static final int max_squads_for_ai = 2;	//max number of squads from an ai player at the same time. -1 to disable this limit
	
	/*	Window Parameters	*/
	//Window height & width
	public static final int width = 800;
	public static final int height = 600;
	
	//Four margins
	public static final double top_margin_size = 50;
	public static final double left_margin_size = 50;
	public static final double bottom_margin_size = 50;
	public static final double right_margin_size = 50;

	/*	Generation parameters */
	public static final int nb_planets_tentatives = 5;	//<-> max planets available	//Exponential complexity
	public static final int min_numbers_of_planets = 2;
	public static final int minimal_distance_between_planets = 150;	//Distance in pixels required between each planets
	
	//Nb of ai squads generated from the beginning
	//Currently for debugging and not working anymore
	//public static final int nb_squads = 50;
	
	/*	Graphics	*/
	public static final double size_squads = 15.0;	//size in pixels of a "squad"
	public static final double size_minimal_planets = 5*15.0;	//minimal size of a planet in pixel ((4*size squad)
	public static final double size_maximal_planets = 8*15.0;
	public static final double size_sun = 10*15.0;
	public static final String message_game_over = "Vous avez perdu !";
		
	/*	Ships Options */
	public static final double min_ship_speed = 3;
	public static final double min_ship_power = 1;
	public static final double min_ship_produce = 1;
	
	public static final double max_ship_speed = 5;
	public static final double max_ship_power = 2;
	public static final double max_ship_produce = 4;
		
	/*	PLAYERS	*/
	//public static final int max_number_of_ai = 1;
	public static final int neutral = 0, player = 1, ai = -1;
	
	public static final User neutral_user = new User(neutral, 0);
	public static final User event_user = new User(neutral, -2);
	public static final User ai_user = new User(ai, -1);
	public static final User human_user = new User(player, 1);
	public static final User sun_user = new User(neutral, 0);
	
		
	/*	Defense & Production */
	//~40 ticks = 1 second
	public static final long tick_per_produce = 40;
	public static final long tick_per_lift_off = 50;
	public static final long tick_per_ai_attack = 91;
	public static final long tick_per_squad_position_update = 1;	//original is 1, higher for better performance
	public static final long tick_per_events = 249;
	public static final long tick_before_counter_reset = 250;
	
	public static final int max_initDefense = 15;//The initial troups in a planet at the generation of the world
	public static final int min_troups = 1;	//minimal troups in a planet
	public static final int max_troups = 100;	//minimal troups in a planet
	public static final int sun_troups = 20;
			
	//4 ship types, attributed to a specific amount of ships
	/*	Type of Squads */
	public static final int size_typeShips1 = 1;	
	public static final int size_typeShips2 = 5;
	public static final int size_typeShips3 = 25;
	public static final int size_typeShips4 = 50;
	
	
	/*	Color	*/
	public static final Color color_neutral = Color.DARKGRAY;
	public static final Color color_player = Color.LIGHTSEAGREEN;
	public static final Color color_ai = Color.RED;
	public static final Color color_default = Color.WHITE;

	/*	file */
	//public static final String path_save = "/saves/";
	public static final String path_save = "";
	public static final String fileName_save = "01";
	

	public static final String path_img_square_basic = "/resources/images/planets/square/default.png";
	public static final String path_img_square_nicolas_cage = "/resources/images/planets/square/nicolas_cage.png";

	public static final String path_img_round_planet1 = "/resources/images/planets/rounds/1.png";
	public static final String path_img_round_planet2 = "/resources/images/planets/rounds/2.png";
	public static final String path_img_round_doge = "/resources/images/planets/rounds/doge.jpg";
	public static final String path_img_round_kfc_planet = "/resources/images/planets/rounds/kfc.png";

	public static final String path_img_sun = "/resources/images/sun.png";
	
	
	//To edit in Squads object
	public static final String path_img_human_ships = "/resources/images/ships/human.gif";
	public static final String path_img_event_ships = "/resources/images/ships/salt.png";
	//public static final String path_img_event_ships = "/resources/images/ships/tenders.png";
	public static final String path_img_AI_ships = "/resources/images/ships/nicolas_cage.png";
	//public static final String path_img_NCage_ships = "/resources/images/ships/nicolas_cage.png";
	//public static final String path_img_AI_ships = "/resources/images/ships/ai.png";

	public static final String path_gfx_ship_explosion = "/resources/images/fx/blast.gif";

	public static final String path_img_background = "/resources/images/background.jpg";
	
	public static final String path_sound_explosion = "/resources/sounds/Engine.mp4";
	public static final String path_sound_aa = "/resources/sounds/aa.mp4";
	
	public static final String path_img_gui_percent_background = "/resources/images/gui/background_progressbar.png";
	public static final String path_img_gui_percent = "/resources/images/gui/progress_bar.png";
	public static final String path_img_gui_logo = "/resources/images/gui/logo.png";


	/*	 Error & Debugging	*/
	public static final int error_greater_x = 2;
	public static final int error_greater_y = 1;
	public static final int error_lower_x = -1;
	public static final int error_lower_y = -2;

	
	public static final boolean DEBUG = false;
	public static final boolean DEBUG_TROUPS = false;
	
	public static final boolean events_enabled = true;
	public static final boolean ai_enabled = true;
	public static final boolean sun_enabled = true;
	
}

package fr.projet.groupe40.util;

import fr.projet.groupe40.client.User;
import javafx.scene.paint.Color;

public final class Constantes {
	/** Deprecated	**/
	public static final String name_player2 = "IA 1";
	public static final double PI = 3.1415926535898;
	
	/**	Window Parameters	**/
	public static final int height = 600;
	public static final int width = 800;
	public static final double top_margin_size = 50;
	public static final double left_margin_size = 50;

	/**	Generation parameters **/
	public static final int nb_planets_tentatives = 3;
	public static final int minimal_distance_between_planets = 150;
	public static final int nb_squads = 5;
	
	/**	Graphics	**/
	public static final double size_squads = 15.0;	//size in pixels of a "squad"
	public static final double size_minimal_planets = 4*15.0;	//minimal size of a planet in pixel ((4*size squad)
	public static final double size_maximal_planets = 6*15.0;
	public static final String message_game_over = "Vous avez perdu !";
		
	/**	Ships Options **/
	public static final double min_ship_speed = 1;
	public static final double min_ship_power = 3;
	public static final double min_ship_produce = 1;
	
	public static final double max_ship_speed = 2;
	public static final double max_ship_power = 7;
	public static final double max_ship_produce = 4;
		
	/**	PLAYERS	**/
	public static final int max_number_of_ai = 1;//Type of a planet
	public static final int neutral = 0, player = 1, ai = -1;//Type of a planet
	public static final User neutral_user = new User(neutral, 0);
	public static final User ai_user = new User(ai, -1);
	public static final User human_user = new User(player, 1);
		
	/**	Defense & Production **/
	public static final long ms_per_produce = 1000;
	public static final int max_initDefense = 15;//The initial troups in a planet at the generation of the world
	public static final int min_troups = 1;	//minimal troups in a planet
	public static final int max_troups = 100;	//minimal troups in a planet
			
	//4 ship types, attributed to a specific amount of ships
	/**	Type of Squads **/
	public static final int size_typeShips1 = 1;	
	public static final int size_typeShips2 = 5;
	public static final int size_typeShips3 = 25;
	public static final int size_typeShips4 = 50;
	
	
	/**	Color	**/
	public static final Color color_neutral = Color.DARKGRAY;
	public static final Color color_player = Color.LIGHTSEAGREEN;
	public static final Color color_ai = Color.RED;
	public static final Color color_default = Color.WHITE;

	/**	file	**/
	public static final String path_img_planets = "/resources/images/square.png";
	public static final String path_img_ships = "/resources/images/alien.png";
	public static final String path_img_background = "/resources/images/background.jpg";
	public static final String path_sound_explosion = "/resources/sounds/Engine.mp4";
	public static final String path_sound_aa = "/resources/sounds/aa.mp4";

	/**	 Error & Debugging	**/
	public static final int error_greater_x = 2;
	public static final int error_greater_y = 1;
	public static final int error_lower_x = -1;
	public static final int error_lower_y = -2;

	public static final boolean DEBUG = false;
	public static final boolean DEBUG_TROUPS = false;
	
	
}

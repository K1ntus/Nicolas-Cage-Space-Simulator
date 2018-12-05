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
	
	
	public static final String message_game_over = "Vous avez perdu !";

	//4 ship types, attributed to a specific amount of ships
	/*	Type of Squads */
	@Deprecated
	public static final int size_typeShips1 = 1;	
	@Deprecated
	public static final int size_typeShips2 = 5;
	@Deprecated
	public static final int size_typeShips3 = 25;
	@Deprecated
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
	

	public static final boolean events_enabled = true;
	public static final boolean ai_enabled = true;
	public static final boolean sun_enabled = true;
	
}

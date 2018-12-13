package fr.groupe40.projet.util.constants;

public final class Windows {

    public static final String form_player_name = "Pseudo: ";
    public static final String button_play = 		"    Jouer    ";
    public static final String button_apply = 		"  Appliquer ";
    public static final String button_settings = 	"  Options  ";
    public static final String button_close = 		"   Quitter  ";
    

	public enum WindowType {
		MAIN_MENU,	//No event invoked
		SETTINGS,		//Randomly generated hostile fleet
		GAME,		//Fleet automatically lift-off from a planet and attack one of the same user
		LOADING	//Planet stop producing for a while (add smoke/poison particles ?)
	}

}

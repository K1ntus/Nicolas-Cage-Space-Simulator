package fr.groupe40.projet.client.handler;

import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Constants;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * \brief Manage the keyboard events
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
@Deprecated
public class KeyboardListener {
	private Galaxy galaxy;
	private Scene scene;
	private DataSerializer saver;
	private boolean new_game_loaded = false;

	/**
	 * 
	 * @param galaxy the game board
	 * @param scene the scene to link the handler
	 * @param saver the object managing game saving/loading
	 */
	public KeyboardListener(Galaxy galaxy, Scene scene, DataSerializer saver) {
		this.galaxy = galaxy;
		this.scene = scene;
		this.saver = saver;
	}



	/**
	 * \brief Manage the saver/loader
	 */
	public void launch() {
	}



	public boolean isNew_game_loaded() {
		return new_game_loaded;
	}



	public void setNew_game_loaded(boolean new_game_loaded) {
		this.new_game_loaded = new_game_loaded;
	}

}

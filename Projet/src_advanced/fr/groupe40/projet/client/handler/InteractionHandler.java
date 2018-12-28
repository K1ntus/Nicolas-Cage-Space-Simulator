package fr.groupe40.projet.client.handler;


import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.util.annot.TODO;
import javafx.scene.Scene;

/**
 * Manage the keyboard/mouse interactions with the user
 * Currently, only the mouse is managed because of 
 * a minor heritage problem with saving/loading
 */
@TODO(comment="implement the keyboard handler there")
public class InteractionHandler {
	/**
	 *  will manage the mouse event
	 */
	private MouseListener mouse;
	
	/**
	 *  constructor for the user input
	 * @param galaxy
	 */
	public InteractionHandler(Galaxy galaxy, Scene scene, DataSerializer saver) {
		this.mouse = new MouseListener(galaxy, scene);
		
	}
	
	/**
	 *  launch the mouse & keyboard handler
	 */
	public void exec() {
		mouse.launch();		
	}
}

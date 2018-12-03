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
public class KeyboardListener {
	private Galaxy galaxy;
	private Scene scene;
	private DataSerializer saver;

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
	public void start() {
		EventHandler<KeyEvent> keyboardHandler = new EventHandler<KeyEvent>() {
	
			@Override
			public void handle(KeyEvent e) {
					
				if(e.getCode() == KeyCode.SPACE) {
					System.out.println("* button space pressed");
						
					for(Planet p : galaxy.getPlanets()) {
						System.out.println("*planet boucle:" +p.toString());
						System.out.println("** ruler: " + p.getRuler().toString());
	
						if(p.getRuler().equals(Constants.human_user)){
							System.out.println("*** ruler well selected");
							Planet source = p.getRuler().getSource();
							Planet destination = p.getRuler().getDestination();
								
							if(destination != null && source != null) {
								Squad s = new Squad(Constants.human_user.getPercent_of_troups_to_send(), source, destination);
								//s.sendFleet(source, destination, Constants.human_user.getPercent_of_troups_to_send());
								
								galaxy.getSquads().add(s);
									
									
							}
						} else {
							p.getRuler().setDestination(null);
						}
					}
	
				}
					
				if (e.getCode() == KeyCode.F5) {
					System.out.println("Saving game ...");
					//OPEN POPUP ?
					saver.save_game();
					
								
				}
					
				if (e.getCode() == KeyCode.F6) {

					System.out.println("Loading game ...");
					galaxy = saver.load_game();
					saver.reload_image_and_data(galaxy);
					
					//interactionHandler = new InteractionHandler(galaxy);
				}
				
			}
		};
    scene.setOnKeyPressed(keyboardHandler);
	}

}

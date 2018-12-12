package fr.groupe40.projet.events;

import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Ship;
import fr.groupe40.projet.model.ships.ShipType;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * \brief squads from random/all colonies are removed, becoming aggressive
 * @author Jordane Masson
 * @author Sarah Portejoie
 */
final class PirateAssault {	
	
	/**
	 * \brief currently not working, because only the first frame of the gif is displayed, the main board refreshing each tick.
	 * @param x the x position on board
	 * @param y the y position on board
	 * @param gc the graphics context to display it
	 */
	private static void renderSummoning(double x, double y, GraphicsContext gc) {
		Image portal = new Image(Resources.path_gfx_ship_explosion, Generation.size_squads, Generation.size_squads, false, false, true);

		gc.drawImage(portal, x, y);
		
	}
	
	/**
	 * \brief run the pirate assault event
	 * @param galaxy the board that will invoke this event
	 * @param gc the graphics environnement
	 */
	protected static void start(Galaxy galaxy, GraphicsContext gc) {
		
		Squad pirate_squad = new Squad();
		for(int i = 0; i < Constants.pirate_assault_max_nb_ships; i++) {
			double x = (Math.random() * (Generation.width - Generation.size_squads));
			double y = (Math.random() * (Generation.height - Generation.size_squads));
			
			Planet destination = galaxy.getPlanets().get((int) (galaxy.getPlanets().size() * Math.random()));
			
			Ship pirate_ship = new Ship(Resources.path_img_event_pirate_ships, Players.pirate_user, destination, null, x, y, new ShipType());
			boolean summon = true;
			pirate_ship.validatePosition();
			for(Planet p: galaxy.getPlanets()) {
				if(pirate_ship.isInside(p)) {
					summon = false;
					break;
				}
			}
			
			if(summon) {
				x = pirate_ship.getX();
				y = pirate_ship.getY();
				
				renderSummoning(x, y, gc);
				
				pirate_squad.getShips().add(pirate_ship);				
			}
			
		}
		
		galaxy.getSquads().add(pirate_squad);
	}

}

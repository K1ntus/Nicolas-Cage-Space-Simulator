package fr.groupe40.projet.events;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Debugging;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.constants.Resources;

/**
 *  squads from random/all colonies are removed, becoming aggressive and attack one of his planet
 * @author Jordane Masson
 * @author Sarah Portejoie
 */
final class Revolt {

	/**
	 *  begin the revolt event
	 * @param galaxy
	 */
	protected static void start(Galaxy galaxy) {
		boolean ai = false, user = false;
		for(Planet src: galaxy.getPlanets()) {
			User u = src.getRuler();

			if(u.equals(Players.human_user) && !user) {
				user = true;
				for(Planet dest: galaxy.getPlanets()) {
					int id1 = dest.getRuler().getId();
					int id2 = u.getId();
					if(!src.equals(dest) && (id1 == id2)) {
						launch_fleet(galaxy, src, dest);

						if(Debugging.DEBUG)
							System.out.println("One of your fleet revolt against you");
						
						break;
					}
				}
			}
			
			if(Constants.ai_enabled)
				if(u.equals(Players.ai_user) && !ai) {
					ai = true;
					
					for(Planet dest: galaxy.getPlanets()) {
						int id1 = dest.getRuler().getId();
						int id2 = u.getId();
						if(!src.equals(dest) && (id1 == id2)) {
							launch_fleet(galaxy, src, dest);
							
							if(Debugging.DEBUG)
								System.out.println("An AI fleet revolt against his ruler");
							
							break;
						}
					}
				}
				if(ai && user)
					break;
		}
	}
	
	/**
	 *  launch a fleet between two planet of the board
	 * @param galaxy the game board
	 * @param src the source planet
	 * @param dest the destination planet
	 */
	private static void launch_fleet(Galaxy galaxy, Planet src, Planet dest) {
		Squad s = new Squad(Resources.path_img_event_ships, Players.event_user, 25, src, dest);		
		
		galaxy.getSquads().add(s);		
	}

}

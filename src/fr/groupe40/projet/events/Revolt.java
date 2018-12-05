package fr.groupe40.projet.events;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Ship;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Constants;

/**
 * \brief squads from random/all colonies are removed, becoming aggressive and attack one of his planet
 * @author Jordane Masson
 * @author Sarah Portejoie
 */
final class Revolt {

	protected static void start(Galaxy galaxy) {
		boolean ai = false, user = false;
		for(Planet src: galaxy.getPlanets()) {
			User u = src.getRuler();

			if(u.equals(Constants.human_user) && !user) {
				user = true;
				System.out.println("revolt event begun1");
				for(Planet dest: galaxy.getPlanets()) {
					int id1 = dest.getRuler().getId();
					int id2 = u.getId();
					if(!src.equals(dest) && (id1 == id2)) {
						launch_fleet(galaxy, src, dest);
						
						System.out.println("revolt event begun2");
						break;
					}
				}
			}
			
			if(Constants.ai_enabled)
				if(u.equals(Constants.ai_user) && !ai) {
					ai = true;
					
					System.out.println("revolt event begun1");
					for(Planet dest: galaxy.getPlanets()) {
						int id1 = dest.getRuler().getId();
						int id2 = u.getId();
						if(!src.equals(dest) && (id1 == id2)) {
							launch_fleet(galaxy, src, dest);
							
							System.out.println("revolt event begun2");
							break;
						}
					}
				}
				if(ai && user)
					break;
		}
	}
	
	private static void launch_fleet(Galaxy galaxy, Planet src, Planet dest) {
		Squad s = new Squad(Constants.path_img_event_ships, Constants.event_user, 25, src, dest);		
		
		galaxy.getSquads().add(s);		
	}

}

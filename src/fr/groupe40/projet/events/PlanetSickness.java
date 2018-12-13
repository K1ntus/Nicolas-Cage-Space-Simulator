package fr.groupe40.projet.events;

import java.util.ArrayList;
import java.util.Iterator;

import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Debugging;
import fr.groupe40.projet.util.constants.Players;

/**
 *  planet see his production lowered by a constant
 * @author Jordane Masson
 * @author Sarah Portejoie
 */
public class PlanetSickness {
	/**
	 *  contains the list of planet affected by sickness
	 */
	private ArrayList<Planet> planet_sickness = new ArrayList<Planet>();
	
	/**
	 *  is this event already running or not to prevent multiple cast of it
	 */
	private boolean running = false;

	/**
	 *  begin the sickness event in the board
	 * @param galaxy board affected by planet sickness
	 */
	protected void start(Galaxy galaxy) {
		this.running = true;
		
		for(Planet p: galaxy.getPlanets()) {
			if(Math.random() > 0.5) {
				if(p.getProduce_rate() > Constants.planet_sickness_value) {
					addSickness(p);
					planet_sickness.add(p);		
					
					if(Debugging.DEBUG)
						if(p.getRuler().equals(Players.human_user))
							System.out.println("One of your planet has been contaminated. Lowering his production by: " + Constants.planet_sickness_value);
				}
			}
			
		}
		

	}

	private void addSickness(Planet p) {
		p.setProduce_rate(p.getProduce_rate() - Constants.planet_sickness_value);
		
	}
	private void removeSickness(Planet p) {
		p.setProduce_rate(p.getProduce_rate() + Constants.planet_sickness_value);
		// TODO Auto-generated method stub
		
	}
	
	/**
	 *  stop the planet sickness events for every currently affected planets
	 */
	public void stop() {
		Iterator<Planet> it = planet_sickness.iterator();
		while (it.hasNext()) {
			Planet p = it.next();
			removeSickness(p);
			it.remove();
		}
		this.running = false;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
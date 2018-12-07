package fr.groupe40.projet.events;

import java.util.ArrayList;
import java.util.Iterator;

import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Debugging;
import fr.groupe40.projet.util.constants.Players;

/**
 * \brief planet see his production lowered by a constant
 * @author Jordane Masson
 * @author Sarah Portejoie
 */
public class PlanetSickness {
	private ArrayList<Planet> planet_sickness = new ArrayList<Planet>();
	private boolean running = false;

	protected void start(Galaxy galaxy) {
		setRunning(true);
		
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
	
	public void stop() {
		Iterator<Planet> it = planet_sickness.iterator();
		while (it.hasNext()) {
			Planet p = it.next();
			removeSickness(p);
			it.remove();
		}
		setRunning(false);
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
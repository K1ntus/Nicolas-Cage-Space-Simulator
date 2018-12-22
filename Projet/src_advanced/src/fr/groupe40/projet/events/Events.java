package fr.groupe40.projet.events;

import java.util.ArrayList;
import java.util.Random;

import fr.groupe40.projet.model.board.Galaxy;
import javafx.scene.canvas.GraphicsContext;

/**
 *  squads from random/all colonies are removed, becoming aggressive
 * @author Jordane Masson
 * @author Sarah Portejoie
 */
public class Events {
	
	private static Events instance = null;
	
	public static Events getInstance(Galaxy galaxy, GraphicsContext gc, boolean pirate, boolean revolt) {
		if(instance == null)
			instance = new Events(galaxy, gc, pirate, revolt);
		return instance;
	}
	
	/**
	 *  contains every available event
	 * @author jordan
	 *
	 */
	private enum events {
		NOTHING,	//No event invoked
		PIRATE,		//Randomly generated hostile fleet
		REVOLT,		//Fleet automatically lift-off from a planet and attack one of the same user
		SICKNESS	//Planet stop producing for a while (add smoke/poison particles ?)
	}

	/**
	 *  array list containing every enabled events from the enum type 'events'
	 */
	private ArrayList<events> event_available = new ArrayList<events>();
	
	/**
	 *  main board to summon/invoke the events on it
	 */
	private Galaxy galaxy;
	
	/**
	 * graphic environnement to display few events
	 */
	private GraphicsContext gc;
	
	/**
	 *  special type needed to manage this event, handle the current planets suffering of sickness, etc
	 */
	private PlanetSickness planet_sickness= new PlanetSickness();
	
	/**
	 *  choose which events are enabled/disabled
	 * @param pirate
	 * @param revolt
	 */
	private Events(Galaxy galaxy, GraphicsContext gc, boolean pirate, boolean revolt) {
		this.galaxy = galaxy;
		this.gc = gc;
		
		event_available.add(events.NOTHING);
		if(pirate)
			event_available.add(events.PIRATE);
		if(revolt)
			event_available.add(events.REVOLT);
	}


	/**
	 *  Pick a random event in the event list and return it
	 * @param event array containing the events
	 * @return	an event
	 */
	private static events getRandomEvent(ArrayList<events> event) {
	    int rnd = new Random().nextInt(event.size());
	    return event.get(rnd);
	}


	/**
	 *  Randomly pick an event and handle it
	 */
	public void event_randomizer() {
		if(event_available.size() == 1)
			return;
		
		if(planet_sickness.isRunning()) {
			planet_sickness.stop();
		}

		switch(getRandomEvent(event_available)) {
			case PIRATE:
				//System.out.println("** pirate assault");
				PirateAssault.start(galaxy, gc);
				break;
			case REVOLT:
				//System.out.println("** planet revolt");
				Revolt.start(galaxy);
				break;
			case SICKNESS:
				//System.out.println("** sickness start **");
				planet_sickness.start(galaxy);
			case NOTHING:
				//System.out.println("*** no event");
				break;
		}

	}
}

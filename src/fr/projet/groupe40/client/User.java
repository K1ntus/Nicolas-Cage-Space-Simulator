package fr.projet.groupe40.client;

import java.io.Serializable;

import fr.projet.groupe40.model.board.Planet;
import fr.projet.groupe40.model.ships.Squad;
import fr.projet.groupe40.util.Constantes;
import javafx.scene.canvas.GraphicsContext;

public class User implements Serializable{
	private static final long serialVersionUID = 8499986523207461968L;
	/**
	 * 
	 */
	
	private int faction;
	private int id;
	
	private int percent_of_troups_to_send;
	
	private Planet source,destination;
	
	private boolean lost;

	public User(int faction, int id) {
		this.faction = faction;
		this.id = id;
		setLost(false);
		percent_of_troups_to_send = 100;
	}
	public User(int faction) {
		this.faction = faction;
		
		switch(faction) {
		case Constantes.ai:
			id = Constantes.ai; break;
		case Constantes.neutral:
			id = Constantes.neutral; break;
		case Constantes.player:
			id = Constantes.player; break;
		}
		percent_of_troups_to_send = 100;

		setLost(false);
		source = null;
		destination = null;
	}

	public User(User user) {
		this.id = user.id;
		this.faction = user.faction;
		setLost(false);
		percent_of_troups_to_send = 100;
	}
	/** AI handler **/

	public Squad sendFleetAI(Planet source, Planet destination) {
		User u_dest = destination.getRuler();
		int troups_destination = destination.getTroups();
		
		if(u_dest.id == id) {
			return null;
		}
		if(troups_destination < source.getTroups()) {
			Squad s = source.sendFleet(destination, 50);	//Ai will send 50% of his troups
			return s;
		}
		
		return null;
	}
	
	public boolean hasLost() {
		//TODO
		return false;
	}
	
	public void renderWhenDefeat(GraphicsContext gc) {
		//TODO
	}
	
	public int getFaction() {
		return faction;
	}

	public void setFaction(int faction) {
		this.faction = faction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the percent_of_troups_to_send
	 */
	public int getPercent_of_troups_to_send() {
		return percent_of_troups_to_send;
	}
	/**
	 * @param percent_of_troups_to_send the percent_of_troups_to_send to set
	 */
	public void setPercent_of_troups_to_send(int percent_of_troups_to_send) {
		if(this.percent_of_troups_to_send <100 || this.percent_of_troups_to_send > 0) {
			this.percent_of_troups_to_send = percent_of_troups_to_send;				
		}else {
			//nothing
		}
		if(this.percent_of_troups_to_send >100) {
			this.percent_of_troups_to_send = 100;
		}
		if(this.percent_of_troups_to_send <0) {
			this.percent_of_troups_to_send = 0;
		}
	}
	/**
	 * @return the destination
	 */
	public Planet getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Planet destination) {
		this.destination = destination;
	}
	/**
	 * @return the source
	 */
	public Planet getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(Planet source) {
		this.source = source;
	}
	public boolean isLost() {
		return lost;
	}
	public void setLost(boolean lost) {
		this.lost = lost;
	}

}

package fr.projet.groupe40.client;

import java.io.Serializable;

import fr.projet.groupe40.model.board.Planet;
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

	public User(int faction, int id) {
		this.faction = faction;
		this.id = id;
		setPercent_of_troups_to_send(100);
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
		setPercent_of_troups_to_send(100);

	}

	public User(User user) {
		this.id = user.id;
		this.faction = user.faction;
		setPercent_of_troups_to_send(100);
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

}

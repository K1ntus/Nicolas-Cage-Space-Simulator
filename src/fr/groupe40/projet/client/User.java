package fr.groupe40.projet.client;

import java.io.Serializable;

import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Players;

/**
 * 
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = 8499986523207461968L;
	/**
	 * 
	 */
	
	/**
	 *  the faction of this user (-1 => AI, 0 => neutral, >1 => humain)
	 */
	private int faction;
	
	/**
	 *  the id of this user
	 */
	private int id;
	
	/**
	 *  percentage of troups to send per planet-launch
	 */
	private int percent_of_troups_to_send;
	
	/**
	 *  his last fleet travel
	 */
	private Planet source,destination;
	
	/**
	 *  this user has lost or not
	 */
	private boolean lost;
	
	/**
	 *  Create a new user from a faction and an id
	 * @param faction the faction of this user
	 * @param id the id of this user
	 */
	public User(int faction, int id) {
		this.faction = faction;
		this.id = id;
		setLost(false);
		percent_of_troups_to_send = 100;
	}
	
	/**
	 *  Create a user only from his id
	 * @param faction the faction of this player
	 */
	public User(int faction) {
		this.faction = faction;
		
		switch(faction) {
		case Players.ai_faction:
			id = Players.ai_faction; break;
		case Players.neutral_faction:
			id = Players.neutral_faction; break;
		case Players.human_faction:
			id = Players.human_faction; break;
		}
		percent_of_troups_to_send = 100;

		setLost(false);
		source = null;
		destination = null;
	}

	/**
	 *  create an user from an user
	 * @param user to copy parameters from
	 */
	public User(User user) {
		this.id = user.id;
		this.faction = user.faction;
		this.lost = user.lost;
		this.percent_of_troups_to_send = user.percent_of_troups_to_send;
	}
	
	/* AI handler */
	/**
	 *  send fleet automatization for ai user
	 * @param source the source planet
	 * @param destination the destination planet
	 * @return the squad that has been send, else null
	 */
	public Squad sendFleetAI(Planet source, Planet destination) {
		User u_dest = destination.getRuler();
		int troups_destination = destination.getTroups();
		
		if(u_dest.id == id) {
			return null;
		}
		if(troups_destination < source.getTroups()) {
			Squad s = new Squad(50, source, destination);
			return s;
		}
		
		return null;
	}
	
	
	/**
	 *  return the faction of an user
	 * @return faction
	 */
	public int getFaction() {
		return faction;
	}

	/**
	 *  set the faction of an user
	 * @param faction his new faction
	 */
	public void setFaction(int faction) {
		this.faction = faction;
	}

	/**
	 *  return the id of an user
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 *  set a new id for an user
	 * @param id
	 */
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
	
	/**
	 *  return if this user has lost or not
	 * @return lost
	 */
	public boolean isLost() {
		return lost;
	}
	
	/**
	 *  set the lost state of an user
	 * @param lost
	 */
	public void setLost(boolean lost) {
		this.lost = lost;
	}
	
	/**
	 *  compare two user
	 * @param u
	 * @return true if they're both equals, else false
	 */
	public boolean equals(User u) {
		if (u.faction == this.faction && u.id == this.id) {
			return true;
		}
		return false;
	}
	
	/**
	 * Casual to string function
	 */
	public String toString() {
		return "Ruler number: "+ id +" - Faction="+faction;
	}

}

package fr.groupe40.projet.client;

import java.io.Serializable;

import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.board.GalaxyGenerator;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Constants;

/**
 * This class consists to define a user into the game, there are 3 types of 'typical' users :
 * AI, neutral, and human
 * </br>
 * There's also few special user type, like pirate's one, or the sun's ruler.
 * 
 * Constants value about the <i>ID</i> and the <i>Faction</i> are defined into the constants class.
 * 
 * Each planet and fleet (ie. Sprite by extension) is defined by a user. 
 * It is represented by a faction and an ID (-1, 0, 1).
 * 
 * @see Sprite
 * @see Constants
 * 
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = 8499986523207461968L;
	
	/**
	 *  the faction of this user (-1 => AI, 0 => neutral, >1 => human)
	 */
	private int faction;
	
	/**
	 *  the id of this user
	 */
	private int id;
	
	/**
	 *  percentage of troops to send per planet-launch
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
	 * Constructor for new ai player
	 */
	public User() {
		this.faction = Constants.ai_faction;
		this.lost = false;
		this.percent_of_troups_to_send = 100;
		this.id = this.getRandomID();
	}
	/**
	 *  Create a new user from a faction and an id
	 * @param faction the faction of this user
	 * @param id the id of this user
	 */
	public User(int faction, int id) {
		this.faction = faction;
		this.id = id;
		this.lost = false;
		percent_of_troups_to_send = 100;
	}
	
	/**
	 *  Create a user only from his id
	 * @param faction the faction of this player
	 */
	public User(int faction) {
		this.faction = faction;
		
		switch(faction) {
		case Constants.ai_faction:
			id = Constants.ai_faction; break;
		case Constants.neutral_faction:
			id = Constants.neutral_faction; break;
		case Constants.human_faction:
			id = Constants.human_faction; break;
		}
		percent_of_troups_to_send = 100;

		this.lost = false;
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

	/**
	 * Return the first available id
	 * @return the id
	 */
	private int getRandomID() {
		GalaxyGenerator.first_free_id += 1;
		return (GalaxyGenerator.first_free_id -1);
	}
	
	/* AI handler */
	/**
	 *  send fleet automatically for ai user
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
		if(troups_destination < source.getTroups() || source.getTroups() >= Constants.max_troups - 5) {
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

}

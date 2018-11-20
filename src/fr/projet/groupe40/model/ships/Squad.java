package fr.projet.groupe40.model.ships;

import java.io.Serializable;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.Sprite;
import fr.projet.groupe40.model.board.Planet;
import fr.projet.groupe40.util.Constantes;

public class Squad extends Sprite implements Serializable{
	private static final long serialVersionUID = 7720386286912131175L;
	/**
	 * 
	 */
	
	private Planet destination;
	private Planet source;
	private Ship type;
	private int nb_of_ships;

	private boolean reached;
	
	
	public Squad(String path, User user, boolean b, int nb_of_ships, Planet destination) {
		super(path, user, b);
		this.setNb_of_ships(nb_of_ships);
		this.destination = destination;
		setType(new Ship());
		setPosition(Constantes.width/2, Constantes.height/2);
	}

	public Squad(String path, User user, boolean b, int nb_of_ships, Planet destination, Ship ships_type) {
		super(path, user, b);
		this.setNb_of_ships(nb_of_ships);
		this.destination = destination;
		this.type = ships_type;
		setPosition(Constantes.width/2, Constantes.height/2);
	}

	public void setPosition(double x, double y) {
		setX(x);
		setY(y);		
	}

	public void remove() {
		nb_of_ships = 0;
		this.setRuler(Constantes.neutral_user);
		this.setImage(null);
		this.reached = true;
	}

	public void updatePosition() {
		if(reached)
			return;
		
		if(destination.isInside(this)) {	//Case if the squads reach the destination			
			if(this.getRuler().getFaction() != destination.getRuler().getFaction()) {	//If the faction are differents, then BOOM
				int difference = destination.getTroups() - nb_of_ships*type.getPower();
				
				if(difference >=1) {	//Difference > 1 => kamikaze
					destination.setTroups(difference);					
				} else {				//Else, negative or 0 => new leader
					destination.setRuler(this.getRuler());
					
					difference = Math.abs(difference);
					if(difference >= Constantes.max_troups) {	//Sum > 100, we lower the amount to stay at the limit
						destination.setTroups(Constantes.max_troups);					
					} else {	//Else, renforcement
						destination.setTroups(difference + 1);
					}
				}
			}else if(this.getRuler().getFaction() == destination.getRuler().getFaction()) {	//Same faction
				int sum = nb_of_ships + destination.getTroups();	//Sum of defense + squad
				if(sum >= Constantes.max_troups) {	//Sum > 100, we lower the amount to stay at the limit
					destination.setTroups(Constantes.max_troups);					
				} else {	//Else, renforcement
					destination.setTroups(sum);
				}
			}
			remove();	//Remove the squads of the galaxy
			return;
		}

		double centre_x = destination.getX() + destination.width()/2; 
		double centre_y = destination.getY() + destination.height()/2;
		double x = this.getX(); double y = this.getY();
		if(x < centre_x) {
			setX(x+getType().getSpeed());
		} else {
			setX(x-getType().getSpeed());
		}
		
		if(y < centre_y) {
			setY(y+getType().getSpeed());
		} else {
			setY(y-getType().getSpeed());
		}
		
		validatePosition();
	}

	public int getNb_of_ships() {
		return nb_of_ships;
	}

	public void setNb_of_ships(int nb_of_ships) {
		this.nb_of_ships = nb_of_ships;
	}

	public boolean isReached() {
		return reached;
	}

	public void setReached(boolean reached) {
		this.reached = reached;
	}

	public Planet getDestination() {
		return destination;
	}

	public void setDestination(Planet destination) {
		this.destination = destination;
	}

	public String toString() {
		return "Squad <" + getX() + ", " + getY() + ">" + "\ndestination"+destination.toString() + "\nsource"+source.toString();
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
	 * @return the type
	 */
	public Ship getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Ship type) {
		this.type = type;
	}

}

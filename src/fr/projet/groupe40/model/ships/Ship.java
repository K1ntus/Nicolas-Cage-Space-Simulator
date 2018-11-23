package fr.projet.groupe40.model.ships;

import java.io.Serializable;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.Sprite;
import fr.projet.groupe40.model.planets.Planet;
import fr.projet.groupe40.util.Constantes;

public class Ship extends Sprite implements Serializable {
	private static final long serialVersionUID = -1872446628467348036L;

	private ShipType ship_type = new ShipType();
	
	protected Planet destination, source;
	protected boolean reached;

	public Ship(String path, User ruler, Planet destination, Planet source, double x_init, double y_init, ShipType ship_type) {
		super(path, ruler, false);
		this.destination = destination;
		this.source = source;
		this.setX(x_init);
		this.setY(y_init);
		this.ship_type = ship_type;
	}


	/**
	 * \brief Update the position of this ships, also handle collision with his destination
	 */
	public void update_position() {

		if(reached)
			return;
		
		if(destination.isInside(this)) {	//Case if the squads reach the destination			
			if(this.getRuler().getFaction() != destination.getRuler().getFaction()) {	//If the faction are differents, then BOOM
				int difference = destination.getTroups() - 1;
				
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
				int sum = 1 + destination.getTroups();	//Sum of defense + squad
				if(sum >= Constantes.max_troups) {	//Sum > 100, we lower the amount to stay at the limit
					destination.setTroups(Constantes.max_troups);					
				} else {	//Else, renforcement
					destination.setTroups(sum);
				}
			}
			remove();	//Remove the squads of the galaxy
			return;
		}
		
		
		
		double speed = ship_type.speed;

		double centre_x = destination.getX() + destination.width()/2; 
		double centre_y = destination.getY() + destination.height()/2;
		double x = this.getX(); double y = this.getY();
		if(x < centre_x) {
			setX(x+speed);
		} else {
			setX(x-speed);
		}
		
		if(y < centre_y) {
			setY(y+speed);
		} else {
			setY(y-speed);
		}

		
	}
	
	/**
	 * \brief prepare his removal from the squad list
	 */
	public void remove() {
		this.setRuler(Constantes.neutral_user);
		this.setImage(null);
		this.reached = true;
	}

	public double getSpeed() {
		return ship_type.speed;
	}

	public void setSpeed(double speed) {
		this.ship_type.speed = speed;
	}

	public double getPower() {
		return ship_type.power;
	}

	public void setPower(double power) {
		this.ship_type.power = power;
	}

	public double getProduction_time() {
		return ship_type.production_time;
	}

	public void setProduction_time(double production_time) {
		this.ship_type.production_time = production_time;
	}

	public Planet getDestination() {
		return destination;
	}

	public void setDestination(Planet destination) {
		this.destination = destination;
	}

	public Planet getSource() {
		return source;
	}

	public void setSource(Planet source) {
		this.source = source;
	}

	public boolean isReached() {
		return reached;
	}

	public void setReached(boolean reached) {
		this.reached = reached;
	}

	
	
}

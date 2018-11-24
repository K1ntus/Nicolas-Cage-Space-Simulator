package fr.groupe40.projet.model.ships;

import java.io.Serializable;
import java.util.List;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.planets.Planet;
import static fr.groupe40.projet.util.Collision.*;
import fr.groupe40.projet.util.Collision;

import fr.groupe40.projet.util.Constantes;

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
	 * \brief Check if the ship has reached his destination, and handle this case
	 */
	public boolean reached_destination() {

		if(reached)
			return true;
		
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
			return true;
		}
		return false;
		
	}
	
	/**
	 * \brief Calculate the next position and provides collision handler
	 * @param planets
	 */
	public void calc_next_position(List<Planet> planets) {
		double speed = ship_type.speed;

		double centre_x = destination.getX() + destination.width()/2; 
		double centre_y = destination.getY() + destination.height()/2;
		double x = this.getX(); double y = this.getY();
		switch(whereis_collision(x, y, centre_x, centre_y, speed)) {
			case NO_COLLISION:
				no_collision_mover(x, y, centre_x, centre_y, speed);
				break;
			case TOP:
				top_collision_mover(x, y, centre_x, centre_y, speed);
				break;
			case BOTTOM:
				bottom_collision_mover(x, y, centre_x, centre_y, speed);
				break;
			case LEFT:
				left_collision_mover(x, y, centre_x, centre_y, speed);
				break;
			case RIGHT:
				right_collision_mover(x, y, centre_x, centre_y, speed);
				break;	
		}
	}
	
	public Collision whereis_collision(double x, double y, double centre_x, double centre_y, double speed) {
		return NO_COLLISION;
		
	}
	
	
	public void top_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
		double deltaX = 0, deltaY = 0;
		//TODO implement top collision for ship
		

		this.setX(x+deltaX);
		this.setY(y+deltaY);
	}
	
	public void bottom_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
		double deltaX = 0, deltaY = 0;
		//TODO implement bottom collision for ship
		

		this.setX(x+deltaX);
		this.setY(y+deltaY);
	}
	
	public void left_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
		double deltaX = 0, deltaY = 0;
		//TODO implement left collision for ship
		

		this.setX(x+deltaX);
		this.setY(y+deltaY);
	}
	
	public void right_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
		double deltaX = 0, deltaY = 0;
		//TODO implement right collision for ship
		

		this.setX(x+deltaX);
		this.setY(y+deltaY);
	}	
	
	public void no_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
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
	 * \brief Update the position of this ship
	 * @param planets
	 */
	public void update_position(List<Planet> planets) {
		
		if(this.reached_destination()) {	//Ships has reached his destination
			return;
		}
		calc_next_position(planets);

		
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

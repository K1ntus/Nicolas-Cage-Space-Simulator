package fr.groupe40.projet.model.ships;

import static fr.groupe40.projet.util.constantes.Collision.*;

import java.io.Serializable;
import java.util.List;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.util.constantes.Collision;
import fr.groupe40.projet.util.constantes.Constantes;

public class Ship extends Sprite implements Serializable {
	private static final long serialVersionUID = -1872446628467348036L;

	private ShipType ship_type = new ShipType();	
	
	protected Planet destination, source;
	protected boolean reached;
	private Planet collision;

	public Ship(String path, User ruler, Planet destination, Planet source, double x_init, double y_init, ShipType ship_type) {
		super(path, ruler, false);
		this.destination = destination;
		this.source = source;
		this.setX(x_init);
		this.setY(y_init);
		this.ship_type = ship_type;
		this.collision = source;
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
		switch(whereis_collision(x, y, speed, planets)) {
			case NO_COLLISION:
				no_collision_mover(x, y, centre_x, centre_y, speed);
				break;
			case TOP:
				top_collision_mover(x, y, centre_x, centre_y, speed);
				//System.out.println("Top collision");
				break;
			case BOTTOM:
				bottom_collision_mover(x, y, centre_x, centre_y, speed);
				//System.out.println("Bottom collision");
				break;
			case LEFT:
				left_collision_mover(x, y, centre_x, centre_y, speed);
				//System.out.println("Right collision");
				break;
			case RIGHT:
				right_collision_mover(x, y, centre_x, centre_y, speed);
				//System.out.println("Left collision");
				break;	
		}
	}
	
	public Collision whereis_collision(double x, double y, double speed, List<Planet> planets) {
		double width = this.width();
		double height = this.height();
		Collision res = NO_COLLISION;
		
		for(Planet p : planets) {
			if(p.equals(destination)) {
				continue;
			}

			  if(p.isInside(x-speed, y, width, height)) {	
				collision = p;
				return Collision.RIGHT;
			} else if(p.isInside(x+speed, y, width, height)) {
				collision = p;
				return Collision.LEFT;
				
			} else if(p.isInside(x-speed, y-speed, width, height) || p.isInside(x+speed, y-speed, width, height)) {
				collision = p;
				return Collision.BOTTOM;
			} else if(p.isInside(x-speed, y+speed, width, height) || p.isInside(x+speed, y+speed, width, height)) {
				collision = p;
				return Collision.TOP;
				
			}
		}
		return res;
		
	}
	
	
	public void top_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
		double deltaX = 0, deltaY = 0;
		
		double xCollision = collision.getX(), yCollision = collision.getY();
		double widthCollision = collision.width();
		
		if(destination.distance(xCollision, yCollision) > destination.distance(xCollision+widthCollision, yCollision))
			deltaX = +speed;
		else
			deltaX = -speed;

		this.setX(x+deltaX);
		this.setY(y+deltaY);
	}
	
	public void bottom_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
		double deltaX = 0, deltaY = 0;
		
		double xCollision = collision.getX(), yCollision = collision.getY();
		double widthCollision = collision.width();

		if(destination.distance(xCollision, yCollision) > destination.distance(xCollision+widthCollision, yCollision))
			deltaX = +speed;
		else
			deltaX = -speed;
		

		this.setX(x+deltaX);
		this.setY(y+deltaY);
	}
	
	public void left_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
		double deltaX = 0, deltaY = 0;
		
		double xCollision = collision.getX(), yCollision = collision.getY();
		double heightCollision = collision.height();

		if(destination.distance(xCollision, yCollision) > destination.distance(xCollision, yCollision+heightCollision))
			deltaY = +speed;
		else
			deltaY = -speed;
		

		this.setX(x+deltaX);
		this.setY(y+deltaY);
	}
	
	public void right_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
		double deltaX = 0, deltaY = 0;
		
		double xCollision = collision.getX(), yCollision = collision.getY();
		double heightCollision = collision.height();

		if(destination.distance(xCollision, yCollision) > destination.distance(xCollision, yCollision+heightCollision))
			deltaY = +speed;
		else
			deltaY = -speed;
		

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

package fr.groupe40.projet.model.ships;

import static fr.groupe40.projet.util.constants.Direction.NO_COLLISION;

import java.io.Serializable;
import java.util.List;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.util.constants.Direction;
import fr.groupe40.projet.util.constants.Constants;

/**
 * \brief Ship of a squad, contains the destination, src, ...
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class Ship extends Sprite implements Serializable {
	private static final long serialVersionUID = -1872446628467348036L;

	/**
	 * \brief ship type of this ship
	 */
	private ShipType ship_type = new ShipType();	
	
	/**
	 * 	\brief Source and Destination planets
	 */
	protected Planet destination, source;
	
	/**
	 * \brief If this ships has reacher or not his destination
	 */
	protected boolean reached;
	
	/**
	 * \brief The planet where there's a collision during his path in the board
	 */
	private Planet collision;

	/**
	 * \brief constructor of the ship object
	 * @param path image path of this ship
	 * @param ruler ruler of this ship
	 * @param destination his destination planet
	 * @param source his source planet
	 * @param x_init his initial x
	 * @param y_init his initial y
	 * @param ship_type ship_type (speed, power, ...)
	 */
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
			if(this.getRuler() != destination.getRuler()) {	//If the faction are differents, then BOOM
				int difference = destination.getTroups() - 1;
				
				if(difference >=1) {	//Difference > 1 => kamikaze
					destination.setTroups(difference);					
				} else {				//Else, negative or 0 => new leader
					destination.setRuler(this.getRuler());
					
					difference = Math.abs(difference);
					if(difference >= Constants.max_troups) {	//Sum > 100, we lower the amount to stay at the limit
						destination.setTroups(Constants.max_troups);					
					} else {	//Else, renforcement
						destination.setTroups(difference + 1);
					}
				}
			}else if(this.getRuler() == destination.getRuler()) {	//Same faction
				int sum = 1 + destination.getTroups();	//Sum of defense + squad
				if(sum >= Constants.max_troups) {	//Sum > 100, we lower the amount to stay at the limit
					destination.setTroups(Constants.max_troups);					
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
	
	/**
	 * \brief return where is the collision between a ship and every planets of a list
	 * @param x horizontal position of the ship
	 * @param y vertical position of the ship
	 * @param speed speed of the sheep
	 * @param planets array with every planets of the board on it
	 * @return a Collision constante
	 */
	public Direction whereis_collision(double x, double y, double speed, List<Planet> planets) {
		double width = this.width();
		double height = this.height();
		Direction res = NO_COLLISION;
		
		for(Planet p : planets) {
			if(p.equals(destination)) {
				continue;
			}

			  if(p.isInside(x-speed, y, width, height)) {	
				collision = p;
				return Direction.RIGHT;
			} else if(p.isInside(x+speed, y, width, height)) {
				collision = p;
				return Direction.LEFT;
				
			} else if(p.isInside(x-speed, y-speed, width, height) || p.isInside(x+speed, y-speed, width, height)) {
				collision = p;
				return Direction.BOTTOM;
			} else if(p.isInside(x-speed, y+speed, width, height) || p.isInside(x+speed, y+speed, width, height)) {
				collision = p;
				return Direction.TOP;
				
			}
		}
		return res;
		
	}
	

	
	/**
	 * \brief Next position calculation when there s a collision in the upper side
	 * @param x current x position
	 * @param y current y position
	 * @param centre_x destination x
	 * @param centre_y destination y
	 * @param speed speed of this ship
	 */
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

	
	/**
	 * \brief Next position calculation when there s a collision in the bottom side
	 * @param x current x position
	 * @param y current y position
	 * @param centre_x destination x
	 * @param centre_y destination y
	 * @param speed speed of this ship
	 */
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
	
	/**
	 * \brief Next position calculation when there s a collision in the left side
	 * @param x current x position
	 * @param y current y position
	 * @param centre_x destination x
	 * @param centre_y destination y
	 * @param speed speed of this ship
	 */	
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
	
	/**
	 * \brief Next position calculation when there s a collision in the right side
	 * @param x current x position
	 * @param y current y position
	 * @param centre_x destination x
	 * @param centre_y destination y
	 * @param speed speed of this ship
	 */
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
	
	/**
	 * \brief calculate the angle between a ship and his destination
	 * @return angle in degrees
	 */
	public double destination_angle() {
		double hyp = this.distance(destination);
		double adjacent_side = Math.abs((destination.getX() + destination.width()/2) - this.getX());
		
		double angle = Math.toDegrees(Math.cos(adjacent_side / hyp)); 
		
		return angle;
	}
	
	/**
	 * \brief case when there s no collision for a ship
	 * @param x current x position
	 * @param y current y position
	 * @param centre_x destination x
	 * @param centre_y destination y
	 * @param speed speed of this ship
	 */
	public void no_collision_mover(double x, double y, double centre_x, double centre_y, double speed) {
		//double angle = destination_angle()/90;
		double angle = 1;
		if(x < centre_x) {
			setX(x+speed*angle);
		} else {
			setX(x-speed*angle);
		}
		
		if(y < centre_y) {
			setY(y+speed*angle);
		} else {
			setY(y-speed*angle);
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
		this.setRuler(Constants.neutral_user);
		this.setImage(null);
		this.reached = true;
	}

	/**
	 * @return the ship_type
	 */
	public ShipType getShip_type() {
		return ship_type;
	}

	/**
	 * @param ship_type the ship_type to set
	 */
	public void setShip_type(ShipType ship_type) {
		this.ship_type = ship_type;
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
	 * @return the reached
	 */
	public boolean isReached() {
		return reached;
	}

	/**
	 * @param reached the reached to set
	 */
	public void setReached(boolean reached) {
		this.reached = reached;
	}

	/**
	 * @return the collision
	 */
	public Planet getCollision() {
		return collision;
	}

	/**
	 * @param collision the collision to set
	 */
	public void setCollision(Planet collision) {
		this.collision = collision;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return ship_type.speed;
	}

	
	
}

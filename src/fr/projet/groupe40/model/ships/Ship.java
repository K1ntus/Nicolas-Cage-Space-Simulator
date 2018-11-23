package fr.projet.groupe40.model.ships;

import java.io.Serializable;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.Sprite;
import fr.projet.groupe40.model.board.Planet;
import fr.projet.groupe40.util.Constantes;

public class Ship extends Sprite implements Serializable {
	private static final long serialVersionUID = -1872446628467348036L;

	private ShipType ship_type = new ShipType();
	
	protected Planet destination, source;
	protected boolean reached;

	public Ship(String path, User ruler, Planet destination, Planet source, double x_init, double y_init) {
		super(path, ruler, false);
		this.destination = destination;
		this.source = source;
		this.setX(x_init);
		this.setY(y_init);
	}


	
	public void update_position() {
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
	
	public void remove() {
		System.out.println("removed");
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

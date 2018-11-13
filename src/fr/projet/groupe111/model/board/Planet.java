package fr.projet.groupe111.model.board;

import fr.projet.groupe111.client.User;
import fr.projet.groupe111.model.Sprite;
import fr.projet.groupe111.model.ships.Ship;
import fr.projet.groupe111.model.ships.Squad;
import fr.projet.groupe111.util.Constantes;

public class Planet extends Sprite {
	private int produce_rate;
	private int troups;
	
	private Ship ships_type;
	
	
	private boolean selected;

	public Planet(Sprite s) {
		super(s);
		generate();
	}
	public Planet(Sprite s, double x, double y) {
		super(s);
		generate();
		setX(x);
		setY(y);
	}

	private void generate() {
		selected = false;
		
		setWidth(Math.random() * Constantes.size_minimal_planets + Constantes.size_minimal_planets);
		setHeight(width());
		setX((Math.random() * (Constantes.width - width())));
		setY((Math.random() * (Constantes.height - height())));
		troups = (int) (Math.random() * (Constantes.max_initDefense - Constantes.min_troups) +1);
		produce_rate =  (int) (Math.random() * (Constantes.max_ship_produce - Constantes.min_ship_produce) +1);
		
		ships_type = new Ship();
		
		updateImage();
		
		
	}

	public boolean isInside(double x, double y) {
		if(x > getX()+width() || x < getX()) {
			return false;
		}
		
		if(y > getY()+height() || y < getY()) {
			return false;
		}
		return true;
	}
	
	public boolean isInside(Squad s) {
		return isInside(s.getX(), s.getY());
	}

	
	public boolean clickedOnPlanet(double x, double y) {
		if(isInside(x, y)) {
			if(Constantes.DEBUG) {
				System.out.println("Vous avez cliqué sur une planète avec "+this.troups);
				System.out.println("Celle ci appartient a l'ID: "+ this.getRuler().getId());
				System.out.println("et de type: "+ this.getRuler().getFaction());
			}
			return true;
		}
		return false;
			
	}
	
	public void updateGarrison() {
    	if(getRuler().getFaction() != Constantes.neutral) {
    		if(troups < Constantes.max_troups) {
    			
    			troups = troups + produce_rate;	
    			if(troups > Constantes.max_troups) {
    				troups = Constantes.max_troups;
    			}
    		
    		}
    	}
	}
	
	public Squad sendFleet(Planet destination) {
		int fleet_size = (getRuler().getPercent_of_troups_to_send()/100) * troups;
		troups -= fleet_size;
		Sprite sprite = new Sprite(getImg_path(), getRuler(), false);
		Squad s = new Squad(sprite, fleet_size, destination, ships_type);
		
		return s;
	}
	
	public boolean intersectCircle(double x_left, double y_top, double x_right, double y_bottom) {
		//(x - center_x)^2 + (y - center_y)^2 < radius^2
		double radius = this.width()/2 + Constantes.minimal_distance_between_planets;
		double circle_x = this.getX() + this.width()/2;
		double circle_y = this.getY() + this.height()/2;
		double circle_left = circle_x - radius;
		double circle_top = circle_y - radius;
		double circle_right = circle_x + radius;
		double circle_bottom = circle_y + radius;
		
		//Reject if corner are clearly out of the circle
		if(x_right < circle_left && x_left > circle_right || y_bottom < circle_top && y_top > circle_bottom) {
			//System.out.println("out 1");
			return true;
		}
		//check if center of circle is inside rectangle
		if(x_left <= circle_x && circle_x <= x_right && y_top <= circle_y && circle_y <= y_bottom) {
			//System.out.println("**Circle is inside rectangle");
			//System.out.println("out 2");
			return true;
		}
		        		
		//Check every point of the rectangle
		for(double x1 = x_left; x1 < x_right; x1++) {
			for(double y1 = y_top; y1 < y_bottom; y1++) {
				if(Math.hypot(x1 - circle_x, y1 - circle_y) <= radius) {
					//System.out.println("out 3");
					return true;
				}
			}
		}
		
		return false;
	}
	public boolean intersectCircle(Planet p) {
		return intersectCircle(
				p.getX(),
				p.getY(),
				p.getX()+p.width(), 
				p.getY()+p.height()
			);
	}

	public int calculateNextPosition() {
		
		if (this.getX() + this.width() >= Constantes.width) {
			return Constantes.error_greater_x;
		} else if (this.getX() < 0) {
			return Constantes.error_lower_x;
		}

		if (this.getY() + this.height() >= Constantes.height) {
			return Constantes.error_greater_y;
		} else if (this.getY() < 0) {
			return Constantes.error_lower_y;
		}
		
		return 0;
	}
	
	public int updatePlanetePosition() {
		setX(this.getX() + this.width()/5);
		switch(calculateNextPosition()) {
			case Constantes.error_greater_x:
				setY(getY() + Constantes.height / 10);
				setX(5);
				break;
			case Constantes.error_lower_x:
				setX(5);
				break;
			case Constantes.error_greater_y:
				return -1;
			case Constantes.error_lower_y:
				setY(5);
				break;
			default:
				return 0;
		}
		return 0;
	}


	public int getProduce_rate() {
		return produce_rate;
	}

	public void setProduce_rate(int produce_rate) {
		this.produce_rate = produce_rate;
	}

	public int getTroups() {
		return troups;
	}

	public void setTroups(int troups) {
		this.troups = troups;
	}

	public Ship getShips_type() {
		return ships_type;
	}

	public void setShips_type(Ship ships_type) {
		this.ships_type = ships_type;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

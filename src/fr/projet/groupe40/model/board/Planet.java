package fr.projet.groupe40.model.board;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.Sprite;
import fr.projet.groupe40.model.ships.Ship;
import fr.projet.groupe40.model.ships.Squad;
import fr.projet.groupe40.util.Constantes;

public class Planet extends Sprite {
	private static final long serialVersionUID = 260148039781013750L;
	/**
	 * 
	 */
	
	private int produce_rate;
	private int troups;
	
	private Ship ships_type;
	
	
	private boolean selected;

	public Planet(String path, User ruler, boolean isPlanet, int x, int y) {
		super(path, ruler, isPlanet);
		generate();
		setX(x);
		setY(y);
	}
	/**	Utils **/
	public boolean clickedOnPlanet(double x, double y) {
		if(isInside(x, y,0,0)) {
			if(Constantes.DEBUG) {
				System.out.println("Vous avez cliqu� sur une plan�te avec "+this.troups);
				System.out.println("Celle ci appartient a l'ID: "+ this.getRuler().getId());
				System.out.println("et de type: "+ this.getRuler().getFaction());
			}
			return true;
		}
		return false;
			
	}
	
	/** Update	**/
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
	
	/**	Interactions **/
	public Squad sendFleet(Planet destination) {
		return sendFleet(destination, getRuler().getPercent_of_troups_to_send());
	}

	public Squad sendFleet(Planet destination, double percent) {
		if(troups > Constantes.min_troups+1) {
			int fleet_size = troups - (Constantes.min_troups);
			
			//IF Percent >Constantes, then ...
			//TODO
			
			fleet_size *= (percent /100.0);
			if(fleet_size < 1) {
				return null;
			}
			troups -= fleet_size;
			//Sprite sprite = new Sprite(Constantes.path_img_ships, getRuler(), false);
			//sprite.setPosition(getX()+width()/2,getY()+height()/2);
			
			Squad s = new Squad(Constantes.path_img_ships, getRuler(), false, (int)fleet_size, destination, ships_type);
			s.setPosition(getX()+width()/2,getY()+height()/2);
			s.setSource(this);
			
			return s;			
		}
		return null;
	}
	
	/**	Planet Generation	**/

	private void generate() {
		selected = false;
		
		setWidth(Math.random() * Constantes.size_minimal_planets + Constantes.size_minimal_planets);
		setHeight(width());
		double x = (Math.random() * (Constantes.width - width()));
		double y = (Math.random() * (Constantes.height - height()));

		if (x <= Constantes.left_margin_size)
			x = Constantes.left_margin_size;
		else if( x >= getMaxX())
			x = getMaxX();
		
		if (getY() <= Constantes.top_margin_size)
			y = Constantes.top_margin_size;
		else if( getY()+height() >= getMaxY())
			y = getMaxY();
		
		setX(x);
		setY(y);
		troups = (int) (Math.random() * (Constantes.max_initDefense - Constantes.min_troups) +1);
		produce_rate =  (int) (Math.random() * (Constantes.max_ship_produce - Constantes.min_ship_produce) +1);
		
		ships_type = new Ship();
		
		updateImage();
	}
	
	//find is place in the universe
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


	public String toString() {
		return "Planet <" + getX() + ", " + getY() + ">";
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

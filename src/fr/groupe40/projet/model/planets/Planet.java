package fr.groupe40.projet.model.planets;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.ships.ShipType;
import fr.groupe40.projet.util.Constantes;

public class Planet extends Sprite {
	private static final long serialVersionUID = 260148039781013750L;
	/**
	 * 
	 */
	double summonX = 1, summonY = 1;
	private int produce_rate;
	private int troups;
	
	private ShipType ships_type;
	
	private boolean selected;

	public Planet(String path, User ruler, boolean isPlanet, int x, int y) {
		super(path, ruler, isPlanet);
		generate();
		setX(x);
		setY(y);
	}
	/**	Utilities **/
	public boolean clickedOnPlanet(double x, double y) {
		if(isInside(x, y,0,0)) {
			if(Constantes.DEBUG) {
				System.out.println("Vous avez clique sur une planete avec "+this.troups);
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
	
	/**	Planet Generation	**/

	private void generate() {
		selected = false;
		
		setWidth(Math.random() * Constantes.size_minimal_planets + Constantes.size_minimal_planets);
		setHeight(width());
		double x = (Math.random() * (Constantes.width - width()));
		double y = (Math.random() * (Constantes.height - height()));

		if (x <= Constantes.left_margin_size + Constantes.size_squads)
			x = Constantes.left_margin_size + Constantes.size_squads;
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
		
		ships_type = new ShipType();
		
		updateImage();
	}
	
	//find his place in the universe
	public int calculateNextPosition() {
		
		if (this.getX() + this.width() >= Constantes.width -  Constantes.right_margin_size + Constantes.size_squads) {
			//System.out.println("[INFO] Greater x case for planet generation");
			return Constantes.error_greater_x;
		} else if (this.getX() < Constantes.left_margin_size  + Constantes.size_squads ) {
			//System.out.println("[INFO] Lower x case for planet generation");
			return Constantes.error_lower_x;
		}

		if (this.getY() + this.height() >= Constantes.height) {
			//System.out.println("[INFO] Higher y case for planet generation. Skip this one.");
			return Constantes.error_greater_y;
		} else if (this.getY() < Constantes.top_margin_size  + Constantes.size_squads) {
			//System.out.println("[INFO] Lower y case for planet generation");
			return Constantes.error_lower_y;
		}
		
		return 0;
	}
	
	
	public int updatePlanetePosition() {
		setX(this.getX() + this.width()/5);
		switch(calculateNextPosition()) {
			case Constantes.error_greater_x:
				setY(getY() + Constantes.height / 10);
				setX(Constantes.left_margin_size  + Constantes.size_squads);
				break;
			case Constantes.error_lower_x:
				setX(Constantes.left_margin_size  + Constantes.size_squads);
				break;
			case Constantes.error_greater_y:
				return -1;
			case Constantes.error_lower_y:
				setX(Constantes.top_margin_size  + Constantes.size_squads);
				setY(Constantes.top_margin_size  + Constantes.size_squads + 1);
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

	public ShipType getShips_type() {
		return ships_type;
	}

	public void setShips_type(ShipType ships_type) {
		this.ships_type = ships_type;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

package fr.groupe40.projet.model.planets;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.ships.ShipType;
import fr.groupe40.projet.util.constants.Constants;

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
	
	/*	Utilities */
	/**
	 * \brief check if a position is in a planet
	 * @param x
	 * @param y
	 * @return true if inside, else false
	 */
	@Deprecated
	public boolean clickedOnPlanet(double x, double y) {
		if(isInside(x, y, 0, 0)) {
			if(Constants.DEBUG) {
				System.out.println("Vous avez clique sur une planete avec "+this.troups);
				System.out.println("Celle ci appartient a l'ID: "+ this.getRuler().getId());
				System.out.println("et de type: "+ this.getRuler().getFaction());
			}
			return true;
		}
		return false;
			
	}
	
	/* Update	*/
	/**
	 * \brief update the garrison value of this planet if not neutral
	 */
	public void updateGarrison() {
    	if(getRuler().getFaction() != Constants.neutral) {
    		if(troups < Constants.max_troups) {
    			
    			troups = troups + produce_rate;	
    			if(troups > Constants.max_troups) {
    				troups = Constants.max_troups;
    			}
    		
    		}
    	}
	}
	
	/*	Planet Generation	*/

	/**
	 * \brief Generate a randomized planet 
	 */
	private void generate() {
		selected = false;
		
		setWidth(Math.random() * Constants.size_minimal_planets + Constants.size_minimal_planets);
		setHeight(width());
		double x = (Math.random() * (Constants.width - width()));
		double y = (Math.random() * (Constants.height - height()));

		if (x <= Constants.left_margin_size + Constants.size_squads)
			x = Constants.left_margin_size + Constants.size_squads;
		else if( x >= getMaxX())
			x = getMaxX();
		
		if (getY() <= Constants.top_margin_size)
			y = Constants.top_margin_size;
		else if( getY()+height() >= getMaxY())
			y = getMaxY();
		
		setX(x);
		setY(y);
		troups = (int) (Math.random() * (Constants.max_initDefense - Constants.min_troups) +1);
		produce_rate =  (int) (Math.random() * (Constants.max_ship_produce - Constants.min_ship_produce) +1);
		
		ships_type = new ShipType();
		
		updateImage();
	}
	
	/**
	 * \brief find his place in the universe
	 * @return 0 if his position is correct, else error value
	 */
	public int calculateNextPosition() {
		
		if (this.getX() + this.width() >= Constants.width -  Constants.right_margin_size + Constants.size_squads) {
			//System.out.println("[INFO] Greater x case for planet generation");
			return Constants.error_greater_x;
		} else if (this.getX() < Constants.left_margin_size  + Constants.size_squads ) {
			//System.out.println("[INFO] Lower x case for planet generation");
			return Constants.error_lower_x;
		}

		if (this.getY() + this.height() >= Constants.height) {
			//System.out.println("[INFO] Higher y case for planet generation. Skip this one.");
			return Constants.error_greater_y;
		} else if (this.getY() < Constants.top_margin_size  + Constants.size_squads) {
			//System.out.println("[INFO] Lower y case for planet generation");
			return Constants.error_lower_y;
		}
		
		return 0;
	}
	
	/**
	 * \brief check new position for generation
	 * @return 0 if ok. -1 if unable to generate
	 */
	public int updatePlanetePosition() {
		setX(this.getX() + this.width()/5);
		switch(calculateNextPosition()) {
			case Constants.error_greater_x:
				setY(getY() + Constants.height / 10);
				setX(Constants.left_margin_size  + Constants.size_squads);
				break;
			case Constants.error_lower_x:
				setX(Constants.left_margin_size  + Constants.size_squads);
				break;
			case Constants.error_greater_y:
				return -1;
			case Constants.error_lower_y:
				setX(Constants.top_margin_size  + Constants.size_squads);
				setY(Constants.top_margin_size  + Constants.size_squads + 1);
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

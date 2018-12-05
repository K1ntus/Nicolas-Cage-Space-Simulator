package fr.groupe40.projet.model.planets;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.ships.ShipType;
import fr.groupe40.projet.util.constants.Constants;

/**
 * \brief Abstract class of the general type "planet"
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public abstract class Planet extends Sprite {
	private static final long serialVersionUID = 260148039781013750L;
	/**
	 * 
	 */
	
	/**
	 * \brief lift off x and y position of a ship
	 */
	double summonX = 1, summonY = 1;
	
	/**
	 * \brief the number of troups produced each time
	 */
	private int produce_rate;
	
	/**
	 * \brief the number of troups in the garrison
	 */
	private int troups;
	
	/**
	 * \brief the type of ships generated there
	 */	
	private ShipType ships_type;
	
	/**
	 * \brief return true if this planet is selected by the user
	 */
	private boolean selected;

	/**
	 * \brief Constructor of a planet
	 * @param path the string image path
	 * @param ruler the beginning ruler of this planet
	 * @param x top left x position
	 * @param y top left y position
	 */
	public Planet(String path, User ruler, int x, int y) {
		super(path, ruler, true);
		this.generate();
		this.setX(x);
		this.setY(y);
	}
	/**
	 * \brief Constructor of a planet
	 * @param path the string image path
	 * @param ruler the beginning ruler of this planet
	 * @param x top left x position
	 * @param y top left y position
	 */
	public Planet(String path, User ruler, int x, int y, double size) {
		super(path, ruler, true);
		this.generate();
		this.setX(x);
		this.setY(y);

		this.setWidth(size);
		this.setHeight(size);
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
			return Constants.error_greater_x;
		} else if (this.getX() < Constants.left_margin_size  + Constants.size_squads ) {
			return Constants.error_lower_x;
		}

		if (this.getY() + this.height() >= Constants.height) {
			return Constants.error_greater_y;
		} else if (this.getY() < Constants.top_margin_size  + Constants.size_squads) {
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


	public abstract String toString();

	/**
	 * \brief return if two planets are the same
	 * @param u
	 * @return true if they're both equals, else false
	 */
	public boolean equals(Planet p) {
		User user1 = this.getRuler();
		User user2 = p.getRuler();
		
		if (user1.equals(user2) && this.getX() == p.getX()) {
			return true;
		}
		return false;
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

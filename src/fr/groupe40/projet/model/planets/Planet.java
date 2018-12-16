package fr.groupe40.projet.model.planets;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.ships.ShipType;
import fr.groupe40.projet.util.constants.Debugging;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.GenerationErrorSide;
import fr.groupe40.projet.util.constants.PlanetsGarrison;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.constants.ShipsParameters;

/**
 *  Abstract class of the general type "planet"
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public abstract class Planet extends Sprite {
	private static final long serialVersionUID = 260148039781013750L;
	
	/**
	 *  lift off x and y position of a ship
	 */
	double summonX = 1, summonY = 1;
	
	/**
	 *  the number of troups produced each time
	 */
	private int produce_rate;
	
	/**
	 *  the number of troups in the garrison
	 */
	private int troups;
	
	/**
	 *  the type of ships generated there
	 */	
	private ShipType ships_type;
	
	/**
	 *  return true if this planet is selected by the user
	 */
	private boolean selected;

	/**
	 *  Constructor of a planet
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
	 *  Constructor of a planet
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
	 *  update the garrison value of this planet if not neutral
	 */
	public void updateGarrison() {
    	if(getRuler().getFaction() != Players.neutral_faction) {
    		if(troups < PlanetsGarrison.max_troups) {
    			
    			troups = troups + produce_rate;	
    			if(troups > PlanetsGarrison.max_troups) {
    				troups = PlanetsGarrison.max_troups;
    			}
    		
    		}
    	}
	}
	
	/*	Planet Generation	*/

	/**
	 *  Generate a randomized planet 
	 */
	private void generate() {
		selected = false;
		
		this.setWidth(Math.random() * Generation.size_minimal_planets + Generation.size_minimal_planets);
		this.setHeight(this.width());
		double x = (Math.random() * (Generation.width - width()));
		double y = (Math.random() * (Generation.height - height()));

		if (x <= Generation.left_margin_size + Generation.size_squads)
			x = Generation.left_margin_size + Generation.size_squads;
		else if( x >= this.getMaxX())
			x = this.getMaxX();
		
		if (this.getY() <= Generation.top_margin_size)
			y = Generation.top_margin_size;
		else if( this.getY()+this.height() >= this.getMaxY())
			y = this.getMaxY();
		
		this.setX(x);
		this.setY(y);
		troups = (int) (Math.random() * (PlanetsGarrison.max_initDefense - PlanetsGarrison.min_troups) +1);
		produce_rate =  (int) (Math.random() * (ShipsParameters.max_ship_produce - ShipsParameters.min_ship_produce) +1);
		
		ships_type = new ShipType();
		
		this.updateImage();
	}
	
	/**
	 *  find his place in the universe
	 * @return 0 if his position is correct, else error value
	 */
	public GenerationErrorSide calculateNextPosition() {
		
		if (this.getX() + this.width() >= Generation.width -  Generation.right_margin_size + Generation.size_squads) {
			return GenerationErrorSide.GREATER_X;
		} else if (this.getX() < Generation.left_margin_size  + Generation.size_squads ) {
			return GenerationErrorSide.LOWER_X;
		}

		if (this.getY() + this.height() >= Generation.height - 2*Generation.bottom_margin_size) {
			return GenerationErrorSide.GREATER_Y;
		} else if (this.getY() < Generation.top_margin_size  + Generation.size_squads) {
			return GenerationErrorSide.LOWER_Y;
		}
		
		return GenerationErrorSide.GREATER_Y;
	}
	
	/**
	 *  check new position for generation
	 * @return 0 if ok. -1 if unable to generate
	 */
	public int updatePlanetePosition() {
		this.setX(this.getX() + this.width()/5);
		switch(this.calculateNextPosition()) {
			case GREATER_X:
				this.setY(this.getY() + Generation.height / 10);
				this.setX(Generation.left_margin_size  + Generation.size_squads);
				break;
			case LOWER_X:
				this.setX(Generation.left_margin_size  + Generation.size_squads);
				break;
			case GREATER_Y:
				return -1;
			case LOWER_Y:
				this.setX(Generation.top_margin_size  + Generation.size_squads);
				this.setY(Generation.top_margin_size  + Generation.size_squads + 1);
				break;
			default:
				return 0;
		}
		return 0;
	}


	public abstract String toString();

	/**
	 *  return if two planets are the same
	 * @param u
	 * @return true if they're both equals, else false
	 */
	public boolean equals(Planet p) {
		User user1 = this.getRuler();
		User user2 = p.getRuler();
		
		if (user1.equals(user2) && this.getX() == p.getX() && p.getY() == this.getY()) {
			return true;
		}
		return false;
	}
	/**
	 * @return the summonX
	 */
	public double getSummonX() {
		return summonX;
	}
	/**
	 * @return the summonY
	 */
	public double getSummonY() {
		return summonY;
	}
	/**
	 * @return the produce_rate
	 */
	public int getProduce_rate() {
		return produce_rate;
	}
	/**
	 * @return the troups
	 */
	public int getTroups() {
		return troups;
	}
	/**
	 * @return the ships_type
	 */
	public ShipType getShips_type() {
		return ships_type;
	}
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param summonX the summonX to set
	 */
	public void setSummonX(double summonX) {
		this.summonX = summonX;
	}
	/**
	 * @param summonY the summonY to set
	 */
	public void setSummonY(double summonY) {
		this.summonY = summonY;
	}
	/**
	 * @param produce_rate the produce_rate to set
	 */
	public void setProduce_rate(int produce_rate) {
		this.produce_rate = produce_rate;
	}
	/**
	 * @param troups the troups to set
	 */
	public void setTroups(int troups) {
		this.troups = troups;
	}
	/**
	 * @param ships_type the ships_type to set
	 */
	public void setShips_type(ShipType ships_type) {
		this.ships_type = ships_type;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	


}

package fr.groupe40.projet.model.ships;

import java.io.Serializable;

import fr.groupe40.projet.util.constants.ShipsParameters;


/**
 * \brief Class containing the ships parameters as speed, ...
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class ShipType implements Serializable {
	private static final long serialVersionUID = 6833813366446523473L;
	
	/**
	 * \brief the speed of this ship
	 */
	protected double speed;
	
	/**
	 * \brief the power of this ship (theorically, when a ship reached a planet, his garrison decrement from this value)
	 */
	protected double power;
	
	/**
	 * \brief the number of ships produced per update for his planet
	 */
	protected double production_time;
	
	/**
	 * \brief Generate random ship parameters
	 */
	public ShipType() {
		generate_parameters();
	}

	/**
	 * \brief Generate power, speed and production time for a planet/squad
	 */
	private void generate_parameters() {
		this.power = (int) (Math.random() * (ShipsParameters.max_ship_power - ShipsParameters.min_ship_power)+1);
		this.speed =  (int) (Math.random() * (ShipsParameters.max_ship_speed - ShipsParameters.min_ship_speed)+1);
		this.production_time =  (int) (Math.random() * (ShipsParameters.max_ship_produce - ShipsParameters.min_ship_produce)+1);
	}
}

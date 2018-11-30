package fr.groupe40.projet.model.ships;

import java.io.Serializable;

import fr.groupe40.projet.util.constants.Constants;

public class ShipType implements Serializable {
	private static final long serialVersionUID = 6833813366446523473L;
	
	protected double speed, power, production_time;
	public ShipType() {
		generate_parameters();
	}

	/**
	 * \brief Generate power, speed and production time for a planet/squad
	 */
	private void generate_parameters() {
		this.power = (int) (Math.random() * (Constants.max_ship_power - Constants.min_ship_power)+1);
		this.speed =  (int) (Math.random() * (Constants.max_ship_speed - Constants.min_ship_speed)+1);
		this.production_time =  (int) (Math.random() * (Constants.max_ship_produce - Constants.min_ship_produce)+1);
	}
}

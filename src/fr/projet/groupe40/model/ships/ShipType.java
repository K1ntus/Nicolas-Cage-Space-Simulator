package fr.projet.groupe40.model.ships;

import java.io.Serializable;

import fr.projet.groupe40.util.Constantes;

public class ShipType implements Serializable {

	protected double speed, power, production_time;
	public ShipType() {
		generate_parameters();
	}

	/**
	 * \brief Generate power, speed and production time for a planet/squad
	 */
	private void generate_parameters() {
		this.power = (int) (Math.random() * (Constantes.max_ship_power - Constantes.min_ship_power)+1);
		this.speed =  (int) (Math.random() * (Constantes.max_ship_speed - Constantes.min_ship_speed)+1);
		this.production_time =  (int) (Math.random() * (Constantes.max_ship_produce - Constantes.min_ship_produce)+1);
	}
}

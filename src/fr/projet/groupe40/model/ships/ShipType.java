package fr.projet.groupe40.model.ships;

import fr.projet.groupe40.util.Constantes;

public class ShipType {

	protected double speed, power, production_time;
	public ShipType() {
		// TODO Auto-generated constructor stub
		generate_parameters();
	}

	private void generate_parameters() {
		this.power = (int) (Math.random() * (Constantes.max_ship_power - Constantes.min_ship_power)+1);
		this.speed =  (int) (Math.random() * (Constantes.max_ship_speed - Constantes.min_ship_speed)+1);
		this.production_time =  (int) (Math.random() * (Constantes.max_ship_produce - Constantes.min_ship_produce)+1);
	}
}

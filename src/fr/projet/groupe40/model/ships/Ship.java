package fr.projet.groupe40.model.ships;

import java.io.Serializable;

import fr.projet.groupe40.util.Constantes;


public class Ship implements Serializable{
	private static final long serialVersionUID = 5784847797502383928L;
	/**
	 * 
	 */
	private int speed, power, production_time;



	public Ship(int speed, int power, int production_time) {
		this.speed = speed;
		this.power = power;
		this.production_time = production_time;
	}
	public Ship() {
		generate();
	}
	
	

	private void generate() {
		power = (int) (Math.random() * (Constantes.max_ship_power - Constantes.min_ship_power)+1);
		speed =  (int) (Math.random() * (Constantes.max_ship_speed - Constantes.min_ship_speed)+1);
		production_time =  (int) (Math.random() * (Constantes.max_ship_produce - Constantes.min_ship_produce)+1);
	}
	
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getProduction_time() {
		return production_time;
	}
	public void setProduction_time(int production_time) {
		this.production_time = production_time;
	}
}

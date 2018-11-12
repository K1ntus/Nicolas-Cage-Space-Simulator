package fr.projet.groupe111.model.ships;

import fr.projet.groupe111.util.Constantes;


public class Ship{
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
		power = (int) (Math.random() * (Constantes.max_shipPower - Constantes.min_shipPower));
		speed =  (int) (Math.random() * (Constantes.max_shipSpeed - Constantes.min_shipSpeed));
		production_time =  (int) (Math.random() * (Constantes.max_shipProduce - Constantes.min_shipProduce));
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

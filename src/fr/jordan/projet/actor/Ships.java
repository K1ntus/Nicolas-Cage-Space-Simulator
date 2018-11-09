package fr.jordan.projet.actor;

public class Ships {
	double speed;
	double produce_speed;
	int power;
	
	public Ships() {
		this.power = (int) Math.random() * (15-1);
		this.speed =  Math.random() * (15-1);
		this.produce_speed =  Math.random() * (15-1);
	}

}

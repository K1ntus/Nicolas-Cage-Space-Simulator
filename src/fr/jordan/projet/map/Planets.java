package fr.jordan.projet.map;

import fr.jordan.projet.Constante;
import fr.jordan.projet.actor.Ships;

public class Planets {
	int type;
	
	int produce_rate;
	int troups;
	
	Ships ships_type;

	public Planets() {
		this.type = Constante.neutral;
		
		this.troups = (int) Math.random() * (15-1);
		this.produce_rate = (int) Math.random() * (15-1);
		
		this.ships_type = new Ships();
	}

	
}

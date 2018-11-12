package fr.projet.groupe111.model.board;

import fr.projet.groupe111.model.Sprite;
import fr.projet.groupe111.model.client.User;
import fr.projet.groupe111.model.ships.Ship;
import fr.projet.groupe111.util.Constantes;

public class Planet extends Sprite {
	private int produce_rate;
	private int troups;
	
	private Ship ships_type;
	private User ruler;
	
	
	private boolean selected;

	public Planet(Sprite s) {
		super(s);
		generate();
		// TODO Auto-generated constructor stub
	}
	public Planet(Sprite s, Ship ships_type, User ruler) {
		super(s);
		generate();
		this.ships_type=ships_type;
	}

	private void generate() {
		selected = false;
		
		//width = Math.random() * Constantes.size_minimalPlanets *0.25 + Constantes.size_minimalPlanets;
		//height = width;
		
		//x = (Math.random() * (Constantes.width - width));
		//y = (Math.random() * (Constantes.height - height));
		

		troups = (int) (Math.random() * (Constantes.max_initDefense - Constantes.min_troups) +1);
		produce_rate =  (int) (Math.random() * (Constantes.max_shipProduce - Constantes.min_shipProduce) +1);
		
		ruler = new User(Constantes.neutral_user);
		ships_type = new Ship();
		
		
	}

	public void clickedOnPlanet(double x, double y) {
		if(isInside(x, y))
			System.out.println("Vous avez cliqu� sur une plan�te avec "+this.troups);
	}

	public boolean isInside(double x, double y) {
		if(x > x+width() || x < x) {
			return false;
		}
		
		if(y > y+height() || y < y) {
			return false;
		}
		return true;
	}
	
	public void updateGarrison() {
    	if(this.ruler.getFaction() != Constantes.neutral) {
    		if(troups < Constantes.max_troups) {
    			
    			troups = troups + produce_rate;	
    			
    			if(troups > Constantes.max_troups) {
    				troups = Constantes.max_troups;
    			}
    		
    		}
    	}
	}


	public User getRuler() {
		return ruler;
	}

	public void setRuler(User ruler) {
		this.ruler = ruler;
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

	public Ship getShips_type() {
		return ships_type;
	}

	public void setShips_type(Ship ships_type) {
		this.ships_type = ships_type;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

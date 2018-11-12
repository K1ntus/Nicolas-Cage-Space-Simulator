package fr.projet.groupe111.model.ships;

import fr.projet.groupe111.model.Sprite;
import fr.projet.groupe111.model.board.Planet;
import fr.projet.groupe111.util.Constantes;

public class Squad extends Sprite{
	private Planet destination;
	private Ship type;
	private int nb_of_ships;

	private boolean reached;
	

	public Squad(Sprite s, int nb_of_ships, Planet destination) {
		super(s);
		this.setNb_of_ships(nb_of_ships);
		// TODO Auto-generated constructor stub

		setReached(false);
	}

	public void setPosition(double d, double e) {
		// TODO Auto-generated method stub
		
	}

	public void remove() {
		destination = null;
		nb_of_ships = 0;
		this.setRuler(Constantes.neutral_user);
		this.setImage(null);
		this.reached = true;
	}

	public void updatePosition() {
		if(destination.isInside(this.getX(),this.getY())) {	//Case if the squads reach the destination
			
			if(this.getRuler().getFaction() != destination.getRuler().getFaction()) {	//If the faction are differents, then BOOM
				int difference = destination.getTroups() - nb_of_ships;
				
				if(difference >=1) {	//Difference > 1 => kamikaze
					destination.setTroups(difference +1);					
				} else {				//Else, negative or 0 => new leader
					destination.setRuler(this.getRuler());
					destination.setTroups(Math.abs(difference) + Constantes.max_initDefense);
				}
			}else if(this.getRuler().getFaction() == destination.getRuler().getFaction()) {	//Same faction
				int sum = nb_of_ships + destination.getTroups();	//Sum of defense + squad
				if(sum >= Constantes.max_troups) {	//Sum > 100, we lower the amount to stay at the limit
					destination.setTroups(Constantes.max_troups);					
				} else {	//Else, renforcement
					destination.setTroups(sum - nb_of_ships/5);
				}
			}
			remove();	//Remove the squads of the galaxy
			return;
		}

		double centre_x = destination.getX() + destination.width()/2; 
		double centre_y = destination.getY() + destination.height()/2;
		double x = this.getX(); double y = this.getY();
		if(x < centre_x) {
			x += type.getSpeed();
		} else {
			x -= type.getSpeed();
		}
		
		if(y < centre_y) {
			y += type.getSpeed();
		} else {
			y -= type.getSpeed();
		}
		
		validatePosition();
	}

	public int getNb_of_ships() {
		return nb_of_ships;
	}

	public void setNb_of_ships(int nb_of_ships) {
		this.nb_of_ships = nb_of_ships;
	}

	public boolean isReached() {
		return reached;
	}

	public void setReached(boolean reached) {
		this.reached = reached;
	}

}

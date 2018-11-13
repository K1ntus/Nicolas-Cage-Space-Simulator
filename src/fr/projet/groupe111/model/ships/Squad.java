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
		this.destination = destination;
		type = new Ship();
		setPosition(Constantes.width/2, Constantes.height/2);
		// TODO Auto-generated constructor stub

		setReached(false);
	}

	public void setPosition(double x, double y) {
		setX(x);
		setY(y);		
	}

	public void remove() {
		nb_of_ships = 0;
		this.setRuler(Constantes.neutral_user);
		this.setImage(null);
		this.reached = true;
	}

	public void updatePosition() {
		if(reached)
			return;
		
		if(destination.isInside(this)) {	//Case if the squads reach the destination			
			if(this.getRuler().getFaction() != destination.getRuler().getFaction()) {	//If the faction are differents, then BOOM
				int difference = destination.getTroups() - nb_of_ships;
				
				if(difference >=1) {	//Difference > 1 => kamikaze
					destination.setTroups(difference +1);					
				} else {				//Else, negative or 0 => new leader
					destination.setRuler(this.getRuler());
					
					difference = Math.abs(difference);
					if(difference >= Constantes.max_troups) {	//Sum > 100, we lower the amount to stay at the limit
						destination.setTroups(Constantes.max_troups);					
					} else {	//Else, renforcement
						destination.setTroups(difference + 1);
					}
				}
			}else if(this.getRuler().getFaction() == destination.getRuler().getFaction()) {	//Same faction
				int sum = nb_of_ships + destination.getTroups();	//Sum of defense + squad
				System.out.println("Somme: "+sum);
				if(sum >= Constantes.max_troups) {	//Sum > 100, we lower the amount to stay at the limit
					destination.setTroups(Constantes.max_troups -1);					
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
			setX(x+type.getSpeed());
		} else {
			setX(x-type.getSpeed());
		}
		
		if(y < centre_y) {
			setY(y+type.getSpeed());
		} else {
			setY(y-type.getSpeed());
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

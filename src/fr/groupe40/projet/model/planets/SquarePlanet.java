package fr.groupe40.projet.model.planets;

import java.io.Serializable;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.util.constants.Constants;

/**
 * \brief Square planet TODO
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class SquarePlanet extends Planet  implements Serializable{
	private static final long serialVersionUID = -7344260544964758721L;
	public SquarePlanet(String path, User ruler, int x, int y) {
		super(path, ruler, x, y);
	}

	public boolean isInsidePlanet(double x, double y) {
		if(isInside(x, y, 0, 0)) {
			if(Constants.DEBUG) {
				System.out.println("Vous avez clique sur une planete avec "+this.getTroups());
				System.out.println("Celle ci appartient a l'ID: "+ this.getRuler().getId());
				System.out.println("et de type: "+ this.getRuler().getFaction());
			}
			return true;
		}
		return false;
			
	}
	
	
	public String toString() {
		return "SquarePlanet <" + getX() + ", " + getY() + "> - Ruled by : " + this.getRuler().toString();
	}
}

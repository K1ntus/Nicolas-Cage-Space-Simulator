package fr.groupe40.projet.model.planets;

import java.io.Serializable;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.util.constants.Constants;

/**
 * \brief Round planet TODO
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class RoundPlanet extends Planet implements Serializable{
	private static final long serialVersionUID = -7903551302927756905L;

	public RoundPlanet(String pathImgPlanets, User user, int x, int y) {
		super(pathImgPlanets, user, x, y);
	}

	public boolean isInsidePlanet(Sprite p) {
		return intersectCircle(p.getX(), p.getY(), p.width(), p.height());
	}
	
	public boolean isInsidePlanet(double x, double y, double width, double height) {
		if(intersectCircle(x, y, width, height)) {
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
		return "RoundPlanet <" + getX() + ", " + getY() + ">";
	}

	@Override
	public boolean isInsidePlanet(double x, double y) {
		System.out.println("inside planet");
		return intersectCircle(x, y, 20, 20);
	}
}

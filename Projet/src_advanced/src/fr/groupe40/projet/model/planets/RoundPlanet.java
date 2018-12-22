package fr.groupe40.projet.model.planets;

import java.io.Serializable;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.util.constants.Constants;

/**
 *  Round planet
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class RoundPlanet extends Planet implements Serializable{
	private static final long serialVersionUID = -7903551302927756905L;

	/**
	 * Construct a squared planet, inherits from Planet & Sprites
	 * @param pathImgPlanets Img path
	 * @param user The initial ruler of this planet
	 * @param x top-left position of the planet
	 * @param y top-left position of the planet
	 */
	public RoundPlanet(String pathImgPlanets, User user, int x, int y) {
		super(pathImgPlanets, user, x, y);
	}
	
	/**
	 * Cast from any type of planet to round planet
	 * @param p the planet to cast from
	 */
	public RoundPlanet(Planet p) {
		super(p.getImg_path(), p.getRuler(), (int) p.getX(), (int) p.getY());
	}


	/**
	 *  Check if a pair of positions is inside this planet
	 * @param x position of the second object
	 * @param y position of the second object
	 * @return true if inside, else false
	 */
	@Override
	public boolean isInside(double x, double y) {
		if(this.isInside(x, y, 0, 0)) {
			if(Constants.DEBUG) {
				System.out.println("Vous avez clique sur une planete avec "+this.getTroups());
				System.out.println("Celle ci appartient a l'ID: "+ this.getRuler().getId());
				System.out.println("et de type: "+ this.getRuler().getFaction());
				System.out.println(this.toString());
				System.out.println("***************************");
			}
			return true;
		}
		return false;
			
	}

	/**
	 *  Check if a rectangle is inside another
	 * @param x	the x-top corner
	 * @param y the y-top corner
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 * @return true if inside else false
	 */
	@Override
	public boolean isInside(double x, double y, double width, double height) {
		
		double distance = Math.min(
				Math.min(this.distance(x, y), this.distance(x+width, y+height)),
				Math.min(this.distance(x+width, y), this.distance(x, y+height)));
		if(distance <= this.width()/2)
			return true;
		return false;
	}

	/**
	 *  Check if a sprite directly intersect another one
	 * @param s the sprite to compare with
	 * @return true if the sprite is inside, else false
	 */
	@Override
	public boolean isInside(Sprite s) {
		double x = this.getX(), y = this.getY();
		double width = this.width(), height = this.height();
		
		double x2 = s.getX(), y2 = s.getY();
		double width2 = s.width(), height2 = s.height();
		
		return ((x >= x2 && x <= x2 + width2) || (x2 >= x && x2 <= x + width))
				&& ((y >= y2 && y <= y2+ height2) || (y2 >= y && y2 <= y + height));
	}
	
	
	public String toString() {
		return "RoundPlanet <" + this.getX() + ", " + this.getY() + "> - Ruled by id: " +this.getRuler().getId();
	}

}

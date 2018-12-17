package fr.groupe40.projet.model.planets;

import java.io.Serializable;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;

/**
 * Square planet
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class SquarePlanet extends Planet  implements Serializable{
	private static final long serialVersionUID = -7344260544964758721L;
	/**
	 * Construct a squared planet, herits from Planet & Sprites
	 * @param path Img path
	 * @param ruler The initial ruler of this planet
	 * @param x top-left position of the planet
	 * @param y top-left position of the planet
	 */
	public SquarePlanet(String path, User ruler, int x, int y) {
		super(path, ruler, x, y);
	}
	

	/**
	 *  Check if a pair of pos is inside another
<<<<<<< HEAD
	 * @param x
	 * @param y
	 * @return
=======
	 * @param x position of the second object
	 * @param y position of the second object
	 * @return true if inside, else false
>>>>>>> masterrace
	 */
	@Override
	public boolean isInside(double x, double y) {
		if(isInside(x, y, 0, 0)) {
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
		double x2 = this.getX(), y2 = this.getY(), width2 = this.width(), height2 = this.height();
		if(x > x2+width2 || x+width < x2) {
			return false;
		}
		
		if(y > y2+height2 || y+height < y2) {
			return false;
		}
		return true;
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
		return "SquarePlanet <" + getX() + ", " + getY() + "> - Ruled by : " + this.getRuler().toString();
	}
}

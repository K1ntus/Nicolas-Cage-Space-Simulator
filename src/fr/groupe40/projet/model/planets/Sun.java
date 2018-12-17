package fr.groupe40.projet.model.planets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.annot.TODO;
import fr.groupe40.projet.util.constants.Debugging;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.PlanetsGarrison;
import fr.groupe40.projet.util.constants.Players;
import javafx.scene.canvas.GraphicsContext;


/**
 * A special planet, work a bit like a planet, but instead of getting ruled by a new player
 * It's exploding and remove every squad on board
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class Sun extends Planet implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * Generate the Sun 'planet'
	 * @param pathImgPlanets the string path to the image of the sun
	 * @param x his x
	 * @param y his y
	 */
	public Sun(String pathImgPlanets, int x, int y) {
		super(pathImgPlanets, Players.sun_user, x, y, Generation.size_sun);
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
	 *  Check if a pair of pos is inside another
	 * @param x position of the second object
	 * @param y position of the second object
	 * @return true if inside, else false
	 */
	@Override
	public boolean isInside(double x, double y) {
		if(isInside(x, y, 0, 0) && this.getRuler().equals(Players.sun_user)) {
			if(Debugging.DEBUG) {
				System.out.println("Vous avez clique sur le soleil");
			}
			return true;
		}
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


	@Override
	public String toString() {
		return "Sun <" + getX() + ", " + getY() + "> - Ruled by id: " +this.getRuler().getId();
	}


	/**
	 * Summoned when the sun got destroyed.
	 * The gif image rendering isnt working because of the sprite superposition by the background.
	 * We remove the sun from the planet list, render a sound and remove every squads + reset every garrison on board
	 * @param planets the list of planets that will get edited
	 * @param squads the list of squads that will be clean
	 * @param gc the graphics context to draw the image
	 */
	@TODO(comment="Render sun explosion")
	public static void sun_destroyed(ArrayList<Planet> planets, ArrayList<Squad> squads, GraphicsContext gc) {
		//Image explosion = new Image(Resources.path_gfx_ship_explosion, Generation.size_sun, Generation.size_sun, false, false, true);
		//gc.drawImage(explosion, planets.get(0).getX(), planets.get(0).getY());
		
		
		for(Planet p : planets) {
			p.setTroups(PlanetsGarrison.min_troups);
		}

		
		Iterator<Squad> it = squads.iterator();
		while (it.hasNext()) {
			squads.remove(it.next());
			it = squads.iterator();
		}
		
	}
}

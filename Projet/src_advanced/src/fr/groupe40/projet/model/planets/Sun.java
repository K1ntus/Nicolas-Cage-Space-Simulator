package fr.groupe40.projet.model.planets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.annot.TODO;
import fr.groupe40.projet.util.constants.Constants;
import javafx.scene.canvas.GraphicsContext;


/**
 * A special planet, work a bit like a planet, but instead of getting ruled by a new player
 * It's exploding and remove every squad on board
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class Sun extends RoundPlanet implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * Generate the Sun 'planet'
	 * @param pathImgPlanets the string path to the image of the sun
	 * @param x his x
	 * @param y his y
	 */
	public Sun(String pathImgPlanets, int x, int y) {
		super(pathImgPlanets, Constants.sun_user, x, y, Constants.size_sun);
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
		//Image explosion = new Image(Resources.path_gfx_ship_explosion, Constants.size_sun, Constants.size_sun, false, false, true);
		//gc.drawImage(explosion, planets.get(0).getX(), planets.get(0).getY());
		
		
		for(Planet p : planets) {
			p.setTroups(Constants.min_troups);
		}

		
		Iterator<Squad> it = squads.iterator();
		while (it.hasNext()) {
			squads.remove(it.next());
			it = squads.iterator();
		}
		
	}
}

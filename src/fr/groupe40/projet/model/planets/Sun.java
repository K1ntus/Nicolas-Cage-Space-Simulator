package fr.groupe40.projet.model.planets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.PlanetsGarrison;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sun extends Planet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sun(String pathImgPlanets, int x, int y) {
		super(pathImgPlanets, Players.sun_user, x, y, Generation.size_sun);
	}

	
	@Override
	public boolean isInside(double x, double y) {
		if(isInside(x, y, 0, 0) && this.getRuler().equals(Players.sun_user)) {
			//if(Debugging.DEBUG) {
				System.out.println("Vous avez clique sur le soleil");
			//}
			return true;
		}
		return false;
			
	}
	
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


	public static void sun_destroyed(ArrayList<Planet> planets, ArrayList<Squad> squads, GraphicsContext gc) {
		Image explosion = new Image(Resources.path_gfx_ship_explosion, Generation.size_sun, Generation.size_sun, false, false, true);
		


		renderExplosion(gc, planets.get(0).getX(), planets.get(0).getY() , explosion);
		
		for(Planet p : planets) {
			p.setTroups(PlanetsGarrison.min_troups);
		}

		Iterator<Squad> it = squads.iterator();
		
		while (it.hasNext()) {
			squads.remove(it.next());
			it = squads.iterator();
		}
		
	}

	public static void renderExplosion(GraphicsContext gc, double x, double y, Image explosion) {
		gc.drawImage(explosion, x, y);
		
	}
}

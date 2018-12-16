package fr.groupe40.projet.model.board;

import java.util.ArrayList;
import java.util.Iterator;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.planets.RoundPlanet;
import fr.groupe40.projet.model.planets.SquarePlanet;
import fr.groupe40.projet.model.planets.Sun;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.PlanetsGarrison;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.constants.Resources;
import javafx.concurrent.Task;

/**
 *  Galaxy Planets Generator
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class GalaxyGenerator extends Task<ArrayList<Planet>> {
	/**
	 *  the results planets array that has been generated
	 */
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	
	/**
	 *  Create and Generate the board
	 */
	public GalaxyGenerator() {
		try {
			this.call();
		} catch (Exception e) {
			System.out.println("******************************");
			System.out.println("* Unable to generate a board *");
			System.out.println("******************************");
			e.printStackTrace();
		}
	}
	


	@Override
	protected ArrayList<Planet> call() throws Exception {
		if(Constants.sun_enabled)
			generateSun();
		generatePlanets();
		return planets;
	}
	
	/*	Sun generation	*/

	/**
	 *  Generate a sun in the center of the board
	 */
	public void generateSun() {
		Planet sun = new Sun(Resources.path_img_sun, Generation.width/2, Generation.height/2);
		sun.updateImage();
		sun.setX(sun.getX() - sun.width()/2);
		sun.setY(sun.getY() - sun.width()/2);
		sun.setTroups(PlanetsGarrison.sun_troups);
		planets.add(sun);
	}

	/* Planets Generation */

	/**
	 *  return a randomly string path for round planet type
	 * @return the path of this ressource
	 */
	private String getRandomRoundPlanetImgPath() {
		double rand= Math.random();
		
		if(rand < 0.5)
			return Resources.path_img_square_time_night;
		return Resources.path_img_square_basic;
	}
	
	/**
	 *  return a randomly string path for square planet type
	 * @return the path of this ressource
	 */
	private String getRandomSquarePlanetImgPath() {
		double rand= Math.random();

		if(rand < 0.25)
			return Resources.path_img_round_planet2;
		else if(rand < 0.5)
			return Resources.path_img_round_doge;
		else if(rand < 0.75)
			return Resources.path_img_round_smoke;
		return Resources.path_img_round_kfc_planet;
	}
	
	/**
	 *  return a randomly generated planet type (ie. round or square)
	 * @return the planet generated
	 */
	private Planet getRandomPlanet() {
		double rand = Math.random();
		
		if(rand < 0.5)
			return new RoundPlanet(getRandomRoundPlanetImgPath(), new User(Players.neutral_user), (int) (Generation.left_margin_size + Generation.size_squads), 0);
		return new SquarePlanet(getRandomSquarePlanetImgPath(), new User(Players.neutral_user), (int) (Generation.left_margin_size + Generation.size_squads), 0);
	}
	
	/**
	 *  Generate the planets for the galaxy initialization
	 */
	public void generatePlanets() {
		double width = Math.random() * Generation.size_maximal_planets *0.25 + Generation.size_minimal_planets;
		double height = width;
		//Sprite(String path, double width, double height, double maxX, double maxY) 

		
		for(int i = 0; i < Generation.nb_planets_tentatives; i++) {
			double y = (Math.random() * (Generation.height - (height + Generation.bottom_margin_size)));
			Planet p = getRandomPlanet();

			p.setY(y);

			while(p.updatePlanetePosition() != -1) {
				if(isFarEnough(p, p.width()/2 + Generation.minimal_distance_between_planets)) {
					planets.add(p);	
					break;
				}
			}

		}
		
		if(planets.size() < Generation.min_numbers_of_planets) {	//si moins de 2 planetes
			System.out.println("Impossible de generer un terrain minimal");
			System.exit(-1);		//quitte le prgm
		}else {		//On attribue 2 planetes, une a l'ia, une au joueur
			planets.get(1).setRuler(Players.human_user);
			planets.get(1).setImg_path(Resources.path_img_planet_human);
			planets.get(1).updateImage();
			if(Constants.ai_enabled)
				planets.get(2).setRuler(Players.ai_user);
		}
		
		
	}

	/**
	 *  Test the valid position of a planet compare to each others.
	 *  
	 *  Please, do not use this function anymore
	 *  Really high complexity for the job he's doing
	 * @param p The planet we are trying to generate
	 * @return false if not able to generate this planet, else true
	 */
	@Deprecated
	private boolean testPlacement(Planet p) {
		Iterator<Planet> it = planets.iterator();
		
		while (it.hasNext()) {
			if(p.getY() > Generation.height - Generation.bottom_margin_size - Generation.size_squads - p.height()) {
				return false;
			}
			
			Planet p_already_placed = it.next();
			
			if(p_already_placed.isInside(p) || p_already_placed.intersectCircle(p, p.width()/2 + Generation.minimal_distance_between_planets)) {
				if(p.updatePlanetePosition() == -1) {
					System.out.println("unable to generate this planet");
					return false;
				}
				
				it = planets.iterator();
			}
		}
		
		return true;
	}

	/**
	 * Check if the planet passed in parameters is
	 * far enough from every others planets
	 * (ie. distance < radius)
	 * @param p The planet to check
	 * @param radius the minimal distance between each planets
	 * @return true if its ok, else false
	 */
	private boolean isFarEnough(Planet p, double radius) {
		Iterator<Planet> it = planets.iterator();
		while (it.hasNext()) {
			Planet planet_already_placed = it.next();
			double distance = p.distance(planet_already_placed) - p.width()/2;
			
			if(distance < radius) {
				return false;
			}
			
		}
		
		return true;
	}
	
	/**
	 * @return the planets
	 */
	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	/**
	 * @param planets the planets to set
	 */
	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
}

package fr.groupe40.projet.model.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.planets.PlanetFactory;
import fr.groupe40.projet.model.planets.RoundPlanet;
import fr.groupe40.projet.util.annot.TODO;
import fr.groupe40.projet.util.constants.ColorAI;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Resources;
import javafx.concurrent.Task;

/**
 * Galaxy Planets Generator
 * 
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class GalaxyGenerator extends Task<ArrayList<Planet>> {
	/**
	 * the results planets array that has been generated
	 */
	private ArrayList<Planet> planets = new ArrayList<Planet>();

	/**
	 * First id available for the next ai
	 */
	public static int first_free_id = Constants.ai_faction;

	/**
	 * Singleton instance
	 */
	private static GalaxyGenerator instance = null;

	/**
	 * Create and Generate the board
	 */
	private GalaxyGenerator() {
		try {
			this.call();
		} catch (Exception e) {
			System.out.println("******************************");
			System.out.println("* Unable to generate a board *");
			System.out.println("******************************");
			e.printStackTrace();
		}
	}

	/**
	 * Get the singleton instance
	 * @return
	 */
	public static GalaxyGenerator getInstance() {
		if (instance == null)
			instance = new GalaxyGenerator();
		return instance;
	}

	/**
	 * Reset the singleton instance
	 */
	public static void resetInstance() {
		instance = null;
	}

	/**
	 * Call when this object is created. Generate the planets and users list
	 */
	@Override
	protected ArrayList<Planet> call() throws Exception {
		if (Constants.sun_enabled)
			generateSun();
		boolean state = generatePlanets();
		if (state)
			return planets;
		else
			return null;
	}

	/**
	 * Generate a sun in the center of the board
	 */
	public void generateSun() {
		Planet sun = PlanetFactory.getPlanet(PlanetFactory.TYPE_SUN);
		planets.add(sun);
	}

	/* Planets Constants */

	/**
	 * return a randomly string path for round planet type
	 * 
	 * @return the path of this resource
	 */
	@TODO(comment = "replace this function by a random picker from a collection")
	private static String getRandomSquarePlanetImgPath() {
		double rand = Math.random();

		if (rand < 0.5)
			return Resources.path_img_square_time_night;
		return Resources.path_img_square_basic;
	}

	/**
	 * return a randomly string path for square planet type
	 * 
	 * @return the path of this resource
	 */
	@TODO(comment = "replace this function by a random picker from a collection")
	private static String getRandomRoundPlanetImgPath() {
		double rand = Math.random();

		if (rand < 0.5)
			return Resources.path_img_round_planet2;
		return Resources.path_img_round_planet1;
		/*
		 * else if (rand < 0.5) return Resources.path_img_round_doge; else if (rand <
		 * 0.75) return Resources.path_img_round_smoke; return
		 * Resources.path_img_round_kfc_planet;
		 */
	}

	/**
	 * return a randomly generated planet type (ie. round or square)
	 * 
	 * @return the planet generated
	 */
	private Planet getRandomPlanet() {
		double rand = Math.random();

		if (rand < 0.5)
			return PlanetFactory.getPlanet(PlanetFactory.TYPE_SQUARE);
		return PlanetFactory.getPlanet(PlanetFactory.TYPE_ROUND);
	}

	/**
	 * Generate the planets for the galaxy initialization
	 * 
	 * @return true if the generation worked, else false
	 */
	public boolean generatePlanets() {
		double width = Math.random() * Constants.size_maximal_planets * 0.25 + Constants.size_minimal_planets;
		double height = width;
		// Sprite(String path, double width, double height, double maxX, double maxY)

		for (int i = 0; i < Constants.nb_planets_tentatives; i++) {
			double y = (Math.random() * (Constants.height - (height + Constants.bottom_margin_size)));
			Planet p = getRandomPlanet();

			p.setY(y);

			while (p.updatePlanetePosition() != -1) {
				if (isFarEnough(p, p.width() / 2 + Constants.minimal_distance_between_planets)) {
					if (planets.size() == 1 && Constants.sun_enabled) // Only the sun has been generated
						p = new RoundPlanet(p);
					if(p.getClass().getName() == "RoundPlanet")
						p.setImg_path(getRandomRoundPlanetImgPath());
					else
						p.setImg_path(getRandomSquarePlanetImgPath());
					p.updateImage();
					planets.add(p);
					break;
				}
			}

		}

		if (planets.size() < Constants.min_numbers_of_planets) { // Summoned when there's less planet than this value
																	// (usually 3 if sun, else set to 2)
			System.out.println("Impossible de generer un terrain minimal");
			return false;
		} else { // We set 2 planets, one to the player, one to an ai
			planets.get(1).setRuler(Constants.human_user);
			planets.get(1).setImg_path(Resources.path_img_planet_human);
			planets.get(1).updateImage();

			/*
			 * Loop to set planets to AI user
			 */
			if (Constants.ai_enabled) {
				for (int i = 1; i < Constants.ai_number - 1 && i < ColorAI.values().length; i++)
					try {
						planets.get(1 + i).setRuler(new User());
					} catch (IndexOutOfBoundsException e) {
						// Not enough planets to handle every ai
						break;
					}
			}
		}

		ArrayList<Planet> planet_ai = new ArrayList<Planet>();
		for (Planet p : planets) {
			if (p.getRuler().getFaction() == Constants.ai_faction)
				planet_ai.add(p);
		}

		normalize_beginning_troups(planets.get(1), planet_ai);
		return true;
	}

	/**
	 * Set the troops for the human player. Depend of the difficulty
	 * 
	 * @param planet_human the planet to change troops
	 * @param planet_ai    an array list with every planets ruled by an ai
	 */
	private void normalize_beginning_troups(Planet planet_human, List<Planet> planet_ai) {
		int max_troups_from_ai = -1;
		for (Planet p : planet_ai) {
			if (p.getTroups() > max_troups_from_ai)
				max_troups_from_ai = p.getTroups();
		}

		switch (Constants.difficulty) {
			case INITIE:
				planet_human.setTroups((int) (max_troups_from_ai * 1.5));
				break;
			case SPACE_MARINE:
				planet_human.setTroups((int) (max_troups_from_ai * 1));
				break;
			case PRAETOR:
				planet_human.setTroups((int) (max_troups_from_ai * 0.75));
				break;
			case PRIMARQUE:
				planet_human.setTroups((int) (max_troups_from_ai * 0.5));
				break;
		}
	}

	/**
	 * Test the valid position of a planet compare to each others.
	 * 
	 * Please, do not use this function anymore Really high complexity for the job
	 * he's doing
	 * 
	 * @deprecated use isFarEnought function instead of this one
	 * @param p The planet we are trying to generate
	 * @return false if not able to generate this planet, else true
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private boolean testPlacement(Planet p) {
		Iterator<Planet> it = planets.iterator();

		while (it.hasNext()) {
			if (p.getY() > Constants.height - Constants.bottom_margin_size - Constants.size_squads - p.height()) {
				return false;
			}

			Planet p_already_placed = it.next();

			if (p_already_placed.isInside(p) || p_already_placed.intersectCircle(p,
					p.width() / 2 + Constants.minimal_distance_between_planets)) {
				if (p.updatePlanetePosition() == -1) {
					System.out.println("unable to generate this planet");
					return false;
				}

				it = planets.iterator();
			}
		}

		return true;
	}

	/**
	 * Check if the planet passed in parameters is far enough from every others
	 * planets (ie. distance < radius)
	 * 
	 * @param p      The planet to check
	 * @param radius the minimal distance between each planets
	 * @return true if its ok, else false
	 */
	private boolean isFarEnough(Planet p, double radius) {
		Iterator<Planet> it = planets.iterator();
		while (it.hasNext()) {
			Planet planet_already_placed = it.next();
			double distance = p.distance(planet_already_placed) - p.width() / 2;

			if (distance < radius) {
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

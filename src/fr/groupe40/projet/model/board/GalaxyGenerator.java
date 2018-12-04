package fr.groupe40.projet.model.board;

import java.util.ArrayList;
import java.util.Iterator;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.planets.RoundPlanet;

import fr.groupe40.projet.model.planets.SquarePlanet;
import fr.groupe40.projet.util.constants.Constants;

/**
 * \brief Galaxy Planets Generator
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
@SuppressWarnings("unused")
public class GalaxyGenerator {
	/**
	 * \brief the results planets array that has been generated
	 */
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	
	/**
	 * \brief Create and Generate the board
	 */
	public GalaxyGenerator() {
		generatePlanets();
	}

	/* Planets Generation */
	/**
	 * \brief Generate the planets for the galaxy initialization
	 */
	public void generatePlanets() {
		double width = Math.random() * Constants.size_maximal_planets *0.25 + Constants.size_minimal_planets;
		double height = width;
		//Sprite(String path, double width, double height, double maxX, double maxY) 

		
		for(int i = 0; i < Constants.nb_planets_tentatives; i++) {
			double y = (Math.random() * (Constants.height - (height + Constants.bottom_margin_size)));
			Planet p;
			if(!Constants.DEBUG)
				p = new RoundPlanet(Constants.path_img_round_kfc_planet, new User(Constants.neutral_user), (int) (Constants.left_margin_size + Constants.size_squads), 0);
			else
				p = new SquarePlanet(Constants.path_img_square_nicolas_cage, new User(Constants.neutral_user), (int) (Constants.left_margin_size + Constants.size_squads), 0);

			p.setY(y);
			p.validatePosition();
			
			if(testPlacement(p)) {
				getPlanets().add(p);
			}
		}
		
		if(getPlanets().size() < Constants.min_numbers_of_planets) {	//si moins de 2 planetes
			System.out.println("Impossible de generer un terrain minimal");
			System.exit(-1);		//quitte le prgm
		}else {		//On attribue 2 planetes, une a l'ia, une au joueur
			getPlanets().get(1).setRuler(Constants.human_user);
			if(Constants.is_ai)
				getPlanets().get(2).setRuler(Constants.ai_user);
		}
		
		
	}

	/**
	 * \brief Test the valid position of a planet compare to each others.
	 * @param p The planet we are trying to generate
	 * @return false if not able to generate this planet, else true
	 */
	private boolean testPlacement(Planet p) {
		Iterator<Planet> it = getPlanets().iterator();
		
		while (it.hasNext()) {
			if(p.getY() > Constants.height - Constants.bottom_margin_size - Constants.size_squads - p.height()) {
				return false;
			}
			
			Planet p_already_placed = it.next();
			
			if(p_already_placed.isInside(p) || p_already_placed.intersectCircle(p, p.width()/2 + Constants.minimal_distance_between_planets)) {
				if(p.updatePlanetePosition() == -1) {
					System.out.println("unable to generate this planet");
					return false;
				}
				
				it = getPlanets().iterator();
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

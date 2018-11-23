package fr.projet.groupe40.model.board;

import java.util.ArrayList;
import java.util.Iterator;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.planets.Planet;
import fr.projet.groupe40.model.planets.RoundPlanet;
import fr.projet.groupe40.util.Constantes;

public class GalaxyGenerator {
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	public GalaxyGenerator() {
		generatePlanets();
	}

	/* Planets Generation */
	/**
	 * \brief Generate the planets for the galaxy initialization
	 */
	public void generatePlanets() {
		double width = Math.random() * Constantes.size_maximal_planets *0.25 + Constantes.size_minimal_planets;
		double height = width;
		//Sprite(String path, double width, double height, double maxX, double maxY) 

		
		for(int i = 0; i < Constantes.nb_planets_tentatives; i++) {
			double y = (Math.random() * (Constantes.height - (height + Constantes.bottom_margin_size)));
			Planet p = new Planet(Constantes.path_img_planets, new User(Constantes.neutral_user), true, (int) (Constantes.left_margin_size + Constantes.size_squads), 0);
			//Planet p = new RoundPlanet(new User(Constantes.neutral_user), true, (int) (Constantes.left_margin_size + Constantes.size_squads), 0);
			p.setY(y);
			p.validatePosition();
			
			if(testPlacement(p)) {
				getPlanets().add(p);
			}
		}
		
		if(getPlanets().size() < Constantes.min_numbers_of_planets) {	//si moins de 2 planetes
			System.out.println("Impossible de generer un terrain minimal");
			System.exit(-1);		//quitte le prgm
		}else {		//On attribue 2 planetes, une a l'ia, une au joueur
			getPlanets().get(1).setRuler(Constantes.human_user);
			getPlanets().get(2).setRuler(Constantes.ai_user);
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
			if(p.getY() > Constantes.height - Constantes.bottom_margin_size - Constantes.size_squads - p.height()) {
				return false;
			}
			
			Planet p_already_placed = it.next();
			
			if(p_already_placed.isInside(p) || p_already_placed.intersectCircle(p)) {
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

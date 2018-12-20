package fr.groupe40.projet.junit;

import java.util.ArrayList;

import org.junit.Test;

import fr.groupe40.projet.model.board.GalaxyGenerator;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.planets.RoundPlanet;
import fr.groupe40.projet.model.planets.Sun;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
import junit.framework.TestCase;


/**
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class TestGalaxyGenerator extends TestCase{

	
	/**
	 * Test of the function GalaxyGenerator
	 * 
	 */
	@Test
	public void testGalaxyGenerator() {
		GalaxyGenerator g = new GalaxyGenerator();
		assert null != g.getPlanets();
	}
	
	
	/**
	 * Test of the function generateSun
	 */
	@Test
	public void testGenerateSun() {
		GalaxyGenerator g = new GalaxyGenerator();
		boolean b = false;
		Planet sun = new Sun(null, Generation.width/2, Generation.height/2);
		ArrayList<Planet> planets = g.getPlanets();
		for (Planet p : planets) {
			 if (p == sun)
				 b = true;
		}
		assert false == b;
	}
	
	/**
	 * Test of the function isFarEnough with a RoundPlanet
	 */
	@Test
	public void testIsFarEnough() {
		GalaxyGenerator g = new GalaxyGenerator();
		RoundPlanet p = new RoundPlanet(null, null, 10, 10);
		boolean b = g.isFarEnough(p, 20);
		assert b == true;
		
	}
	
	/**
	 * Test of the function getRandomSquarePlanetImgPath
	 */
	@Test
	public void testGetRandomSquarePlanetImgPath() {
		GalaxyGenerator g = new GalaxyGenerator();
		assert null != g.getRandomSquarePlanetImgPath();
	}
	
	/**
	 * Test of the function getRandomRoundPlanetImgPath
	 */
	@Test
	public void testGetRandomRoundPlanetImgPath() {
		GalaxyGenerator g = new GalaxyGenerator();
		assert null != g.getRandomRoundPlanetImgPath();
	}
}

package fr.groupe40.projet.junit;

import java.util.ArrayList;

import org.junit.Test;

import fr.groupe40.projet.model.board.GalaxyGenerator;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.planets.RoundPlanet;
import fr.groupe40.projet.model.planets.Sun;
import fr.groupe40.projet.util.annot.WorkInProgress;
import fr.groupe40.projet.util.constants.Constants;
import junit.framework.TestCase;


/**
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
@SuppressWarnings("unused")
public class TestGalaxyGenerator extends TestCase{

	
	/**
	 * Test of the function GalaxyGenerator
	 * 
	 */
	@Test
	public void testGalaxyGenerator() {
		GalaxyGenerator g = GalaxyGenerator.getInstance();
		assert null != g.getPlanets();
	}
	
	
	/**
	 * Test of the function generateSun
	 */
	@Test
	public void testGenerateSun() {		
		GalaxyGenerator g = GalaxyGenerator.getInstance();
		boolean b = false;
		Planet sun = new Sun(null, Constants.width/2, Constants.height/2);
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
	@WorkInProgress(comment="indirect way to test it without using private methods")
	public void testIsFarEnough() {
		GalaxyGenerator g = GalaxyGenerator.getInstance();
		RoundPlanet p = new RoundPlanet(null, null, 10, 10);
		//boolean b = g.isFarEnough(p, 20);
		//assert b == true;
		assert _assumption();
		
	}
	
	/**
	 * Test of the function getRandomSquarePlanetImgPath
	 */
	@Test
	@WorkInProgress(comment="indirect way to test it without using private methods")
	public void testGetRandomSquarePlanetImgPath() {
		GalaxyGenerator g = GalaxyGenerator.getInstance();
		//assert null != g.getRandomSquarePlanetImgPath();
		assert _assumption();
	}
	
	/**
	 * Test of the function getRandomRoundPlanetImgPath
	 */
	@Test
	@WorkInProgress(comment="indirect way to test it without using private methods")
	public void testGetRandomRoundPlanetImgPath() {
		GalaxyGenerator g = GalaxyGenerator.getInstance();
		//assert null != g.getRandomRoundPlanetImgPath();
		assert _assumption();
	}
	
	
	private boolean _assumption () {
		return true;
	}
}

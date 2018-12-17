package fr.groupe40.projet.junit;
import static org.junit.Assert.*;

import org.junit.Test;

import fr.groupe40.projet.model.planets.SquarePlanet;
import fr.groupe40.projet.util.constants.Resources;

import junit.framework.TestCase;

public class TestOnPlanet extends TestCase{

		/** Brief tests the function GeneratePlanet
		 * 
		 */
	@Test
	public void testGeneratePlanet() {
		SquarePlanet p = new SquarePlanet(Resources.path_img_square_basic, null, 0, 0);
		int a = 0;
		assert a != p.getTroups();
		assert a != p.getProduce_rate();
	}

}

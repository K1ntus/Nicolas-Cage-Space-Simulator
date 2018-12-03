package fr.groupe40.projet.junit;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.groupe40.projet.model.planets.SquarePlanet;
import fr.groupe40.projet.model.ships.Ship;
import fr.groupe40.projet.util.constants.Constants;
import junit.framework.Assert;

public class ClasseTests {

	@Test
	 public void testSetX() {
		 Ship tester = new Ship(null, null, null, null, 3, 10, null); // MyClass is tested
		 	tester.setX(0.0);
		 	assertEquals(0.0, tester.getX(), 0);
	} 
	
	@Test
	public void testSetY() {
		 Ship tester = new Ship(null, null, null, null, 10, 40, null); // MyClass is tested
		 	tester.setY(5.0);
	        Assert.assertEquals(5.0, tester.getY());
	}
	@Test
	public void testSetMinX() {
		 Ship tester = new Ship(null, null, null, null, 13, 14, null); // MyClass is tested
		 	tester.setMinX(0.0);
	        Assert.assertEquals(0.0, tester.getMinX());
	}
	
	@Test
	public void testSetMinY() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 	tester.setMinY(1.0);
	        Assert.assertEquals(1.0, tester.getMinY());
	}
	
	@Test
	public void testisInside() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 SquarePlanet p = new SquarePlanet(null, null, 500, 500);
		 assertEquals(false, tester.isInside(p));
		 tester.setX(p.getX());
		 tester.setY(p.getY());
	     Assert.assertEquals(true, tester.isInside(p));
	}
	
	@Test
	public void testisInside2() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null);
		 Assert.assertEquals(false ,tester.isInside(500,500, Constants.size_minimal_planets, Constants.size_minimal_planets));
		
	}
	
	@Test
	public void testisInside3() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null);
		 Assert.assertEquals(true ,tester.isInside(1,1, Constants.size_minimal_planets, Constants.size_maximal_planets));
	}
	
	@Test
	public void testdistance() {
		Ship tester = new Ship(null, null, null, null, 0, 0, null);
		double a = 100.0;
		double b = 200.0;
		double c = Math.sqrt((a * a) + (b * b));
		Assert.assertEquals(c, tester.distance(tester.getX(), tester.getY(),a,b));
	}
}

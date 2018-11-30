package fr.groupe40.projet.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Ship;

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
	        assertEquals(5.0, tester.getY());
	}
	@Test
	public void testSetMinX() {
		 Ship tester = new Ship(null, null, null, null, 13, 14, null); // MyClass is tested
		 	tester.setMinX(0.0);
	        assertEquals(0.0, tester.getMinX());
	}
	
	@Test
	public void testSetMinY() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 	tester.setMinY(2.0);
	        assertEquals(2.0, tester.getMinY());
	}
	
	@Test
	public void testisInside() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 Planet p = new Planet(null, null, false, 500, 500);
		 assertEquals(false, tester.isInside(p));
		 tester.setX(p.getX());
		 tester.setY(p.getY());
	     assertEquals(true, tester.isInside(p));
	}
	/*
	@Test
	public void testisInside2() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null);
		 Assert.assertEquals(false ,tester.isInside(500,500, Constantes.size_maximal_planets, Constantes.size_maximal_planets));
		 Assert.assertEquals(false ,tester.isInside(1,1, Constantes.size_maximal_planets, Constantes.size_maximal_planets));
	}
	*/
}

package fr.groupe40.projet.junit;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

<<<<<<< HEAD
import fr.groupe40.projet.model.planets.Planet;
=======
import fr.groupe40.projet.model.Sprite;
>>>>>>> masterrace
import fr.groupe40.projet.model.planets.SquarePlanet;
import fr.groupe40.projet.model.ships.Ship;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.ShipsParameters;

@SuppressWarnings("unchecked")
public class ClasseTests {
	/**
	 * test of the function setX() with case 0.0
	 */
	@Test
	 public void testSetX() {
		 Ship tester = new Ship(null, null, null, null, 3, 10, null); // MyClass is tested
		 tester.setX(0.0);
		 assert 0.0 == tester.getX();
		 //assertEquals(0.0, tester.getX(), 0);
	}
	
	/**
	 * test of the function setY() with case 5.0
	 */
	@Test
	public void testSetY() {
		 Ship tester = new Ship(null, null, null, null, 10, 40, null); // MyClass is tested
		 tester.setY(5.0);
		 assert 5.0 == tester.getY();
	    // assertEquals(5.0, tester.getY());
	}
	
	/**
	 * test of the function setmMinX() with case 0.0
	 */
	@Test
	public void testSetMinX() {
		 Ship tester = new Ship(null, null, null, null, 13, 14, null); // MyClass is tested
		 tester.setMinX(0.0);
		 assert 0.0 == tester.getMinX();
	     //assertEquals(0.0, tester.getMinX());
	}
	
	/**
	 * test of the function setMinY() with case 1.0
	 */
	@Test
	public void testSetMinY() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 tester.setMinY(1.0);
		 assert 1.0 == tester.getMinY();
	    // assertEquals(1.0, tester.getMinY());
	}
	
	/**
	 * test of the function isInside(Sprite s) with SquarePlanet objects, case true
	 */
	@Test
	public void testisInsideSquarePlanet() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 SquarePlanet p = new SquarePlanet(null, null, 10, 10);
		 assert true ==  tester.isInside(p);
	     //assertEquals(true, tester.isInside(p));
	}
	
	
	/**
	 * test of the function isInside(Sprite s) with SquarePlanet objects, case false
	 */
	@Test
	public void testisInsideSquarePlanet2() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 SquarePlanet p = new SquarePlanet(null, null, 900, 900);
		 assertEquals(false, tester.isInside(p));
		 assert false ==  tester.isInside(p);
	     //assertEquals(true, tester.isInside(p));
	}
	
	
	/**
	 * test of the function isInside(Sprite s) with RoundPlanet objects, case false
	 */
	@Test
	public void testisInsideRoundPlanet() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 SquarePlanet p = new SquarePlanet(null, null, 900, 900);
		 assert false ==  tester.isInside(p);
	}
	

	/**
	 * test of the function isInside(Sprite s) with RoundPlanet objects, case true
	 */
	@Test
	public void testisInsideRoundPlanet2() {
		 Ship tester = new Ship(null, null, null, null, 10, 10, null); // MyClass is tested
		 SquarePlanet p = new SquarePlanet(null, null, 10, 10);
		 assert true ==  tester.isInside(p);
	}
	
	
	/**
	 * test of the function isInside(double x, double y, double width, double height) case false
	 */
	@Test
	public void testisInside2() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null);
		 assert false == tester.isInside(500,500, Generation.size_minimal_planets, Generation.size_minimal_planets);
		
	}
	
	
	/**
	 * test of the function isInside(double x, double y, double width, double height) case true
	 */
	@Test
	public void testisInside3() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null);
		 assert true == tester.isInside(1,1, Generation.size_minimal_planets, Generation.size_maximal_planets) ;
	}
	
	
	/**
	 * test of the function distance(double x, double y)
	 */
	@Test
	public void testdistance() {
		Ship tester = new Ship(null, null, null, null, 0, 0, null);
		double a = 100.0;
		double b = 200.0;
		double c = Math.sqrt((a * a) + (b * b));
<<<<<<< HEAD
		assert c == tester.distance(tester.getX(), tester.getY(),a,b);
=======
		assertEquals(c, Sprite.distance(tester.getX(), tester.getY(),a,b));
>>>>>>> masterrace
	}
	
	/**
	 * test of the function distance(Sprite p) with an object at a pos of (0,0)
	 * \ and a SquarePlanet with a pos of (100,200)
	 */
	@Test
	public void testdistance2() {
		Ship tester = new Ship(null, null, null, null, 0, 0, null);
		SquarePlanet p = new SquarePlanet(null, null, 100, 200);
		double x = 100.0 - (Generation.size_squads /2);
		double y = 200.0 - (Generation.size_squads /2);
		double c = Math.sqrt((x * x) + (y * y));
		assert c == tester.distance(p);
		
	}
	/**
	 * test of the function distance(Sprite p) with a ship at a pos of (0,0)
	 * \ and a RoundPlanet with a pos of (100,200)
	 */
	@Test
	public void testdistance3() {
		Ship tester = new Ship(null, null, null, null, 0, 0, null);
		SquarePlanet p = new SquarePlanet(null, null, 100, 200);
		double x = 100.0 - (Generation.size_squads /2);
		double y = 200.0 - (Generation.size_squads /2);
		double c = Math.sqrt((x * x) + (y * y));
		assert c == tester.distance(p);
		
	}
	
	/**
	 * test of the function validatePosition() with a RoundPlanet at a pos of (-10,-10)
	 */
	/*
	@Test
	public void testValidatePosition() {
		RoundPlanet p = new RoundPlanet(null, null, -10, -10);
		System.out.println(p.getMaxX());
		System.out.println(p.getMaxY());
		p.validatePosition();
		System.out.println(p.getX());
		System.out.println(p.getY());
		assert 0 != p.getX() && 0 != p.getY();
				
	}
	*/
	
	/**
	 * test of the function whereis_collision(double x, double y, double speed, List<Planet> planets) with a RoundPlanet at a pos of (150,150)
	 * \and a ship
	 */
	@Test
	public void TestWhereis_collision() {
		Ship tester = new Ship(null, null, null, null, 0, 0, null);
		SquarePlanet p = new SquarePlanet(null, null, 100, 200);
		List<Planet> planets = new ArrayList();
		planets.add(p);
		double speed = ShipsParameters.min_ship_speed;
		tester.whereis_collision(tester.getX(), tester.getY(),speed,planets);
		
	} 
	
	/**
	 * test of the function calc_next_position with a RoundPlanet at a pos of (150,150)
	 * \and a ship
	 */
	@Test
	public void TestCalc_next_position() {
		Ship tester = new Ship(null, null, null, null, 0, 0, null);
		SquarePlanet p = new SquarePlanet(null, null, 100, 200);
		List<Planet> planets = new ArrayList();
		planets.add(p);
		
		tester.setDestination(p);
		
		tester.calc_next_position(planets);
		
	}
	
}

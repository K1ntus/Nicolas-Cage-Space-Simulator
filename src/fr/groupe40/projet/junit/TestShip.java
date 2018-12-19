package fr.groupe40.projet.junit;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.sun.javafx.scene.traversal.Direction;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.planets.SquarePlanet;
import fr.groupe40.projet.model.ships.Ship;
import fr.groupe40.projet.model.ships.ShipType;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.ShipsParameters;
import junit.framework.TestCase;


/**
 * 
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class TestShip extends TestCase{
	/**
	 * Test of the function setX() with case 0.0
	 */
	@Test
	 public void testSetX() {
		 Ship tester = new Ship(null, null, null, null, 3, 10, null); // MyClass is tested
		 tester.setX(0.0);
		
		 assert 0.0 == tester.getX();
		 //assertEquals(0.0, tester.getX(), 0);
	}
	
	/**
	 * Test of the function setY() with case 5.0
	 */
	@Test
	public void testSetY() {
		 Ship tester = new Ship(null, null, null, null, 10, 40, null); // MyClass is tested
		 tester.setY(5.0);
		 
		 assert 5.0 == tester.getY();
	    // assertEquals(5.0, tester.getY());
	}
	
	/**
	 * Test of the function setmMinX() with case 0.0
	 */
	@Test
	public void testSetMinX() {
		 Ship tester = new Ship(null, null, null, null, 13, 14, null); // MyClass is tested
		 tester.setMinX(0.0);
		
		 assert 0.0 == tester.getMinX();
	     //assertEquals(0.0, tester.getMinX());
	}
	
	/**
	 * Test of the function setMinY() with case 1.0
	 */
	@Test
	public void testSetMinY() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 tester.setMinY(1.0);
		
		 assert 1.0 == tester.getMinY();
	    // assertEquals(1.0, tester.getMinY());
	}
	
	/**
	 * Test of the function isInside(Sprite s) with SquarePlanet objects, case true
	 */
	@Test
	public void testisInsideSquarePlanet() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null); // MyClass is tested
		 SquarePlanet p = new SquarePlanet(null, null, 10, 10);
		 
		 assert true ==  tester.isInside(p);
	     //assertEquals(true, tester.isInside(p));
	}
	
	
	/**
	 * Test of the function isInside(Sprite s) with SquarePlanet objects, case false
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
	 * Test of the function isInside(Sprite s) with SquarePlanet objects, case true
	 */
	@Test
	public void testisInsideRoundPlanet2() {
		 Ship tester = new Ship(null, null, null, null, 10, 10, null); // MyClass is tested
		 SquarePlanet p = new SquarePlanet(null, null, 10, 10);
		 
		 assert true ==  tester.isInside(p);
	}
	
	
	/**
	 * Test of the function isInside(double x, double y, double width, double height) case false
	 */
	@Test
	public void testisInside2() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null);
		 
		 assert false == tester.isInside(500,500, Generation.size_minimal_planets, Generation.size_minimal_planets);
		
	}
	
	
	/**
	 * Test of the function isInside(double x, double y, double width, double height) case true
	 */
	@Test
	public void testisInside3() {
		 Ship tester = new Ship(null, null, null, null, 11, 12, null);
		 assert true == tester.isInside(1,1, Generation.size_minimal_planets, Generation.size_maximal_planets) ;
	}
	
	
	/**
	 * Test of the function distance(double x, double y)
	 */
	@Test
	public void testdistance() {
		Ship tester = new Ship(null, null, null, null, 0, 0, null);
		double a = 100.0;
		double b = 200.0;
		double c = Math.sqrt((a * a) + (b * b));
		
		assert c == tester.distance(tester.getX(), tester.getY(),a,b);
	}
	
	/**
	 * Test of the function distance(Sprite p) with an object at a pos of (0,0)
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
	 * Test of the function distance(Sprite p) with a ship at a pos of (0,0)
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
	 * Test of the function validatePosition() with a SquarePlanet at a pos of (-10,-10)
	 */
	
	@Test
	public void testValidatePosition() {
		SquarePlanet p = new SquarePlanet(null, null, -10, -10);
		p.validatePosition();
		assert 0 == p.getX() && 0 != p.getY();
				
	}

	
	/*
	@Test
	public void testWhereis_collision() {
		SquarePlanet p = new SquarePlanet(null, null, 100, 200);
		SquarePlanet p2 = new SquarePlanet(null, null, 2, 2);
		ShipType t = new ShipType();
		User usr = new User(1,1);
		Ship tester = new Ship(null, usr, p, null, 0, 0, t);	
		List<Planet> planets = new ArrayList<Planet>();
		planets.add(p);
		planets.add(p2);
		assert d != tester.whereis_collision(tester.getX(), tester.getY(),tester.getSpeed(),planets);
	}
	*/
	
	/*
	@Test
	public void testCalc_next_position() {
		Ship tester = new Ship(null, null, null, null, 0, 0, null);
		SquarePlanet p = new SquarePlanet(null, null, 100, 200);
		List<Planet> planets = new ArrayList<Planet>();
		planets.add(p);
		
		double x = tester.getX();
		double y = tester.getY();
		tester.setDestination(p);
		
		tester.calc_next_position(planets);
		assert x != tester.getX() && y != tester.getY();	
	}
	*/
	
	/*
	@Test
	public void testTop_collision_mover() {
		ShipType t = new ShipType();
		SquarePlanet p = new SquarePlanet(null, null, 100, 200);
		Ship tester = new Ship(null, null, p, null, 0, 0, t);
		double x = tester.getX();
		double y = tester.getY();
		System.out.println("debug: " + x);
		System.out.println("debug: " + y);

		List<Planet> planets = new ArrayList<Planet>();
		planets.add(p);
		while(tester.reached_destination()) {
			tester.calc_next_position(planets);
		}
		System.out.println("debug: " + x);
		System.out.println("debug: " + y);
		System.out.println("speed: " + tester.getSpeed());
		tester.top_collision_mover(tester.getX(), tester.getY(),  p.getX() + Generation.width /2,  p.getY() + + Generation.height /2, tester.getSpeed());
		System.out.println("debug2: " + x);
		System.out.println("debug2: " + y);
		assert x != tester.getX();
		assert y != tester.getY();
	}
	*/
	
	/*
	@Test
	public void testBottom_collision_mover() {
		ShipType t = new ShipType();
		Ship tester = new Ship(null, null, null, null, 0, 0, t);
		SquarePlanet p = new SquarePlanet(null, null, 100, 200);
		double x = tester.getX();
		double y = tester.getY();
		tester.bottom_collision_mover(tester.getX(), tester.getY(),  p.getX() + Generation.width /2,  p.getY() + + Generation.height /2, tester.getSpeed());
		
		assert x != tester.getX();
		assert y != tester.getY();
	}
	*/
	
	
	/**
	 * \Brief test of the function Reached_destination with a SquarePlanet at a pos of (100,200)
	 * \and a ship
	 */
	@Test
	public void testReached_destination() {
		ShipType t = new ShipType();
		SquarePlanet p = new SquarePlanet(null, null, 100, 200);
		Ship tester = new Ship(null, null, p, null, 0, 0, t);
		boolean b = false;
		System.out.println(tester.reached_destination());
		assert b == tester.reached_destination();
	}
	
	

}
package fr.groupe40.projet.junit;
import static org.junit.Assert.*;

import org.junit.Test;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.planets.SquarePlanet;
import fr.groupe40.projet.model.ships.Squad;
import junit.framework.TestCase;

/**
 * 
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */

public class TestOnUser extends TestCase{

	/**
	 * Test of the function user with an Human user
	 */
	@Test
	public void testUser() {
		User u = new User(1, 42);
		assert u.getId() == 42 && u.getFaction() == 1;
	}
	
	/**
	 * Test of the function setId with an Human user
	 */
	@Test
	public void testSetId() {
		User u = new User(1, 0);
		u.setId(10);
		assert 10 == u.getId();
	}
	
	/**
	 * Test of the function setFaction with an Human user
	 */
	@Test
	public void testSetFaction() {
		User u = new User(1, 0);
		u.setFaction(-1);
		assert -1 == u.getFaction();
	}
	

	/**
	 * Test of the function sendFleetAi with an AI user
	 */
	@Test
	public void testsendFleetAI() {
		User u = new User(-1, 0);
		SquarePlanet p = new SquarePlanet(null, u, 100, 100);
		p.setTroups(25);
		SquarePlanet p2 = new SquarePlanet(null, null, 100, 100);
		Squad s = u.sendFleetAI(p, p2);
		assert s != null;
	}
	
	
	/**
	 * Test of the function setPercent_of_troups_to_send with an AI user
	 */
	@Test
	public void testSetPercent_of_troups_to_send() {
		User u = new User(-1, 0);
		u.setPercent_of_troups_to_send(80);
		assert 80 == u.getPercent_of_troups_to_send();
	
	}
	
	
	/**
	 * Test of the fuction setDestination with a square planet and a Human user
	 */
	@Test
	public void setDestination() {
		User u = new User(1, 0);
		SquarePlanet destination = new SquarePlanet(null, u, 0, 0);
		u.setDestination(destination);
		assert destination == u.getDestination();
	}
	
	/**
	 * Test of the fuction setSource with a square planet and a Human user
	 */
	@Test
	public void setSource() {
		User u = new User(1, 12);
		SquarePlanet source = new SquarePlanet(null, u, 0, 0);
		u.setSource(source);
		assert source == u.getSource();
		
	}
	
	/**
	 * Test of the fuction setLost with an AI user
	 */
	@Test
	public void TestSetLost() {
		User u = new User(-1, 0);
		u.setLost(true);
		assert true == u.isLost();
	}
}

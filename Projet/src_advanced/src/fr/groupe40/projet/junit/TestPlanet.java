package fr.groupe40.projet.junit;

import org.junit.Test;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.planets.RoundPlanet;
import fr.groupe40.projet.model.ships.ShipType;
import junit.framework.TestCase;


/**
 * 
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class TestPlanet extends TestCase{

		/** 
		 * Test of the function GeneratePlanet with a round planet
		 */
	@Test
	public void testGeneratePlanet() {
		User u = new User(1,10);
		RoundPlanet p = new RoundPlanet(null, u, 0, 0);
		int a = 0;
		assert a != p.getTroups();
		assert a != p.getProduce_rate();
	}
	/**
	 * Test of the function summonX with a round planet
	 */
	@Test
	public void testSetSummonX() {
		RoundPlanet p = new RoundPlanet(null, null, 0, 0);
		p.setSummonX(40);
		double d = p.getSummonX();
		assert d == 40;
	}
	
	
	/**
	 * Test of the function summonY with a round planet
	 */
	@Test
	public void testSetSummonY() {
		RoundPlanet p = new RoundPlanet(null, null, 0, 0);
		p.setSummonY(10);
		double d = p.getSummonY();
		assert d == 10;
	}
	
	/**
	 * Test of the function setProduce_rate with a round planet
	 */
	@Test
	public void testSetProduce_rate() {
		RoundPlanet p = new RoundPlanet(null, null, 0, 0);
		p.setProduce_rate(5);
		double d = p.getProduce_rate();
		assert d == 5;
		
	}
	
	/**
	 * Test of the function setTroups with a round planet
	 */
	public void testSetTroups() {
		RoundPlanet p = new RoundPlanet(null, null, 0, 0);
		p.setTroups(7);
		int nb = p.getTroups();
		assert nb == 7;
	}
	
	
	/**
	 * Test of the function setShips_type with a round planet
	 */
	@Test
	public void testSetShips_type() {
		RoundPlanet p = new RoundPlanet(null, null, 0, 0);
		ShipType t = new ShipType();
		p.setShips_type(t);
		assert t == p.getShips_type();
	}
		
	
	/**
	 * Test of the function setSelected with a round planet
	 */
	@Test
	public void testSetSelected() {
		RoundPlanet p = new RoundPlanet(null, null, 0, 0);
		boolean b = true;
		p.setSelected(false);
		assert b != p.isSelected();
	}
	
	/**
	 * Test of the function updatePlanetePosition with a round planet
	 */
	@Test
	public void testUpdatePlanetePosition() {
		RoundPlanet p = new RoundPlanet(null, null, -10, -10);
		p.updatePlanetePosition();
		assert -10 != p.getX() && -10 == p.getY();
	}
	
	/**
	 * Test of the function Equals with a round planet
	 */
	@Test
	public void testEquals() {
		User u = new User(1,1);
		RoundPlanet p = new RoundPlanet(null, u, 200, 100);
		RoundPlanet p2 = new RoundPlanet(null, u, 10, 50);
		boolean b = p.equals(p2);
		assert false == b;
		
	}


}

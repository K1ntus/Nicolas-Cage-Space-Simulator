package fr.groupe40.projet.util.constants;


/**
 * Different types of collision between SpaceShips and Planets.
 *
 */
public enum Collision {
	
	/**
	 * There is no collisions
	 *
	 */
	NO_COLLISION,
	
	/**
	 * TOP collision between a ship and a planet
	 */
	TOP,
	
	/**
	 * BOTTOM collision between a ship and a planet
	 */
	BOTTOM,
	
	/**
	 * LEFT collision between a ship and a planet
	 */
	LEFT,
	
	/**
	 * RIGHT collision between a ship and a planet
	 */
	RIGHT
}
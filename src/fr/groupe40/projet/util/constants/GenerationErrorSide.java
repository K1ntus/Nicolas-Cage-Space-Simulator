package fr.groupe40.projet.util.constants;


public enum GenerationErrorSide {
	
	/**
	 * There is no collisions
	 *
	 */
	GREATER_X,
	
	/**
	 * TOP collision between a ship and a planet
	 */
	LOWER_X,
	
	/**
	 * BOTTOM collision between a ship and a planet
	 */
	GREATER_Y,
	
	/**
	 * LEFT collision between a ship and a planet
	 */
	LOWER_Y,
	
	NO_PROBLEM
}

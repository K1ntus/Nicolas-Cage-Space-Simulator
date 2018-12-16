package fr.groupe40.projet.util.constants;

/**
 * Used for planet generation
 * Return a specific value if his position make 
 * the planet out of bounds
 *
 */
public enum GenerationErrorSide {
	
	/**
	 * Planet is oob (out of bounds) to the left side
	 */
	GREATER_X,

	/**
	 * Planet is oob (out of bounds) to the right side
	 */
	LOWER_X,

	/**
	 * Planet is oob (out of bounds) to the bottom side
	 */
	GREATER_Y,

	/**
	 * Planet is oob (out of bounds) to the top side
	 */
	LOWER_Y,
	
	/**
	 * If the current osition fit the board
	 */
	NO_PROBLEM
}

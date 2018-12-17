package fr.groupe40.projet.util.constants;

public final class Debugging {
	
	/*	Those 4 constants should be converted as a enum type	*/
	/**
	 * Used for planet generation.
	 * Get this value when the x position of the planet is out of bounds
	 */
	@Deprecated
	public static final int error_greater_x = 2;
	
	/**
	 * Used for planet generation.
	 * Get this value when the x position of the planet is out of bounds
	 */
	@Deprecated
	public static final int error_greater_y = 1;
	
	/**
	 * Used for planet generation.
	 * Returned when the x position of the planet is too small (because use of margins)
	 */
	@Deprecated
	public static final int error_lower_x = -1;	
	
	/**
	 * Used for planet generation
	 * Returned when the y position of the planet is too small (because use of margins)
	 */
	@Deprecated
	public static final int error_lower_y = -2;
	/*	**************	*/

	/**
	 * Enable some print to be done to check informations
	 * Enabling it display the time used for each game board initialization
	 * Display which planet you have selected, ...
	 */
	public static final boolean DEBUG = false;

	public static final boolean DEBUG_TIMER = true;
	
	@Deprecated
	public static final boolean DEBUG_TROUPS = false;

}


package fr.groupe40.projet.util.constants;

import fr.groupe40.projet.util.annot.TODO;

/**
 * Few constants about garrison
 *
 */
public final class PlanetsGarrison {
	/**
	 * Maximal troop available when the game got initialized
	 */
	public static final int max_initDefense = 15;//The initial troops in a planet at the generation of the world
	
	/**
	 * Minimal number of troups before getting ruled
	 */
	public static final int min_troups = 1;	//minimal troops in a planet
	
	/**
	 * Maximal number of troops available in a planet
	 */
	@TODO(comment="Make it variable a constant * ratio (with ratio depending of the planet width or something like that ?")
	public static final int max_troups = 100;	//minimal troops in a planet
	
	/**
	 * The troop of the sun 'planet'
	 */
	public static final int sun_troups = 100;
}

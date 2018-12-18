package fr.groupe40.projet.util.constants;

/**
 * Manage the time for every event/game update that had to be done
 * Note: ~40ticks = equals 1 second usually
 */
public final class Ticks {
	
	/**
	 * The ticks needed to produce troops of each planets
	 */
	public static final long tick_per_produce = 40;
	
	/**
	 * The ticks between each fleet lift-off
	 */
	public static final long tick_per_lift_off = 20;
	
	/**
	 * The ticks between each ai-attack check
	 */
	public static final long tick_per_ai_attack = 91;
	
	/**
	 * Tick between each ship move on board
	 */
	public static final long tick_per_squad_position_update = 1;	//original is 1, higher for better performance
	
	/**
	 * Tick between each event caster
	 */
	public static final long tick_per_events = 249;
	
	/**
	 * Tick between each check if the main theme is playing
	 */
	public static final long tick_per_main_theme_check = 500;
	
	/**
	 * @deprecated do not use anymore
	 */
	@Deprecated
	public static final long tick_per_garbage_check = 15000;
	
}

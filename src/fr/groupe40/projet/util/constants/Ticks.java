package fr.groupe40.projet.util.constants;

public final class Ticks {

	//~40 ticks = 1 second
	public static final long tick_per_produce = 40;
	public static final long tick_per_lift_off = 20;
	public static final long tick_per_ai_attack = 91;
	public static final long tick_per_squad_position_update = 1;	//original is 1, higher for better performance
	public static final long tick_per_events = 249;
	public static final long tick_per_main_theme_check = 500;
	public static final long tick_per_garbage_check = 15000;
	
	@Deprecated
	public static final long tick_before_counter_reset = 500;
	

}

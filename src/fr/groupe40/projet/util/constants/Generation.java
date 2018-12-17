package fr.groupe40.projet.util.constants;

public final class Generation {

	/*	Window Parameters	*/
	//Window height & width
	public static final int width = 800;
	public static final int height = 600;
	
	//Four margins
	public static final double top_margin_size = 50;
	public static final double left_margin_size = 50;
	public static final double bottom_margin_size = 50;
	public static final double right_margin_size = 50;

	/*	Generation parameters */
	public static int nb_planets_tentatives = 20;	//<-> max planets available	//Exponential complexity
	public static final int min_numbers_of_planets = 3;
	public static final int minimal_distance_between_planets = 200;	//Distance in pixels required between each planets
	
	//Nb of ai squads generated from the beginning
	//Currently for debugging and not working anymore
	//public static final int nb_squads = 50;
	
	/*	Graphics	*/
	public static double size_squads = 10.0;	//size in pixels of a "squad"
	public static double size_minimal_planets = 5*size_squads;	//minimal size of a planet in pixel ((4*size squad)
	public static double size_maximal_planets = 8*size_squads;
	public static double size_sun = 10*size_squads;

}

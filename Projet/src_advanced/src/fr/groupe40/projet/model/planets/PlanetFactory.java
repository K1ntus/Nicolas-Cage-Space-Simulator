package fr.groupe40.projet.model.planets;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Resources;

/**
 * Manage the creation of new planets, whatever his type. </br>
 * </br>
 * That his the why the <b>Factory Design Pattern</b> is used there.
 * 
 * Using the type passed in parameters of the constructor, it will pre-generate a
 * planet without any image allocated to him (except if the type of the planet
 * to generate is a sun)
 * 
 * @see PlanetFactory#getPlanet(int)
 * 
 * @throws IllegalArgumentException if the type of the planet passed as
 *                                  parameters of the <b>
 *                                  PlanetFactory.getPlanet(int <i>type</i>)</b>
 *                                  is unknow
 *
 */
public class PlanetFactory {

	/**
	 * Constant used to choose the type of the planet generated into the factory.
	 */
	public static final int TYPE_SUN = 0;

	/**
	 * Constant used to choose the type of the planet generated into the factory.
	 */
	public static final int TYPE_SQUARE = 1;

	/**
	 * Constant used to choose the type of the planet generated into the factory.
	 */
	public static final int TYPE_ROUND = 2;

	/**
	 * Return a Planet, dependent of the type passed in parameters. Constants about
	 * that are fixed in the class PlanetFactory to make it easier.
	 * 
	 * @param type_planet the type of the planet (round, square, sun, ...)
	 * @return the planet just created
	 */
	public static Planet getPlanet(int type_planet) {
		Planet planet = null;

		switch (type_planet) {
		case TYPE_SUN:
			planet = new Sun(Resources.path_img_sun, Constants.width / 2, Constants.height / 2);
			planet.updateImage();
			planet.setX(planet.getX() - planet.width() / 2);
			planet.setY(planet.getY() - planet.width() / 2);
			planet.setTroups(Constants.sun_troups);
			break;

		case TYPE_SQUARE:
			planet = new SquarePlanet(null, new User(Constants.neutral_user),
					(int) (Constants.left_margin_size + Constants.size_squads), 0);
			break;

		case TYPE_ROUND:
			planet = new RoundPlanet(null, new User(Constants.neutral_user),
					(int) (Constants.left_margin_size + Constants.size_squads), 0);
			break;

		default:
			throw new IllegalArgumentException("Unknow planet type");
		}

		return planet;
	}

}

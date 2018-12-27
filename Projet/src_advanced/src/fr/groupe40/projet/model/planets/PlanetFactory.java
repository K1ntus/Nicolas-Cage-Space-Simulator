package fr.groupe40.projet.model.planets;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Resources;

public class PlanetFactory {

	public static final int TYPE_SUN = 0;
	public static final int TYPE_SQUARE = 1;
	public static final int TYPE_ROUND = 2;

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

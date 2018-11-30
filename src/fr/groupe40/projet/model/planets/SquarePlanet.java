package fr.groupe40.projet.model.planets;

import java.io.Serializable;

import fr.groupe40.projet.client.User;

/**
 * \brief Square planet TODO
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class SquarePlanet extends Planet  implements Serializable{
	public SquarePlanet(String path, User ruler, boolean isPlanet, int x, int y) {
		super(path, ruler, x, y);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -7344260544964758721L;

}

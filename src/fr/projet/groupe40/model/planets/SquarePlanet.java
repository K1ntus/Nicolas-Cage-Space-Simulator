package fr.projet.groupe40.model.planets;

import java.io.Serializable;

import fr.projet.groupe40.client.User;

public class SquarePlanet extends Planet  implements Serializable{
	public SquarePlanet(String path, User ruler, boolean isPlanet, int x, int y) {
		super(path, ruler, isPlanet, x, y);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -7344260544964758721L;

}

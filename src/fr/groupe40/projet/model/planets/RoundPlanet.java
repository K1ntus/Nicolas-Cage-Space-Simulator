package fr.groupe40.projet.model.planets;

import java.io.Serializable;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.util.constantes.Constantes;

public class RoundPlanet extends Planet implements Serializable{
	private static final long serialVersionUID = -7903551302927756905L;

	public RoundPlanet(User ruler, boolean isPlanet, int x, int y) {
		super(Constantes.path_img_round_planets, ruler, isPlanet, x, y);
		// TODO Auto-generated constructor stub
	}

}

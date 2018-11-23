package fr.projet.groupe40.model.planets;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.board.Planet;
import fr.projet.groupe40.util.Constantes;

@Deprecated
public class RoundPlanet extends Planet{
	private static final long serialVersionUID = -7903551302927756905L;

	public RoundPlanet(User ruler, boolean isPlanet, int x, int y) {
		super(Constantes.path_img_round_planets, ruler, isPlanet, x, y);
		// TODO Auto-generated constructor stub
	}

}

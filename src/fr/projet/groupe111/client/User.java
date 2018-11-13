package fr.projet.groupe111.client;

import fr.projet.groupe111.util.Constantes;
import javafx.scene.canvas.GraphicsContext;

public class User {
	private int faction;
	private int id;

	public User(int faction, int id) {
		this.faction = faction;
		this.id = id;
	}
	public User(int faction) {
		this.faction = faction;
		
		switch(faction) {
		case Constantes.ai:
			id = Constantes.ai; break;
		case Constantes.neutral:
			id = Constantes.neutral; break;
		case Constantes.player:
			id = Constantes.player; break;
		}

	}

	public User(User user) {
		this.id = user.id;
		this.faction = user.faction;
	}

	public boolean hasLost() {
		//TODO
		return false;
	}
	
	public void renderWhenDefeat(GraphicsContext gc) {
		//TODO
	}
	
	public int getFaction() {
		return faction;
	}

	public void setFaction(int faction) {
		this.faction = faction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

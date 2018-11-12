package fr.projet.groupe111.model.client;

public class User {
	private int faction;
	private int id;

	public User(int faction, int id) {
		this.faction = faction;
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	public User(User user) {
		this.id = user.id;
		this.faction = user.faction;
		// TODO Auto-generated constructor stub
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

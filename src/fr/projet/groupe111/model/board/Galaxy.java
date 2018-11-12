package fr.projet.groupe111.model.board;

import java.util.ArrayList;

import fr.projet.groupe111.model.Sprite;
import fr.projet.groupe111.model.client.User;
import fr.projet.groupe111.model.ships.Squad;
import fr.projet.groupe111.util.Constantes;

public class Galaxy extends Thread{
	private ArrayList<Planet> planets;
	private ArrayList<Squad> squads;
	private ArrayList<User> users;
	
	public Galaxy() {
		users = new ArrayList<User>();
		squads = new ArrayList<Squad>();
		planets = new ArrayList<Planet>();


		generatePlanets();

		for(int i = 0; i < Constantes.nb_squads; i++) {
			
		}
			//Squad s = new Squad(getRessourcePathByName("resources/images/alien.png"), 100, getPlanets().get((int) (Math.random() * (getPlanets().size() - 0))));
			//Squad s = new Squad(getRessourcePathByName("resources/images/alien.png"), i, planets.get((int) (Math.random() * (planets.size() - 0))));
			//s.setPosition(Constantes.width * Math.random() - Constantes.size_squads, Constantes.height * Math.random() - Constantes.size_squads);
			//squads.add(s);

		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
		
	}
	@Override
	public void run() {
		while(true) {
			for(Planet p : planets)
				p.updateGarrison();
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


	public void generatePlanets() {
		double width = Math.random() * Constantes.size_minimalPlanets *0.25 + Constantes.size_minimalPlanets;
		double height = width;
		double maxX = Constantes.width - width;;
		double maxY = Constantes.height - height;;
		//Sprite(String path, double width, double height, double maxX, double maxY) 
		Planet p = new Planet(new Sprite(Constantes.path_img_planets, width, height, maxX, maxY, new User(Constantes.neutral_user)));
		planets.add(p);
	}
	public void generateRandomSquads() {
		double width =Constantes.size_squads;
		double height = width;
		double maxX = Constantes.width - width;
		double maxY = Constantes.height - height;
		//Sprite(String path, double width, double height, double maxX, double maxY) 
		Planet p = new Planet(new Sprite(Constantes.path_img_planets, width, height, maxX, maxY, new User(Constantes.neutral_user)));
		planets.add(p);
	}

	public ArrayList<Squad> getSquads() {
		return squads;
	}

	public void setSquads(ArrayList<Squad> squads) {
		this.squads = squads;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
}

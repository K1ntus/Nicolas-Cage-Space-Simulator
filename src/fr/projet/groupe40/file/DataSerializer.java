package fr.projet.groupe40.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.board.Galaxy;
import fr.projet.groupe40.model.board.Planet;
import fr.projet.groupe40.model.ships.Squad;
import fr.projet.groupe40.util.Constantes;

@SuppressWarnings("unused")
public class DataSerializer {
	private String name;
	private Galaxy data;
	private ObjectOutputStream oos;
	
	public DataSerializer(String name, Galaxy data) {
		this.name = name;
		this.data = data;
		
		
	}
	
	public boolean save_game() {
		try {
			final FileOutputStream file = new FileOutputStream(name + ".save");
			oos = new ObjectOutputStream(file);
			
			oos.writeObject(data.getPlanets());
			oos.writeObject(data.getSquads());
			oos.writeObject(data.getUsers());
			
			oos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
		
	}
	
	public boolean load_game(Galaxy g) {
		FileInputStream file;
		try {
			file = new FileInputStream(name + ".save");
			ObjectInputStream ois = new ObjectInputStream(file);
			
			ArrayList<Planet> planet =  (ArrayList<Planet>) ois.readObject();
			ArrayList<Squad> squad =  (ArrayList<Squad>) ois.readObject();
			ArrayList<User> user =  (ArrayList<User>) ois.readObject();
			g.setPlanets(planet);
			g.setSquads(squad);
			g.setUsers(user);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to load game");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to load game");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to load game");
			e.printStackTrace();
		}	
		return true;
	}
	
	public void reload_image(Galaxy g) {
		for(Planet p : g.getPlanets()) {
			p.setImg_path(Constantes.path_img_planets);
			p.updateImage();
		}
		for(Squad s : g.getSquads()) {
			s.setImg_path(Constantes.path_img_ships);
			s.updateImage();
		}
	}
}

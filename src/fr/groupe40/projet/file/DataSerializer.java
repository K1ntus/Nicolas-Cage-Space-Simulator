package fr.groupe40.projet.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Ship;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constantes.Constantes;

@SuppressWarnings("unused")
public class DataSerializer {
	private String name;
	private Galaxy data;
	private ObjectOutputStream oos;
	
	public DataSerializer(String name, Galaxy data) {
		this.name = name;
		this.data = data;
		
		
	}
	/**
	 * \brief Save the current game state in a file
	 * @return true if the game has been saved, else false
	 */
	public boolean save_game() {
		try {
			final FileOutputStream file = new FileOutputStream(name + ".save");
			oos = new ObjectOutputStream(file);
			
			//oos.writeObject(data.getPlanets());
			//oos.writeObject(data.getSquads());
			//oos.writeObject(data.getUsers());
			
			oos.writeObject(data);
			
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
	
	/**
	 * \brief Load a game from a save and apply it to the current game start
	 * @return the galaxy loaded from the save file
	 */
	public Galaxy load_game() {
		FileInputStream file;
		Galaxy loaded = new Galaxy();
		try {
			file = new FileInputStream(name + ".save");
			ObjectInputStream ois = new ObjectInputStream(file);
			
			loaded = (Galaxy) ois.readObject();
			
			ois.close();
			
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
		Galaxy res = new Galaxy(loaded);
		return res;
	}
	/**
	 * \brief Reload the game to apply the loading
	 * @param g Galaxy to be reloaded
	 */
	public void reload_image_and_data(Galaxy g) {
		for(Planet p : g.getPlanets()) {
			
			User u = p.getRuler();
			if (u.getFaction() == Constantes.ai) {
				p.setRuler(Constantes.ai_user);
			}else if (u.getFaction() == Constantes.player) {
				p.setRuler(Constantes.human_user);
			}else {
				p.setRuler(Constantes.neutral_user);
			}

			p.getRuler().setDestination(null);
			p.getRuler().setSource(null);
			
			p.setImg_path(Constantes.path_img_planets);
			p.updateImage();
		}
		
		for(Squad s : g.getSquads()) {
			ArrayList<Ship> ships = s.getShips();
			User u = ships.get(0).getRuler();
			
			if (u.getFaction() == Constantes.ai) {
				s.update_ruler(Constantes.ai_user);
			}else if (u.getFaction() == Constantes.player) {
				s.update_ruler(Constantes.human_user);
			}else {
				s.update_ruler(Constantes.neutral_user);
			}
			
			s.updateImage();
		}
	}
}

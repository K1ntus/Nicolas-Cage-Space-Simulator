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
			final FileOutputStream file = new FileOutputStream(Constantes.path_save + name + ".save");
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
	
	public Galaxy load_game(Galaxy g) {
		FileInputStream file;
		Galaxy loaded = new Galaxy();
		try {
			file = new FileInputStream(Constantes.path_save + name + ".save");
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
	
	public void reload_image(Galaxy g) {
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
			User u = s.getRuler();
			
			if (u.getFaction() == Constantes.ai) {
				s.setRuler(Constantes.ai_user);
			}else if (u.getFaction() == Constantes.player) {
				s.setRuler(Constantes.human_user);
			}else {
				s.setRuler(Constantes.neutral_user);
			}
			
			s.setImg_path(Constantes.path_img_ships);
			s.updateImage();
		}
	}
}

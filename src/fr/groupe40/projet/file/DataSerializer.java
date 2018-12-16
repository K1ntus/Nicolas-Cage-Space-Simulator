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
import fr.groupe40.projet.util.constants.Debugging;
import fr.groupe40.projet.util.constants.Players;
import javafx.scene.canvas.GraphicsContext;


/**
 *  Class managing the saving/loading of the game
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class DataSerializer {
	
	/**
	 *  the fileName of the save
	 */
	private String name;
	
	/**
	 *  the object containing all the data to save
	 */
	private Galaxy data;
	
	/**
	 *  variable that handle the object saving in file
	 */
	private ObjectOutputStream oos;
	
	@Deprecated
	private boolean new_game_loaded = false;
	
	/**
	 *  create the structure containing the data and the fileName
	 * @param name save fileName
	 * @param data game data
	 */
	public DataSerializer(String name, Galaxy data) {
		this.name = name;
		this.data = data;		
	}
	
	/**
	 *  Save the current game state in a file
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
	 *  Load a game from a save and apply it to the current game start
	 * @return the galaxy loaded from the save file
	 */
	public Galaxy load_game(GraphicsContext gc) {
		/*
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select your game save");
		fileChooser.showOpenDialog(stage);
		fileChooser.setInitialDirectory(new File("\""));
		*/
		
		FileInputStream file;
		Galaxy loaded = null;
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
		Galaxy res = new Galaxy(loaded, gc);
		setNew_game_loaded(true);
		return res;
	}
	/**
	 *  Reload the game to apply the loading
	 * @param g Galaxy to be reloaded
	 */
	public void reload_image_and_data(Galaxy g) {

		for(Squad s : g.getSquads()) {
			ArrayList<Ship> ships = s.getShips();
			try {
				User u = ships.get(0).getRuler();	//Get the ruler of the first ship of the squad
				
				switch (u.getFaction()) {
				case Players.human_faction:
					s.update_ruler(Players.human_user); break;
				case Players.ai_faction:
					s.update_ruler(Players.ai_user); break;
				case Players.neutral_faction:
					s.update_ruler(Players.neutral_user); break;
				default:
					break;
				}
				
			} catch (IndexOutOfBoundsException e) {
				continue;	//Every ships of the squad has reached dest, will be automatically removed by the game updater
			}
			
			s.updateImage();
		}
		
		if(Debugging.DEBUG) {
			System.out.println("Squads has been loaded ...");
			System.out.println("Loading planets ...");			
		}
		
		for(Planet p : g.getPlanets()) {		
			User u = p.getRuler();

			//Shall be done using id + faction if more players
			switch (u.getFaction()) {
				case Players.human_faction:
					p.setRuler(Players.human_user); break;
				case Players.ai_faction:
					p.setRuler(Players.ai_user); break;
				case Players.neutral_faction:
					p.setRuler(Players.neutral_user); break;
				default:
					break;
			}
			
			p.updateImage();
		}
		
		if(Debugging.DEBUG) 
			System.out.println("Planets has been loaded ...");
	}

	public boolean isNew_game_loaded() {
		return new_game_loaded;
	}

	public void setNew_game_loaded(boolean new_game_loaded) {
		this.new_game_loaded = new_game_loaded;
	}
}

package fr.groupe40.projet.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Ship;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.annot.TODO;
import fr.groupe40.projet.util.constants.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


/**
 *  Class managing the saving/loading of the game
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class DataSerializer {
	
	/**
	 *  the object containing all the data to save
	 */
	private Galaxy data;
	
	/**
	 *  variable that handle the object saving in file
	 */
	private ObjectOutputStream oos;
	
	/**
	 * Is true if any loading/saving window is opened
	 * Like that, we can know when we had to set the game 
	 * in pause 
	 */
	private boolean box_opened = false;
	
	/**
	 *  create the structure containing the data and the fileName
	 * @param name save fileName
	 * @param data game data
	 */
	public DataSerializer(Galaxy data) {
		this.data = data;		
	}
	
	/**
	 *  Save the current game state in a file
	 * @return true if the game has been saved, else false
	 * @throws Exception 
	 */
	public boolean save_game(Stage stage) throws Exception {
		box_opened = true;
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save game");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Save Files", "*."+Constants.fileName_extension)
				//new ExtensionFilter("All Files", "*.*")
			);
		String currentPath = Paths.get(Constants.path_save).toAbsolutePath().normalize().toString();
		fileChooser.setInitialDirectory(new File(currentPath));
		File file_raw = fileChooser.showSaveDialog(stage);
		FileOutputStream file = null;

		try {
			if(file_raw != null)
				file = new FileOutputStream(file_raw);
			else
				file = new FileOutputStream(currentPath + "/default."+Constants.fileName_extension);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(file == null)
			throw new Exception("ErrorSavingGame");

		
		/*	**************	*/
		
		
		try {
			oos = new ObjectOutputStream(file);
			
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
	 * @throws Exception 
	 */
	@TODO(comment="error if the file loaded is still invalid")
	public Galaxy load_game(GraphicsContext gc, Stage stage) throws Exception {
		box_opened = true;
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load Game");
		String currentPath = Paths.get(Constants.path_save).toAbsolutePath().normalize().toString();
		fileChooser.setInitialDirectory(new File(currentPath));
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Save Files", "*."+Constants.fileName_extension)
				//new ExtensionFilter("All Files", "*.*")
			);
		File file_raw = fileChooser.showOpenDialog(stage);
		FileInputStream file = null;

		try {
			if(file_raw != null)
				file = new FileInputStream(file_raw);
			else
				throw new Exception("FileNotFoundException");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(file == null)
			throw new Exception("SaveNotFound");
		

		/*	**************	*/
		
		
		Galaxy loaded = new Galaxy(gc);
		try {
			
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
				if (u.getId() < 0) {
					switch(u.getId()) {
					case Constants.event_id:
						s.update_ruler(Constants.event_user);
						break;
					case Constants.sun_id:
						s.update_ruler(Constants.sun_user);
						break;
					case Constants.pirate_id:
						s.update_ruler(Constants.pirate_user);
						break;
					default:
						s.update_ruler(Constants.ai_user);
						break;
					}
				}else if (u.getFaction() == Constants.human_faction) {
					s.update_ruler(Constants.human_user);
				}else {
					s.update_ruler(Constants.neutral_user);
				}
			} catch (IndexOutOfBoundsException e) {
				continue;	//Every ships of the squad has reached destination, will be automatically removed by the game updater
			}
			
			s.updateImage();
		}
		
		for(Planet p : g.getPlanets()) {		
					
			switch(p.getRuler().getId()) {
				case Constants.event_id:
					//System.out.println("** event user");
					p.setRuler(Constants.event_user);
					break;
				case Constants.sun_id:
					//System.out.println("** sun");
					p.setRuler(Constants.sun_user);
					break;
				case Constants.pirate_id:
					//System.out.println("** pirate user");
					p.setRuler(Constants.pirate_user);
					break;
				case Constants.human_faction:
					//System.out.println("** human user");
					p.setRuler(Constants.human_user);
					break;
				case Constants.neutral_faction:
					//System.out.println("** neutral user");
					p.setRuler(Constants.neutral_user);
					break;
				default:
					//System.out.println("** ai user");
					p.setRuler(Constants.ai_user);
					break;
			}

			
			p.updateImage();
		}
		
	}


	/**
	 * @return the box_opened
	 */
	public boolean isBox_opened() {
		return box_opened;
	}

	/**
	 * @param box_opened the box_opened to set
	 */
	public void setBox_opened(boolean box_opened) {
		this.box_opened = box_opened;
	}

}

package fr.groupe40.projet.util;

import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.image.Image;

public class ResourcesContainer {
	private Image main_menu_background;
	private Image game_background;

	private Image game_human_ships;
	private Image game_ai_ships;
	private Image game_pirate_ships;
	
	
	
	public ResourcesContainer(){
		main_menu_background = ResourcesManager.getImageByPath_dynamic(ResourcesManager.getRessourcePathByName(Resources.path_img_menu_background), Generation.width);
		game_background = ResourcesManager.getImageByPath_dynamic(ResourcesManager.getRessourcePathByName(Resources.path_img_game_background), Generation.width);

		game_human_ships = ResourcesManager.getImageByPath_dynamic(ResourcesManager.getRessourcePathByName(Resources.path_img_human_ships), Generation.size_squads);
		game_ai_ships = ResourcesManager.getImageByPath_dynamic(ResourcesManager.getRessourcePathByName(Resources.path_img_AI_ships), Generation.size_squads);
		game_pirate_ships = ResourcesManager.getImageByPath_dynamic(ResourcesManager.getRessourcePathByName(Resources.path_img_event_pirate_ships), Generation.size_squads);
		
		
		
	}

	/**
	 * @return the game_background
	 */
	public Image getGame_background() {
		return game_background;
	}


	/**
	 * @param game_background the game_background to set
	 */
	public void setGame_background(Image game_background) {
		this.game_background = game_background;
	}


	/**
	 * @return the main_menu_background
	 */
	public Image getMain_menu_background() {
		return main_menu_background;
	}


	/**
	 * @param main_menu_background the main_menu_background to set
	 */
	public void setMain_menu_background(Image main_menu_background) {
		this.main_menu_background = main_menu_background;
	}

	/**
	 * @return the game_human_ships
	 */
	public Image getGame_human_ships() {
		return game_human_ships;
	}

	/**
	 * @return the game_ai_ships
	 */
	public Image getGame_ai_ships() {
		return game_ai_ships;
	}

	/**
	 * @return the game_pirate_ships
	 */
	public Image getGame_pirate_ships() {
		return game_pirate_ships;
	}

	/**
	 * @param game_human_ships the game_human_ships to set
	 */
	public void setGame_human_ships(Image game_human_ships) {
		this.game_human_ships = game_human_ships;
	}

	/**
	 * @param game_ai_ships the game_ai_ships to set
	 */
	public void setGame_ai_ships(Image game_ai_ships) {
		this.game_ai_ships = game_ai_ships;
	}

	/**
	 * @param game_pirate_ships the game_pirate_ships to set
	 */
	public void setGame_pirate_ships(Image game_pirate_ships) {
		this.game_pirate_ships = game_pirate_ships;
	}

}

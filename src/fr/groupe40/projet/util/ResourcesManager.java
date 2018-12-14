package fr.groupe40.projet.util;

import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class ResourcesManager extends Thread{
	private static Image loading_image;
	
	private static Image main_menu_background;
	private static Image game_background;
	
	private static Image ai_ship;
	private static Image humain_ship;
	private static Image pirate_ship;
	private static Image event_ship;
	
	private Image sun_image;
	
	private static Image round_planet_doge;
	
	
	public ResourcesManager() {
		ResourcesManager.loading_image = new Image(
				getRessourcePathByName(Resources.path_img_loading), 
				Generation.width/4, 
				Generation.height/4, 
				false, 
				false
			);
	}
	

	public static String getRessourcePathByName(String name) {
		return ResourcesManager.class.getResource('/' + name).toString();
	}
	
	public static Image getImageByPath(String path, double size) {
		Image res = new Image(path, size, size, false, false);
		return res;
	}
	
	public static void render_loading_pict(GraphicsContext gc) {
		render_animated_pict(gc, loading_image);
	}
	
	public static void render_animated_pict(GraphicsContext gc, Image img) {
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					gc.drawImage(loading_image, Generation.width/2, Generation.height-Generation.height/4);
					
				}
			}
		);
	}

}

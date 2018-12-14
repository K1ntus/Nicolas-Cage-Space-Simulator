package fr.groupe40.projet.util;

import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class ImageManager extends Thread{
	private static Image loading_image;
	
	public ImageManager() {
		ImageManager.loading_image = new Image(Resources.path_img_loading, Generation.width/4, Generation.height/4, false, false);
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

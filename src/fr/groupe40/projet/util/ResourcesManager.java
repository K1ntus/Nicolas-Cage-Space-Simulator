package fr.groupe40.projet.util;

import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


//TODO COMMENTS
public final class ResourcesManager extends Thread{
	private static Image loading_image;
		
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
	
	public static void render_loading_pict(Group root, Canvas canvas) {
		render_animated_pict(root, canvas, loading_image);
	}
	
	public static void render_animated_pict(Group root, Canvas canvas, Image img) {
		/*
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					gc.drawImage(loading_image, Generation.width/2, Generation.height-Generation.height/4);
					
				}
			}
		);
		*/
		ImageView gif = new ImageView();

		gif.setImage(img);
		gif.setFitWidth(100);
		gif.setPreserveRatio(true);
		gif.setSmooth(true);
		gif.setCache(true);

        HBox box = new HBox();
        box.getChildren().add(gif);
		root.getChildren().add(box);
		//root.getChildren().addAll(canvas, gif);
	}

}

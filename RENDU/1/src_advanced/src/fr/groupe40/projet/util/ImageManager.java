package fr.groupe40.projet.util;

import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


//TODO COMMENTS
public final class ImageManager {
	private static Image loading_image;

		
	public ImageManager() {
		loading_image = new Image(
				getRessourcePathByName(Resources.path_img_loading), 
				Generation.width/4, 
				Generation.height/4, 
				false, 
				false
			);

	}
	

	public static String getRessourcePathByName(String name) {
		return ImageManager.class.getResource('/' + name).toString();
	}

	public static Image getImageByPath(String path, double size) {
		return new Image(path, size, size, false, false);
	}
	
	
	private static Image result = null;
	public static Image getImageByPath_dynamic(String path, double size) {
		final Service<Image> imageLoadingService = new Service<Image>(){

			  @Override
			  protected Task<Image> createTask() {
			    return new Task<Image>(){

			     @Override
			     protected Image call() throws Exception {
			 		return new Image(path, size, size, false, false);
			      }
			    };
			  }
			};

			imageLoadingService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			    @Override
			    public void handle(WorkerStateEvent t) {
			        result = imageLoadingService.getValue();
			    }
			});
			imageLoadingService.start();
			if(result != null)
				return result;
			else
				return new Image(path, size, size, false, false);
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

package fr.groupe40.projet.util;

import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


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

}

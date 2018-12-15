package fr.groupe40.projet.util;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;


//TODO COMMENTS
public final class ImageManager {


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

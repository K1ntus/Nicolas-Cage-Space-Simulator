package fr.groupe40.projet.util;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;


/**
 * Manage image loading, and offer an image loading from threads
 * @author Jordane Masson
 * @author Sarah Portejoie
 */
public final class ImageManager {

	/**
	 * Return the string reformatted to get the correct path
	 * @param name the file name/initial path
	 * @return the corrected path string
	 */
	public static String getRessourcePathByName(String name) {
		return ImageManager.class.getResource('/' + name).toString();
	}

	/**
	 * Load an image using main processus
	 * @param path the image path
	 * @param size the width and height of the image (so, only available for squared pict)
	 * @return the image loaded
	 */
	public static Image getImageByPath(String path, double size) {
		return new Image(path, size, size, false, false);
	}
	
	/**
	 * Will contain the result of function 'getImageByPath_dynamic'
	 */
	private static Image result = null;
	
	/**
	 * Load an image using another processus
	 * @param path the image path
	 * @param size the width and height of the image (so, only available for squared pict)
	 * @return the image loaded
	 */
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

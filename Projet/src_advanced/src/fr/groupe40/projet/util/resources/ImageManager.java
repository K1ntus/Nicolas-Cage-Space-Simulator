package fr.groupe40.projet.util.resources;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;


/**
 * Manage image loading, and offer an image loading from threads
 * 
 * This class offers few algorithms to load images file to reduce the main thread calculation charge, but also to offers easiest ways. 
 * This class also offers few static function to manage the loading of image file.
 * The default loading will use another processus to do that (Service + Task) and reduce the overload for the main thread.
 * 
 * Following the Design pattern of the Singleton, to prevent the usage of multiple occurence of this class.
 * 
 * @see ImageManager#getImageByPath(String, double)
 * @see ImageManager#getImageByPath_dynamic(String, double, double)
 * 
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
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
		return getImageByPath_dynamic(path, size, size);
	}

	/**
	 * Load an image using another processus
	 * @param path the image path
	 * @param width of the img
	 * @param height of the image
	 * @return the image loaded
	 */
	public static Image getImageByPath_dynamic(String path, double width, double height) {
		final Service<Image> imageLoadingService = new Service<Image>(){

			  @Override
			  protected Task<Image> createTask() {
			    return new Task<Image>(){

			     @Override
			     protected Image call() throws Exception {
			 		return new Image(path, width, height, true, false);
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
		 		return new Image(path, width, height, true, false);
	}

}

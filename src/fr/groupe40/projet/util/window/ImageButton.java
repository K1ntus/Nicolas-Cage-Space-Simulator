package fr.groupe40.projet.util.window;

import fr.groupe40.projet.util.annot.WorkInProgress;
import fr.groupe40.projet.util.resources.ImageManager;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Source: https://stackoverflow.com/questions/10518458/javafx-create-custom-button-with-image
 *
 */
@WorkInProgress(comment = "Pour la création de boutons avec des images")
public class ImageButton extends Button {

    private Image NORMAL_IMAGE;
    private Image PRESSED_IMAGE;

    private final ImageView iv;

    public ImageButton(Image normal, Image pressed) {
    	NORMAL_IMAGE = normal;
    	PRESSED_IMAGE = pressed;
        this.iv = new ImageView(NORMAL_IMAGE);
    	
    	this.init();
    }

    /**
     * Create a button from two image path string.
     * @param normal The path to the image that will be displayed when the button isnt active
     * @param pressed The path of the image that will be displayed when its active (ie mouse over it, ...)
     */
    public ImageButton(String normal, String pressed) {
    	NORMAL_IMAGE = ImageManager.getImageByPath_dynamic(normal, 167, 0);	//167 bcz current width of the buttons
    	PRESSED_IMAGE = ImageManager.getImageByPath_dynamic(pressed, 167, 0);
    	if (NORMAL_IMAGE == null || PRESSED_IMAGE == null)
    		System.out.println("button init error");
        this.iv = new ImageView(NORMAL_IMAGE);
        this.iv.setFitWidth(167);
        this.init();
    }

    /**
     * Initialize the image switching
     */
    private void init() {   
        this.setOnMousePressed(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent evt) {
                iv.setImage(PRESSED_IMAGE);
            }

        });    

        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                iv.setImage(NORMAL_IMAGE);
            }
        });


        super.setGraphic(iv);
    }
    
    /**
     * Get the button's imageView
     * @deprecated do not use this function, it will not even work
     * @param path the button image
     * @return the button imageview
     */
    @Deprecated
    public static ImageView get_image_view_for_button(String path) {
    	return new ImageView(ImageManager.getImageByPath_dynamic(path, 167, 0));	//167 bcz current width of the buttons
    	
    }
	/**
	 * @return the iv
	 */
	public ImageView getIv() {
		return iv;
	}
    
}
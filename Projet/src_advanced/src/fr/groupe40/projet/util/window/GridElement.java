package fr.groupe40.projet.util.window;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Few function to factorize code
 *
 */
public class GridElement {
	
	/**
	 * Add a button to a javafx gridpane
	 * @param label the button text
	 * @param offsetX his x position on the grid
	 * @param offsetY his y position on the grid
	 * @param grid the grid to edit
	 * @return the button edited if specific edit had to be done
	 */
	public static Button add_button_to_gridpane(String label, int offsetX, int offsetY, GridPane grid) {
		Button btn = new Button(label);
		HBox hbbtn = new HBox(50);
		hbbtn.setAlignment(Pos.TOP_LEFT);
		hbbtn.getChildren().add(btn);
		grid.add(btn, offsetX, offsetY);		
		
		return btn;
	}

	/**
	 * Display a text on javafx gridpane
	 * @param string the text of the label
	 * @param font the font of the text
	 * @param color the color of the text
	 * @param offsetX his x position on the grid
	 * @param offsetY his y position on the grid
	 * @param grid the grid to edit
	 */ 
	public static void add_label_to_gridpane(String string, Font font, Color color, int offsetX, int offsetY, GridPane grid) {
		Label label = new Label(string);
		label.setFont(font);
		label.setTextFill(color);
		grid.add(label, offsetX, offsetY);
	}
	
	/**
	 * Display an image on a javafx gridpane and return his imageview
	 * @param img_path the string path to the image
	 * @param fit_wdith the width to fit with
	 * @param preserveRatio if the image should keep his ratio
	 * @param offsetX his x position on the grid
	 * @param offsetY his y position on the grid
	 * @param grid the grid to add the image
	 * @return the imageview that has been created, if more change had to be done
	 */
	public static ImageView add_image_to_gridpane(String img_path, int fit_wdith, boolean preserveRatio, int offsetX, int offsetY, GridPane grid) {
		Image img = new Image(img_path);
		ImageView img_view = new ImageView();
		img_view.setImage(img);
		img_view.setFitWidth(fit_wdith);
		img_view.setPreserveRatio(preserveRatio);
		grid.add(img_view, offsetX, offsetY);
		
		return img_view;		
	}

	/**
	 * Display an already loaded image on a javafx gridpane and return his imageview
	 * @param img the img to display
	 * @param fit_wdith the width to fit with
	 * @param preserveRatio if the image should keep his ratio
	 * @param offsetX his x position on the grid
	 * @param offsetY his y position on the grid
	 * @param grid the grid to edit
	 * @return the imageview that has been created
	 */
	public static ImageView add_image_to_gridpane(Image img, int fit_wdith, boolean preserveRatio, int offsetX, int offsetY, GridPane grid) {
		ImageView img_view = new ImageView();
		img_view.setImage(img);
		img_view.setFitWidth(fit_wdith);
		img_view.setPreserveRatio(preserveRatio);
		grid.add(img_view, offsetX, offsetY);
		
		return img_view;
	}

}

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

public class GridElement {
	
	public static Button add_button_to_gridpane(String label, int offsetX, int offsetY, GridPane grid) {
		Button btn = new Button(label);
		HBox hbbtn = new HBox(50);
		hbbtn.setAlignment(Pos.TOP_LEFT);
		hbbtn.getChildren().add(btn);
		grid.add(btn, offsetX, offsetY);		
		
		return btn;
	}

	public static void add_label_to_gridpane(String string, Font font, Color color, int offsetX, int offsetY, GridPane grid) {
		Label label = new Label(string);
		label.setFont(font);
		label.setTextFill(color);
		grid.add(label, offsetX, offsetY);
	}
	
	public static ImageView add_image_to_gridpane(String img_path, int fit_wdith, boolean preserveRatio, int offsetX, int offsetY, GridPane grid) {
		Image img = new Image(img_path);
		ImageView img_view = new ImageView();
		img_view.setImage(img);
		img_view.setFitWidth(fit_wdith);
		img_view.setPreserveRatio(preserveRatio);
		grid.add(img_view, offsetX, offsetY);
		
		return img_view;		
	}

	public static ImageView add_image_to_gridpane(Image main_menu_background, int fit_wdith, boolean preserveRatio, int offsetX, int offsetY, GridPane grid) {
		ImageView img_view = new ImageView();
		img_view.setImage(main_menu_background);
		img_view.setFitWidth(fit_wdith);
		img_view.setPreserveRatio(preserveRatio);
		grid.add(img_view, offsetX, offsetY);
		
		return img_view;
	}

}

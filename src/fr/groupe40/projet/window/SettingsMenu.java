package fr.groupe40.projet.window;


import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
import fr.groupe40.projet.util.constants.ShipsParameters;
import fr.groupe40.projet.util.constants.Windows;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SettingsMenu {
		private GridPane grid = new GridPane();
		private TextField max_numbers_of_planets, gui_size;
		private TextField parameters_ships_min_speed, parameters_ships_max_speed;
		
		private boolean applied = false;
	    
		public SettingsMenu() {
			this.init();
		
		}
		
		public void init() {
			//Grid init
	        grid.setAlignment(Pos.TOP_LEFT);
	        grid.setHgap(5);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));
	        
	        
	        //Pseudo player not yet implemented
	        //grid.add(new Label(Windows.form_player_name), 0, 1);
	        //grid.add(player_nickname_form, 1, 1);

	        //Top title
	        Image title = new Image(Resources.path_img_menu_title);
	        ImageView title_img = new ImageView();
	        title_img.setImage(title);
	        title_img.setFitWidth(Generation.width/2);
	        title_img.setPreserveRatio(true);
	        grid.add((title_img),2,0);
	        title_img.setTranslateX(Generation.width/3);
	        
	        //Nicolas cage pict
	        Image nicolassse_cage = new Image(Resources.path_img_sun);
	        ImageView nicolas_cage_img = new ImageView();
	        nicolas_cage_img.setImage(nicolassse_cage);
	        nicolas_cage_img.setFitWidth(Generation.width/5);
	        nicolas_cage_img.setPreserveRatio(true);
	        grid.add((nicolas_cage_img),2,10);
	        
	        
	        
	        //Buttons        
	        Button btn_apply = new Button(Windows.button_apply);
	        HBox hbbtn_apply = new HBox(50);
	        hbbtn_apply.setAlignment(Pos.TOP_LEFT);
	        hbbtn_apply.getChildren().add(btn_apply);
	        grid.add(btn_apply, 4, 10);


	        Button btn_exit = new Button(Windows.button_close);
	        HBox hbBtn_exit = new HBox(50);
	        hbBtn_exit.setAlignment(Pos.TOP_LEFT);
	        hbBtn_exit.getChildren().add(btn_exit);
	        grid.add(btn_exit, 4, 11);


	        //Generation parameters
	        Label label_max_nb_planets = new Label("Maximal number of planets");
	        label_max_nb_planets.setFont((new Font("Arial", 30)));
	        label_max_nb_planets.setTextFill(Color.ANTIQUEWHITE);
	        grid.add(label_max_nb_planets, 2, 2);
	        max_numbers_of_planets = new TextField();
	        grid.add(max_numbers_of_planets, 4, 2);
	        
	        Label label_gui_size = new Label("Gui size (pixels)");
	        label_gui_size.setFont((new Font("Arial", 30)));
	        label_gui_size.setTextFill(Color.ANTIQUEWHITE);
	        grid.add(label_gui_size, 2, 3);
	        gui_size = new TextField();
	        grid.add(gui_size, 4, 3);

	        //Ships parameters
	        Label label_ship_min_speed = new Label("Minimal ship speed");
	        label_ship_min_speed.setFont((new Font("Arial", 30)));
	        label_ship_min_speed.setTextFill(Color.ANTIQUEWHITE);
	        grid.add(label_ship_min_speed, 2, 5);
	        parameters_ships_min_speed = new TextField();
	        grid.add(parameters_ships_min_speed, 4, 5);

	        Label label_ship_max_speed = new Label("Maximal ship speed");
	        label_ship_max_speed.setFont((new Font("Arial", 30)));
	        label_ship_max_speed.setTextFill(Color.ANTIQUEWHITE);
	        grid.add(label_ship_max_speed, 2, 6);
	        parameters_ships_max_speed = new TextField();
	        grid.add(parameters_ships_max_speed, 4, 6);
	        

	        btn_apply.setOnAction(new EventHandler<ActionEvent>() { 
	            @Override
	            public void handle(ActionEvent e) {
	            	handleApplyButton();
	            	//actiontarget.setText("Il y a un matelas de disponible");
	            }

	        });
	        

	        
	        btn_exit.setOnAction(new EventHandler<ActionEvent>() { 
	            @Override
	            public void handle(ActionEvent e) {
	            	handleQuitButton();
	            }
	        });
	        
	        grid.setBackground(
	        		new Background(
	        				new BackgroundImage(
		        				new Image((Resources.path_img_menu_background)),
		        				null,
		        				null,
		        				BackgroundPosition.DEFAULT,
		        				new BackgroundSize(Generation.width*1.0, 0.0, true, false, false, true)
		        			)
	        			)
	        		);
	             
		}
		

		private void handleApplyButton() {
	    	double ship_max_speed = ShipsParameters.max_ship_speed, ship_min_speed;
	        if ((parameters_ships_max_speed.getText() != null && !parameters_ships_max_speed.getText().isEmpty())) {
	        	ship_max_speed = Double.parseDouble(parameters_ships_max_speed.getText());
	        }
	        if ((parameters_ships_min_speed.getText() != null && !parameters_ships_min_speed.getText().isEmpty())) {
	            ship_min_speed = Double.parseDouble(parameters_ships_min_speed.getText());
	            
	            if(ship_min_speed > 0 && ship_min_speed < ship_max_speed) {
		            ShipsParameters.min_ship_speed = ship_min_speed;
		            ShipsParameters.max_ship_speed = ship_max_speed;	            	
	            }
	        }
	        if ((gui_size.getText() != null && !gui_size.getText().isEmpty())) {
	            double gui_size_value = Double.parseDouble(gui_size.getText());
	            if(gui_size_value > 1) {
		            Generation.size_squads = gui_size_value;
		            Generation.size_minimal_planets = 5 * gui_size_value;
		            Generation.size_maximal_planets = 8 * gui_size_value;
		            Generation.size_sun = 10 * gui_size_value;	            	
	            }
	        }
	        if ((max_numbers_of_planets.getText() != null && !max_numbers_of_planets.getText().isEmpty())) {
	            int max_numbers_of_planets_value = Integer.parseInt(max_numbers_of_planets.getText());
	            if(max_numbers_of_planets_value > Generation.min_numbers_of_planets && max_numbers_of_planets_value < 25)
	            	Generation.nb_planets_tentatives = max_numbers_of_planets_value;
	        }
	        
	        this.applied = true;			
		}
		
		private void handleQuitButton() {
	    	System.out.println("Bye !");
	    	System.exit(0);			
		}

		
	    public Parent getView() {
	        return grid ;
	    }
		
	    public Scene getScene() {
	        Scene res = new Scene(grid, Generation.width, Generation.height);  
			
	        return res;
	    }

		/**
		 * @return the applied
		 */
		public boolean isApplied() {
			return applied;
		}

		/**
		 * @param applied the applied to set
		 */
		public void setApplied(boolean applied) {
			this.applied = applied;
		}


}

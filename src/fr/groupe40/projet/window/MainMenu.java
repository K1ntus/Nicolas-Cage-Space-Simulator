package fr.groupe40.projet.window;

import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;


public class MainMenu {
	private GridPane grid = new GridPane();
    private TextField player_nickname_form = new TextField();
    private boolean play_game = false;
	

	public MainMenu() {
		this.init();
	
	}
	
	public void init() {
		//Grid init
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Pseudo player
        grid.add(new Text("-  Nicolas Cage Space Simulator  -"), 1, 0, 2, 1);

        grid.add(new Label(Windows.form_player_name), 0, 1);
        grid.add(player_nickname_form, 1, 1);
        
        
        //Buttons        
        Button btn_play = new Button(Windows.button_play);
        HBox hbbtn_play = new HBox(50);
        hbbtn_play.setAlignment(Pos.TOP_LEFT);
        hbbtn_play.getChildren().add(btn_play);
        grid.add(btn_play, 3,1);


        Button btn_settings = new Button(Windows.button_settings);
        HBox hbBtn_settings = new HBox(50);
        hbBtn_settings.setAlignment(Pos.TOP_LEFT);
        hbBtn_settings.getChildren().add(btn_settings);
        grid.add(btn_settings, 3, 2);
        
        Button btn_exit = new Button(Windows.button_close);
        HBox hbBtn_exit = new HBox(50);
        hbBtn_exit.setAlignment(Pos.TOP_LEFT);
        hbBtn_exit.getChildren().add(btn_exit);
        grid.add(btn_exit, 3, 3);


        


        btn_play.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent e) {
            	handlePlayButton();
            	//actiontarget.setText("Il y a un matelas de disponible");
            }

        });
        

        btn_settings.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent e) {
            	handleSettingButton();
            }
        });
        
        btn_exit.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent e) {
            	handleQuitButton();
            }
        });
             
	}
	

	private void handlePlayButton() {
    	System.out.println("Play button");
		play_game = true;
		// TODO Auto-generated method stub
		
	}
	private void handleQuitButton() {
    	System.out.println("Bye !");
    	System.exit(0);
		// TODO Auto-generated method stub
		
	}
	
	
	private void handleSettingButton() {
    	System.out.println("Not yet implemented");
		// TODO Auto-generated method stub
		
	}

	
    public Parent getView() {
        return grid ;
    }
	
    public Scene getScene() {
        Scene res = new Scene(grid, Generation.width, Generation.height);  

        Image background_image = new Image(Resources.path_img_menu_background, Generation.width, Generation.height, false, false, true);
		ImagePattern pattern = new ImagePattern(background_image);
		res.setFill(pattern);
		
        return res;
    }

	/**
	 * @return the play_game
	 */
	public boolean isPlay_game() {
		return play_game;
	}

	/**
	 * @param play_game the play_game to set
	 */
	public void setPlay_game(boolean play_game) {
		this.play_game = play_game;
	}

}

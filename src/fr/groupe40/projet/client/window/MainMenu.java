package fr.groupe40.projet.client.window;

import fr.groupe40.projet.model.board.GalaxyRenderer;
import fr.groupe40.projet.util.annot.WorkInProgress;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Resources;
import fr.groupe40.projet.util.constants.Windows;
import fr.groupe40.projet.util.resources.ImageManager;
import fr.groupe40.projet.util.resources.ResourcesContainer;
import fr.groupe40.projet.util.window.GridElement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Jordane Masson
 * @author Sarah Portejoie
 */
public class MainMenu {
	/**
	 * gridpane containing the button of this scene
	 */
	private GridPane grid = new GridPane();

	/**
	 * is true if the user has pressed "play" button
	 */
	private boolean play_game = false;

	/**
	 * is true if the user has pressed "setting" button
	 */
	private boolean settings_menu = false;

	/**
	 * Constructor, build the scene
	 */
	public MainMenu() {
		this.init();
	}

	/**
	 * Initialize the main menu javafx scene
	 */
	public void init() {
		// Grid init
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(20);
		grid.setPadding(new Insets(25, 25, 25, 25));

		display_title(grid);

		// Nicolas cage pict
		GridElement.add_image_to_gridpane(Resources.path_img_sun, Generation.width/5, true, 2, 10, grid);
		
		//Background
		GridElement.add_image_to_gridpane(ResourcesContainer.getMain_menu_background(), Generation.width/5, true, 3, 6, grid);

		// Buttons
		Button btn_play = GridElement.add_button_to_gridpane(Windows.button_play, 1, 1, grid);
		Button btn_how_to_play = GridElement.add_button_to_gridpane(Windows.button_how_to_play, 1, 2, grid);
		Button btn_settings = GridElement.add_button_to_gridpane(Windows.button_settings, 1, 3, grid);
		Button btn_exit = GridElement.add_button_to_gridpane(Windows.button_close, 1, 4, grid);

		//ImageButton btn_play = new ImageButton(ResourcesContainer.gui_play_selected, ResourcesContainer.gui_play_unselected);
        //grid.add(btn_play.getIv(), 1, 1);
        //init_play_btn();

		
		btn_play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//btn_play.setGraphic(null);
				handlePlayButton();
			}
		});
        
		btn_settings.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				handleSettingButton();
			}
		});

		btn_how_to_play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				handleHowToPlayButton();
			}
		});

		btn_exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				handleQuitButton();
			}
		});

		grid.setBackground(new Background(new BackgroundImage(new Image((Resources.path_img_menu_background)), null,
				null, BackgroundPosition.DEFAULT,
				new BackgroundSize(Generation.width, 0.0, true, false, false, true))));

	}

	@WorkInProgress(comment = "Menu image bouton")
	private void init_play_btn() {
		//btn_play.setGraphic(ImageButton.get_image_view_for_button(Resources.path_img_gui_play_unselected));
		/*
		btn_play.setOnMouseEntered((event) -> {
				btn_play.setGraphic(ImageButton.get_image_view_for_button(Resources.path_img_gui_play_selected));
			}
		);
		
		btn_play.setOnMouseExited((event) -> {
				btn_play.setGraphic(ImageButton.get_image_view_for_button(Resources.path_img_gui_play_unselected));
			}
		);*/
		
	}
	
	/**
	 * Display the title in grid
	 * @param grid
	 */
	private void display_title(GridPane grid) {
		// Top title
		Image title = new Image(ImageManager.getRessourcePathByName(Resources.path_img_menu_title));
		ImageView title_img = new ImageView();
		title_img.setImage(title);
		title_img.setFitWidth(Generation.width / 2);
		title_img.setPreserveRatio(true);
		grid.add((title_img), 2, 0);
		title_img.setTranslateX(Generation.width / 5);
		
	}

	/**
	 * Launch the game
	 */
	private void handlePlayButton() {
		play_game = true;
		try {
			GalaxyRenderer.getRESOURCES_CONTAINER().getPlay_button_sound().play();
		} catch(NullPointerException e) {
			
		}
	}

	/**
	 * Function summoned when the quit button is pressed (quit the game)
	 */
	private void handleQuitButton() {
		System.out.println("Bye !");
		
		System.exit(0);
	}

	/**
	 * Function summoned when the setting button is pressed
	 */
	private void handleSettingButton() {
		try {
			GalaxyRenderer.getRESOURCES_CONTAINER().getSettings_button_sound().play();
		} catch(NullPointerException e) {
			
		}
		settings_menu = true;
	}

	/**
	 * Function summoned when the howto button is pressed
	 */
	@WorkInProgress(comment="Had to make a better window + add explanation about game goals, ... ?")
	private void handleHowToPlayButton() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        //dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Commandes:\n"
        		+ "* Deplacer des troupes: Drag&Drop\n"
        		+ "** Les vaisseaux peuvent etre rediriges\n\n"
        		+ "* Modifier le pourcentage de troupe a envoyer: Molette\n\n"
        		+ "* Sauvegarde rapide: F5\n"
        		+ "* Chargement rapide: F6\n"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.setTitle("How to play");
        dialog.show();
	}

	/**
	 * @return the grid
	 */
	public Parent getView() {
		return grid;
	}

	/**
	 * @return the scene
	 */
	public Scene getScene() {
		Scene res = new Scene(grid, Generation.width, Generation.height);

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

	/**
	 * @return the settings_menu
	 */
	public boolean isSettings_menu() {
		return settings_menu;
	}

	/**
	 * @param settings_menu the settings_menu to set
	 */
	public void setSettings_menu(boolean settings_menu) {
		this.settings_menu = settings_menu;
	}

}

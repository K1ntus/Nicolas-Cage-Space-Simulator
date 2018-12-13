package fr.groupe40.projet.window;

import fr.groupe40.projet.client.Music;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

		// Top title
		Image title = new Image(Resources.path_img_menu_title);
		ImageView title_img = new ImageView();
		title_img.setImage(title);
		title_img.setFitWidth(Generation.width / 2);
		title_img.setPreserveRatio(true);
		grid.add((title_img), 2, 0);
		title_img.setTranslateX(Generation.width / 5);

		// Nicolas cage pict
		Image nicolassse_cage = new Image(Resources.path_img_sun);
		ImageView nicolas_cage_img = new ImageView();
		nicolas_cage_img.setImage(nicolassse_cage);
		nicolas_cage_img.setFitWidth(Generation.width / 5);
		nicolas_cage_img.setPreserveRatio(true);
		grid.add((nicolas_cage_img), 3, 6);
		nicolas_cage_img.setTranslateX(-Generation.width / 2 + Generation.width / 3);

		// Buttons
		Button btn_play = new Button(Windows.button_play);
		HBox hbbtn_play = new HBox(50);
		hbbtn_play.setAlignment(Pos.TOP_LEFT);
		hbbtn_play.getChildren().add(btn_play);
		grid.add(btn_play, 1, 1);

		Button btn_how_to_play = new Button(Windows.button_how_to_play);
		HBox hbBtn_how_to_play = new HBox(50);
		hbBtn_how_to_play.setAlignment(Pos.TOP_LEFT);
		hbBtn_how_to_play.getChildren().add(btn_how_to_play);
		grid.add(btn_how_to_play, 1, 2);

		Button btn_settings = new Button(Windows.button_settings);
		HBox hbBtn_settings = new HBox(50);
		hbBtn_settings.setAlignment(Pos.TOP_LEFT);
		hbBtn_settings.getChildren().add(btn_settings);
		grid.add(btn_settings, 1, 3);

		Button btn_exit = new Button(Windows.button_close);
		HBox hbBtn_exit = new HBox(50);
		hbBtn_exit.setAlignment(Pos.TOP_LEFT);
		hbBtn_exit.getChildren().add(btn_exit);
		grid.add(btn_exit, 1, 4);

		btn_play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
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

	/**
	 * Launch the game
	 */
	private void handlePlayButton() {
		play_game = true;
		Music.getPlay_button_sound().play();
	}

	/**
	 * Function summoned when the quit button is pressed (quit the game)
	 */
	private void handleQuitButton() {
		System.out.println("Bye !");
		Music.getQuit_button_sound().play();
		System.exit(0);
	}

	/**
	 * Function summoned when the setting button is pressed
	 */
	private void handleSettingButton() {
		Music.getSettings_button_sound().play();
		settings_menu = true;
	}

	/**
	 * Function summoned when the howto button is pressed
	 */
	private void handleHowToPlayButton() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        //dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Commandes:\n"
        		+ "* Déplacer des troupes: Drag&Drop\n"
        		+ "** Les vaisseaux peuvent être redirigés\n\n"
        		+ "* Modifier le pourcentage de troupe à envoyer: Molette\n\n"
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

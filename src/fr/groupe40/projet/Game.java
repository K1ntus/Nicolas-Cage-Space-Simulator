package fr.groupe40.projet;


import fr.groupe40.projet.client.Sound;
import fr.groupe40.projet.client.handler.InteractionHandler;
import fr.groupe40.projet.events.Events;
import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.board.GalaxyGenerator;
import fr.groupe40.projet.util.ResourcesManager;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Debugging;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.constants.Resources;
import fr.groupe40.projet.util.constants.Ticks;
import fr.groupe40.projet.util.constants.Windows;
import fr.groupe40.projet.window.MainMenu;
import fr.groupe40.projet.window.SettingsMenu;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *  Main class. Currently managing users interactions and display
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class Game extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 *  manage the user input, currently, only the mouse is managed there
	 */
	private InteractionHandler interactionHandler;

	/**
	 *  Board object containing every sprites, etc
	 */
	private Galaxy galaxy;
	
	/**
	 * Main menu
	 */
	private MainMenu main_menu = new MainMenu();
	
	/**
	 * Setting menu
	 */
	private SettingsMenu setting_menu = new SettingsMenu();
	
	/**
	 * Graphic handler of the game
	 */
	private GraphicsContext gc;
	
	/**
	 * Graphic scene of the game
	 */
	private Scene scene_game;
	
	/**
	 * Saving/Loading manager
	 */
	private DataSerializer saver;
	
	/**
	 * Is true if a game has been loaded
	 */
	private boolean game_loaded = false;
	
	
	/**
	 *  'main' method
	 */
	public void start(Stage stage) {
		/* 	---| OS check |---	
		 * Doing that because there s a little white
		 * margin under Windows (only tested under win 7 btw)
		 * So, if that's a windows OS, we re editing
		 * the window style to remove it
		 */
		String OS = System.getProperty("os.name").toLowerCase();
		if((OS.indexOf("win") >= 0)) {
			if(Debugging.DEBUG)
				System.out.println("OS type is windows");
			stage.initStyle(StageStyle.UNDECORATED);
		}else {
			if(Debugging.DEBUG)
				System.out.println("Non windows OS");
		}

		/* Window and game kernel creation */
		stage.setTitle("Nicolas Cage Space Simulator");
		stage.setResizable(false);

		Group root = new Group();
		
		Canvas canvas_mainMenu = new Canvas(Generation.width, Generation.height);
		gc = canvas_mainMenu.getGraphicsContext2D();
        Image background_image = new Image(Resources.path_img_menu_background, Generation.width, Generation.height, true, true, true);
        gc.drawImage(background_image, 0, 0);
		root.getChildren().add(canvas_mainMenu);

		Scene scene_main_menu = main_menu.getScene();

		stage.setScene(scene_main_menu);
		stage.show();


		
		/* KEYBOARD HANDLER */
		EventHandler<KeyEvent> keyboardHandler = new EventHandler<KeyEvent>() {
	
			@Override
			public void handle(KeyEvent e) {
					
				if (e.getCode() == KeyCode.F5) {
					ResourcesManager.render_loading_pict(gc);
					System.out.println("Saving game ...");
					//OPEN POPUP ?
					saver.save_game();
				}
					
				if (e.getCode() == KeyCode.F6) {
					ResourcesManager.render_loading_pict(gc);
					System.out.println("Loading game ...");
					galaxy = saver.load_game(gc);
					saver = new DataSerializer(Constants.fileName_save, galaxy);
					game_loaded = true;
				}
				
				if (e.getCode() == KeyCode.ESCAPE) {
					game_loaded = false;
					galaxy = null;
					interactionHandler = null;
					main_menu = null;
					setting_menu = null;
					
					System.out.println("Coward !");
					System.exit(0);
				}
				
			}
		};
		
        
		/*	Rendering, game initialisation, etc */
		new AnimationTimer() {
			
			/**
			 *  game_tick counter for events, etc
			 */
			private long game_tick = 0;	//long because counter, had to prevent the overflow case

			/**
			 *  manage the game events
			 */
			private Events eventManager;	
			
			/**
			 *  manage the background game sound + methods to simplify sounds usage
			 */
			private Sound soundHandler = new Sound(true);
			
			/**
			 * Canvas of the game board
			 */
			private Canvas canvas_game;

			/**
			 * Equals to the current window displayed (ie. setting, main menu or game)
			 */
			private Windows.WindowType window_type = Windows.WindowType.MAIN_MENU;
			
			/**
			 * Is true if the board has been pre-initialized
			 */
			private boolean game_pre_init_done = false;
			
			/**
			 * Is true if the game has been initialized
			 */
			private boolean game_init_done = false;
			
			/**
			 * Generate the planet over the board, used during pre-init to split the calculation
			 */
			private GalaxyGenerator gg;

			/**
			 * Pre-initialize the game board with few elements
			 */
			private void pre_init() {	
				canvas_game = new Canvas(Generation.width, Generation.height);
				
				gg = new GalaxyGenerator();
				
				game_pre_init_done = true;
				
				if(Debugging.DEBUG)
					System.out.println("-> Pre-Init done");
			}
			
			/**
			 * Initialize the game board
			 */
			private void init() {

				
				root.getChildren().remove(canvas_mainMenu);
				root.getChildren().add(canvas_game);

				galaxy = new Galaxy(gc, gg);

				saver = new DataSerializer(Constants.fileName_save, galaxy);
				
				if(Constants.events_enabled) 
					eventManager = new Events(galaxy, gc, true, true);	
				
				gc = canvas_game.getGraphicsContext2D();
				galaxy.setGraphicsContext(gc);
				galaxy.initFont(gc);
				
				scene_game = new Scene(root);
				
				interactionHandler = new InteractionHandler(galaxy, scene_game, saver);
				interactionHandler.exec();			

				scene_game.setOnKeyPressed(keyboardHandler);

				window_type = Windows.WindowType.GAME;
				
				stage.setScene(scene_game);
				stage.show();	
				
				game_init_done = true;
				
				if(Debugging.DEBUG)
					System.out.println("-> Init done");
			}
			
			/**
			 * Tick updater
			 */
			private void run() {
				game_tick += 1;
				
				if(game_loaded) {
					saver.reload_image_and_data(galaxy);
					interactionHandler = new InteractionHandler(galaxy, scene_game, saver);
					interactionHandler.exec();
					game_loaded = false;
				}
				
				galaxy.render(gc);
				
				if(game_tick % Ticks.tick_per_squad_position_update == 0)
					galaxy.updateSquadPosition(Sound.getSound_ship_explosion());
				
				if(game_tick % Ticks.tick_per_produce == 0)
					galaxy.updateGarrison();
				
				if(game_tick % Ticks.tick_per_lift_off == 0)
					galaxy.updateWavesSending();
				
				if(game_tick % Ticks.tick_per_ai_attack == 0)
					galaxy.updateAI();

				
				if(game_tick % Ticks.tick_per_events == 0)
					if(Constants.events_enabled)
						eventManager.event_randomizer();
				
				if(game_tick % Ticks.tick_per_main_theme_check == 0)
					soundHandler.run();
								
				if(galaxy.userHasLost(Players.human_user)) {	//The user has lost
					System.out.println("Vous avez perdu");
					galaxy.render(gc);
					galaxy.renderDefeat(gc);
					galaxy.setGame_is_over(true);
				}
				
				if(galaxy.isGame_is_over()) {
					ResourcesManager.render_loading_pict(gc);
					
					if(Debugging.DEBUG)
						System.out.println(
							"Partie terminee\n"
							+ "Generating new board ...");
						
						galaxy.resetEveryUsersLostState();
						galaxy = new Galaxy(gc);
						interactionHandler = new InteractionHandler(galaxy, scene_game, saver);
						interactionHandler.exec();					
				}
			}
			
			/**
			 * Function called to display the setting menu
			 */
			private void display_settings_menu() {
				main_menu.setSettings_menu(false);
				Canvas canvas_settings = new Canvas(Generation.width, Generation.height);
				gc = canvas_settings.getGraphicsContext2D();
		        gc.drawImage(background_image, 0, 0);
				
				root.getChildren().add(canvas_settings);

				window_type = Windows.WindowType.SETTINGS;
				Scene scene_settings = setting_menu.getScene();
				
				stage.setScene(scene_settings);
				stage.show();				
			}
			
			/**
			 * Apply the setting to the game board (need a total re-init of the game board)
			 */
			private void apply_settings_to_game() {				
				setting_menu.setApplied(false);
				main_menu.setPlay_game(false);
				main_menu.setSettings_menu(false);
				window_type = Windows.WindowType.MAIN_MENU;
				game_init_done = false;
				game_pre_init_done = false;

				stage.setScene(scene_main_menu);
				stage.show();
				
			}
			
			/**
			 * Manage each ticks and the differents screens displayed
			 */
			public void handle(long arg0) {	
				if(window_type == Windows.WindowType.GAME) {
					if(game_init_done) {
						run();
					}else {
						init();
					}
				} else if(window_type == Windows.WindowType.SETTINGS && setting_menu.isApplied()) {
					apply_settings_to_game();
				} else if(window_type == Windows.WindowType.MAIN_MENU && !main_menu.isPlay_game() && !main_menu.isSettings_menu()) {
					if(!game_pre_init_done) {
						pre_init();
					}
				} else if (main_menu.isPlay_game()) {
					if(!game_pre_init_done) {
						pre_init();
					} else if(!game_init_done) {
						init();
					} else {
						run();
					}
				} else if (main_menu.isSettings_menu() && window_type == Windows.WindowType.MAIN_MENU) {
					display_settings_menu();
				}
			}
		}.start();

	}

	
}

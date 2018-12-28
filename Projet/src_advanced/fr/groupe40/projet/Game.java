package fr.groupe40.projet;

import java.io.File;

import fr.groupe40.projet.client.handler.InteractionHandler;
import fr.groupe40.projet.client.window.MainMenu;
import fr.groupe40.projet.client.window.SettingsMenu;
import fr.groupe40.projet.events.Events;
import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.board.GalaxyGenerator;
import fr.groupe40.projet.model.board.GalaxyRenderer;
import fr.groupe40.projet.util.annot.TODO;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Resources;
import fr.groupe40.projet.util.constants.Windows.WindowType;
import fr.groupe40.projet.util.resources.SoundManager;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Main class. Currently managing users interactions and display
 * 
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
@TODO(comment = "Create a specific class for window management")
public class Game extends Application {
	public static void main(String[] args) {
		File dir = new File(Constants.path_save);
		dir.mkdir();
		
		launch(args);
	}
	/*
	  @Override
	  public void init() throws Exception {
		  root = new Group();
		  
	      notifyPreloader(new LoaderScreen.ProgressNotification(0.0));
	      
	      soundHandler = SoundManager.getInstance();
	      notifyPreloader(new LoaderScreen.ProgressNotification(0.25));
	      
	      main_menu = new MainMenu();
	      notifyPreloader(new LoaderScreen.ProgressNotification(0.67));
	      
	      setting_menu = new SettingsMenu();
	      notifyPreloader(new LoaderScreen.ProgressNotification(0.100));
	  }
	  */

	/**
	 * manage the user input, currently, only the mouse is managed there
	 */
	private InteractionHandler interactionHandler;

	/**
	 * manage the background game sound + methods to simplify sounds usage
	 */
	private SoundManager soundHandler = SoundManager.getInstance();

	/**
	 * Board object containing every sprites, etc
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
	 * Canvas of the game board
	 */
	private Canvas canvas_game = new Canvas(Constants.width, Constants.height);

	/**
	 * Canvas of the setting menu
	 */
	private Canvas canvas_settings = new Canvas(Constants.width, Constants.height);

	/**
	 * Canvas of the main menu
	 */
	private Canvas canvas_mainMenu = new Canvas(Constants.width, Constants.height);

	/**
	 * Javafx root
	 */
	Group root = new Group();

	/**
	 * The scene for the setting part
	 */
	private Scene scene_settings_menu;

	/**
	 * The scene for the main menu
	 */
	private Scene scene_main_menu;

	/**
	 * Handle keyboard I/O. (Save/Load & Escape)
	 */
	@TODO(comment = "Put the keyboard handler into another class")
	private EventHandler<KeyEvent> keyboardHandler;

	/**
	 * 'main' method
	 */
	public void start(Stage stage) {
		if(!Resources.sounds_enabled) {
			soundHandler = null;
			SoundManager.destroy();
			System.gc();
		}
		
		init_window(stage);

		/* Rendering, game initialization, etc */
		clock_updater(stage);
	}

	/**
	 * Remove every canvas from the root Node
	 */
	private void clear_root() {
		root.getChildren().remove(canvas_game);
		root.getChildren().remove(canvas_settings);
		root.getChildren().remove(canvas_mainMenu);
	}

	/**
	 * Function that handle the game Constants, ticks update, etc ...
	 * 
	 * @param stage
	 *            the program main stage
	 */
	@TODO(comment = "Fix his complexity and create a specific updating class")
	private void clock_updater(Stage stage) {
		new AnimationTimer() {

			/**
			 * game_tick counter for events, etc
			 */
			private long game_tick = 0; // long because counter, had to prevent the overflow case

			/**
			 * manage the game events
			 */
			private Events eventManager;

			/**
			 * Equals to the current window displayed (ie. setting, main menu or game)
			 */
			private WindowType window_type = WindowType.MAIN_MENU;

			/**
			 * Is true if the board has been pre-initialized
			 */
			private boolean game_pre_init_done = false;
			/**
			 * Is true if the game is init/preinitalizing
			 */
			private boolean game_pre_init_running, game_init_running = false;

			/**
			 * Is true if the game has been initialized
			 */
			private boolean game_init_done = false;

			/**
			 * Pre-generate the galaxy list of planets
			 */
			private GalaxyGenerator gg;

			/**
			 * Pre-initialize the game board with few elements
			 */
			private void pre_init() {
				if (game_pre_init_running) {
					return;
				}
				long startTime = System.currentTimeMillis();

				Thread pre_init_thread = new Thread(new Runnable() {
					@Override
					public void run() {
						game_pre_init_running = true;

						gg = GalaxyGenerator.getInstance();
						long endTime = System.currentTimeMillis();
						if (Constants.DEBUG || Constants.DEBUG_TIMER)
							System.out.println("Pre-Init done in " + (endTime - startTime) + " ms");

						if (gg == null) {
							game_pre_init_running = false;
							return;
						}

						game_pre_init_done = true;
						game_pre_init_running = false;
						return;
					}
				});

				pre_init_thread.setPriority(1);
				pre_init_thread.setDaemon(true);
				
				try {
					pre_init_thread.start();
					pre_init_thread.join(2000);
				} catch (InterruptedException e) {
					gg = null;
					long endTime = System.currentTimeMillis();
					System.out.println("Pre-Init failed after " + (endTime - startTime) + " ms");
					game_pre_init_running = false;
					e.printStackTrace();
				}

			}

			/**
			 * Initialize the game board
			 */
			private void init() {
				if (game_init_running)
					return;
				long startTime = System.currentTimeMillis();

				game_init_running = true;

				galaxy = new Galaxy(gc, gg);
				saver = new DataSerializer(galaxy);

				if (Constants.events_enabled)
					eventManager = Events.getInstance(galaxy, gc, true, true);

				gc = canvas_game.getGraphicsContext2D();
				galaxy.setGraphicsContext(gc);
				galaxy.initFont(gc);

				interactionHandler = new InteractionHandler(galaxy, scene_game, saver);
				interactionHandler.exec();

				scene_game.setOnKeyPressed(keyboardHandler);

				try {
					clear_root();
					root.getChildren().add(canvas_game);
				} catch (IllegalArgumentException e) {

				}

				window_type = WindowType.GAME;

				stage.setScene(scene_game);
				stage.show();

				GalaxyGenerator.resetInstance();
				game_init_done = true;
				game_init_running = false;

				long endTime = System.currentTimeMillis();
				if (Constants.DEBUG || Constants.DEBUG_TIMER)
					System.out.println("Init done in " + (endTime - startTime) + " ms");
			}

			/**
			 * Tick updater
			 */
			private void run() {
				if (saver.isBox_opened()) {
					return;
				}

				game_tick += 1;

				if (game_loaded) {
					saver.reload_image_and_data(galaxy);
					interactionHandler = new InteractionHandler(galaxy, scene_game, saver);
					interactionHandler.exec();
					game_loaded = false;
				}

				galaxy.render(gc);
				galaxy.updateSquadPosition();

				/*
				 * if(game_tick % Ticks.tick_per_squad_position_update == 0)
				 * galaxy.updateSquadPosition();
				 */

				if (game_tick % Constants.tick_per_produce == 0)
					galaxy.updateGarrison();

				if (game_tick % Constants.tick_per_lift_off == 0)
					galaxy.updateWavesSending();

				if (game_tick % Constants.tick_per_ai_attack == 0)
					galaxy.updateAI();

				if (game_tick % Constants.tick_per_events == 0)
					if (Constants.events_enabled)
						eventManager.event_randomizer();

				if (game_tick % Constants.tick_per_main_theme_check == 0 && Resources.sounds_enabled)
					soundHandler.run();

				if (galaxy.isGame_is_over()) {
					if (galaxy.userHasLost(Constants.human_user)) { // The user has lost
						System.out.println("Vous avez perdu");
						galaxy.render(gc);
						GalaxyRenderer.renderDefeat(gc);
						galaxy.setGame_is_over(true);

					} else {
						System.out.println("Vous avez gagne");
						galaxy.render(gc);
						GalaxyRenderer.renderWinner(gc);
						galaxy.setGame_is_over(true);
					}

					if (Constants.DEBUG)
						System.out.println("Partie terminee\n" + "Generating new board ...");

					galaxy.resetEveryUsersLostState();
					game_init_done = false;
					game_pre_init_done = false;

					game_tick = 0;
					
				}
			}

			/**
			 * Function called to display the setting menu
			 */
			private void display_settings_menu() {
				main_menu.setSettings_menu(false);
				gc = canvas_settings.getGraphicsContext2D();
				window_type = WindowType.SETTINGS;

				stage.setScene(scene_settings_menu);
				stage.show();
			}

			/**
			 * Apply the setting to the game board (need a total re-init of the game board)
			 */
			private void apply_settings_to_game() {
				setting_menu.setApplied(false);
				main_menu.setPlay_game(false);
				main_menu.setSettings_menu(false);
				window_type = WindowType.MAIN_MENU;

				game_init_done = false;
				game_pre_init_done = false;
				
				if(!Resources.sounds_enabled) {
					soundHandler.run();
					SoundManager.destroy();
				} else {
					soundHandler = SoundManager.getInstance();
				}

				stage.setScene(scene_main_menu);
				stage.show();
			}

			/**
			 * Manage each ticks and the differents screens displayed
			 */
			public void handle(long arg0) {
				if (window_type == WindowType.GAME) {
					if (game_init_done) {
						run();
					} else if (game_pre_init_done) {
						init();
					} else {
						pre_init();
					}
				} else if (window_type == WindowType.MAIN_MENU && !main_menu.isPlay_game()
						&& !main_menu.isSettings_menu()) {
					if (!game_pre_init_done) {
						pre_init();
					}
				} else if (window_type == WindowType.SETTINGS && setting_menu.isApplied()) {
					apply_settings_to_game();
				} else if (main_menu.isPlay_game()) {
					if (!game_pre_init_done) {
						pre_init();
					} else if (!game_init_done) {
						init();
					} else {
						run();
					}
				} else if (main_menu.isSettings_menu() && window_type == WindowType.MAIN_MENU) {
					display_settings_menu();
				}
			}
		}.start();
	}

	/**
	 * Initialize the first properties of the game window
	 * 
	 * @param stage
	 *            the application stage
	 */
	private void init_window(Stage stage) {
		long startTime = System.currentTimeMillis();
		/*
		 * ---| OS check |--- Doing that because there s a little white margin under
		 * Windows (only tested under win 7 btw) So, if that's a windows OS, we re
		 * editing the window style to remove it
		 */
		String OS = System.getProperty("os.name").toLowerCase();
		if ((OS.indexOf("win") >= 0)) {
			if (Constants.DEBUG)
				System.out.println("OS type is windows");
			stage.initStyle(StageStyle.DECORATED);
		} else {
			if (Constants.DEBUG || Constants.DEBUG_TIMER)
				System.out.println("Non windows OS");
		}

		/* Window and game kernel creation */
		stage.setTitle("Nicolas Cage Space Simulator");
		stage.setResizable(false);

		gc = canvas_mainMenu.getGraphicsContext2D();

		root.getChildren().add(canvas_mainMenu);

		scene_main_menu = main_menu.getScene();
		scene_settings_menu = setting_menu.getScene();

		scene_game = new Scene(root);

		stage.setScene(scene_main_menu);
		stage.show();

		keyboardHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {

				if (e.getCode() == KeyCode.F5) {
					System.out.println("Saving game ...");
					long startTime = System.currentTimeMillis();

					try {
						saver.save_game(stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					long endTime = System.currentTimeMillis();
					if (Constants.DEBUG || Constants.DEBUG_TIMER)
						System.out.println("Saving done in " + (endTime - startTime) + " ms");

					saver.setBox_opened(false);
				}

				if (e.getCode() == KeyCode.F6) {
					System.out.println("Loading game ...");
					long startTime = System.currentTimeMillis();

					try {
						galaxy = saver.load_game(gc, stage);
						saver = new DataSerializer(galaxy);
						game_loaded = true;
					} catch (Exception e1) {
						// Save not found, do nothing
					}

					long endTime = System.currentTimeMillis();
					if (Constants.DEBUG || Constants.DEBUG_TIMER)
						System.out.println("Loading done in " + (endTime - startTime) + " ms");

					saver.setBox_opened(false);
				}

				if (e.getCode() == KeyCode.ESCAPE) {
					game_loaded = false;
					galaxy = null;
					interactionHandler = null;
					main_menu = null;
					setting_menu = null;

					/* NOT WORKING */
					FadeTransition fade_out = new FadeTransition(Duration.millis(3000), root);
					fade_out.setFromValue(1.0);
					fade_out.setToValue(0.0);
					fade_out.play();
					/* NOT WORKING */

					System.out.println("Coward !");
					System.exit(0);
				}

			}
		};

		long endTime = System.currentTimeMillis();
		if (Constants.DEBUG || Constants.DEBUG_TIMER)
			System.out.println("Game window done in " + (endTime - startTime) + " ms");
	}

}

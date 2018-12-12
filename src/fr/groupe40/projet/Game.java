package fr.groupe40.projet;


import fr.groupe40.projet.client.Music;
import fr.groupe40.projet.client.handler.InteractionHandler;
import fr.groupe40.projet.events.Events;
import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Debugging;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.constants.Resources;
import fr.groupe40.projet.util.constants.Ticks;
import fr.groupe40.projet.util.constants.Windows;
import fr.groupe40.projet.window.MainMenu;
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
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * \brief Main class. Currently managing users interactions and display
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class Game extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * \brief Board object containing every sprites, etc
	 */
	
	private Galaxy galaxy;
	
	
	/**
	 * \brief game_tick counter for events, etc
	 */
	private long game_tick = 0;	//long because counter, had to prevent the overflow case
	
	/**
	 * \brief manage the game events
	 */
	private Events eventManager;	
	
	/**
	 * \brief manage the background game sound + methods to simplify sounds usage
	 */
	private Music soundHandler = new Music(true);
	
	/**
	 * \brief contain the sound of a ship collision, set there to not worries about serialization for loading/saving
	 */
	private AudioClip mediaPlayer_ship_explosion;
	
	/**
	 * \brief get the OS type string, used to change the window style
	 */
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	/**
	 * \brief manage the user input, currently, only the mouse is managed there
	 */
	private InteractionHandler interactionHandler;
	
	private Windows.WindowType window_type = Windows.WindowType.MAIN_MENU;
	private boolean game_pre_init_done = false;
	private boolean game_init_done = false;
	private boolean game_loaded = false;
	
	private MainMenu main_menu = new MainMenu();
	private GraphicsContext gc;
	private Scene scene_game;
	private DataSerializer saver;
	/**
	 * \brief 'main' method
	 */
	public void start(Stage stage) {
		/* 	---| OS check |---	
		 * Doing that because there s a little white
		 * margin under Windows (only tested under win 7 btw)
		 * So, if that's a windows OS, we re editing
		 * the window style to remove it
		 */
		if((OS.indexOf("win") >= 0)) {
			if(Debugging.DEBUG)
				System.out.println("OS type is windows");
			stage.initStyle(StageStyle.UNDECORATED);
		}else {
			if(Debugging.DEBUG)
				System.out.println("Non windows OS");
		}
		
		/* Initialize ships explosion sound */
		mediaPlayer_ship_explosion = soundHandler.generateAudioClip(Resources.path_sound_explosion, Resources.ship_explosion_volume);

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
        //scene_main_menu.setRoot(root);
		stage.setScene(scene_main_menu);
		stage.show();


		
		/**	KEYBOARD HANDLER	**/
		EventHandler<KeyEvent> keyboardHandler = new EventHandler<KeyEvent>() {
	
			@Override
			public void handle(KeyEvent e) {
					
				if (e.getCode() == KeyCode.F5) {
					System.out.println("Saving game ...");
					//OPEN POPUP ?
					saver.save_game();
				}
					
				if (e.getCode() == KeyCode.F6) {
					System.out.println("Loading game ...");
					galaxy = saver.load_game(gc);
					game_loaded = true;
				}
				
			}
		};
        
		/*	Rendering, game initialisation, etc */
		new AnimationTimer() {
			private Canvas canvas_game;

			private void pre_init() {
				galaxy = new Galaxy(gc);

				saver = new DataSerializer(Constants.fileName_save, galaxy);
				
				if(Constants.events_enabled) 
					eventManager = new Events(galaxy, gc, true, true);	
				
				game_pre_init_done = true;
				System.out.println("Pre_init done");	
			}
			
			private void init() {

				canvas_game = new Canvas(Generation.width, Generation.height);
				
				root.getChildren().remove(canvas_mainMenu);
				root.getChildren().add(canvas_game);
				
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
				
				System.out.println("init done");
			}
			
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
					galaxy.updateSquadPosition(mediaPlayer_ship_explosion);
				
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
					//System.exit(0);
				}
				
				if(galaxy.isGame_is_over()) {
					System.out.println("Generating new board");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					galaxy = new Galaxy(gc);
					interactionHandler = new InteractionHandler(galaxy, scene_game, saver);
					interactionHandler.exec();					
				}
				
				
			}
			
			private void apply_settings_to_game() {
				
			}
			
			
			public void handle(long arg0) {	
				if(window_type == Windows.WindowType.GAME) {
					if(game_init_done) {
						run();
					}else {
						init();
					}
				} else if(window_type == Windows.WindowType.MAIN_MENU && !main_menu.isPlay_game()) {
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
				} else if (window_type == Windows.WindowType.SETTINGS) {
					//TODO Setting screen
					apply_settings_to_game();
				}
			}
		}.start();

	}

	
}

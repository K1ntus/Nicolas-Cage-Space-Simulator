package fr.groupe40.projet;


import fr.groupe40.projet.client.handler.InteractionHandler;
import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.util.constants.Constants;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


/**
 * Main class. Currently managing users interactions and display
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class Game extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Board object containing every sprites, etc
	 */
	private Galaxy galaxy;
	
		
	/**
	 * Manage the mouse interactions
	 */
	private InteractionHandler interactionHandler;
	
	
	public void start(Stage stage) {

		/* Window and game kernel creation */
		stage.setTitle("Nicolas Cage Space Simulator");
		stage.setResizable(false);

		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(Constants.width, Constants.height);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		

		galaxy = new Galaxy(gc);
		galaxy.initFont(gc);

		DataSerializer saver = new DataSerializer(Constants.fileName_save, galaxy);
		

		interactionHandler = new InteractionHandler(galaxy, scene, saver);
		interactionHandler.exec();
		
		
		stage.setScene(scene);
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
					saver.reload_image_and_data(galaxy);

					interactionHandler = new InteractionHandler(galaxy, scene, saver);
					interactionHandler.exec();
				}
				
			}
		};
		
		scene.setOnKeyPressed(keyboardHandler);
        
		/*	Rendering */
		new AnimationTimer() {
			/**
			 * game_tick counter for events, etc
			 */
			private long game_tick = 0;	//long as counter, but BigInteger could be used (9trillion trillion)
			
			/**
			 * Manage the board updater & renderer
			 */
			public void handle(long arg0) {	
				game_tick += 1;

				galaxy.render(gc);
				
				if(game_tick % Constants.tick_per_squad_position_update == 0)
					galaxy.updateSquadPosition();
				
				if(game_tick % Constants.tick_per_produce == 0)
					galaxy.updateGarrison();
				
				if(game_tick % Constants.tick_per_lift_off == 0)
					galaxy.updateWavesSending();
				
				if(game_tick % Constants.tick_per_ai_attack == 0)
					galaxy.updateAI();

								
				
				//Generate new board and play again
				if(galaxy.isGame_is_over()) {	
					if(galaxy.userHasLost(Constants.human_user)) {	//The human has lost
						System.out.println("you lose");
						galaxy.render(gc);
						galaxy.renderDefeat(gc);
						galaxy.setGame_is_over(true);
						//System.exit(0);
					} else if(galaxy.userHasLost(Constants.ai_user)) {
						System.out.println("you won");
						galaxy.render(gc);
						galaxy.renderWinner(gc);
						galaxy.setGame_is_over(true);
					}
					
					System.out.println("Generating new board");
					galaxy = new Galaxy(gc);
					interactionHandler = new InteractionHandler(galaxy, scene, saver);
					interactionHandler.exec();					
				}
				
			}
		}.start();
	}
	
	
}
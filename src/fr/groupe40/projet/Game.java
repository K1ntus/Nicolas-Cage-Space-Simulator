package fr.groupe40.projet;


import fr.groupe40.projet.client.handler.InteractionHandler;
import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.util.constants.Constants;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
	//private InteractionHandler interactionHandler;
	
	
	/**
	 * \brief game_tick counter for events, etc
	 */
	private long game_tick = 0;	//long because counter, had to prevent the overflow case
	
	public void start(Stage stage) {
		
		/** Window and game kernel creation **/
		stage.setTitle("Surgeon Simulator 2");
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);

		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(Constants.width, Constants.height);
		root.getChildren().add(canvas);

		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		galaxy = new Galaxy();
		galaxy.initFont(gc);

		DataSerializer saver = new DataSerializer(Constants.fileName_save, galaxy, stage);
		
		InteractionHandler interactionHandler = new InteractionHandler(galaxy, scene, saver);
		interactionHandler.start();

		
		stage.setScene(scene);
		stage.show();

		
        
		/*	Rendering */
		new AnimationTimer() {
			public void handle(long arg0) {
				
				game_tick += 1;
				
				if(game_tick % Constants.tick_per_squad_position_update == 0)
					galaxy.updateSquadPosition();
				
				if(game_tick % Constants.tick_per_produce == 0)
					galaxy.updateGarrison();
				
				if(game_tick % Constants.tick_per_lift_off == 0)
					galaxy.updateWavesSending();
				
				if(game_tick % Constants.tick_per_ai_attack == 0)
					galaxy.updateAI();
				
				galaxy.render(gc);
				
								
				if(galaxy.userHasLost(Constants.human_user)) {	//The user has lost
					System.out.println("Vous avez perdu");
					galaxy.renderDefeat(gc);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		}.start();
	}
	
	
}

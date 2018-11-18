package fr.projet.groupe40;

import fr.projet.groupe40.client.handler.InteractionHandler;
import fr.projet.groupe40.file.DataSerializer;
import fr.projet.groupe40.model.board.Galaxy;
import fr.projet.groupe40.util.Constantes;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Game extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		
		/** Window and game kernel creation **/
		stage.setTitle("Surgeon Simulator 2");
		stage.setResizable(false);

		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(Constantes.width, Constantes.height);
		root.getChildren().add(canvas);

		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Galaxy galaxy = new Galaxy();
		galaxy.initFont(gc);

		DataSerializer saver = new DataSerializer("01", galaxy);
		InteractionHandler interactionHandler = new InteractionHandler(galaxy,saver);
		
		stage.setScene(scene);
		stage.show();

		
		/** Human interaction Handler	**/
		scene.setOnScroll(interactionHandler.getScrollEvent());
        scene.setOnMousePressed(interactionHandler.getMousePressedEvent());
        scene.setOnMouseDragged(interactionHandler.getMouseDraggedEvent());
        scene.setOnKeyPressed(interactionHandler.getKeyboardEvent());
        
		/**	Rendering **/
		new AnimationTimer() {
			public void handle(long arg0) {
				galaxy.update();
				galaxy.render(gc);				
			}
		}.start();
	}
	
	
}

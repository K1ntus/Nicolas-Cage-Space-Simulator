package fr.projet.groupe111;

import fr.projet.groupe111.model.board.Galaxy;
import fr.projet.groupe111.model.board.Planet;
import fr.projet.groupe111.util.Constantes;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

		stage.setScene(scene);
		stage.show();

		/** Mouse interaction **/
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				for(Planet p : galaxy.getPlanets()) {
					p.clickedOnPlanet(e.getX(), e.getY());
				}
				//TODO
			}
		};

		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);

		/** Keyboard interaction **/
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				//TODO
			}
		});
		
		/**	Rendering **/
		new AnimationTimer() {
			public void handle(long arg0) {
				galaxy.update();
				galaxy.render(gc);
				
			}
		}.start();
	}
}

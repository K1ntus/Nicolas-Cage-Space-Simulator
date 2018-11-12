package fr.projet.groupe111;

import java.io.InputStream;
import java.util.Iterator;

import fr.projet.groupe111.model.board.Galaxy;
import fr.projet.groupe111.model.board.Planet;
import fr.projet.groupe111.model.ships.Squad;
import fr.projet.groupe111.util.Constantes;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Game extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public static InputStream getRessourcePathByName(String name) {
		return Game.class.getResourceAsStream(name);
	}
	
	public void start(Stage stage) {

		stage.setTitle("Surgeon Simulator 2");
		stage.setResizable(false);

		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(Constantes.width, Constantes.height);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);
		
		Galaxy galaxy = new Galaxy();
		Image background = new Image(getRessourcePathByName("/resources/images/background.jpg"), Constantes.width, Constantes.height, false, false);

		stage.setScene(scene);
		stage.show();

		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//TODO
			}
		};

		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);

		/** Keyboard interaction **/
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {

			}
		});

		new AnimationTimer() {
			public void handle(long arg0) {

				gc.drawImage(background, 0, 0);

				for(Planet p : galaxy.getPlanets()) {
					if(p != null)
						p.render(gc);					
				}
				
				Iterator<Squad> it = galaxy.getSquads().iterator();
				while (it.hasNext()) {
					Squad ss = it.next();
					if(ss.isReached()) {
						System.out.println("Ship "+ss.getNb_of_ships()+" has reached destination");
						it.remove();
					}else {
						ss.updatePosition();
						ss.render(gc);							
					}
				}
				
			}
		}.start();
	}
}

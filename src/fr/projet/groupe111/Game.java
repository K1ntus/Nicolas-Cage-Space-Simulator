package fr.projet.groupe111;

import fr.projet.groupe111.model.board.Galaxy;
import fr.projet.groupe111.model.board.Planet;
import fr.projet.groupe111.model.ships.Squad;
import fr.projet.groupe111.util.Constantes;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class Game extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	private Galaxy galaxy = new Galaxy();

	public void start(Stage stage) {
		
		/** Window and game kernel creation **/
		stage.setTitle("Surgeon Simulator 2");
		stage.setResizable(false);

		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(Constantes.width, Constantes.height);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		galaxy.initFont(gc);

		stage.setScene(scene);
		stage.show();

		/**	Init drag&drop	**/
		/**	Drag&Drop	**/		
		/** Mouse interaction **/
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			Planet source = null; Planet destination = null;

			@Override
			public void handle(MouseEvent event) {
				try {
					if(source == null) {
						for(Planet p : galaxy.getPlanets()) {
							if(p.clickedOnPlanet(event.getX(), event.getY())) {
								source = p;
								if(source == null) {	break;	}
								if(source.getRuler().getFaction() == Constantes.player) {
								break;
								}
							}else {
								if(Constantes.DEBUG) {
									System.out.println("Vous n'etes pas le ruler de cette colonie");
								}
							}
						}	

					}
							
					if(source == null) {
						return;
					}
						for(Planet p : galaxy.getPlanets()) {
							if(p.clickedOnPlanet(event.getX(), event.getY())) {
								if(!source.intersects(destination)) {
									destination = p;
									Squad s = source.sendFleet(destination);
									galaxy.getSquads().add(s);
								}
								break;
							}
					}
					
					
				}catch(NullPointerException e) {
					source = null;
					destination = null;
					
				}
				System.out.println(source + " -> " + destination);
			}
		};

		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);

		/** Mouse Scrolling interaction */
		EventHandler<ScrollEvent> scrollEventHandler = new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
			    if (event.getDeltaY() < 0) {	//diminution
			    	if(Constantes.DEBUG)
			    		System.out.println("Scroll down");
			        galaxy.clientScrollHandler(0);
			    } else if (event.getDeltaY() > 0){//augmentation
			    	if(Constantes.DEBUG)
			    		System.out.println("Scroll up");
			        galaxy.clientScrollHandler(-1);
			    } else {
			    	//nothing
			    }
				
			}
		};
		scene.setOnScroll(scrollEventHandler);
		
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

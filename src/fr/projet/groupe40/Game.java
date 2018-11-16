package fr.projet.groupe40;

import fr.projet.groupe40.file.DataSerializer;
import fr.projet.groupe40.model.board.Galaxy;
import fr.projet.groupe40.model.board.Planet;
import fr.projet.groupe40.model.ships.Squad;
import fr.projet.groupe40.util.Constantes;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
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

		DataSerializer saver = new DataSerializer("01", galaxy);
		stage.setScene(scene);
		stage.show();

		/** Mouse interaction **/
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			Planet source = null; Planet destination = null;

			@Override
			public void handle(MouseEvent event) {
					for(Planet p : galaxy.getPlanets()) {
						
						try {
							if(event.isPrimaryButtonDown()) {	//usually left button
								if(p.clickedOnPlanet(event.getX(), event.getY())) {
									source = p;
									if(source == null) {	break;	}
									if(source.getRuler().getFaction() == Constantes.player) {
										source.getRuler().setSource(p);
										break;
									}else {
										if(Constantes.DEBUG) {
											System.out.println("Vous n'etes pas le dirigeant de cette colonie");
										}
									}
								}							
							}
							
							if(event.isSecondaryButtonDown()) {	//usually right button
								if(p.clickedOnPlanet(event.getX(), event.getY())) {
									if(!source.intersects(p)) {
										destination = p;
										source.getRuler().setDestination(p);
									}
									break;
								}
								
							}
	
						} catch(NullPointerException e) {
						//Nothing
						}
					}
					
				
					System.out.println(source + " -> " + destination);
				}
		};

		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);

		/** Mouse Scrolling interaction */
		EventHandler<ScrollEvent> scrollEventHandler = new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
			    if (event.getDeltaY() < 0) {	//diminution
			        galaxy.clientScrollHandler(0);
			    } else if (event.getDeltaY() > 0){//augmentation
			        galaxy.clientScrollHandler(-1);
			    } else {
			    	//Do nothing
			    }
				
			}
		};
		scene.setOnScroll(scrollEventHandler);
		
		/** Keyboard interaction **/
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				
				if(e.getCode() == KeyCode.SPACE) {
					System.out.println("* button space pressed");
					
					for(Planet p : galaxy.getPlanets()) {
						System.out.println("*planet boucle:" +p.toString());
						System.out.println("** ruler: " + p.getRuler().toString());
						
						if(p.getRuler() == Constantes.human_user) {
							System.out.println("*** ruler well selected");
							Planet source = p.getRuler().getSource();
							Planet destination = p.getRuler().getDestination();
							
							if(destination != null && source != null) {
								Squad s = source.sendFleet(destination);
								System.out.println("**** fleet sent");
								System.out.println("******************");
								galaxy.getSquads().add(s);
								
								
							}
						} else {
							p.getRuler().setDestination(null);
						}
					}

				}
				
				if (e.getCode() == KeyCode.F5) {
					System.out.println("Saving game ...");
					saver.save_game();
				}
				
				if (e.getCode() == KeyCode.F6) {
					System.out.println("Loading game ...");
					galaxy = saver.load_game(galaxy);
					saver.reload_image(galaxy);
				}
				
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

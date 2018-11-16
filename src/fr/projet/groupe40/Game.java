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
	private Galaxy galaxy = new Galaxy();
    private double orgSceneX, orgSceneY;
	private Planet source = null; private Planet destination = null;
	
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
		galaxy.initFont(gc);

		DataSerializer saver = new DataSerializer("01", galaxy);
		stage.setScene(scene);
		stage.show();

		/** Mouse interaction **/
		//Obsolete
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
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
					
				
				}
		};

		
		/** Drag & Drop **/
	    EventHandler<MouseEvent> canvasOnMousePressedEventHandler = new EventHandler<MouseEvent>()
	    {
	        @Override
	        public void handle(MouseEvent mouseEvent) {
		        //System.out.println("Event on Source: mouse pressed");
		        mouseEvent.setDragDetect(true);
		        
	            orgSceneX = mouseEvent.getSceneX();
	            orgSceneY = mouseEvent.getSceneY();

				for(Planet p : galaxy.getPlanets()) {
					
		            if(p.clickedOnPlanet(orgSceneX, orgSceneY)) {
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
	            
	        }
	    };

	    EventHandler<MouseEvent> canvasOnMouseDraggedEventHandler = new EventHandler<MouseEvent>()
	    {
	        @Override
	        public void handle(MouseEvent mouseEvent){
	        	mouseEvent.setDragDetect(false);
	        	//System.out.println("Drag detected - Source: " + source.toString());
	        	if(source == null) {	return;	}
	        	
	            double offsetX = mouseEvent.getSceneX();
	            double offsetY = mouseEvent.getSceneY();


				for(Planet p : galaxy.getPlanets()) {
					try {						
							if(p.clickedOnPlanet(offsetX, offsetY)) {
								if(!source.intersects(p)) {
									destination = p;
									source.getRuler().setDestination(p);
								}
								break;
							
						}

					} catch(NullPointerException e) {
					//Nothing
						return;
					}
				}
				if(source.getRuler() != Constantes.human_user) {
					return;
				}

	        	if(source == null || destination == null) {	
	        		return;
	        	}else {
					Squad s = source.sendFleet(destination);
					galaxy.getSquads().add(s);	
					source = null;
					destination = null;
	        	}
				//System.out.println(source.toString() + " -> " + destination.toString());
				//((Canvas) (mouseEvent.getSource())).setTranslateX(newTranslateX);  //transform the object
	            //((Canvas) (mouseEvent.getSource())).setTranslateY(newTranslateY);
	        }
	    };

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

		//scene.setOnMouseDragged(mouseHandler);
		//scene.setOnMousePressed(mouseHandler);
		//scene.setOnMouseReleased(mouseHandler);
		scene.setOnScroll(scrollEventHandler);
        canvas.setOnMousePressed(canvasOnMousePressedEventHandler);
        canvas.setOnMouseDragged(canvasOnMouseDraggedEventHandler);
		
		/**	Rendering **/
		new AnimationTimer() {
			public void handle(long arg0) {
				galaxy.update();
				galaxy.render(gc);				
			}
		}.start();
	}
	
	
}

package fr.groupe40.projet.window;

import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Constants;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class GameScreen {
	private Galaxy galaxy;
	//private InteractionHandler interactionHandler;
	private double orgSceneX, orgSceneY;
	private Planet source, destination;
	private Squad selected;

	private Scene scene;
	private Canvas canvas;
	private GraphicsContext gc;
	private DataSerializer saver;
	
	public GameScreen(Scene scene) {
		this.scene = scene;
		this.canvas = new Canvas(Constants.width, Constants.height);
		this.setGc(canvas.getGraphicsContext2D());
		this.setGalaxy(new Galaxy());
		this.getGalaxy().initFont(getGc());
		this.saver = new DataSerializer("01", getGalaxy());
	}

	

	
	

	//interactionHandler = new InteractionHandler(galaxy);

	public void main() {
		
	    
	    /**	Saver	**/
		EventHandler<KeyEvent> keyboardHandler = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
					
				if(e.getCode() == KeyCode.SPACE) {
					System.out.println("* button space pressed");
						
					for(Planet p : getGalaxy().getPlanets()) {
						System.out.println("*planet boucle:" +p.toString());
						System.out.println("** ruler: " + p.getRuler().toString());

						if(p.getRuler().equals(Constants.human_user)){
							System.out.println("*** ruler well selected");
							Planet source = p.getRuler().getSource();
							Planet destination = p.getRuler().getDestination();
								
							if(destination != null && source != null) {
								Squad s = new Squad(Constants.human_user.getPercent_of_troups_to_send(), source, destination);
								//s.sendFleet(source, destination, Constants.human_user.getPercent_of_troups_to_send());
								
								getGalaxy().getSquads().add(s);
									
									
							}
						} else {
							p.getRuler().setDestination(null);
						}
					}

				}
					
				if (e.getCode() == KeyCode.F5) {
					System.out.println("Saving game ...");
					//OPEN POPUP ?
					saver.save_game();
					
								
				}
					
				if (e.getCode() == KeyCode.F6) {
					System.out.println("Loading game ...");
					setGalaxy(saver.load_game());
					saver.reload_image_and_data(getGalaxy());
					
					//interactionHandler = new InteractionHandler(galaxy);
				}
				
			}
		};
		
		EventHandler<MouseEvent> mousePressedEvent = new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent mouseEvent) {
		        mouseEvent.setDragDetect(true);
		        
	            orgSceneX = mouseEvent.getSceneX();
	            orgSceneY = mouseEvent.getSceneY();
	            
	            
				for(Squad s : getGalaxy().getSquads()) {
					if(s.squad_selected(orgSceneX, orgSceneY)) {
						if(s.getRuler().getFaction() == Constants.player) {
							selected = s;
						}
					}

				}
				
	            for(Planet p : getGalaxy().getPlanets()) {
		            if(p.clickedOnPlanet(orgSceneX, orgSceneY)) {
						source = p;
						if(source == null) {	break;	}
						if(p.getRuler().equals(Constants.human_user)){
							source.getRuler().setSource(p);
							break;
						}else {
							if(Constants.DEBUG) {
								System.out.println("Vous n'etes pas le dirigeant de cette colonie");
							}
						}
					}
				}
	            
	        }
	    };
		
		EventHandler<MouseEvent> mouseDraggedEvent = new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent mouseEvent){
	        	mouseEvent.setDragDetect(false);
	        	//System.out.println("Drag detected - Source: " + source.toString());
	        	
	            double offsetX = mouseEvent.getSceneX();
	            double offsetY = mouseEvent.getSceneY();


	        	if(selected != null) {	
	    			for(Planet p : getGalaxy().getPlanets()) {
	    				try {						
	    					if(p.clickedOnPlanet(offsetX, offsetY)) {
	    						selected.update_destination(p);
	    		    			selected = null;
	    						return;
	    					}

	    				} catch(NullPointerException e) {
	    				//Nothing
	    	    			selected = null;
	    					return;
	    				}
	    			}
	    			return;
	        	}
	        	
	        	if(source == null) { return; }
				for(Planet p : getGalaxy().getPlanets()) {
					try {						
						if(p.clickedOnPlanet(offsetX, offsetY)) {
							if(!source.isInside(p)) {
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
	        	if(source == null || destination == null || source.getRuler() != Constants.human_user) {	
	        		return;
	        	}else {
					Squad s = new Squad(Constants.human_user.getPercent_of_troups_to_send(), source, destination);
					//s.sendFleet(source, destination, Constants.human_user.getPercent_of_troups_to_send());
					getGalaxy().getSquads().add(s);

					source = null;
					destination = null;
	        	}
	        	
	        }
		};
		
		
		EventHandler<ScrollEvent> scrollEvent = new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
			    if (event.getDeltaY() < 0) {	//diminution
			        getGalaxy().clientScrollHandler(0);
			    } else if (event.getDeltaY() > 0){//augmentation
			        getGalaxy().clientScrollHandler(-1);
			    } else {
			    	//Do nothing
			    }
				
			}
			
		};
		
	    getScene().setOnKeyPressed(keyboardHandler);
		getScene().setOnScroll(scrollEvent);
	    getScene().setOnMousePressed(mousePressedEvent);
	    getScene().setOnMouseDragged(mouseDraggedEvent);	
	    
		/**	Rendering **/
		new AnimationTimer() {
			public void handle(long arg0) {
				getGalaxy().update();
				getGalaxy().render(getGc());			
			}
		}.start();
		
	}






	/**
	 * @return the scene
	 */
	public Scene getScene() {
		return scene;
	}






	/**
	 * @param scene the scene to set
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}






	/**
	 * @return the canvas
	 */
	public Canvas getCanvas() {
		return canvas;
	}






	/**
	 * @param canvas the canvas to set
	 */
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}






	/**
	 * @return the galaxy
	 */
	public Galaxy getGalaxy() {
		return galaxy;
	}






	/**
	 * @param galaxy the galaxy to set
	 */
	public void setGalaxy(Galaxy galaxy) {
		this.galaxy = galaxy;
	}






	/**
	 * @return the gc
	 */
	public GraphicsContext getGc() {
		return gc;
	}






	/**
	 * @param gc the gc to set
	 */
	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}
}
	
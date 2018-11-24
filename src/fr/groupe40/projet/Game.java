package fr.groupe40.projet;

import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.Constantes;
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
	private Galaxy galaxy;
	//private InteractionHandler interactionHandler;
	private double orgSceneX, orgSceneY;
	private Planet source, destination;
	private Squad selected;
	
	public void start(Stage stage) {
		
		/** Window and game kernel creation **/
		stage.setTitle("Surgeon Simulator 2");
		stage.setResizable(false);

		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(Constantes.width, Constantes.height);
		root.getChildren().add(canvas);

		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		galaxy = new Galaxy();
		galaxy.initFont(gc);

		DataSerializer saver = new DataSerializer("01", galaxy);
		//interactionHandler = new InteractionHandler(galaxy);

		
		stage.setScene(scene);
		stage.show();

		
        
        /**	Saver	**/
    	EventHandler<KeyEvent> keyboardHandler = new EventHandler<KeyEvent>() {

    		@Override
    		public void handle(KeyEvent e) {
    				
    			if(e.getCode() == KeyCode.SPACE) {
    				System.out.println("* button space pressed");
    					
    				for(Planet p : galaxy.getPlanets()) {
    					System.out.println("*planet boucle:" +p.toString());
    					System.out.println("** ruler: " + p.getRuler().toString());

    					if(p.getRuler().equals(Constantes.human_user)){
    						System.out.println("*** ruler well selected");
    						Planet source = p.getRuler().getSource();
    						Planet destination = p.getRuler().getDestination();
    							
    						if(destination != null && source != null) {
    							Squad s = new Squad();
    							s.sendFleet(source, destination, Constantes.human_user.getPercent_of_troups_to_send());
    							
    							galaxy.getSquads().add(s);
    								
    								
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
    				galaxy = saver.load_game();
    				saver.reload_image_and_data(galaxy);
    				
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
                
                
    			for(Squad s : galaxy.getSquads()) {
    				if(s.squad_selected(orgSceneX, orgSceneY)) {
    					if(s.getRuler().getFaction() == Constantes.player) {
    						selected = s;
    					}
    				}

    			}
    			
                for(Planet p : galaxy.getPlanets()) {
    	            if(p.clickedOnPlanet(orgSceneX, orgSceneY)) {
    					source = p;
    					if(source == null) {	break;	}
    					if(p.getRuler().equals(Constantes.human_user)){
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
    	
    	EventHandler<MouseEvent> mouseDraggedEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent){
            	mouseEvent.setDragDetect(false);
            	//System.out.println("Drag detected - Source: " + source.toString());
            	
                double offsetX = mouseEvent.getSceneX();
                double offsetY = mouseEvent.getSceneY();

    
            	if(selected != null) {	
        			for(Planet p : galaxy.getPlanets()) {
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
    			for(Planet p : galaxy.getPlanets()) {
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
            	if(source == null || destination == null || source.getRuler() != Constantes.human_user) {	
            		return;
            	}else {
					Squad s = new Squad();
					s.sendFleet(source, destination, Constantes.human_user.getPercent_of_troups_to_send());
					galaxy.getSquads().add(s);

    				source = null;
    				destination = null;
            	}
            	
            }
    	};
    	
    	
    	EventHandler<ScrollEvent> scrollEvent = new EventHandler<ScrollEvent>() {
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
    	
        scene.setOnKeyPressed(keyboardHandler);
		scene.setOnScroll(scrollEvent);
        scene.setOnMousePressed(mousePressedEvent);
        scene.setOnMouseDragged(mouseDraggedEvent);	
        
		/**	Rendering **/
		new AnimationTimer() {
			public void handle(long arg0) {
				galaxy.update();
				galaxy.render(gc);			
			}
		}.start();
	}
	
	
}
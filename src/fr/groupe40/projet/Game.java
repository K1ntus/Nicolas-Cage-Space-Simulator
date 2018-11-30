package fr.groupe40.projet;

import com.sun.javafx.scene.traversal.Direction;

import fr.groupe40.projet.file.DataSerializer;
import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Squad;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	 * \brief for the drag&drop, the next x&y position
	 */
	private double orgSceneX, orgSceneY;
	
	/**
	 * \brief the source and destination planets selected
	 */
	private Planet source, destination;
	
	/**
	 * \brief squads selected by the user
	 */
	private Squad selected;
	
	/**
	 * \brief game_tick counter for events, etc
	 */
	private int game_tick = 0;
	
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

		DataSerializer saver = new DataSerializer("01", galaxy);
		//interactionHandler = new InteractionHandler(galaxy);

		
		stage.setScene(scene);
		stage.show();

		

    	/**
    	 * \brief Manage the saver/loader
    	 */
    	EventHandler<KeyEvent> keyboardHandler = new EventHandler<KeyEvent>() {

    		@Override
    		public void handle(KeyEvent e) {
    				
    			if(e.getCode() == KeyCode.SPACE) {
    				System.out.println("* button space pressed");
    					
    				for(Planet p : galaxy.getPlanets()) {
    					System.out.println("*planet boucle:" +p.toString());
    					System.out.println("** ruler: " + p.getRuler().toString());

    					if(p.getRuler().equals(Constants.human_user)){
    						System.out.println("*** ruler well selected");
    						Planet source = p.getRuler().getSource();
    						Planet destination = p.getRuler().getDestination();
    							
    						if(destination != null && source != null) {
    							Squad s = new Squad(Constants.human_user.getPercent_of_troups_to_send(), source, destination);
    							//s.sendFleet(source, destination, Constants.human_user.getPercent_of_troups_to_send());
    							
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

    	/**
    	 * \brief Manage the drag initial event
    	 */
    	EventHandler<MouseEvent> mousePressedEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
    	        mouseEvent.setDragDetect(true);
    	        
                orgSceneX = mouseEvent.getSceneX();
                orgSceneY = mouseEvent.getSceneY();
                
                
    			for(Squad s : galaxy.getSquads()) {
    				if(s.squad_selected(orgSceneX, orgSceneY)) {
    					if(s.getRuler().getFaction() == Constants.player) {
    						selected = s;
    					}
    				}

    			}
    			
                for(Planet p : galaxy.getPlanets()) {
    	            if(p.isInsidePlanet(orgSceneX, orgSceneY)) {
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

    	/**
    	 * \brief Manage the drop of the mouse
    	 */
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
        					if(p.isInsidePlanet(offsetX, offsetY)) {
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
    					if(p.isInsidePlanet(offsetX, offsetY)) {
    						if(!source.isInside(p)) {
    							destination = p;
    							source.getRuler().setDestination(p);
    						}
    						break;
    					}

    				} catch(NullPointerException e) {
    					return;
    				}
    			}
            	if(source == null || destination == null || source.getRuler() != Constants.human_user) {	
            		return;
            	}else {
					Squad s = new Squad(Constants.human_user.getPercent_of_troups_to_send(), source, destination);
					//s.sendFleet(source, destination, Constants.human_user.getPercent_of_troups_to_send());
					galaxy.getSquads().add(s);

    				source = null;
    				destination = null;
            	}
            	
            }
    	};
    	
    	/**
    	 * \brief Manage the scroll event
    	 */
    	EventHandler<ScrollEvent> scrollEvent = new EventHandler<ScrollEvent>() {
    		@Override
    		public void handle(ScrollEvent event) {
    		    if (event.getDeltaY() < 0) {	//diminution
    		        galaxy.clientScrollHandler(Direction.DOWN);
    		    } else if (event.getDeltaY() > 0){//augmentation
    		        galaxy.clientScrollHandler(Direction.UP);
    		    } else {
    		    	//Do nothing
    		    }
    			
    		}
    		
    	};
    	
        scene.setOnKeyPressed(keyboardHandler);
		scene.setOnScroll(scrollEvent);
        scene.setOnMousePressed(mousePressedEvent);
        scene.setOnMouseDragged(mouseDraggedEvent);	
        
		/*	Rendering */
		new AnimationTimer() {
			public void handle(long arg0) {
				
				game_tick += 1;
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

package fr.groupe40.projet.client.handler;

import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Direction;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class MouseListener {
	private Galaxy galaxy;
	private Scene scene;

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
	 * \brief create the object with the minimal requirement
	 * @param galaxy to check the elements in the board
	 * @param scene linking the handler to this scene
	 */
	public MouseListener(Galaxy galaxy, Scene scene) {
		this.galaxy = galaxy;
		this.scene = scene;
	}

	
	/**
	 * \brief launch the mouse handler
	 */
	public void start() {

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

		scene.setOnScroll(scrollEvent);
	    scene.setOnMousePressed(mousePressedEvent);
	    scene.setOnMouseDragged(mouseDraggedEvent);	
	}
}

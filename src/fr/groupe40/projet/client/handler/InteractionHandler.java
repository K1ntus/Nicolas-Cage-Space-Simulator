package fr.groupe40.projet.client.handler;


import fr.groupe40.projet.model.board.Galaxy;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Direction;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

@Deprecated
public abstract class  InteractionHandler {

	protected Galaxy galaxy;	//To be accessed by handler
    private double orgSceneX, orgSceneY;
	private Planet source = null; private Planet destination = null;
	//private Squad selected = null;
	
	/**
	 * \brief constructor for the user input
	 * @param galaxy
	 */
	public InteractionHandler(Galaxy galaxy) {
		this.galaxy = galaxy;
	}
	
	private EventHandler<MouseEvent> mousePressedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
	        //System.out.println("Event on Source: mouse pressed");
	        mouseEvent.setDragDetect(true);
	        
            orgSceneX = mouseEvent.getSceneX();
            orgSceneY = mouseEvent.getSceneY();
            
            /*
			for(Squad s : galaxy.getSquads()) {
	            if(s.isInside(orgSceneX, orgSceneY, Constants.size_squads, Constants.size_squads)) {
					if(s.getRuler().getFaction() == Constants.player) {
						selected = s;
						System.out.println("selected: "+s.toString());
						return;
					}else {
						if(Constants.DEBUG) {
							System.out.println("Vous n'etes pas le dirigeant de cette colonie");
						}
					}
				}
			}
			*/
			
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
	
	private EventHandler<MouseEvent> mouseDraggedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent){
        	mouseEvent.setDragDetect(false);
        	//System.out.println("Drag detected - Source: " + source.toString());
        	
            double offsetX = mouseEvent.getSceneX();
            double offsetY = mouseEvent.getSceneY();

            /*
        	if(selected != null) {	
    			for(Planet p : galaxy.getPlanets()) {
    				try {						
    					if(p.clickedOnPlanet(offsetX, offsetY)) {
    						if(!selected.intersects(p)) {
    							destination = p;
    							selected.getRuler().setDestination(p);
    						}
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
        	*/
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
				//Nothing
					return;
				}
			}
        	if(source == null || destination == null || source.getRuler() != Constants.human_user) {	
        		return;
        	}else {
				source = null;
				destination = null;
        	}
        }
	};
	
	
	private EventHandler<ScrollEvent> scrollEvent = new EventHandler<ScrollEvent>() {
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

	
	
	
	
	public EventHandler<MouseEvent> getMousePressedEvent() {
		return mousePressedEvent;
	}

	public void setMousePressedEvent(EventHandler<MouseEvent> mousePressedEvent) {
		this.mousePressedEvent = mousePressedEvent;
	}

	public EventHandler<MouseEvent> getMouseDraggedEvent() {
		return mouseDraggedEvent;
	}

	public void setMouseDraggedEvent(EventHandler<MouseEvent> mouseDraggedEvent) {
		this.mouseDraggedEvent = mouseDraggedEvent;
	}

	public EventHandler<ScrollEvent> getScrollEvent() {
		return scrollEvent;
	}

	public void setScrollEvent(EventHandler<ScrollEvent> scrollEvent) {
		this.scrollEvent = scrollEvent;
	}
	
	public Galaxy getGalaxy() {
		return galaxy;
	}

	public void setGalaxy(Galaxy galaxy) {
		this.galaxy = galaxy;
	}
}

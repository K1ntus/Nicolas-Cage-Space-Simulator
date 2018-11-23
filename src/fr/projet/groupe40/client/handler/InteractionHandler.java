package fr.projet.groupe40.client.handler;

import fr.projet.groupe40.model.board.Galaxy;
import fr.projet.groupe40.model.planets.Planet;
import fr.projet.groupe40.util.Constantes;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

@Deprecated
public class InteractionHandler {

	protected Galaxy galaxy;	//To be accessed by handler
    private double orgSceneX, orgSceneY;
	private Planet source = null; private Planet destination = null;
	//private Squad selected = null;
	
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
	            if(s.isInside(orgSceneX, orgSceneY, Constantes.size_squads, Constantes.size_squads)) {
					if(s.getRuler().getFaction() == Constantes.player) {
						selected = s;
						System.out.println("selected: "+s.toString());
						return;
					}else {
						if(Constantes.DEBUG) {
							System.out.println("Vous n'etes pas le dirigeant de cette colonie");
						}
					}
				}
			}*/
			
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
				//Squad s = source.sendFleet(destination);
				//galaxy.getSquads().add(s);	
				source = null;
				destination = null;
        	}
        	
			//System.out.println(source.toString() + " -> " + destination.toString());
			//((Canvas) (mouseEvent.getSource())).setTranslateX(newTranslateX);  //transform the object
            //((Canvas) (mouseEvent.getSource())).setTranslateY(newTranslateY);
        }
	};
	
	
	private EventHandler<ScrollEvent> scrollEvent = new EventHandler<ScrollEvent>() {
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

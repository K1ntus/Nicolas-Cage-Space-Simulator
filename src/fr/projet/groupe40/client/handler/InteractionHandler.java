package fr.projet.groupe40.client.handler;

import fr.projet.groupe40.file.DataSerializer;
import fr.projet.groupe40.model.board.Galaxy;
import fr.projet.groupe40.model.board.Planet;
import fr.projet.groupe40.model.ships.Squad;
import fr.projet.groupe40.util.Constantes;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class InteractionHandler {
	public InteractionHandler(Galaxy galaxy, DataSerializer saver) {
		this.galaxy = galaxy;
		this.saver = saver;
	}

	private Galaxy galaxy;
	private DataSerializer saver;

    private double orgSceneX, orgSceneY;
	private Planet source = null; private Planet destination = null;
	
	private EventHandler<MouseEvent> mousePressedEvent = new EventHandler<MouseEvent>() {
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
	
	private EventHandler<MouseEvent> mouseDraggedEvent = new EventHandler<MouseEvent>() {
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

	private EventHandler<KeyEvent> keyboardEvent = new EventHandler<KeyEvent>() {

		@Override
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
					//Open popup window
					/*
						Stage newStage = new Stage();
						VBox comp = new VBox();
						TextField nameField = new TextField("Name");
						TextField phoneNumber = new TextField("Phone Number");
						comp.getChildren().add(nameField);
						comp.getChildren().add(phoneNumber);

						Scene stageScene = new Scene(comp, 300, 300);
						newStage.setScene(stageScene);
						newStage.show();
						*/
				saver.save_game();
							
			}
				
			if (e.getCode() == KeyCode.F6) {
				System.out.println("Loading game ...");
				galaxy = saver.load_game(galaxy);
				saver.reload_image(galaxy);
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

	public EventHandler<KeyEvent> getKeyboardEvent() {
		return keyboardEvent;
	}

	public void setKeyboardEvent(EventHandler<KeyEvent> keyboardEvent) {
		this.keyboardEvent = keyboardEvent;
	}

	
}

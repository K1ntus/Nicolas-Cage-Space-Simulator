package fr.groupe40.projet.model.board;

import java.util.ArrayList;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.resources.ResourcesContainer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

//TODO COMMENTS
public final class GalaxyRenderer {

	/**
	 * Contains few image data for better memory (but cost more memory)
	 */
	private static ResourcesContainer RESOURCES_CONTAINER = new ResourcesContainer();
	
	protected static void run(Galaxy galaxy, GraphicsContext gc) {
		GalaxyRenderer.renderBackground(gc);
		GalaxyRenderer.renderPlanets(galaxy, gc);
		for(Squad s : galaxy.getSquads())
			s.render_ships(gc);	
		GalaxyRenderer.renderGarrison(galaxy, gc);
		GalaxyRenderer.renderPercentageSelected(galaxy, gc);
	}

	/**
	 *  Render the background image
	 * @param gc
	 */
	private static void renderBackground(GraphicsContext gc) {
		gc.drawImage(
				RESOURCES_CONTAINER.getGame_background(), 
				0, 
				0
			);
	}
	
	/**
	 *  Render each planets image
	 * @param gc
	 */
	private static void renderPlanets(Galaxy galaxy, GraphicsContext gc) {
		ArrayList<Planet> planets = galaxy.getPlanets();
		for(Planet p : planets) {
			if(p != null)
				p.render(gc);					
		}
	}

	
	/**
	 *  Render the garrison amount of each planets on board
	 * @param gc
	 */
	private static void renderGarrison(Galaxy galaxy, GraphicsContext gc) {
		ArrayList<Planet> planets = galaxy.getPlanets();
		for (Planet p : planets) {
			String txt = Integer.toString(p.getTroups());
			gc.setTextAlign(TextAlignment.CENTER);	
			gc.setStroke(Color.BLACK);
			
			switch(p.getRuler().getFaction()) {
				case Players.human_faction:
					gc.setFill(Constants.color_player); break;
				case Players.ai_faction:
					gc.setFill(Constants.color_ai); break;
				case Players.neutral_faction:
					gc.setFill(Constants.color_neutral); break;
				default:
					gc.setFill(Constants.color_default); break;
			}
			gc.fillText(txt, p.getX() + (p.width()/2), p.getY() + (p.height()/2));
			gc.strokeText(txt, p.getX() + (p.width()/2), p.getY() + (p.height()/2));
		}
	}

	/**
	 *  Render the percentage of troups to send selected by the player
	 * @param gc
	 */
	private static void renderPercentageSelected(Galaxy galaxy, GraphicsContext gc) {
		ArrayList<Planet> planets = galaxy.getPlanets();
		gc.setFill(Constants.color_default);
		gc.setStroke(Color.RED);
		gc.setTextAlign(TextAlignment.CENTER);	
		
		if(!galaxy.isGame_is_over())
			for(Planet p :planets) {
				User u = p.getRuler();
				if (u.getFaction() == Players.human_faction) {
					String txt = "Troupes: "+u.getPercent_of_troups_to_send()+"%";
					
					gc.fillText(txt, Generation.width/7, 25);
					gc.strokeText(txt, Generation.width/7, 25);
					
					return;				
				}
			}
	}
	
	/**
	 *  render in case of defeat 
	 * @param gc the Graphics Context
	 */
	public static void renderDefeat(GraphicsContext gc) {
		gc.setFill(Constants.color_default);
		gc.setStroke(Color.RED);
		gc.setTextAlign(TextAlignment.CENTER);	

		String txt = Constants.message_game_over;
		gc.fillText(txt, Generation.width/2, 25);
		gc.strokeText(txt, Generation.width/2, 25);
		
	}

	
	/**
	 *  render in case of victory 
	 * @param gc the Graphics Context
	 */
	public static void renderWinner(GraphicsContext gc) {
		gc.setFill(Constants.color_default);
		gc.setStroke(Color.RED);
		gc.setTextAlign(TextAlignment.CENTER);	

		String txt = Constants.message_winner;
		gc.fillText(txt, Generation.width/2, 25);
		gc.strokeText(txt, Generation.width/2, 25);
		
	}

	/**
	 * @return the rESOURCES_CONTAINER
	 */
	public static ResourcesContainer getRESOURCES_CONTAINER() {
		return RESOURCES_CONTAINER;
	}

	/**
	 * @param rESOURCES_CONTAINER the rESOURCES_CONTAINER to set
	 */
	public static void setRESOURCES_CONTAINER(ResourcesContainer rESOURCES_CONTAINER) {
		RESOURCES_CONTAINER = rESOURCES_CONTAINER;
	}
	
	

}

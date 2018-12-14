package fr.groupe40.projet.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.model.planets.Sun;
import fr.groupe40.projet.model.ships.Squad;
import fr.groupe40.projet.util.SoundManager;
import fr.groupe40.projet.util.constants.Constants;
import fr.groupe40.projet.util.constants.Direction;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.PlanetsGarrison;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *  'Board' class. It contains the data of the game as ships, planets, ...
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class Galaxy implements Serializable{
	private static final long serialVersionUID = 3668540725184418675L;

	/**
	 *  Array with every planet of the board
	 */
	private ArrayList <Planet> planets;

	/**
	 *  Array with every squads of the board
	 */
	private ArrayList <Squad> squads;

	/**
	 *  board generator
	 */
	private GalaxyGenerator generator = new GalaxyGenerator();

	/**
	 *  background image
	 */
	private transient Image background;
	
	/**
	 *  graphical environnement
	 */
	private transient GraphicsContext gc;
	
	/**
	 *  state of the game
	 */
	private boolean game_is_over = false;

	/**
	 *  Generate a game board with every parameters randomized
	 */
	public Galaxy(GraphicsContext gc) {
		this.squads = new ArrayList<Squad>();
		this.planets = generator.getPlanets();
		this.generator = null;
		this.gc = gc;
		
		this.background = new Image(Resources.path_img_game_background, Generation.width, Generation.height, false, false, true);
		
	}
	/**
	 *  Generate a game board with every parameters randomized
	 */
	public Galaxy(GraphicsContext gc, GalaxyGenerator gg) {
		this.squads = new ArrayList<Squad>();
		this.planets = gg.getPlanets();
		this.generator = null;
		this.gc = gc;
		
		this.background = new Image(Resources.path_img_game_background, Generation.width, Generation.height, false, false, true);
		
	}

	/**
	 *  Generate a game board from another
	 * @param g The game board to copy from
	 */
	public Galaxy(Galaxy g, GraphicsContext gc) {
		this.planets = g.planets;
		this.squads = g.squads;
		this.generator = null;
		this.gc = gc;

		this.background = new Image(Resources.path_img_game_background, Generation.width, Generation.height, false, false, true);
	}

	/**
	 *  Generate a game board from an array of squads and planets
	 * @param planets Planets that we want to have
	 * @param squads Squads already present
	 */
	public Galaxy(List<Planet> planets, List<Squad> squads, GraphicsContext gc) {
		this.squads = (ArrayList<Squad>) squads;
		this.planets = generator.getPlanets();
		this.generator = null;
		this.gc = gc;
		
		this.background = new Image(Resources.path_img_game_background, Generation.width, Generation.height, false, false, true);
		
	}
	
	/* Render & Game Update */
	/**
	 * Main rendering function
	 * @param gc
	 */
	public void render(GraphicsContext gc) {
		GalaxyRenderer.run(this, gc, background);
	}
	
	/**
	 *  Main update function, manage AI, squads and hasLost
	 */
	public void updateSquadPosition() {
		for(Squad s : squads) {
			if(s==null) {	squads.remove(s);	continue;}
			s.update_all_positions(planets);			
		}

		if(userHasLost(Players.ai_user)) {
			game_is_over = true;
		}
	}
	
	/*	AI	*/
	/**
	 *  Manage the AI to send fleets
	 */
	public void updateAI() {
		Planet source, destination;
		
		for(Planet p : planets) {
			User ruler = p.getRuler();
			
			if(ruler.getId() < 0 && ruler.getFaction() == Players.ai_faction) {	//0 = neutral, >0 human, <0 bot
				source = p;
				
				for(Planet p2 : planets) {	//Check again the planets list
					destination = p2;
					if(p2.getTroups() < source.getTroups() || p2.getTroups() >= PlanetsGarrison.max_troups) {
						Squad s = ruler.sendFleetAI(source, destination);
						if(s != null)
							squads.add(s);
						break;
					}
				}
			}
		}

	}

	/**
	 *  update the garrison value of each planets
	 */
	public void updateGarrison() {
		if(planets.get(0).getTroups() <= PlanetsGarrison.min_troups+1 && planets.get(0).getRuler().getId() == Players.sun_id) {

	    	AudioClip sun_explosion_sound = SoundManager.generateAudioClip(Resources.path_sound_sun_explosion, Resources.sun_explosion_volume);

	    	sun_explosion_sound.play(); 
			Sun.sun_destroyed(planets, squads, gc);
			planets.remove(0);
		}
		
		for(Planet p : planets)
			p.updateGarrison();	
	}

	/**
	 *  send all a new wave if need for each squads
	 */
	public void updateWavesSending() {
		for(Squad s : squads) {
			s.sendFleet();
		}
		
	}
	
	/* Defeat handler */
	/**
	 *  Check if an user has lost
	 * @param u the user to check
	 * @return true if he has last, else false
	 */
	public boolean userHasLost(User u) {	
		if(u.isLost()) {	//if user already registered has loser
			System.out.println("ai: "+u.toString()+" has lost");
			return true;
		}
		
		int id = u.getId();
		
		for(Planet p : planets) { //if this user still have at least ONE planet, then doesnt has lost
			int ruler_id = p.getRuler().getId();
			if(ruler_id == id) {
				return false;
			}
		}
		
		for(Squad s : squads) {		//if this user still have at least ONE squad, then doesnt has lost	
			try {
				User ruler = s.getRuler();
				int ruler_id = ruler.getId();
				if(ruler_id == id) 
					return false;
				continue;
			}catch(NullPointerException e) {
				continue;
			}
		}
		
		u.setLost(true);
		return true;
	}
	
	public void resetEveryUsersLostState() {
		Players.ai_user.setLost(false);
		Players.human_user.setLost(false);
	}
	
	
	/* Client handler */
	/**
	 *  handle the scrolls event to change the percent of a fleet to send
	 * @param action The scroll action (up or down case)
	 */
	public void clientScrollHandler(Direction direction) {
		User u = Players.human_user;
		int percent = u.getPercent_of_troups_to_send();
		
		switch(direction) {
			case DOWN://lower
				u.setPercent_of_troups_to_send(percent - 5); break;
			case UP://greater
				u.setPercent_of_troups_to_send(percent + 5); break;
			default:
				break;
		}
	}
	

	
	/* init the font type */
	/**
	 *  Init the default font style of the graphics context
	 * @param gc
	 */
	public void initFont(GraphicsContext gc) {
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Constants.color_default);
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);		
	}
	
	/* Getter & Setter */

	/**
	 *  Set the list containing every squads by another one
	 * @param planets
	 */
	public ArrayList<Squad> getSquads() {
		return squads;
	}

	/**
	 *  Return the list containing every squads on board
	 * @return
	 */
	public void setSquads(ArrayList<Squad> squads) {
		this.squads = squads;
	}

	/**
	 *  Set the list containing every planet by another one
	 * @param planets
	 */
	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
	
	/**
	 *  Return the list containing every planets on board
	 * @return
	 */
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	
	/**
	 *  Return the background image
	 * @return Image 
	 */
	public Image getBackground() {
		return background;
	}

	/**
	 *  Set the background image
	 * @param background
	 */
	public void setBackground(Image background) {
		this.background = background;
	}

	/**
	 * @return the game_is_over
	 */
	public boolean isGame_is_over() {
		return game_is_over;
	}

	/**
	 * @param game_is_over the game_is_over to set
	 */
	public void setGame_is_over(boolean game_is_over) {
		this.game_is_over = game_is_over;
	}

	/**
	 * @return the gc
	 */
	public GraphicsContext getGraphicsContext() {
		return gc;
	}

	/**
	 * @param gc the gc to set
	 */
	public void setGraphicsContext(GraphicsContext gc) {
		this.gc = gc;
	}

	
}

package fr.projet.groupe40.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.planets.Planet;
import fr.projet.groupe40.model.ships.Ship;
import fr.projet.groupe40.model.ships.Squad;
import fr.projet.groupe40.util.Constantes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


public class Galaxy extends Thread implements Serializable{
	private static final long serialVersionUID = 3668540725184418675L;

	@SuppressWarnings("unused")
	private volatile transient Thread blinker;
	
	private ArrayList <Planet> planets;
	private ArrayList <Squad> squads;
	
	private GalaxyGenerator generator = new GalaxyGenerator();
	
	private transient Image background;
	
	/**
	 * \brief Generate a game board with every parameters randomized
	 */
	public Galaxy() {
		this.squads = new ArrayList<Squad>();
		this.planets = generator.getPlanets();
		this.generator = null;
		
		setBackground(new Image(Constantes.path_img_background, Constantes.width, Constantes.height, false, false, true));
		
		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
		
	}

	/**
	 * \brief Generate a game board from another
	 * @param g The game board to copy from
	 */
	public Galaxy(Galaxy g) {
		this.planets = g.planets;
		this.squads = g.squads;
		this.generator = null;
		
		setBackground(new Image(Constantes.path_img_background, Constantes.width, Constantes.height, false, false, true));
		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
		
	}

	/**
	 * \brief Generate a game board from an array of squads and planets
	 * @param planets Planets that we want to have
	 * @param squads Squads already present
	 */
	public Galaxy(List<Planet> planets, List<Squad> squads) {
		this.squads = (ArrayList<Squad>) squads;
		this.planets = generator.getPlanets();
		this.generator = null;
		
		setBackground(new Image(Constantes.path_img_background, Constantes.width, Constantes.height, false, false, true));
		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
		
	}

	/*	Thread	*/

	/**
	 * \brief Thread updating the garrison value for each planets / 1 second
	 */
	@Override
	public void run() {
		@SuppressWarnings("unused")
		Thread thisThread = Thread.currentThread();
		while(true) {
			for(Planet p : planets)
				p.updateGarrison();	
				//generateRandomSquads();		
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * \brief Stop the thread updating the garrison when called
	 */
	public void stopThread() {
        blinker = null;
    }
	
	
	/* Render & Game Update */
	/**
	 * Main rendering function
	 * @param gc
	 */
	public void render(GraphicsContext gc) {
		renderBackground(gc);
		renderPlanets(gc);
		for(Squad s : squads)
			s.render_ships(gc);	
		renderGarrison(gc);
		renderPercentageSelected(gc);
	}
	
	/**
	 * \brief Main update function, manage AI, squads and hasLost
	 */
	public void update() {
		updateAI();

		for(Squad s : squads) {
			if(s==null) {	squads.remove(s);	continue;}
			s.update_all_positions(planets);			
		}

		userHasLost(Constantes.ai_user);
	}
	
	/*	AI	*/
	/**
	 * \brief Manage the AI to send fleets
	 */
	public void updateAI() {
		Planet source, destination;
		
		for(Planet p : planets) {
			User ruler = p.getRuler();
			
			if(ruler.getId() < 0) {	//0 = neutral, >0 human, <0 bot
				source = p;
				
				for(Planet p2 : planets) {	//Check again the planets list
					destination = p2;
					if(p2.getTroups() < source.getTroups()) {
						Squad s = ruler.sendFleetAI(source, destination);
						if(s != null)
							squads.add(s);
						break;
					}
				}
			}
		}

	}
	
	
	/* Defeat handler */
	/**
	 * \brief Check if an user has lost
	 * @param u the user to check
	 * @return true if he has last, else false
	 */
	public boolean userHasLost(User u) {	//S il existe au moins une planete lui appartenant -> pas perdu
		if(u.hasLost()) {	//if user already registered has loser
			return true;
		}
		
		int id = u.getId();
		
		for(Planet p : planets) {
			int ruler_id = p.getRuler().getId();
			if(ruler_id == id) {
				return false;
			}
		}
		
		for(Squad s : squads) {
			User ruler = s.getRuler();
			int ruler_id = 0;
			if(ruler != null)
				ruler_id = ruler.getId();
			else
				continue;
			
			if(ruler_id == id) {
				return false;
			}
		}
		
		u.setLost(false);
		return true;
	}
	
	
	/* Client handler */
	/**
	 * \brief handle the scrolls event to change the percent of a fleet to send
	 * @param action The scroll action (up or down case)
	 */
	public void clientScrollHandler(int action) {
		for(Planet p : planets) {
			User u = p.getRuler();
			if(u.getFaction() == Constantes.player) {
				switch(action) {
				case 0://lower
					u.setPercent_of_troups_to_send(u.getPercent_of_troups_to_send() - 5); break;
				case -1://greater
					u.setPercent_of_troups_to_send(u.getPercent_of_troups_to_send() + 5); break;
				}
			}
		}
	}
	
	/* Collisions Between Ships & Planets */
	/**
	 * \brief Manage the collisions between squads & intermediate planets
	 * @param s The squad to manage
	 * @param p The planet to test collision with
	 */
	public void collisionHandler(Ship s, Planet p) {
		double deltaY = 0, deltaX = 0;
		double x = s.getX(), y = s.getY();
		double width = s.width(), height = s.height();
		double speed = s.getSpeed();
		/*
		double distance = p.distance(x, y, p.getX(), p.getY());
		Planet source = s.getSource();
		Planet destination = s.getDestination();
		*/
		
		double xCenter = p.getX() +p.width()/2;
		double yCenter = p.getY()+p.height()/2;
		//double distanceBetween2Planets = source.distance(destination);
		
		if(p.isInside(x-2*speed, y, width, height)) {	
			if(yCenter > y) {
				deltaY -= speed;		
			} else {
				deltaY += speed;
			}
			
			//System.out.println("squad inside a planet on his way1");
		} else if(p.isInside(x+2*speed, y, width, height)) {
			if(yCenter > y) {
				deltaY -= speed;		
			} else {
				deltaY += speed;
			}
			
			//System.out.println("squad inside a planet on his way2");
		}
		if(p.isInside(x, y-2*speed, width, height)) {
			if(xCenter > x) {
				deltaX += speed;
			} else {
				deltaX -= speed;	
			}
			
			//System.out.println("squad inside a planet on his way3");
		} else if(p.isInside(x, y+2*speed, width, height)) {
			if(xCenter > x) {	
				deltaX += speed;
			} else {
				deltaX -= speed;	
			}
			//System.out.println("squad inside a planet on his way4");
		}
		if(p.isInside(x+deltaX,y+deltaY,width,height))
			return;
		s.setPosition(x+deltaX, y+deltaY);
			
	}

	/**
	 * \brief check if there s a collision between a squad and every planet on board
	 * @param s the squad to check
	 * @return true if there s a collision, else false
	 */
	public boolean isCollision(Ship s) {
		for(Planet p : planets) {
			if(p != s.getDestination() && p != s.getSource()) {
				collisionHandler(s, p);
				return true;
			}
		}
		s.update_position(planets);
		//s.updateAllShipsPosition();
		return false;
	}

	

	/* Others Generations */
	//mainly for debugging for the moment, mb using it has pirate ?
	/**
	 * \brief Generate random squads on board that are aggressive to the player
	 */
	/*
	@Deprecated
	public void generateRandomSquads() {
		for(int i = 0, j=0; i < Constantes.nb_squads; i++) {
			for(Planet p : planets) {
				Squad s = new Squad(Constantes.path_img_ships, new User(Constantes.ai), false, Constantes.max_troups, planets.get(2));
				s.setPosition(Constantes.width * Math.random() - Constantes.size_squads, Constantes.height * Math.random() - Constantes.size_squads);
				j += 1;
				if(j == Constantes.nb_squads) {
					return;
				}
				if (p.isInside(s))
					continue;
				squads.add(s);

			}
		}
	}*/
	
	/* Sound Rendering Subfonction */
	
	/* Rendering subfunction */

	/**
	 * \brief Render the background image
	 * @param gc
	 */
	public void renderBackground(GraphicsContext gc) {
		gc.drawImage(getBackground(), 0, 0);
	}
	
	/**
	 * \brief Render each planets image
	 * @param gc
	 */
	public void renderPlanets(GraphicsContext gc) {
		for(Planet p : planets) {
			if(p != null)
				p.render(gc);					
		}
	}
	
	/**
	 * \brief Render every squads on board
	 * @param gc
	 */
	public void renderSquads(GraphicsContext gc) {
		Iterator<Squad> it = squads.iterator();
		while (it.hasNext()) {
			Squad ss = it.next();		
			if(ss == null) {	continue;	}
			
			for (Ship ship : ss.getShips()) {
				if(ship.isReached()) {
					System.out.println("des reached");
					ss.getShips().remove(ship);
					continue;					
				}else {
					//isCollision(ss);
					System.out.println("rendering ship");
					ss.render_ships(gc);	
					
				}
			}
		}
	}
	/**
	 * \brief Render the garrison amount of each planets on board
	 * @param gc
	 */
	public void renderGarrison(GraphicsContext gc) {
		for (Planet p : planets) {
			String txt = Integer.toString(p.getTroups());
			gc.setTextAlign(TextAlignment.CENTER);	
			gc.setStroke(Color.BLACK);
			
			switch(p.getRuler().getFaction()) {
				case Constantes.player:
					gc.setFill(Constantes.color_player); break;
				case Constantes.ai:
					gc.setFill(Constantes.color_ai); break;
				case Constantes.neutral:
					gc.setFill(Constantes.color_neutral); break;
				default:
					gc.setFill(Constantes.color_default); break;
			}
			gc.fillText(txt, p.getX() + (p.width()/2), p.getY() + (p.height()/2));
			gc.strokeText(txt, p.getX() + (p.width()/2), p.getY() + (p.height()/2));
		}
	}

	/**
	 * \brief Render the percentage of troups to send selected by the player
	 * @param gc
	 */
	public void renderPercentageSelected(GraphicsContext gc) {
		gc.setFill(Constantes.color_default);
		gc.setStroke(Color.RED);
		gc.setTextAlign(TextAlignment.CENTER);	
		
		for(Planet p :planets) {
			User u = p.getRuler();
			if (u.getFaction() == Constantes.player) {
				String txt = "Troupes: "+u.getPercent_of_troups_to_send()+"%";
				
				gc.fillText(txt, Constantes.width/7, 25);
				gc.strokeText(txt, Constantes.width/7, 25);
				
				return;				
			}
		}
		String txt = Constantes.message_game_over;
		gc.fillText(txt, Constantes.width/5, 25);
		gc.strokeText(txt, Constantes.width/5, 25);
		
	}
	
	/**
	 * \brief render a explosion when a ship reach his destination
	 * @param gc
	 */
	public void renderExplosion(GraphicsContext gc) {
		
	}
	
	/* init the font type */
	/**
	 * \brief Init the default font style of the graphics context
	 * @param gc
	 */
	public void initFont(GraphicsContext gc) {
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Constantes.color_default);
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);		
	}
	
	/* Getter & Setter */

	/**
	 * \brief Set the list containing every squads by another one
	 * @param planets
	 */
	public ArrayList<Squad> getSquads() {
		return squads;
	}

	/**
	 * \brief Return the list containing every squads on board
	 * @return
	 */
	public void setSquads(ArrayList<Squad> squads) {
		this.squads = squads;
	}

	/**
	 * \brief Set the list containing every planet by another one
	 * @param planets
	 */
	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
	
	/**
	 * \brief Return the list containing every planets on board
	 * @return
	 */
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	
	/**
	 * \brief Return the background image
	 * @return Image 
	 */
	public Image getBackground() {
		return background;
	}

	/**
	 * \brief Set the background image
	 * @param background
	 */
	public void setBackground(Image background) {
		this.background = background;
	}
	
}

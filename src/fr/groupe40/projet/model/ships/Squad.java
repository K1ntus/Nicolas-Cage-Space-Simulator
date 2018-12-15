package fr.groupe40.projet.model.ships;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.model.board.GalaxyRenderer;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.util.constants.Direction;
import fr.groupe40.projet.util.constants.Generation;
import fr.groupe40.projet.util.constants.PlanetsGarrison;
import fr.groupe40.projet.util.constants.Players;
import fr.groupe40.projet.util.constants.Resources;
import javafx.scene.canvas.GraphicsContext;


/**
 *  Squad object
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class Squad implements Serializable {
	private static final long serialVersionUID = -6736174123976474099L;
	
	/**
	 *  array with every ships of the squad
	 */
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	
	/**
	 *  buffer with the number of ships waiting to be sent
	 */
	private int nb_ship;
	
	/**
	 *  src and destination planets
	 */
	private Planet source, destination;
	
	/**
	 *  x and y summoning position 
	 */
	private int summonX = 0, summonY = 0;
	
	/**
	 *  value change if we re able to summon or not a wave
	 */
	private boolean summoning = true;
	
	/**
	 *  the player controlling this squad
	 */
	private User ruler;
	
	/**
	 *  the img path of all his ships
	 */
	private String img_path;
	
	/**
	 *  constructor of this squad
	 * @param percent the percent of troups from a planet to send
	 * @param src source planet
	 * @param dest destination planet
	 */
	public Squad(double percent, Planet src, Planet dest) {
		this.source = src;
		this.destination = dest;
		this.nb_ship = (int) (src.getTroups() * (percent / 100));
		this.ruler = src.getRuler();
		

		if (ruler.equals(Players.ai_user)) {
			img_path = Resources.path_img_AI_ships;
		} else if (ruler.equals(Players.event_user)) {
			img_path = Resources.path_img_event_ships;
		} else {
			img_path = Resources.path_img_human_ships;
		}

	}
	

	/**
	 *  constructor of this squad for pirate event
	 */
	public Squad() {

	}
	
	/**
	 *  constructor of this squad
	 * @param percent the percent of troups from a planet to send
	 * @param src source planet
	 * @param dest destination planet
	 */
	public Squad(String img_path, User ruler, double percent, Planet src, Planet dest) {
		this.source = src;
		this.destination = dest;
		this.nb_ship = (int) (src.getTroups() * (percent / 100));
		this.ruler = ruler;
		this.img_path = img_path;
	}

	/**
	 *  send a fleet, summoned when there s ships that had to be sent
	 */
	public void sendFleet() {
		if(nb_ship <= 0)
			return;

		
		while(summoning) {
			if(source.getTroups() -1 <= PlanetsGarrison.min_troups) {
				nb_ship = 0;
				return;
			}
			double x, y;
			//if(source instanceof SquarePlanet) {	//Planet is a square
				switch(summoningSide()) {
					case TOP:
						x = this.horizontalSummoning();
						y = source.getY() - Generation.size_squads*1.5;
						break;
					case BOTTOM:
						x = this.horizontalSummoning();
						y = source.getY() + source.height() + 0.5*Generation.size_squads;
						break;
					case LEFT:
						y = this.verticalSummoning();
						x = source.getX() - Generation.size_squads*1.5;
						break;
					case RIGHT:
						y = this.verticalSummoning();
						x = source.getX() + source.width() + 0.5*Generation.size_squads;
						break;
					default:
						x = this.decollageX(source);
						y = this.decollageY(source);	
						break;
				}
				/*
			} else {	//Planet is a circle
				x = this.decollageX(source);
				y = this.decollageY(source);							
			}
			*/
			
			ships.add(
				new Ship(
						img_path,
						ruler,
						destination,
						source,
						x,
						y,
						source.getShips_type()
					)
			);
			source.setTroups(source.getTroups()-1);
				
			nb_ship-=1;
		}
		summoning = true;
	}
	
	/**
	 * Calculate the summon y position
	 * @return the y position
	 */
	private double verticalSummoning() {
		//Y*taille +y(source) greater than y(source+height(source))
		if(summonY*Generation.size_squads + source.getY() < source.getY()+source.height()) {
			summonY += 1;	//Reset summon y
			
			
		} else if (summonY*Generation.size_squads + source.getY() >= source.getY()+source.height()) {
			summonY = 1;
			if(summonX != -1)
				summonX = -1;
			if(summonX == -1) {
				summoning = false;
				summonX = 1;				
			}
		}

		return (summonY*Generation.size_squads + (source.getY() - Generation.size_squads));
	}
	
	/**
	 * Calculate the summon x position
	 * @return the x position
	 */
	private double horizontalSummoning() {
		if(summonX*Generation.size_squads + source.getX() < source.getX()+source.width()) {
			summonX += 1;
			
			
		} else if (summonX*Generation.size_squads + source.getX() >= source.getX()+source.width()) {
			summonX = 1;
			if(summonY != -1)
				summonY = -1;
			if(summonY == -1) {
				summoning = false;
				summonY = 1;				
			}
		}

		return (summonX*Generation.size_squads + (source.getX() - Generation.size_squads));
	}
	
	/**
	 * Return the nearby side of his destination
	 * @return enum type destination
	 */
	private Direction summoningSide() {
		double min = Generation.width;
		Direction res = Direction.BOTTOM;
		
		if(Sprite.distance(source.getX()/2, source.getY(), destination.getX()/2, destination.getY()/2) < min) {
			min = Sprite.distance(source.getX()/2, source.getY(), destination.getX()/2, destination.getY()/2);
			res = Direction.LEFT;
		}

		if(Sprite.distance(source.getX()/2, source.getY()+source.height(), destination.getX()/2, destination.getY()/2) < min) {
			min = Sprite.distance(source.getX()/2, source.getY()+source.height(), destination.getX()/2, destination.getY()/2);
			res = Direction.BOTTOM;
		}

		if(Sprite.distance(source.getX(), source.getY()/2, destination.getX()/2, destination.getY()/2) < min) {
			min = Sprite.distance(source.getX(), source.getY()/2, destination.getX()/2, destination.getY()/2);
			res = Direction.TOP;
		}

		if(Sprite.distance(source.getX()+source.width(), source.getY()/2, destination.getX()/2, destination.getY()/2) < min) {
			min = Sprite.distance(source.getX()+source.width(), source.getY()/2, destination.getX()/2, destination.getY()/2);
			res = Direction.RIGHT;
		}
		
		return res;
	}
	
	/**
	 *  check if a squad has been selected or not
	 * @param x position to check
	 * @param y position to check
	 * @return true if a squad has been selected
	 */
	public boolean squad_selected(double x, double y) {
		for(Ship s : ships) {
			if(s.isInside(x, y, Generation.size_squads, Generation.size_squads)) {
				return true;
			}
		}
		return false;		
	}
	
	/**
	 *  Render every ships of this squad
	 * @param gc GraphicsContext
	 */
	public void render_ships(GraphicsContext gc) {
			for(Ship s : ships) {
				try {
					s.render(gc);
				} catch (ConcurrentModificationException e) {
					continue;
				}
			}
	}

	/**
	 *  update destination planet of every ships of this squads
	 * @param destination the new destination
	 */
	public void update_destination(Planet destination) {
		for(Ship ship : ships) {
			ship.setDestination(destination);
		}
	}
	
	/**
	 *  Update the position of every ships of this squad
	 */
	public void update_all_positions(List<Planet> planets) {
		Iterator<Ship> it = ships.iterator();
		
		while (it.hasNext()) {
			Ship ship = it.next();
			try {
				if(ship.getDestination().isInside(ship)) {	//Case when it reach his destination
					GalaxyRenderer.getRESOURCES_CONTAINER().renderCollisionSound();
					ships.remove(ship);
					it = ships.iterator();
				}

				ship.update_position(planets);	//else
				ship.validatePosition();
			} catch(NullPointerException e) {
				it.remove();
			} catch(ConcurrentModificationException e) {
				//List getting edited at the same time
				//Reset the boucle
				it = ships.iterator();
			}
		}
	}
	

	/**
	 *  updateImage of every ships of this squad
	 */
	public void updateImage() {
		for(Ship s : ships) {
			try {
			if(destination.getRuler() == Players.human_user)
				s.setImg_path(Resources.path_img_human_ships);
			else if(destination.getRuler() == Players.ai_user)
				s.setImg_path(Resources.path_img_AI_ships);
			
			}catch(NullPointerException e) {
				//Do nothing, ship has reached destination
			}
			
			s.updateImage();	
		}	
	}
	
	/**
	 *  update ruler of every ships of this squads
	 * @param ruler the new ruler
	 */
	public void update_ruler(User ruler) {
		for(Ship ship : ships) {
			ship.setRuler(ruler);
		}
	}

	
	/**
	 *  Calculate the x Position for liftoff
	 * @param source the source planet
	 * @return abscissa position
	 */
	private double decollageX(Planet source) {
		if(summonX*Generation.size_squads + source.getX() < source.getX()+source.width()) {
			summonX += 1;
			
			
		} else if (summonX*Generation.size_squads + source.getX() >= source.getX()+source.width()) {
			summonX = 1;
			if(summonY != -1)
				summonY = -1;
			if(summonY == -1) {
				summoning = false;
				summonY = 1;				
			}
		}

		return (summonX*Generation.size_squads + (source.getX() - Generation.size_squads));
	}
	
	/**
	 *  Calculate the y Position for liftoff
	 * @param source the source planet
	 * @return ordered position
	 */
	private double decollageY(Planet source) {
		if(source.getY() > destination.getY())
			return (source.getY() - Generation.size_squads-1);
		else
			return (source.width() + source.getY() + 1);		
	}

	/**
	 * @return the ships
	 */
	public ArrayList<Ship> getShips() {
		return ships;
	}

	/**
	 * @param ships the ships to set
	 */
	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	/**
	 * @return the nb_ship
	 */
	public int getNb_ship() {
		return nb_ship;
	}

	/**
	 * @param nb_ship the nb_ship to set
	 */
	public void setNb_ship(int nb_ship) {
		this.nb_ship = nb_ship;
	}

	/**
	 * @return the source
	 */
	public Planet getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Planet source) {
		this.source = source;
	}

	/**
	 * @return the destination
	 */
	public Planet getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Planet destination) {
		this.destination = destination;
	}

	/**
	 * @return the summonX
	 */
	public int getSummonX() {
		return summonX;
	}

	/**
	 * @param summonX the summonX to set
	 */
	public void setSummonX(int summonX) {
		this.summonX = summonX;
	}

	/**
	 * @return the summonY
	 */
	public int getSummonY() {
		return summonY;
	}

	/**
	 * @param summonY the summonY to set
	 */
	public void setSummonY(int summonY) {
		this.summonY = summonY;
	}

	/**
	 * @return the summoning
	 */
	public boolean isSummoning() {
		return summoning;
	}

	/**
	 * @param summoning the summoning to set
	 */
	public void setSummoning(boolean summoning) {
		this.summoning = summoning;
	}


	/**
	 * Get the ruler of the squad (by getting the ruler of the first element of the squad's ship array)
	 * @return the ruler
	 */
	public User getRuler() {
		try {
			return ships.get(0).getRuler();
		} catch (IndexOutOfBoundsException e) {
			//Every squads has reached destination
			return null;
		}
	}

}

package fr.groupe40.projet.model.ships;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.planets.Planet;
import fr.groupe40.projet.util.constantes.Constantes;
import javafx.scene.canvas.GraphicsContext;

public class Squad extends Thread implements Serializable {
	private static final long serialVersionUID = -6736174123976474099L;
	
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	
	private int nb_ship;
	private Planet source, destination;
	
	
	public Squad(double pourcent, Planet src, Planet dest) {
		this.source = src;
		this.destination = dest;
		this.nb_ship = (int) (src.getTroups() * (pourcent / 100));

		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
	}

	/**
	 * \brief gestion des decollages
	 */
	@Override
	public void run() {
		@SuppressWarnings("unused")
		Thread thisThread = Thread.currentThread();
		while(nb_ship>0) {
			if(source.getTroups() -1 <= Constantes.min_troups) {
				nb_ship = 0;
				break;
			}
			double x = this.decollageX(source);
			double y = this.decollageY(source);
			ships.add(
				new Ship(
						Constantes.path_img_ships,
						source.getRuler(),
						destination,
						source,
						x,
						y,
						source.getShips_type()
					)
			);
			source.setTroups(source.getTroups()-1);
			
			nb_ship-=1;
			try {
				sleep(250);	//Magic number
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * \brief check if a squad has been selected or not
	 * @param x position to check
	 * @param y position to check
	 * @return true if a squad has been selected
	 */
	public boolean squad_selected(double x, double y) {
		for(Ship s : ships) {
			if(s.isInside(x, y, Constantes.size_squads, Constantes.size_squads)) {
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * \brief Render every ships of this squad
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
	 * \brief update destination planet of every ships of this squads
	 * @param destination the new destination
	 */
	public void update_destination(Planet destination) {
		for(Ship ship : ships) {
			ship.destination = destination;
		}
	}
	
	/**
	 * \brief Update the position of every ships of this squad
	 */
	public void update_all_positions(List<Planet> planets) {
		Iterator<Ship> it = ships.iterator();
		
		while (it.hasNext()) {
			Ship ship = it.next();
			try {
				if(ship.isInside(ship.destination)) {	//Case when it reach his destination
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
	 * \brief updateImage of every ships of this squad
	 */
	public void updateImage() {
		for(Ship s : ships) {
			s.setImg_path(Constantes.path_img_ships);
			s.updateImage();			
		}		
	}
	
	/**
	 * \brief update ruler of every ships of this squads
	 * @param ruler the new ruler
	 */
	public void update_ruler(User ruler) {
		for(Ship ship : ships) {
			ship.setRuler(ruler);
		}
	}

	
	public ArrayList<Ship> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	public User getRuler() {
		try {
			return ships.get(0).getRuler();
		} catch (IndexOutOfBoundsException e) {
			//Every squads has reached destination
			return null;
		}
	}

	/**
	 * \brief Send fleets from a source planet to his destination
	 * @param source Source planet
	 * @param destination Destination planet
	 * @param percent The percent of the source planet troups to be sent
	 * @return
	 */
	public Squad sendFleet(Planet source, Planet destination, int percent) {
		int troups = source.getTroups();
		
		this.source = source;
		this.destination = destination;
		
		
		if(troups > Constantes.min_troups+1) {	//Not send more troups than possible
			int fleet_size = troups - (Constantes.min_troups);
				
			//TODO IF Percent >Constantes, then ... type of the ship for advanced version
				
			fleet_size *= (percent /100.0);
			if(fleet_size < 1) {
				return null;
			}
			source.setTroups(troups - fleet_size);
				
			for(int i = 0; i < fleet_size; i++) {
				double x = this.decollageX(source);
				double y = this.decollageY(source);
				ships.add(
					new Ship(
							Constantes.path_img_ships,
							source.getRuler(),
							destination,
							source,
							x,
							y,
							source.getShips_type()
						)
				);
			}	
		}
		return null;
	}
	
	/**
	 * \brief Calculate the x Position for liftoff
	 * @param source the source planet
	 * @return abscissa position
	 */
	private double decollageX(Planet source) {
		/*
		 
		if(summonX*Constantes.size_squads + source.getX() < source.getX()+source.width()) {
			summonX += 1;
			
		} else if (summonX*Constantes.size_squads + source.getX() >= source.getX()+source.width()) {
			summonX = 1;
			summonY = -1;
		}

		return (summonX*Constantes.size_squads + (source.getX() - Constantes.size_squads));
		
		*/
		return(source.getX()+source.width()/2);
	}
	
	/**
	 * \brief Calculate the y Position for liftoff
	 * @param source the source planet
	 * @return ordered position
	 */
	private double decollageY(Planet source) {
		if(source.getY() > destination.getY())
			return (source.getY() - Constantes.size_squads - 2*Constantes.min_ship_speed);
		else
			return (source.width() + source.getY() + Constantes.size_squads + 2*Constantes.min_ship_speed);		
	}
	
}

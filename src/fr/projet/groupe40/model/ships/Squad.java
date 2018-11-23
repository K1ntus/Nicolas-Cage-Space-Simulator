package fr.projet.groupe40.model.ships;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.planets.Planet;
import fr.projet.groupe40.util.Constantes;
import javafx.scene.canvas.GraphicsContext;

public class Squad implements Serializable {
	private static final long serialVersionUID = -6736174123976474099L;
	
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private int summonX = 1, summonY = 1;

	public Squad() {
		// TODO Auto-generated constructor stub
	}

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
			s.render(gc);
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
	public void update_all_positions() {
		Iterator<Ship> it = ships.iterator();
		
		while (it.hasNext()) {
			Ship ship = it.next();
			try {
				if(ship.isInside(ship.destination)) {
					ships.remove(ship);
					it = ships.iterator();
				}

				ship.update_position();
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
		
		if(troups > Constantes.min_troups+1) {	//Not send more troups than possible
			int fleet_size = troups - (Constantes.min_troups);
				
			//IF Percent >Constantes, then ... type of the ship
			//TODO
				
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
		double x = 0;
		if(summonX*Constantes.size_squads + source.getX() < source.getX()+source.width()) {
			summonX += 1;
			
		} else if (summonX*Constantes.size_squads + source.getX() >= source.getX()+source.width()) {
			summonX = 1;
			summonY = -summonY;
		}

		return (summonX*Constantes.size_squads + (source.getX() - Constantes.size_squads));
		
	}
	
	/**
	 * \brief Calculate the y Position for liftoff
	 * @param source the source planet
	 * @return ordered position
	 */
	private double decollageY(Planet source) {
		if(summonY >= 1)
			return (source.getY() - Constantes.size_squads);
		else
			return (source.width() + source.getY() + Constantes.size_squads);		
	}
	
}

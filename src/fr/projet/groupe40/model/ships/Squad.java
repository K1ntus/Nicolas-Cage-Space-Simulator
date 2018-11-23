package fr.projet.groupe40.model.ships;

import java.util.ArrayList;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.board.Planet;
import fr.projet.groupe40.util.Constantes;
import javafx.scene.canvas.GraphicsContext;

public class Squad {
	
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private int summonX = 1, summonY = 1;

	public Squad() {
		// TODO Auto-generated constructor stub
	}

	public void render_ships(GraphicsContext gc) {
		for(Ship s : ships) {
			s.render(gc);
		}
		
	}

	public void update_destination(Planet destination) {
		for(Ship ship : ships) {
			ship.destination = destination;
		}
	}
	
	public void update_all_positions() {
		for(Ship ship : ships) {
			if(ship.isInside(ship.destination)) {
				ships.remove(ship);
				continue;
			}

			ship.update_position();
			ship.validatePosition();
		}
	}

	public void updateImage() {
		for(Ship s : ships) {
			s.setImg_path(Constantes.path_img_ships);
			s.updateImage();			
		}		
	}
	
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
		return ships.get(0).getRuler();
	}


	public Squad sendFleet(Planet source, Planet destination, int percent) {
		int troups = source.getTroups();
		
		if(troups > Constantes.min_troups+1) {	//Not send more troups than possible
			System.out.println("...");
			int fleet_size = troups - (Constantes.min_troups);
				
			//IF Percent >Constantes, then ... type of the ship
			//TODO
				
			fleet_size *= (percent /100.0);
			if(fleet_size < 1) {
				return null;
			}
			source.setTroups(troups - fleet_size);
				
			for(int i = 0; i < fleet_size; i++) {
					ships.add(
							new Ship(Constantes.path_img_ships, source.getRuler(), source, destination, this.decollageX(source), this.decollageY(source))
							);
			}
				//Squad s = new Squad(ships, (int)fleet_size, destination, ships_type);
				//sendFleet_position(s);
				
				//s.setSource(this);
				
				//return s;			
		}
		return null;
	}

	private double decollageX(Planet source) {
		double x = 0;
		if(summonX*Constantes.size_squads + source.getX() < source.getX()+source.width())
			summonX += 1;
		else if (summonX*Constantes.size_squads + source.getX() >= source.getX()+source.width())
			summonX = 1;

		return (summonX*Constantes.size_squads + (source.getX() - Constantes.size_squads));
		
	}
	private double decollageY(Planet source) {
		if(Math.random()>= 0.5)
			return (source.getY() - Constantes.size_squads);
		else
			return (source.width() + source.getY() + Constantes.size_squads);
		
	}
	
}

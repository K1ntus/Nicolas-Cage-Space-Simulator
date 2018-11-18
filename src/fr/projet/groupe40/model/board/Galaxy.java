package fr.projet.groupe40.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.client.handler.InteractionHandler;
import fr.projet.groupe40.model.Sprite;
import fr.projet.groupe40.model.ships.Squad;
import fr.projet.groupe40.util.Constantes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


@SuppressWarnings("unused")
public class Galaxy extends Thread implements Serializable{
	private static final long serialVersionUID = 3668540725184418675L;
	/**
	 * 
	 */

	private volatile transient Thread blinker;
	
	//private final static Sprite ai = new Sprite(Constantes.path_img_planets, new User(Constantes.ai_user), true);
	//private final static Sprite player = new Sprite(Constantes.path_img_planets, new User(Constantes.human_user), true);
	//private final static Sprite neutral = new Sprite(Constantes.path_img_planets, new User(Constantes.neutral_user), true);
	
	
	private ArrayList<Planet> planets;
	private ArrayList<Squad> squads;
	
	private transient Image background;
	
	public Galaxy() {
		squads = new ArrayList<Squad>();
		planets = new ArrayList<Planet>();

		generatePlanets();
		//generateRandomSquads();
		
		setBackground(new Image(Constantes.path_img_background, Constantes.width, Constantes.height, false, false, true));
		
		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
	}

	
	public Galaxy(Galaxy g) {
		planets = g.planets;
		squads = g.squads;
		
		setBackground(new Image(Constantes.path_img_background, Constantes.width, Constantes.height, false, false, true));
		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
	}

	/**	Thread	**/
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		while(true) {
			for(Planet p : planets)
				p.updateGarrison();	
				generateRandomSquads();		
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void stopThread() {
        blinker = null;
    }
	
	/** Render & Game Update **/
	public void render(GraphicsContext gc) {
		renderBackground(gc);
		renderPlanets(gc);
		renderSquads(gc);	
		renderGarrison(gc);
		renderPercentageSelected(gc);
	}
	

	public void update() {
		updateAI();
		updateSquad();
		userHasLost(Constantes.ai_user);
	}
	
	public void updateSquad() {
		Iterator<Squad> it = getSquads().iterator();
		
		while (it.hasNext()) {
			Squad ss = it.next();
			try {
				if(ss.isReached()) {
					it.remove();
				} else {
					ss.updatePosition();
				}
			} catch(NullPointerException e) {
				it.remove();
			}
		}		
	}
	
	/**	AI	**/
	public void updateAI() {
		Planet source, destination;
		
		for(Planet p : planets) {
			User ruler = p.getRuler();
			
			if(ruler.getId() < 0) {	//0 = neutral, >0 human, <0 bot
				source = p;
				
				//Check if the ai have reach the max numbers of squads at the same time
				if(Constantes.limit_ai_squads_number) {
					int sum = 0;
					for(Squad s : squads) {
						try {
							if(s.getRuler().getId() == ruler.getId()) {
								sum += 1;
							}							
						} catch (NullPointerException e) {		}
					}
					if (sum > Constantes.max_squads_for_ai) {
						break;
					}
					
				}
				
				for(Planet p2 : planets) {	//Check again the planets list
					destination = p2;
					if(p2.getTroups() < source.getTroups()) {
						Squad s = ruler.sendFleetAI(source, destination);
						squads.add(s);
						break;
					}
				}
			}
		}

	}
	
	
	/** Defeat handler **/
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
			int ruler_id = s.getRuler().getId();
			if(ruler_id == id) {
				return false;
			}
		}
		
		u.setLost(false);
		return true;
	}
	
	
	/** Client handler **/
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
	
	/** Collisions Between Ships & Planets **/
	public void collisionHandler(Squad s, Planet p) {
		double deltaY = 0, deltaX = 0;
		double x = s.getX(), y = s.getY();
		double width = s.width(), height = s.height();
		double speed = s.getType().getSpeed();
		double distance = p.distance(x, y, p.getX(), p.getY());
		Planet source = s.getSource();
		Planet destination = s.getDestination();
		
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

	public boolean isCollision(Squad s) {
		for(Planet p : planets) {
			if(p != s.getDestination() && p != s.getSource()) {
				//collisionHandler(s, p);
				return true;
			}
		}

		s.updatePosition();
		return false;
	}

	
	/** Planets Generation **/
	public void generatePlanets() {
		double width = Math.random() * Constantes.size_maximal_planets *0.25 + Constantes.size_minimal_planets;
		double height = width;
		//Sprite(String path, double width, double height, double maxX, double maxY) 

		
		for(int i = 0; i < Constantes.nb_planets_tentatives; i++) {
			double y = (Math.random() * (Constantes.height - (height + Constantes.bottom_margin_size)));
			Planet p = new Planet(Constantes.path_img_planets, new User(Constantes.neutral_user), true, 0, 0);
			p.setY(y);
			p.validatePosition();
			
			if(testPlacement(p)) {
				if(!(p.getY() >= Constantes.height - Constantes.bottom_margin_size - p.height()))
					planets.add(p);							
			}
		}
		
		if(planets.size() < Constantes.min_numbers_of_planets) {	//si moins de 2 planetes
			System.out.println("Impossible de generer un terrain minimal");
			System.exit(-1);		//quitte le prgm
		}else {		//On attribue 2 planetes, une a l'ia, une au joueur
			planets.get(0).setRuler(Constantes.human_user);
			planets.get(1).setRuler(Constantes.ai_user);
		}
		
		for(Planet p : planets) {
			p.setImg_path(Constantes.path_img_planets);
			p.updateImage();
		}
	}


	private boolean testPlacement(Planet p) {
		if(p.getY() > Constantes.height - p.height()/2 - Constantes.bottom_margin_size) {
			return false;
		}
		Iterator<Planet> it = planets.iterator();
		int sum = 0;
		
		while (it.hasNext()) {
			Planet p_already_placed = it.next();

			if(p_already_placed.intersects(p) || p_already_placed.intersectCircle(p)) {
				if(p.updatePlanetePosition() == -1) {
					if(Constantes.DEBUG)
						return false;
				}
				
				it = planets.iterator();
			}
		}
		if(sum > 0)
			System.out.println(" Unable to generate "+ sum +" planets");
		
		return true;
	}

	/** Others Generations **/
	//mainly for debugging for the moment, mb using it has pirate ?
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
	}
	
	/** Sound Rendering Subfonction **/
	
	/** Rendering subfunction **/

	public void renderBackground(GraphicsContext gc) {
		gc.drawImage(getBackground(), 0, 0);
	}
	public void renderPlanets(GraphicsContext gc) {
		for(Planet p : planets) {
			if(p != null)
				p.render(gc);					
		}
	}
	
	public void renderSquads(GraphicsContext gc) {
		Iterator<Squad> it = squads.iterator();
		while (it.hasNext()) {
			Squad ss = null;
			try {
				ss = it.next();				
			}catch(ConcurrentModificationException e) {
				it = squads.iterator();
				continue;
			}
			if(ss == null) {	continue;	}
			if(ss.isReached()) {
				squads.remove(ss);
				it = squads.iterator();
			}else {
				isCollision(ss);
				ss.render(gc);							
			}
		}
	}

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
	
	public void renderExplosion(GraphicsContext gc) {
		
	}
	
	/** init the font type **/
	public void initFont(GraphicsContext gc) {
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Constantes.color_default);
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);		
	}
	
	/** Getter & Setter **/
	public ArrayList<Squad> getSquads() {
		return squads;
	}

	public void setSquads(ArrayList<Squad> squads) {
		this.squads = squads;
	}

	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	
	public Image getBackground() {
		return background;
	}

	public void setBackground(Image background) {
		this.background = background;
	}
	
}

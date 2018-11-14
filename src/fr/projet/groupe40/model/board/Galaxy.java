package fr.projet.groupe40.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.model.Sprite;
import fr.projet.groupe40.model.ships.Squad;
import fr.projet.groupe40.util.Constantes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


@SuppressWarnings("unused")
public class Galaxy extends Thread{
	private volatile transient Thread blinker;
	
	private final static Sprite ai = new Sprite(Constantes.path_img_planets, new User(Constantes.ai1_user), true);
	private final static Sprite player = new Sprite(Constantes.path_img_planets, new User(Constantes.player1_user), true);
	private final static Sprite neutral = new Sprite(Constantes.path_img_planets, new User(Constantes.neutral_user), true);
	
	
	private ArrayList<Planet> planets;
	private ArrayList<Squad> squads;
	private ArrayList<User> users;
	
	private transient Image background;
	
	public Galaxy() {
		users = new ArrayList<User>();
		squads = new ArrayList<Squad>();
		planets = new ArrayList<Planet>();

		initUsers();
		generatePlanets();
		
		if(Constantes.DEBUG && Constantes.DEBUG_TROUPS)
			generateRandomSquads();

		setBackground(new Image(Constantes.path_img_background, Constantes.width, Constantes.height, false, false, true));
		
		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
	}
	
	public Galaxy(Galaxy g) {
		// TODO Auto-generated constructor stub
		planets = g.planets;
		squads = g.squads;
		users = g.users;
		
		setBackground(new Image(Constantes.path_img_background, Constantes.width, Constantes.height, false, false, true));
		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
	}

	/**	Thread	**/
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		//while(blinker == thisThread) {
		while(true) {
			for(Planet p : planets)
				p.updateGarrison();			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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

		Iterator<Squad> it = getSquads().iterator();
		while (it.hasNext()) {
			Squad ss = it.next();
			try {
				if(ss.isReached()) {
					it.remove();
				}else {
					//if(!isCollision(ss))
						ss.updatePosition();
				}
			} catch(NullPointerException e) {
				it.remove();
			}
		}
	}
	
	public void clientScrollHandler(int action) {
		for(User u : users) {
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

	public boolean isCollision(Squad s) {
		double x = s.getX(), y = s.getY();
		double speed = s.getType().getSpeed();
		boolean collision = false;
		
		for(Planet p : planets) {
			if(p != s.getDestination() && p != s.getSource()) {
				if(p.isInside(x+speed, y)) {
					s.setX(x - speed);
					double distance = p.distance(x, y, p.getX(), p.getY());
					if(distance > p.height()/2) {
						System.out.println("augmente y");
						s.setY(y+speed);				
					}else {
						System.out.println("diminue y");
						s.setY(y-speed);
					}
					System.out.println("squad inside a planet on his way1");
					collision = true;
				}else if(p.isInside(x, y+speed)) {
					//s.setY(y)
					double distance = p.distance(x, y, p.getX(), p.getY());	
					s.setY(y-speed);
					if(distance > p.width()/2) {
						System.out.println("augmente x");
						s.setX(x+speed);	
					}else {
						System.out.println("diminue x");
						s.setX(x-speed);
					}
					System.out.println("squad inside a planet on his way2");
					collision = true;
				}
				
				if(collision) {
					return true;
				}
			}
		}

		return false;
	}

	public void collisionHandler(Sprite s1, Sprite s2) {
		
	}
	/** Generation utilities **/
	public void generatePlanets() {
		double width = Math.random() * Constantes.size_maximal_planets *0.25 + Constantes.size_minimal_planets;
		double height = width;
		//Sprite(String path, double width, double height, double maxX, double maxY) 
		
		
		for(int i = 0; i < Constantes.nb_planets_tentatives; i++) {
			double y = (Math.random() * (Constantes.height - height));
			Planet p = new Planet(neutral,0,0);
			p.setY(y);
			
			if(testPlacement(p)) {
				planets.add(p);							
			}
		}
		
		if(planets.size() < 2) {	//si moins de 2 planetes
			System.out.println("Impossible de générer un terrain correct");
			System.exit(-1);		//quitte le prgm
		}else {		//On attribue 2 planètes, une a l'ia, une au joueur
			planets.get(0).setRuler(Constantes.player1_user);
			planets.get(1).setRuler(Constantes.ai1_user);
		}
		

		for(Planet p : planets) {
			p.setImg_path(Constantes.path_img_planets);
			p.updateImage();
		}
		
	}
	
	private void initUsers() {
		users.add(Constantes.ai1_user);
		users.add(Constantes.neutral_user);
		users.add(Constantes.player1_user);
	}
	
	private boolean testPlacement(Planet p) {
		Iterator<Planet> it = planets.iterator();
		while (it.hasNext()) {
			Planet p_already_placed = it.next();

			if(p_already_placed.intersects(p) || p_already_placed.intersectCircle(p)) {
				if(p.updatePlanetePosition() == -1) {
					if(Constantes.DEBUG)
						System.out.println(" Unable to generate this planet");
					return false;
				}
				
				it = planets.iterator();
			}
		}
		return true;
	}


	//mainly for debugging for the moment, mb using it has pirate ?
	public void generateRandomSquads() {
		for(int i = 0; i < Constantes.nb_squads; i++) {
			Squad s = new Squad(new Sprite(Constantes.path_img_ships, new User(Constantes.ai), false), Constantes.max_troups, planets.get(0));
			//Squad s = new Squad(getRessourcePathByName("resources/images/alien.png"), 100, getPlanets().get((int) (Math.random() * (getPlanets().size() - 0))));
			//Squad s = new Squad(getRessourcePathByName("resources/images/alien.png"), i, planets.get((int) (Math.random() * (planets.size() - 0))));
			s.setPosition(Constantes.width * Math.random() - Constantes.size_squads, Constantes.height * Math.random() - Constantes.size_squads);
			squads.add(s);
		}
	}

	
	/** Rendering subfunction **/
	public void renderSquads(GraphicsContext gc) {
		Iterator<Squad> it = squads.iterator();
		while (it.hasNext()) {
			Squad ss = it.next();
			if(ss.isReached()) {
				System.out.println("Ship "+ss.getNb_of_ships()+" has reached's destination");
				it.remove();
			}else {
				ss.updatePosition();
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
		for(User u : users) {
			if (u.getFaction() == Constantes.player) {
				gc.setFill(Constantes.color_default);
				gc.setStroke(Color.RED);
				gc.setTextAlign(TextAlignment.CENTER);	
				
				String txt = "Troupes: "+u.getPercent_of_troups_to_send()+"%";
				
				gc.fillText(txt, Constantes.width/7, 50);
				gc.strokeText(txt, Constantes.width/7, 50);
				
				break;				
			}
		}
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

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	
	public void renderBackground(GraphicsContext gc) {
		gc.drawImage(getBackground(), 0, 0);
	}
	
	public void renderPlanets(GraphicsContext gc) {
		for(Planet p : planets) {
			if(p != null)
				p.render(gc);					
		}
	}

	/**
	 * @return the background
	 */
	public Image getBackground() {
		return background;
	}

	/**
	 * @param background the background to set
	 */
	public void setBackground(Image background) {
		this.background = background;
	}
	
}

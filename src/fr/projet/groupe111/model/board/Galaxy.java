package fr.projet.groupe111.model.board;

import java.util.ArrayList;
import java.util.Iterator;

import fr.projet.groupe111.client.User;
import fr.projet.groupe111.model.Sprite;
import fr.projet.groupe111.model.ships.Squad;
import fr.projet.groupe111.util.Constantes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


public class Galaxy extends Thread{
	//private final static Sprite ai = new Sprite(Constantes.path_img_planets, new User(Constantes.ai1_user), true);
	//private final static Sprite player = new Sprite(Constantes.path_img_planets, new User(Constantes.player1_user), true);
	private final static Sprite neutral = new Sprite(Constantes.path_img_planets, new User(Constantes.neutral_user), true);
	
	
	private ArrayList<Planet> planets;
	private ArrayList<Squad> squads;
	private ArrayList<User> users;
	
	Image background;
	
	public Galaxy() {
		users = new ArrayList<User>();
		squads = new ArrayList<Squad>();
		planets = new ArrayList<Planet>();

		generatePlanets();
		
		if(Constantes.DEBUG)
			generateRandomSquads();

		background = new Image(Constantes.path_img_background, Constantes.width, Constantes.height, false, false, true);
		
		setDaemon(true);	//Thread will close if game window has been closed
		start();			//Run the thread which is generating troups
		
	}
	
	/**	Thread	**/
	@Override
	public void run() {
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
	
	/** Render & Game Update **/
	public void render(GraphicsContext gc) {
		renderBackground(gc);
		renderPlanets(gc);
		renderSquads(gc);	
		renderGarrison(gc);
	}
	

	public void update() {
		for(Squad s : squads) {
			s.updatePosition();
		}
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
			//planets.get(1).setRuler(ai);
		}
		
		
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
					gc.setStroke(Constantes.color_default); break;
			}
			gc.fillText(txt, p.getX() + (p.width()/2), p.getY() + (p.height()/2));
			gc.strokeText(txt, p.getX() + (p.width()/2), p.getY() + (p.height()/2));
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
		gc.drawImage(background, 0, 0);
	}
	
	public void renderPlanets(GraphicsContext gc) {
		for(Planet p : planets) {
			if(p != null)
				p.render(gc);					
		}
	}
	
}

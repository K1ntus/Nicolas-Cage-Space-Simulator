package fr.projet.groupe40.model;

import java.io.Serializable;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.util.Constantes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Sprite implements Serializable {
	private static final long serialVersionUID = 361050239890707789L;
	/**
	 * 
	 */
	
	private transient Image image;	//unserializable
	private String img_path;
	
	private double width, height;
	private double x, y;
	private double xSpeed, ySpeed;
	private double maxX, maxY;

	private User ruler;

	public Sprite(String path, User ruler, boolean isPlanet) {
		setImg_path(path);
		
		width = Constantes.size_squads;
		maxX = Constantes.width - width;
		maxY = Constantes.height - height;

		this.ruler = ruler;
		
		if(isPlanet) {
			width = Math.random() * Constantes.size_maximal_planets  + Constantes.size_minimal_planets;
			if(width > Constantes.size_maximal_planets)
				width = Constantes.size_maximal_planets;
			
			maxX = Constantes.width - width*1.25;
			maxY = Constantes.height - height*1.25;
		}
		height = width;			
		
		updateImage();
	}

	public Sprite(Sprite s) {
		image = s.image;
		this.x = s.x;
		this.y = s.y;
		this.width = s.width;
		this.height = s.height;
		this.ruler = s.ruler;
		maxX = Constantes.width - width*1.25;
		maxY = Constantes.height - height*1.25;
	}

	public void updateImage() {
		try {
			image = new Image(getImg_path(), width, height, false, false);
		} catch(NullPointerException e) {
			//No image
		}
	}
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double distance(double x, double y) {
		double center_x = x + width()/2;
		double center_y = y + height()/2;
		return distance(x,y, center_x, center_y);
	}
	public double distance(double x1, double y1, double x2, double y2) {
		double res = Math.hypot(x1-x2, y1-y2); 
		return res;
	}
	public double distance(Sprite p) {
		return distance(p.x, p.y);
	}
	
	public void validatePosition() {
		if (x + width() >= maxX) {
			x = maxX - width();
			xSpeed *= -1;
		} else if (x < 0) {
			x = 0;
			xSpeed *= -1;
		}

		if (y + height() >= maxY) {
			y = maxY - height();
			ySpeed *= -1;
		} else if (y < 0) {
			y = 0;
			ySpeed *= -1;
		}
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		validatePosition();
	}

	public void setSpeed(double xSpeed, double ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public void changeSpeed(KeyCode code) {
		switch (code) {
		case LEFT:
			xSpeed--;
			break;
		case RIGHT:
			xSpeed++;
			break;
		case UP:
			ySpeed--;
			break;
		case DOWN:
			ySpeed++;
			break;
		case SPACE:
			ySpeed = xSpeed = 0;
			break;
		default:
		}
	}

	public void updatePosition() {
		x += xSpeed;
		y += ySpeed;
		validatePosition();
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(image, x, y);
	}

	public boolean intersects(Sprite s) {
		return ((x >= s.x && x <= s.x + s.width()) || (s.x >= x && s.x <= x + width()))
				&& ((y >= s.y && y <= s.y + s.height()) || (s.y >= y && s.y <= y + height()));
	}
	public boolean intersects(double x2, double y2, double width, double height) {
		return ((x >= x2 && x <= x2 + width) || (x2 >= x && x2 <= x + width))
				&& ((y >= y2 && y <= y2 + height) || (y2 >= y && y2 <= y + height));
	}
		
	/** Conflict Area **/
	
	
	/** Getter & Setter **/
	public double width() {
		return width;
	}

	public double height() {
		return height;
	}

	public String toString() {
		return "Sprite<" + x + ", " + y + ">";
	}

	public User getRuler() {
		return ruler;
	}

	public void setRuler(User ruler) {
		this.ruler = ruler;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public double getySpeed() {
		return ySpeed;
	}

	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the img_path
	 */
	public String getImg_path() {
		return img_path;
	}

	/**
	 * @param img_path the img_path to set
	 */
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

}

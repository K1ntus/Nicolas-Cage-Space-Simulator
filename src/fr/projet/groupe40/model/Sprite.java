package fr.projet.groupe40.model;

import java.io.Serializable;

import fr.projet.groupe40.client.User;
import fr.projet.groupe40.util.Constantes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite implements Serializable {
	private static final long serialVersionUID = 361050239890707789L;
	
	private transient Image image;	//unserializable
	private String img_path;
	
	private double width, height;
	private double x, y;
	private double xSpeed, ySpeed;
	private double maxX, maxY;
	private double minX, minY;

	private User ruler;

	/**
	 * \brief Create a sprite from an image path, a ruler and if its a planets or not
	 * @param path Path to the image file
	 * @param ruler Ruler of this sprite
	 * @param isPlanet Its a planet or not
	 */
	public Sprite(String path, User ruler, boolean isPlanet) {
		setImg_path(path);
		
		width = Constantes.size_squads;
		setMaxX(Constantes.width - width);
		setMaxY(Constantes.height - height);

		this.ruler = ruler;
		
		if(isPlanet) {
			width = Math.random() * Constantes.size_maximal_planets  + Constantes.size_minimal_planets;
			if(width > Constantes.size_maximal_planets)
				width = Constantes.size_maximal_planets;

			setMaxX(Constantes.width - width - Constantes.right_margin_size);
			setMaxY(Constantes.height - height - Constantes.bottom_margin_size);
		}

		minY = Constantes.top_margin_size - height;
		minX = Constantes.left_margin_size - width;
		height = width;			
		
		updateImage();
	}

	/**
	 * \brief Update the image linked to a sprite
	 */
	public void updateImage() {
		try {
			image = new Image(getImg_path(), width, height, false, false);
		} catch(NullPointerException e) {
			//No image
		}
	}
	
	/**
	 * \brief Set the x and y position of a sprite
	 * @param x New x position
	 * @param y New y position
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * \brief Return the distance between a sprite and a 2d coord.
	 * @param x 
	 * @param y
	 * @return Distance between those 2 positions
	 */
	public double distance(double x, double y) {
		double center_x = this.x + width()/2;
		double center_y = this.y + height()/2;
		return distance(x,y, center_x, center_y);
	}
	
	/**
	 * \brief Return the distance between 2 dots
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return A distance
	 */
	public double distance(double x1, double y1, double x2, double y2) {
		double res = Math.hypot(x1-x2, y1-y2); 
		return res;
	}
	
	/**
	 * \brief Calculate the distance between 2 sprites
	 * @param p Second sprite to compare with
	 * @return The distance
	 */
	public double distance(Sprite p) {
		return distance(p.x, p.y);
	}
	
	/**
	 * \brief Verify if a sprite is not out of bounds
	 */
	public void validatePosition() {
		if (x + width >= getMaxX()) {
			x = getMaxX() - width();
			xSpeed *= -1;
		} else if (x <= minX) {
			x = minX;
			xSpeed *= -1;
		}
		else if (x < 0) {
			x = 0;
			xSpeed *= -1;
		}

		if (y + height >= getMaxY()) {
			y = getMaxY() - height - Constantes.bottom_margin_size;
			ySpeed *= -1;
		} else if (y <= minY) {
			y = minY;
			ySpeed *= -1;
		}else if (y < 0) {
			y = 0;
			ySpeed *= -1;
		}
	}
	
	/**
	 * \brief Check if a rectangle intersect a circle
	 * @param x_left
	 * @param y_top
	 * @param x_right
	 * @param y_bottom
	 * @return true if there s a collision, else false
	 */
	public boolean intersectCircle(double x_left, double y_top, double x_right, double y_bottom) {
		//(x - center_x)^2 + (y - center_y)^2 < radius^2
		double radius = this.width()/2 + Constantes.minimal_distance_between_planets;
		double circle_x = this.getX() + this.width()/2;
		double circle_y = this.getY() + this.height()/2;
		double circle_left = circle_x - radius;
		double circle_top = circle_y - radius;
		double circle_right = circle_x + radius;
		double circle_bottom = circle_y + radius;
		
		//Reject if corner are clearly out of the circle
		if(x_right < circle_left && x_left > circle_right || y_bottom < circle_top && y_top > circle_bottom) {
			return true;
		}
		//check if center of circle is inside rectangle
		if(x_left <= circle_x && circle_x <= x_right && y_top <= circle_y && circle_y <= y_bottom) {
			//System.out.println("**Circle is inside rectangle");
			return true;
		}
		        		
		//Check every point of the rectangle
		for(double x1 = x_left; x1 < x_right; x1++) {
			for(double y1 = y_top; y1 < y_bottom; y1++) {
				if(Math.hypot(x1 - circle_x, y1 - circle_y) <= radius) {
					return true;
				}
			}
		}
		
		return false;
	}
	/**
	 * \brief Check if a sprite intersect another circle from a sprite
	 * In fact, we're generating a circle around a sprite to check the distance between each others
	 * @param p
	 * @return
	 */
	public boolean intersectCircle(Sprite p) {
		return intersectCircle(
				p.getX(),
				p.getY(),
				p.getX()+p.width(), 
				p.getY()+p.height()
			);
	}

	/**
	 * \brief Check if a rectangle is inside another
	 * @param x	
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean isInside(double x, double y, double width, double height) {
		if(x > this.getX()+this.width() || x+width < this.getX()) {
			return false;
		}
		
		if(y > this.getY()+this.height() || y+height < this.getY()) {
			return false;
		}
		return true;
	}
	
	/**
	 * \brief Check if a sprite directly intersect another one
	 * @param s
	 * @return
	 */
	public boolean isInside(Sprite s) {
		return ((x >= s.x && x <= s.x + s.width()) || (s.x >= x && s.x <= x + width()))
				&& ((y >= s.y && y <= s.y + s.height()) || (s.y >= y && s.y <= y + height()));
		//return isInside(s.getX(), s.getY());
	}

	/**
	 * \brief Set the position of a sprite then validate his position
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		validatePosition();
	}

	/**
	 * \brief Render the image of this sprite
	 * @param gc
	 */
	public void render(GraphicsContext gc) {
		gc.drawImage(image, x, y);
	}

	/* Conflict Area */
	
	
	/* Getter & Setter */
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

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

}

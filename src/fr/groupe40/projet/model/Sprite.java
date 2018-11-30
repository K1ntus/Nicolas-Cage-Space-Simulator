package fr.groupe40.projet.model;

import java.io.Serializable;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.util.constants.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * 
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public abstract class Sprite implements Serializable {
	private static final long serialVersionUID = 361050239890707789L;
	
	/**
	 * \brief his image to display
	 */
	private transient Image image;	//unserializable
	
	/**
	 * \brief his img path
	 */
	private String img_path;
	
	/**
	 * \brief his width & height
	 */
	private double width, height;
	
	/**
	 * \brief x and y position
	 */
	private double x, y;
	
	/**
	 * \brief maximal x and y position
	 */
	private double maxX, maxY;
	
	/**
	 * \brief minimal x and y position
	 */
	private double minX, minY;

	/**
	 * \brief the ruler of this sprites (Planets, Ship, ...)
	 */
	private User ruler;

	/**
	 * \brief Create a sprite from an image path, a ruler and if its a planets or not
	 * @param path Path to the image file
	 * @param ruler Ruler of this sprite
	 * @param isPlanet Its a planet or not
	 */
	public Sprite(String path, User ruler, boolean isPlanet) {
		this.img_path = path;
		
		width = Constants.size_squads;
		setMaxX(Constants.width - width);
		setMaxY(Constants.height - height);

		this.ruler = ruler;
		
		if(isPlanet) {
			width = Math.random() * Constants.size_maximal_planets  + Constants.size_minimal_planets;
			if(width > Constants.size_maximal_planets)
				width = Constants.size_maximal_planets;

			setMaxX(Constants.width - width - Constants.right_margin_size);
			setMaxY(Constants.height - height - Constants.bottom_margin_size);
		}

		minY = Constants.top_margin_size - height;
		minX = Constants.left_margin_size - width;
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
		double center_x = this.x + width/2;
		double center_y = this.y + height/2;
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
			x = getMaxX() - width;
		} else if (x <= minX) {
			x = minX;
		}
		else if (x < 0) {
			x = 0;
		}

		if (y + height >= getMaxY()) {
			y = getMaxY() - height - Constants.bottom_margin_size;
		} else if (y <= minY) {
			y = minY;
		}else if (y < 0) {
			y = 0;
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
		double radius = this.width/2 + Constants.minimal_distance_between_planets;
		double circle_x = this.x + this.width/2;
		double circle_y = this.y + this.height/2;
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
	 * @param p a sprite
	 * @return true if the sprite is in the circle else false
	 */
	public boolean intersectCircle(Sprite p) {
		return intersectCircle(
				p.getX(),
				p.getY(),
				p.getX()+p.width, 
				p.getY()+p.height
			);
	}

	/**
	 * \brief Check if a rectangle is inside another
	 * @param x	the x-top corner
	 * @param y the y-top corner
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 * @return true if inside else false
	 */
	public boolean isInside(double x, double y, double width, double height) {
		if(x > this.x+this.width || x+width < this.x) {
			return false;
		}
		
		if(y > this.y+this.height || y+height < this.y) {
			return false;
		}
		return true;
	}
	
	/**
	 * \brief Check if a sprite directly intersect another one
	 * @param s the sprite to compare with
	 * @return true if the sprite is inside, else false
	 */
	public boolean isInside(Sprite s) {
		return ((x >= s.x && x <= s.x + s.width) || (s.x >= x && s.x <= x + width))
				&& ((y >= s.y && y <= s.y + s.height) || (s.y >= y && s.y <= y + height));
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

	
	/* Getter & Setter */

	/**
	 * \brief convert the object to string with his 2D position
	 */
	public String toString() {
		return "Sprite<" + x + ", " + y + ">";
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
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

	/**
	 * @return the width
	 */
	public double width() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public double height() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return the maxX
	 */
	public double getMaxX() {
		return maxX;
	}

	/**
	 * @param maxX the maxX to set
	 */
	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	/**
	 * @return the maxY
	 */
	public double getMaxY() {
		return maxY;
	}

	/**
	 * @param maxY the maxY to set
	 */
	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	/**
	 * @return the minX
	 */
	public double getMinX() {
		return minX;
	}

	/**
	 * @param minX the minX to set
	 */
	public void setMinX(double minX) {
		this.minX = minX;
	}

	/**
	 * @return the minY
	 */
	public double getMinY() {
		return minY;
	}

	/**
	 * @param minY the minY to set
	 */
	public void setMinY(double minY) {
		this.minY = minY;
	}

	/**
	 * @return the ruler
	 */
	public User getRuler() {
		return ruler;
	}

	/**
	 * @param ruler the ruler to set
	 */
	public void setRuler(User ruler) {
		this.ruler = ruler;
	}


}

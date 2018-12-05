package fr.groupe40.projet.model.planets;

import java.io.Serializable;

import fr.groupe40.projet.client.User;
import fr.groupe40.projet.model.Sprite;
import fr.groupe40.projet.util.constants.Constants;

/**
 * \brief Round planet
 * @author Jordane Masson
 * @author Sarah Portejoie
 *
 */
public class RoundPlanet extends Planet implements Serializable{
	private static final long serialVersionUID = -7903551302927756905L;

	public RoundPlanet(String pathImgPlanets, User user, int x, int y) {
		super(pathImgPlanets, user, x, y);
	}
	

	@Override
	public boolean isInside(double x, double y) {
		if(isInside(x, y, 0, 0)) {
			if(Constants.DEBUG) {
				System.out.println("Vous avez clique sur une planete avec "+this.getTroups());
				System.out.println("Celle ci appartient a l'ID: "+ this.getRuler().getId());
				System.out.println("et de type: "+ this.getRuler().getFaction());
			}
			return true;
		}
		return false;
			
	}
	
	@Override
	public boolean isInside(double x, double y, double width, double height) {
		double x2 = this.getX(), y2 = this.getY(), width2 = this.width(), height2 = this.height();
		if(x > x2+width2 || x+width < x2) {
			return false;
		}
		
		if(y > y2+height2 || y+height < y2) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isInside(Sprite s) {
		double x = this.getX(), y = this.getY();
		double width = this.width(), height = this.height();
		
		double x2 = s.getX(), y2 = s.getY();
		double width2 = s.width(), height2 = s.height();
		
		return ((x >= x2 && x <= x2 + width2) || (x2 >= x && x2 <= x + width))
				&& ((y >= y2 && y <= y2+ height2) || (y2 >= y && y2 <= y + height));
	}
	
	
	/*
	@Override
	public boolean isInside(Sprite p) {
		return isInside(
				p.getX(),
				p.getY(),
				p.getX()+p.width(), 
				p.getY()+p.height()
			);
	}
	
	@Override
	public boolean isInside(double x, double y) {
		double width = 1, height = 1;
		double x2 = this.getX(), y2 = this.getY(), width2 = this.width(), height2 = this.height();
		if(x > x2+width2 || x+width < x2) {
			return false;
		}
		
		if(y > y2+height2 || y+height < y2) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isInside(double x, double y, double width, double height){

		double radius = this.width();
		double circle_x = this.getX() + this.width()/2;
		double circle_y = this.getY() + this.height()/2;
		double circle_left = circle_x - radius;
		double circle_top = circle_y - radius;
		double circle_right = circle_x + radius;
		double circle_bottom = circle_y + radius;
		
		double x_right = x, x_left = x+width, y_bottom = y+height, y_top = y;
		
		//Reject if corner are clearly out of the circle
		if(x_right < circle_left && x_left > circle_right || y_bottom < circle_top && y_top > circle_bottom) {
			return true;
		}
		//check if center of circle is inside rectangle
		if(x_left <= circle_x && circle_x <= x_right && y_top <= circle_y && circle_y <= y_bottom) {
			//System.out.println("**Circle is inside rectangle");
			return true;
		}
		        		
		//Check every point of the 'rectangle'
		//High consumption
		for(double x1 = x_left; x1 < x_right; x1++) {
			for(double y1 = y_top; y1 < y_bottom; y1++) {
				if(Math.hypot(x1 - circle_x, y1 - circle_y) <= radius) {
					return true;
				}
			}
		}
		
		return false;
	}
	*/
	
	public String toString() {
		return "RoundPlanet <" + getX() + ", " + getY() + ">";
	}




}

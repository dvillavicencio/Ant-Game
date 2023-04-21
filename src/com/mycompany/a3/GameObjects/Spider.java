package com.mycompany.a3.GameObjects;

import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.mycompany.a3.GameWorld;

public class Spider extends MoveableObject {
	
	private ArrayList<GameObject> collisionArray;

	/*
	 * Constructor
	 */
	public Spider(int size, Point location, int color, double heading, double speed) {
		super(size, location, color, heading, speed);
		this.collisionArray = new ArrayList<>();
	}
	
	/*
	 * Return the collision array
	 */
	@Override
	public ArrayList<GameObject> getCollisionArray(){ return collisionArray; }

	/*
	 * Spider toString method
	 */
	public String toString() {
		return "Spider:" + super.toString();
	}
	
	/*
	 * Spider move method (Note: the method is abstract in moveable but the implementation is the same for both Ant and Spider)
	 */
	public void move(int xLimit, int yLimit, int ellapsedTime) {
		Point newLocation;
		double deltaX = Math.cos(Math.toRadians(90.0 - this.getHeading())) * this.getSpeed() * (double)ellapsedTime/1000.0;
		double deltaY = Math.sin(Math.toRadians(90.0 - this.getHeading())) * this.getSpeed() * (double)ellapsedTime/1000.0;

		float newX = (float)deltaX + this.getLocation().getX();
		float newY = (float)deltaY + this.getLocation().getY();
		newLocation = new Point((float)newX, (float)newY);
		
		if(newX > xLimit-this.getSize()/2) {
			this.setHeading(270.0);
		}
		else if(newX < 0 + this.getSize()/2) {
			this.setHeading(90.0);
		}
		if(newY > yLimit-this.getSize()/2){
			this.setHeading(180.0);
		}
		else if(newY < 0 + this.getSize()/2){
			this.setHeading(0.0);
		}
		newLocation = new Point((float)newX, (float)newY);
		this.setLocation(newLocation);
	}

	@Override
	public void draw(Graphics g, Point pCmpeRelPrnt) {
		int spiderXLocation = (int)this.getLocation().getX() + (int)pCmpeRelPrnt.getX() - this.getSize()/2;
		int spiderYLocation = (int)this.getLocation().getY() + (int)pCmpeRelPrnt.getY() - this.getSize()/2;
		g.setColor(this.getColor());
		int[] xPoints = { 
				spiderXLocation,
				spiderXLocation + this.getSize(), 
				this.getSize()/2 + (spiderXLocation)
				};
		
		int[] yPoints = { 
				spiderYLocation, 
				spiderYLocation, 
				spiderYLocation + this.getSize() 
				};
		
		g.drawPolygon(xPoints, yPoints, 3);
	}

	@Override
	public boolean collidesWith(GameObject otherObject) {
		int spiderRight = (int)this.getLocation().getX()+this.getSize()/2;
		int spiderLeft = (int)this.getLocation().getX()-this.getSize()/2;
		int spiderTop = (int)this.getLocation().getY()+this.getSize()/2;
		int spiderBottom = (int)this.getLocation().getY()-this.getSize()/2;
		
		int otherRight = (int)otherObject.getLocation().getX()+otherObject.getSize()/2;
		int otherLeft = (int)otherObject.getLocation().getX()-otherObject.getSize()/2;
		int otherTop = (int)otherObject.getLocation().getY()+otherObject.getSize()/2;
		int otherBottom = (int)otherObject.getLocation().getY()-otherObject.getSize()/2;
		
		return !(spiderRight < otherLeft || spiderLeft > otherRight) && !(otherTop < spiderBottom || spiderTop < otherBottom);
	}

	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		
		if(collidesWith(otherObject)) {	
			// If this object's collision array has it, or the other one does, then return
			if((this.collisionArray.contains(otherObject) || otherObject.getCollisionArray().contains(this))){
				return;
			}
			else {	
				if(otherObject instanceof Ant) {
					collisionArray.add(otherObject);
					gw.spiderCollision();
					if(gw.getSound()) {
						gw.getSpiderSound().play();
					}
				}
			}
		}
		else{
			collisionArray.remove(otherObject);
		}
	}
}

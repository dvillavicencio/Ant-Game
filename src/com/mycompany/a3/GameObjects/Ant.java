package com.mycompany.a3.GameObjects;

import java.util.ArrayList;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.GameWorld;

public class Ant extends MoveableObject implements ISteerable{
	
	/*
	 * Class variables
	 */
	private static Ant ant;
	private double maximumSpeed;
	private double foodLevel;
	private double foodConsumptionRate;
	private int healthLevel;
	private int lastFlagReached;
	private ArrayList<GameObject> collisionArray;
	
	/*
	 * Private Ant Constructor
	 */
	private Ant() {
		super(100, new Point(0,0), ColorUtil.rgb(255, 0, 0), 90.0, 0.0);
		this.maximumSpeed = 150;
		this.foodLevel = 100;
		this.foodConsumptionRate = 0.05;
		this.healthLevel = 10;
		this.lastFlagReached = 1;
		this.collisionArray = new ArrayList<>();
	}

	public static Ant getAnt() {
		if(ant == null) {
			ant = new Ant();
		}
		return ant;
	}
	
	public void resetAnt() {
		super.setLocation(new Point(this.getSize()/2,this.getSize()/2));
		super.setColor(ColorUtil.rgb(255, 0, 0));
		super.setHeading(90.0);
		super.setSpeed(0.0);
		this.maximumSpeed = 100;
		this.foodLevel = 100.0;
		this.foodConsumptionRate = 0.05;
		this.healthLevel = 10;
		this.lastFlagReached = 1;
		this.collisionArray.clear();
	}
	/*
	 * Ant getter methods
	 */
	public double getMaxmimumSpeed() { return this.maximumSpeed; }
	
	public double getFoodLevel() { return (this.foodLevel*100.0)/100.0; }
	
	public double getFoodConsumptionRate() { return this.foodConsumptionRate; }
	
	public int getHealthLevel() { return this.healthLevel; }
	
	public int getLastFlagReached() { return this.lastFlagReached; }
	
	@Override
	public ArrayList<GameObject> getCollisionArray() { return this.collisionArray; }

	/*
	 * Ant setter methods
	 */
	public void setMaximumSpeed(double newMaximumSpeed) { this.maximumSpeed = newMaximumSpeed; }
	
	public void setFoodLevel(double newFoodLevel) {
		if(0.0 > newFoodLevel) this.foodLevel = 0.0; 
		else if(newFoodLevel > 100.0) this.foodLevel = 100.0;
		else this.foodLevel = newFoodLevel; 
	}
	
	public void setFoodConsumptionRate(double newConsumptionRate) { this.foodConsumptionRate = newConsumptionRate; }
	
	public void setHealthLevel(int newHealthLevel) { this.healthLevel = newHealthLevel; }
	
	public void setLastFlagReached(int newLastFlagReached) { this.lastFlagReached = newLastFlagReached; }

	/*
	 * Ant toString method
	 */
	public String toString() {
		String parentString = super.toString();
		String childString = "Ant: maxSpeed=" + Math.round(this.maximumSpeed*10.0)/10.0 + " foodConsumptionRate=" + this.foodConsumptionRate;
		return childString + parentString ;
	}
	
	/*
	 * Ant milestones method to get all levels and milestones for display
	 */
	public String milestones() {
		String levelString = "Food level: " + this.foodLevel + "\nHealth level: " + this.healthLevel + "\nHighest Flag Reached: " + this.lastFlagReached;
		return levelString;
	}
	
	/*
	 * Ant move method (Note: the method is abstract in moveable but the implementation is the same for both Ant and Spider)
	 */
	@Override
	public void move(int xLimit, int yLimit, int ellapsedTime) {
		Point newLocation;
		double deltaX = Math.cos(Math.toRadians(90.0 - this.getHeading())) * this.getSpeed() * (double)ellapsedTime/1000;
		double deltaY = Math.sin(Math.toRadians(90.0 - this.getHeading())) * this.getSpeed() * (double)ellapsedTime/1000;

		float newX = (float)deltaX + this.getLocation().getX();
		float newY = (float)deltaY + this.getLocation().getY();
		newLocation = new Point((float)newX, (float)newY);
		
		if(newX > xLimit - this.getSize()/2) {
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
	public void steering(int steer) { }

	@Override
	public void draw(Graphics g, Point pCmpeRelPrnt) {
		int xLoc = (int)pCmpeRelPrnt.getX() + (int)this.getLocation().getX() - this.getSize()/2;
		int yLoc = (int)pCmpeRelPrnt.getY() + (int)this.getLocation().getY() - this.getSize()/2;

		g.setColor(this.getColor());
		g.fillArc(xLoc, yLoc, this.getSize(), this.getSize(), 0, 360);	
	}
	
	@Override
	public boolean collidesWith(GameObject otherObject) {
		int antRight = (int)this.getLocation().getX()+this.getSize()/2;
		int antLeft = (int)this.getLocation().getX()-this.getSize()/2;
		int antTop = (int)this.getLocation().getY()+this.getSize()/2;
		int antBottom = (int)this.getLocation().getY()-this.getSize()/2;
		
		int otherRight = (int)otherObject.getLocation().getX()+otherObject.getSize()/2;
		int otherLeft = (int)otherObject.getLocation().getX()-otherObject.getSize()/2;
		int otherTop = (int)otherObject.getLocation().getY()+otherObject.getSize()/2;
		int otherBottom = (int)otherObject.getLocation().getY()-otherObject.getSize()/2;
		
		return !(antRight < otherLeft || antLeft > otherRight) && !(otherTop < antBottom || antTop < otherBottom);
	}

	@Override                    
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		
		// If this object's collision array has it, or the other one does, then return
		if((this.collisionArray.contains(otherObject) || otherObject.getCollisionArray().contains(this))){
			return;
		}
		else {	
			if(otherObject instanceof Spider) {
				collisionArray.add(otherObject);
				gw.spiderCollision();
				if(gw.getSound()) {
					gw.getSpiderSound().play();
				}
			}
			if(otherObject instanceof FoodStation) {
				collisionArray.add(otherObject);
				gw.foodStationCollision((FoodStation)otherObject);
				if(gw.getSound()) {
					gw.getFoodStationSound().play();
				}
			}
			if(otherObject instanceof Flag) {
				collisionArray.add(otherObject);
				Flag flag = (Flag) otherObject;
				boolean collision = gw.flagCollision(flag.getSequenceNumber());
				if(gw.getSound()) {
					if(collision) gw.getRightFlagHitSound().play();
					else gw.getwrongFlagHitSound().play();
				}
			}
		}
	}
}

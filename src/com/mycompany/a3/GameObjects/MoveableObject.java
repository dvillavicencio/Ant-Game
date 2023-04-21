package com.mycompany.a3.GameObjects;

import com.codename1.charts.models.Point;

public abstract class MoveableObject extends GameObject {
	
	/*
	 * Class variables
	 */
	private double heading;
	private double speed;
	
	/*
	 * Constructor
	 */
	public MoveableObject(int size, Point location, int color, double heading, double speed) {
		super(size, location, color);
		this.heading = heading;
		this.speed = speed;
	}
	
	/*
	 * Abstract move method
	 */ 
	public abstract void move(int xLimit, int yLimit, int ellapsedTime);
	
	/*
	 * MoveableObject getter methods
	 */
	public double getHeading() { return this.heading; }
	
	public double getSpeed() { return this.speed; }
	
	/*
	 * MoveableObject setter methods
	 * setHeading does not allow the heading to an angle to be negative
	 * nor the angle to be greater than 360 degrees
	 * setSpeed does not allow the speed to be negative
	 */
	public void setHeading(double newHeading) { 
		if(0 > newHeading) {
			this.heading = ((newHeading) + 360.0) % 360.0;
		}
		else if(newHeading > 360.0) {
			this.heading = (newHeading) % 360.0;
		}
		else {
			this.heading = newHeading;
		}
	}
	
	public void setSpeed(double newSpeed) {
		if(0 > newSpeed) {
			this.speed = 0.0;
		}
		else this.speed = newSpeed;
	}

	/*
	 * MoveableObject toString method
	 */
	public String toString() {
		String parentString = super.toString();
		String childString = " heading=" + Math.round(this.heading*10.0)/10.0 +
				" speed=" + Math.round(this.speed*10.0)/10.0;
		return childString + parentString;
	}	
}

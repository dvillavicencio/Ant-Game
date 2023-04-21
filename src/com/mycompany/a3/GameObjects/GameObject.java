package com.mycompany.a3.GameObjects;

import java.util.ArrayList;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public abstract class GameObject implements IDrawable, ICollider {
	
	/*
	 * Class variables
	 */
	private int size;
	private Point location;
	private int color;
	
	/*
	 * Constructor
	 */
	public GameObject(int size, Point location, int color) {
		this.size = size;
		this.location = location;
		this.color = color;
	}
	
	/*
	 * GameObject getter methods
	 */
	public int getSize() { return this.size; }
	
	public Point getLocation() { return this.location; }
	
	public int getColor() { return this.color; }
	
	public abstract ArrayList<GameObject> getCollisionArray();
	
	/*
	 * GameObject setter methods
	 */
	public void setLocation(Point newLocation) { this.location = newLocation; }
	
	public void setColor(int newColor) { this.color = newColor; }
	
	/*
	 * GameObject toString method
	 */
	public String toString() {
		return " loc=(" + Math.round(location.getX()*10.0)/10.0 +"," + Math.round(location.getY()*10.0)/10.0 + ")" +
				" color=["+ ColorUtil.red(color) 
				+ "," + ColorUtil.green(color) 
				+ "," + ColorUtil.blue(color) + "]" 
				+ " size=" + size;
	}  
}

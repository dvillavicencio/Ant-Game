package com.mycompany.a3.GameObjects;

import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.GameWorld;

public class FoodStation extends FixedObject {

	/*
	 * Class variables
	 */
	private int capacity; 
	private boolean selected;
	private ArrayList<GameObject> collisionArray;
	
	/*
	 * Constructor
	 */
	public FoodStation(int size, Point location, int color) {
		super(size, location, color);
		this.capacity = size/2;
		this.collisionArray = new ArrayList<>();
		this.selected = false;
	}
	
	/*
	 * FoodStation getter methods
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	@Override
	public ArrayList<GameObject> getCollisionArray(){ return this.collisionArray; }
	
	/*
	 * FoodStation setter methods
	 */
	public void setCapacity(int newCapacity) {
		this.capacity = newCapacity;
	}

	/*
	 * FoodStation toString method
	 */
	public String toString() {
		String parentString = super.toString();
		String childString = "Food Station: capacity=" + this.capacity;
		return childString + parentString;
	}

	@Override
	public void draw(Graphics g, Point pCmpeRelPrnt) {
		
		// Location of the food station object
		int xLoc = (int)pCmpeRelPrnt.getX() + (int)this.getLocation().getX() - this.getSize()/2;
		int yLoc = (int)pCmpeRelPrnt.getY() + (int)this.getLocation().getY() - this.getSize()/2;
		
		String strNumber = String.valueOf(this.capacity);
		
		if(this.isSelected()) {
			g.setColor(this.getColor());
			g.drawRect(xLoc, yLoc, this.getSize(), this.getSize());
			g.setColor(ColorUtil.BLACK);
			g.drawString(strNumber, xLoc + this.getSize()/4, yLoc + this.getSize()/4);
			
		}
		else {
			g.setColor(this.getColor());
			g.fillRect(xLoc, yLoc, this.getSize(), this.getSize());
			g.setColor(ColorUtil.BLACK);
			g.drawString(strNumber, xLoc + this.getSize()/4, yLoc + this.getSize()/4);
		}
	}

	@Override
	public boolean collidesWith(GameObject otherObject) {
		int foodStationRight = (int)this.getLocation().getX()+this.getSize()/2;
		int foodStationLeft = (int)this.getLocation().getX()-this.getSize()/2;
		int foodStationTop = (int)this.getLocation().getY()+this.getSize()/2;
		int foodStationBottom = (int)this.getLocation().getY()-this.getSize()/2;
		
		int otherRight = (int)otherObject.getLocation().getX()+otherObject.getSize()/2;
		int otherLeft = (int)otherObject.getLocation().getX()-otherObject.getSize()/2;
		int otherTop = (int)otherObject.getLocation().getY()+otherObject.getSize()/2;
		int otherBottom = (int)otherObject.getLocation().getY()-otherObject.getSize()/2;
		
		return !(foodStationRight < otherLeft || foodStationLeft > otherRight) && !(otherTop < foodStationBottom || foodStationTop < otherBottom);
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
					boolean validFoodStation = gw.foodStationCollision(this);
					if(gw.getSound() && validFoodStation) {
						gw.getFoodStationSound().play();
					}
				}
			}
		}
		else{
			if(this.collisionArray.contains(otherObject)) collisionArray.remove(otherObject);
			return;
		}
	}

	@Override
	public void setSelected(boolean b) {
		this.selected = b;
	}

	@Override
	public boolean isSelected() {
		if(this.selected) return true;
		else return false;
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = (int)pPtrRelPrnt.getX();
		int py = (int)pPtrRelPrnt.getY();
		int xLoc = (int)(pCmpRelPrnt.getX() + this.getLocation().getX() - this.getSize()/2);
		int yLoc = (int)(pCmpRelPrnt.getY() + this.getLocation().getY() - this.getSize()/2);
		return ((px >= xLoc) && (px <= xLoc + this.getSize()) && (py >= yLoc) && (py <= yLoc + this.getSize()));	
	}
}

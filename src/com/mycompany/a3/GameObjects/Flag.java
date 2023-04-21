package com.mycompany.a3.GameObjects;

import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.GameWorld;

public class Flag extends FixedObject{

	/*
	 * Class variables
	 */
	private int sequenceNumber;
	private ArrayList<GameObject> collisionArray;
	private boolean selected;
	
	/*
	 * Constructor
	 */
	public Flag(int size, Point location, int color, int sequenceNumber) {
		super(size, location, color);
		this.sequenceNumber = sequenceNumber;
		this.collisionArray = new ArrayList<>();
	}

	/*
	 * Overrided setColor and setLocation methods with empty body implementation
	 */
	public void setColor(int newColor) {} 
	
	/*
	 * Getter to retrieve the flag's sequence number
	 */
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}
	
	@Override
	public ArrayList<GameObject> getCollisionArray(){ return this.collisionArray; }

	/*
	 * Flag toString method
	 */
	public String toString() {
		String parentString = super.toString();
		String childString = "Flag: seqNum=" + this.sequenceNumber;
		return childString + parentString;
	}
	
	@Override
	public void draw(Graphics g, Point pCmpeRelPrnt) {
		int flagXLocation = (int)this.getLocation().getX() + (int)pCmpeRelPrnt.getX() - this.getSize()/2;
		int flagYLocation = (int)this.getLocation().getY() + (int)pCmpeRelPrnt.getY() - this.getSize()/2;
		g.setColor(this.getColor());
		int[] xPoints = { flagXLocation, flagXLocation + this.getSize(), this.getSize()/2 + (flagXLocation) };
		int[] yPoints = { flagYLocation, flagYLocation, flagYLocation + this.getSize() };
		
		if(this.isSelected()) {
			g.setColor(this.getColor());
			g.drawPolygon(xPoints, yPoints, 3);
			g.setColor(ColorUtil.BLACK);
			g.drawString(String.valueOf(this.sequenceNumber), flagXLocation+this.getSize()/2, flagYLocation+ this.getSize()/2);	
		}
		else {
			g.setColor(this.getColor());
			g.fillPolygon(xPoints, yPoints, 3);
			g.setColor(ColorUtil.WHITE);
			g.drawString(String.valueOf(this.sequenceNumber), flagXLocation+this.getSize()/2, flagYLocation+ this.getSize()/2);	
		}
	}

	@Override
	public boolean collidesWith(GameObject otherObject) {
		int flagRight = (int)this.getLocation().getX()+this.getSize()/2;
		int flagLeft = (int)this.getLocation().getX()-this.getSize()/2;
		int flagTop = (int)this.getLocation().getY()+this.getSize()/2;
		int flagBottom = (int)this.getLocation().getY()-this.getSize()/2;
		
		int otherRight = (int)otherObject.getLocation().getX()+otherObject.getSize()/2;
		int otherLeft = (int)otherObject.getLocation().getX()-otherObject.getSize()/2;
		int otherTop = (int)otherObject.getLocation().getY()+otherObject.getSize()/2;
		int otherBottom = (int)otherObject.getLocation().getY()-otherObject.getSize()/2;
		return !(flagRight < otherLeft || flagLeft > otherRight) && !(otherTop < flagBottom || flagTop < otherBottom);
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
					boolean collision = gw.flagCollision(this.sequenceNumber);
					if(gw.getSound()) {
						if(collision) gw.getRightFlagHitSound().play();
						else gw.getwrongFlagHitSound().play();
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
		if(selected) return true;
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

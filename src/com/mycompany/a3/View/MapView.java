package com.mycompany.a3.View;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.mycompany.a3.GameObjectCollection;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.Command.PauseCommand;
import com.mycompany.a3.GameObjects.FixedObject;
import com.mycompany.a3.GameObjects.GameObject;
import com.mycompany.a3.GameObjects.IIterator;
import com.mycompany.a3.GameObjects.ISelectable;

/*
 * Map View class that is a container and implements codename1's observer interface
 */
public class MapView extends Container implements Observer{
	
	private GameWorld gameWorld;
	private PauseCommand pauseCommand;
	private boolean positionChange;
	private ISelectable selectedObject;

	// Initialize MapView by registerting MapView as an observer in the Observable class
	public MapView(GameWorld gameWorld, PauseCommand pauseCommand) {
		this.gameWorld = gameWorld;
		this.pauseCommand = pauseCommand;
		this.selectedObject = null;
		this.positionChange = false;
		gameWorld.addObserver(this);
	}

	@Override
	public void update(Observable observable, Object data) {
		((GameWorld)observable).map();
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		// Get the relative origin from the center area
		Point p = new Point(this.getX(), this.getY());
		GameObjectCollection collection = gameWorld.getCollection();
		IIterator iterator = collection.getIterator();
		while(iterator.hasNext()) {
			Object o = iterator.getNext();
			((GameObject)o).draw(g, p);
		}	
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		// Get the location of the pointer pressed inside the MapView container
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		
		Point pPtrRelPrnt = new Point(x, y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		if(pauseCommand.isPauseFlag() && positionChange) {			
			if(selectedObject != null) {
				
				// New location for the selected object
				Point newLocation = new Point(x - this.getX(), y - this.getY());
				
				// Set the location for the selected object
				((FixedObject)selectedObject).setLocation(newLocation);
				
				// Paint the object in its new location
				repaint();
				
				// Reset the values of the selected object
				positionChange = false;
				selectedObject.setSelected(false);
				selectedObject = null;
				
				// Repaint the object in its unselected style
				repaint();
			}
		}
		
		if(pauseCommand.isPauseFlag()) {
			// Iterate through the GameWorld to see if any of the objects are selected
			IIterator iterator = gameWorld.getCollection().getIterator();
			while(iterator.hasNext()) {
				GameObject g = (GameObject)iterator.getNext();
				if(g instanceof ISelectable) {
					if(((ISelectable)g).contains(pPtrRelPrnt, pCmpRelPrnt)) {
						((ISelectable)g).setSelected(true);
						selectedObject = (ISelectable)g;
					}
					else {
						((ISelectable)g).setSelected(false);
					}
				}
			}
			repaint();
		}
		
	}
	
	public ISelectable getSelectedObject() {
		return selectedObject;
	}
	
	public boolean isPositionChange() {
		return positionChange;
	}

	public void setPositionChange(boolean positionChange) {
		this.positionChange = positionChange;
	}
	
}

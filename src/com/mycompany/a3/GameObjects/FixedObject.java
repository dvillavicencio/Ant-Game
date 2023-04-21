package com.mycompany.a3.GameObjects;

import com.codename1.charts.models.Point;

public abstract class FixedObject extends GameObject implements ISelectable{

	/*
	 * Constructor
	 */
	public FixedObject(int size, Point location, int color) {
		super(size, location, color);
	}
	
	/*
	 * FixedObject toString method
	 */
	public String toString() {
		return super.toString();
	}

}

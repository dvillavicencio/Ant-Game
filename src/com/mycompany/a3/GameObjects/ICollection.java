package com.mycompany.a3.GameObjects;

public interface ICollection {
	
	/*
	 * Adds an object into the collection
	 */
	public void add(GameObject o);
	
	/*
	 * Return an object of type IIterator
	 */
	public IIterator getIterator();
}

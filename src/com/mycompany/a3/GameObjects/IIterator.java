package com.mycompany.a3.GameObjects;

public interface IIterator {
	/*
	 * Return true if it finds the next instance in the iteration
	 * else returns false
	 */
	public boolean hasNext();
	/*
	 * Returns an Object from the collection
	 */
	public Object getNext();
	/*
	 * Clears all the objects from the collection
	 */
	public void clear();
}

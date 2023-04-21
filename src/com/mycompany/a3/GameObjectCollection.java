package com.mycompany.a3;

import java.util.ArrayList;

import com.mycompany.a3.GameObjects.GameObject;
import com.mycompany.a3.GameObjects.ICollection;
import com.mycompany.a3.GameObjects.IIterator;

public class GameObjectCollection implements ICollection{

	/*
	 * The collection itself will be an ArrayList
	 */
	private ArrayList<GameObject> gameObjects;
	
	public GameObjectCollection() {
		gameObjects = new ArrayList<>();
	}
	
	
	@Override
	public void add(GameObject o) {
		gameObjects.add(o);
	}

	@Override
	public IIterator getIterator() {
		return new GameObjectIterator();
	}
	
	/*
	 * Private instance of the GameObject iterator class
	 * it implements the IIterator interface
	 */
	private class GameObjectIterator implements IIterator {

		/*
		 * Initialize the currentElementIndex as -1 so that if the list is empty
		 * we will know right away
		 */
		private int currentElementIndex;
		
		public GameObjectIterator() {
			currentElementIndex = -1;
		}
		
		@Override
		public boolean hasNext() {
			if(gameObjects.isEmpty()) return false;
			if(currentElementIndex == gameObjects.size()-1) return false;
			return true;
		}

		@Override
		public GameObject getNext() {
			currentElementIndex++;
			return gameObjects.get(currentElementIndex);
		}

		@Override
		public void clear() {
			gameObjects.clear();
		}		
	}
}

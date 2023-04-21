package com.mycompany.a3.GameObjects;

import com.mycompany.a3.GameWorld;

public interface ICollider {
	
	boolean collidesWith(GameObject otherObject);
	
	void handleCollision(GameObject otherObject, GameWorld gw);
}

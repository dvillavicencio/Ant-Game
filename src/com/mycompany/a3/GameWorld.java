package com.mycompany.a3;

import java.util.Observable;
import java.util.Random;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.GameObjects.Ant;
import com.mycompany.a3.GameObjects.Flag;
import com.mycompany.a3.GameObjects.FoodStation;
import com.mycompany.a3.GameObjects.GameObject;
import com.mycompany.a3.GameObjects.IIterator;
import com.mycompany.a3.GameObjects.Spider;


public class GameWorld extends Observable {
	
	/*
	 * State variables: 
	 * - collection is a polymorphic array that holds a collection of GameObjects
	 * - clock keeps track of the amount of ticks in the game
	 * - numberOfLives is the default amount of tries a player has at the start of the game
	 * - acceleration is the default acceleration the ant has (see accelerate())
	 * - ant object is an uninitialized instance of the Ant protagonist that the player controls (ease of access)
	 */
	private GameObjectCollection collection = new GameObjectCollection();
	private Random rand = new Random();
	private boolean sound;
	private boolean generalSound;
	private double clock;
	private int numberOfLives;
	private Ant ant;
	private int height;
	private int width;
	private Sound spiderHit;
	private Sound foodStationHit;
	private Sound rightFlagHit;
	private Sound wrongFlagHit;
	private BGSound backgroundSound;
	
	/*
	 * Initialize numberOfLives to 3, clock to 0 seconds, and sound to false
	 */
	public GameWorld() {
		this.numberOfLives = 3;
		this.clock = 0;
		this.sound = false;
		this.generalSound = false;
		this.width = 0;
		this.height = 0;
	}
	
	/*
	 * Initialize all the game objects according to the written specifications
	 * Number of spiders: at least 2 (randomly placed)
	 * Number of  flags: 4
	 * Number of food stations: 2 (randomly placed)
	 */
	public void init() {

		Flag flag1 = new Flag(150, new Point((float)90/2, (float)90/2), ColorUtil.rgb(0, 0, 255), 1);
		Flag flag2 = new Flag(150, new Point((float)1500, (float)800.0), ColorUtil.rgb(0, 0, 255), 2);
		Flag flag3 = new Flag(150, new Point((float)544.0, (float)200.0), ColorUtil.rgb(0, 0, 255), 3);
		Flag flag4 = new Flag(150, new Point((float)250.0, (float)1000), ColorUtil.rgb(0, 0, 255), 4);

		collection.add(flag1);
		collection.add(flag2);
		collection.add(flag3);
		collection.add(flag4);
		
		// Initialize two Spider objects
		for(int i = 0; i < 2; i++) {
			int spiderSize = 100 + rand.nextInt(75);
			
			// Here we need to make sure that spiders do not spawn off bounds, all the random points need to be inside the MapView dimensions
			Point p = new Point(spiderSize/2 + rand.nextInt(width-spiderSize/2), spiderSize/2 + rand.nextInt(height-spiderSize/2));
			collection.add(new Spider(spiderSize, p, ColorUtil.rgb(0, 0, 0), ((double)rand.nextInt(360)), (double)(100 + rand.nextInt(15))));
		}
		
		
		// Initialize two FoodStation obejcts, with randomly generated positions and randomly generated sizes
		// Size ranges from 50 to 150, Locations are random, and color value is GREEN in RGB
		for(int i = 0; i < 2; i++) {
			int foodStationSize = 50 + rand.nextInt(100);
			Point p = new Point(foodStationSize/2 + rand.nextInt(width-foodStationSize/2), foodStationSize/2+rand.nextInt(height-foodStationSize/2));
			collection.add(new FoodStation(foodStationSize, p, ColorUtil.rgb(0, 255, 0)));
		}
		
		// Initialize an Ant object	
		ant = Ant.getAnt();
		
		// Set the initial location to be half its size so it can show properly in the origin of the MapView
		Point antInitialLocation = new Point(ant.getSize()/2, ant.getSize()/2);
		ant.setLocation(antInitialLocation);
		
		// Add the ant to the collection
		collection.add(ant);
	}
	
	/* 
	 * Command used to simulate the ant accelerating
	 */
	public void accelerate() {	
		
		// How much the ant accelerate depends on how much food and health it has
		int health = ant.getHealthLevel();
		double food = ant.getFoodLevel();
		
		// Calculate the acceleration based on food level and health level
		double acceleration = (ant.getMaxmimumSpeed()/10)*((double)health + food/10.0)/10.0;

		// This piece of code is a modular way to not let the ant's speed get past the maximum speed threshold
		if(ant.getSpeed() + acceleration > ant.getMaxmimumSpeed()) {
			ant.setSpeed(ant.getSpeed() + acceleration - (ant.getSpeed() + acceleration) % ant.getMaxmimumSpeed());
			System.out.println("The ant has reached maximum speed!");
		}
		
		// Else just add speed and acceleration together
		else {
			ant.setSpeed(ant.getSpeed() + acceleration);
			System.out.println("The ant accelerated by " + Math.round(acceleration*10.0)/10.0);
		}	
		setChanged();
		notifyObservers(ant);
	}
	
	/*
	 * Command used to simulate the ant braking
	 */
	public void brake() {
		
		// If the ant's speed is 0, then the ant has reached a full stop
		// else, subtract a fixed amount (10) from the speed
		if(ant.getSpeed() == 0.0) System.out.println("The ant has come to a full stop!");
		else {
			ant.setSpeed(ant.getSpeed() - 10);
			System.out.println("The ant has taken a brake!");
		}
		
		setChanged();
		notifyObservers(ant);
	}
	
	/*
	 * Command used to simulate the ant turning 10 degrees to the left
	 */
	public void left() {
		
		// Subtract 10 degrees from the current heading
		ant.setHeading(ant.getHeading() - 10);
		System.out.println("The ant has turned 5 degrees to the left!");
		
		setChanged();
		notifyObservers(ant);
	}
	
	/*
	 * Command used to simulate the ant turning 10 degrees to the right
	 */
	public void right() {
		
		// Add 10 degrees from the current heading
		ant.setHeading(ant.getHeading() + 10);
		System.out.println("The ant has turned 5 degrees to the right!");
		
		setChanged();
		notifyObservers(ant);
	}
	
	/*
	 * Command used to simulate the ant colliding with a flag
	 */
	public boolean flagCollision(int flagNumber) {
		
		boolean correctFlag = false;
		// If the difference between the lastFlagReached attribute of the ant
		// and flagNumber is a positive 1, it means the ant reached
		// the next flag on the sequence.\
		if(flagNumber-ant.getLastFlagReached() == 1 && ant.getLastFlagReached() < 10) {	
			
			// Set the ants lastFlagReached attribute to the flagNumber passed 
			ant.setLastFlagReached(flagNumber);
			
			// If the ant reached the last flag, which is number 9, display a win message
			if(ant.getLastFlagReached() == 4) {
				setChanged();
				notifyObservers(ant);
				System.out.println("Congratulations you've won!\nTotal time is " + this.clock + "s");
				System.exit(0);
			}
			// Set flagNumber to the ant's lastFlagReached attribute and 
			// display a message that the ant has progressed
			System.out.println("Nice! The ant has collided with flag number " + flagNumber 
					+ "!\nNext flag is flag number " + (ant.getLastFlagReached() + 1));
			correctFlag = true;
		}  
		
		// If the flagNumber is not the next one in the sequence, display a message that the ant
		// collided with a flag without suggesting any progress on the sequence
		else {
			System.out.println("The ant has collided with flag number " + flagNumber
					+ "\nNext flag is flag number " + (ant.getLastFlagReached() + 1));
		}
		setChanged();
		notifyObservers(ant);
		return correctFlag;
	}
	
	/*
	 * Command used to simulate the ant colliding with a food station
	 */
	public boolean foodStationCollision(FoodStation g) {
		
		if(g.getCapacity() == 0) {
			return false;
		}
		else {
			// Increase the ant's food level accordingly
			ant.setFoodLevel(ant.getFoodLevel()+((FoodStation) g).getCapacity());
			
			// Set the FoodStation's capacity to zero
			((FoodStation) g).setCapacity(0);
			
			// Shade the color of the FoodStation 
			((FoodStation) g).setColor(ColorUtil.rgb(200, 255, 200));
			
			// Generate a new FoodStation and add it to the GameObjectCollection
			int foodStationSize = 30 + rand.nextInt(100);
			Point p = new Point(foodStationSize/2 + rand.nextInt(width-foodStationSize/2), foodStationSize/2+rand.nextInt(height-foodStationSize/2));
			collection.add(new FoodStation(30 + rand.nextInt(100), p, ColorUtil.rgb(0, 255, 0)));	
			
			// Throw a message that the Ant has effectively increased its foodLevel,
			System.out.println("The ant has collided with a food station!" +
			"\nThe ant's food level has increased by " + ((FoodStation)g).getSize());
			
			setChanged();
			notifyObservers(ant);
			
			return true;
		}
	}
	
	/*
	 * Command used to simulate the ant colliding with a spider
	 */
	public void spiderCollision() {		
		
		// Reduce the health of the ant by 1 and adjust the color of the ant according to its health level
		ant.setHealthLevel(ant.getHealthLevel()-1);
			
		if(4 > ant.getHealthLevel()) ant.setColor(ColorUtil.rgb(255, 175, 175));
		else if(6 > ant.getHealthLevel()) ant.setColor(ColorUtil.rgb(255, 125, 125));	
		else if(8 > ant.getHealthLevel()) ant.setColor(ColorUtil.rgb(255, 75, 75));
		
		// Reduce the speed of the Ant by a small amount when it collides with a Spider
		if(ant.getSpeed()-3.0 < 0.0) ant.setSpeed(0.0);
		
		// If its less than 0, set it to zero, else, substract normally
		else ant.setSpeed(ant.getSpeed()-3.0);
		
		// In the case where the last life is consumed
		if(1 >= this.numberOfLives && 1 > ant.getHealthLevel()) {
			System.out.println("The ant has no lives remaining. Sadly, you have lost!" +
					"\nYour total time was " + this.clock + "s");
			System.exit(0);
		}

		// If the health of the ant reaches zero and the player still has more than one life
		else if(1 > ant.getHealthLevel() && this.numberOfLives > 1) {
			
			// Subtract from the number of lives
			this.numberOfLives--;
			
			// Reset the attributes of the ant
			ant.resetAnt();
			
			// Clear the game objects collection
			IIterator gameObjectIterator = collection.getIterator();
			gameObjectIterator.clear();
			
			// Reinitialize the game world
			this.init();
			
			// Aesthethic purposes
			if(numberOfLives == 1) System.out.println("The ant has collided with a spider and you lost a life! You have " + this.numberOfLives + " try remaining");
			else System.out.println("The ant has collided with a spider and you lost a life! You have " + this.numberOfLives + " tries remaining");
		}

		// If none of this happens just print out that the ant collided with a spider
		// and the remaining health of the ant
		else{
			System.out.println("Oh no! The ant has collided with a spider! The ant's remaining health is " + ant.getHealthLevel());
		}
		setChanged();
		notifyObservers(ant);
	}
	
	/*
	 * Command used to simulate a clock tick of the game clock
	 */
	public void gameTick(int ellapsedTime) {		
		
		try {
			// Update the clock accordingly and then iterating through the gameObjects array then updating the spider's location 
			clock += ellapsedTime/1000.0;
			
			// Update the foodLevel of the ant with respect to its consumption rate
			ant.setFoodLevel(ant.getFoodLevel() - ant.getFoodConsumptionRate());
			
			// Update the locations of all moveable objects
			IIterator gameObjectIterator1 = collection.getIterator();
			while(gameObjectIterator1.hasNext()) {
				Object g = gameObjectIterator1.getNext();
				if(g instanceof Spider) {
					((Spider) g).setHeading(((Spider)g).getHeading() + rand.nextInt(2));
					((Spider) g).move(this.width, this.height, ellapsedTime); 
				}
				if(g instanceof Ant) {
					ant.move(this.width, this.height, ellapsedTime);
				}
			}
			
			// Collision detection algorithm
			gameObjectIterator1 = collection.getIterator();
			while(gameObjectIterator1.hasNext()) {
				
				// Get the object iterator from the first collection iterator
				GameObject firstObj = (GameObject)gameObjectIterator1.getNext();
				IIterator gameObjectIterator2 = collection.getIterator();
				while(gameObjectIterator2.hasNext()) {
					GameObject otherObj = (GameObject)gameObjectIterator2.getNext();
					if(firstObj!=otherObj) {
						if(firstObj.collidesWith(otherObj)) {
							firstObj.handleCollision(otherObj, this);
						}
						else{
							if(firstObj.getCollisionArray().contains(otherObj)) {
								firstObj.getCollisionArray().remove(otherObj);	
							}
							if(otherObj.getCollisionArray().contains(firstObj)) {
								otherObj.getCollisionArray().remove(firstObj);	
							}
						}
					}
				}
			}
			
			// In the case where the last life is consumed
			if(1 >= this.numberOfLives && 1 > ant.getFoodLevel()) {
				System.out.println("The ant has no lives remaining. Sadly, you have lost!" +
						"\nYour total time was " + this.clock + "s");
				System.exit(0);
			}
			
			// If the FoodLevel of the ant reaches zero, and the player still has lives remaining
			else if(0 >= ant.getFoodLevel() && this.numberOfLives > 1) {
				// Set the speed of the ant to zero
				ant.setSpeed(0);
				
				// Reduce the number of lives
				this.numberOfLives--;
				
				// Reset the attributes of the ant
				ant.resetAnt();
				
				// Clear the game collection
				gameObjectIterator1.clear();
				
				// Reinitialize the Game World
				this.init();
				
				// This is just aesthetic to differentiate between 1 try(s) and more than 1 try
				if(this.numberOfLives == 1) System.out.println("The ant's food level has reached 0 and you lost a life! You have " + this.numberOfLives + " try remaining");
				else System.out.println("The ant's food level has reached 0 and you lost a life! You have " + this.numberOfLives + " tries remaining");
			}
			setChanged();
			notifyObservers(ant);
		}
		catch(NullPointerException e) { System.out.println("There is a null pointer exception here"); }
	}
	
	/*
	 * Loop that iterates through the polymorphic array of GameObjects
	 * and calls their respective toString methods.
	 */
	public void map() {
		
		// Iterate using the iterator provided by the GameObjectCollection class
		IIterator gameObjectsIterator = collection.getIterator();
		while(gameObjectsIterator.hasNext()) {
			Object g = gameObjectsIterator.getNext();
			System.out.println(g.toString());
		}
	}
	
	public void CreateSounds() {
		spiderHit = new Sound("spiderHit.wav");
		rightFlagHit = new Sound("flagHit.wav");
		wrongFlagHit = new Sound("error.wav");
		foodStationHit = new Sound("foodStationHit.wav");
		backgroundSound = new BGSound("gameLoop.wav");
	}
	
	/*
	 * Getters
	 */
	public double getClock() {
		return this.clock;
	}
	
	public boolean getSound() {
		return this.sound;
	}
	
	public int getLives() {
		return this.numberOfLives;
	}
	
	public GameObjectCollection getCollection() {
		return collection;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public Sound getSpiderSound() {
		return this.spiderHit;
	}
	
	public Sound getRightFlagHitSound() {
		return this.rightFlagHit;
	}
	
	public Sound getwrongFlagHitSound() {
		return this.wrongFlagHit;
	}
	
	public Sound getFoodStationSound() {
		return this.foodStationHit;
	}
	
	public BGSound getBackgroundSound() {
		return this.backgroundSound;
	}
	
	public boolean isGeneralSound() {
		return generalSound;
	}
	
	/*
	 * Setters 
	 */
	public void setClock(double newClock) {
		this.clock = newClock;
	}
	
	public void setLives(int newLives) {
		this.numberOfLives = newLives;
	}
	
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}
	
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	
	public void setSound(boolean newSound) {
		this.sound = newSound;
		this.setChanged();
		this.notifyObservers(ant);
	}
	
	public void setGeneralSound(boolean generalSound) {
		this.generalSound = generalSound;
	}
	
}
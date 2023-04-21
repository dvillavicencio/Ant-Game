package com.mycompany.a3.View;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.GameObjects.Ant;

/*
 * Score view class that is a container and implements Codename1's observer interface
 */
public class ScoreView extends Container implements Observer{

	// Initialize all the required labels that will be displayed in the top
	// view of the game's GUI
	private Label clockLabel;
	private Label livesLabel;
	private Label lastFlagReachedLabel;
	private Label healthLevelLabel;
	private Label foodLevelLabel;
	private Label soundLabel;
	
	public ScoreView(Observable myModel) {
		// Initialize the ScoreView, which is also a container, as a FlowLayout container
		super(new FlowLayout(Component.CENTER));
		
		// Register ScoreView as an observer of the Observable class
		myModel.addObserver(this);
		
		// Set all the labels to the default values of the game
		clockLabel = new Label("Time: " + 0);
		livesLabel = new Label("Lives: " + 3);
		lastFlagReachedLabel = new Label("Last Flag Reached: " + 1);
		healthLevelLabel = new Label("Health Level: " + 10);
		foodLevelLabel = new Label("Food Level: " + 100);
		soundLabel = new Label("Sound: OFF");
		
		// Set the style of all labels with color blue 
		clockLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		livesLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		lastFlagReachedLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		healthLevelLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		foodLevelLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		soundLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		
		// Add all the labels to the ScoreView container
		this.add(clockLabel);
		this.add(livesLabel);
		this.add(lastFlagReachedLabel);
		this.add(healthLevelLabel);
		this.add(foodLevelLabel);
		this.add(soundLabel);
	}

	@Override
	public void update(Observable observable, Object data) {
		
		// If for some reason, the Observable parameter is not of type 'GameWorld' then throw an error message
		if(!(observable instanceof GameWorld))
				System.err.println("For some reason, observable is not a Gameworld(?)");
		else {
			
			// Get game state variables from the GameWorld model class
			clockLabel.setText("Time: " + (int)((GameWorld)observable).getClock());
			livesLabel.setText("Lives: " + ((GameWorld)observable).getLives());
			lastFlagReachedLabel.setText("Last Flag Reached: " + ((Ant)data).getLastFlagReached());
			healthLevelLabel.setText("Health Level: " + ((Ant)data).getHealthLevel());
			foodLevelLabel.setText("Food Level: " + (int)((Ant)data).getFoodLevel());
			
			// Check to see if the sound is on or off by using accessors
			if(((GameWorld)observable).getSound()) {
				soundLabel.setText("Sound: ON");
			}
			else {
				soundLabel.setText("Sound: OFF");
			}
		}
	}
}

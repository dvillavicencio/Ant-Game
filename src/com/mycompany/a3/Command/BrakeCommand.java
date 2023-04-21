package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/*
 * Brake command that invokes the Brake()
 * method in the GameWorld class
 */
public class BrakeCommand extends Command {

	// Private instance of GameWorld
	private GameWorld gw; 
	
	// Call super constructor and initialize GameWorld
	public BrakeCommand(GameWorld gw) {
		super("Break");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.brake();
	}
}

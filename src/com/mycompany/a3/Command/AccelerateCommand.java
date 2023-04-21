package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/*
 * Accelerate command that invokes the Accelerate()
 * method in the GameWorld class
 */
public class AccelerateCommand extends Command {

	// Private instance of GameWorld
	private GameWorld gw;
	
	// Call super constructor
	public AccelerateCommand(GameWorld gw) {
		super("Accelerate");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.accelerate();
	}
}

package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/*
 * Turn left command that calls GameWorlds Left() method
 */
public class TurnLeftCommand extends Command {
	
	// Private instance of GameWorld
	private GameWorld gw;

	// Call super constructor and initialize GameWorld
	public TurnLeftCommand(GameWorld gw) {
		super("Left");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.left();
	}
}

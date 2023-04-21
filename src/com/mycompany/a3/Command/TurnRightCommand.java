package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/*
 * Turn right command that calls GameWorld's Right() method
 */
public class TurnRightCommand extends Command {
	
	// Private instance of GameWorld
	private GameWorld gw;

	// Call super constructor and initialize GameWorld
	public TurnRightCommand(GameWorld gw) {
		super("Right");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.right();
	}
}

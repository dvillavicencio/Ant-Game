package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/*
 * Help command that displays the keybinds for all the commands in the Game
 */
public class HelpCommand extends Command {

	// Call super constructor
	public HelpCommand() {
		super("Help");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		String info = "Accelerate - 'a'\nBrake - 'b'\nTurn Left - 'l'\nTurn Right - 'r'";
		Dialog.show("Keyboard Shortcuts", info, "Got it!", null);
	}
}

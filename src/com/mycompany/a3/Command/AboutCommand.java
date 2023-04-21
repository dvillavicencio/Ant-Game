package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/*
 * About command that displays a dialog box
 * and returns details of the project version
 * and author
 */
public class AboutCommand extends Command {
	
	// Call super constructor
	public AboutCommand() {
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		String info = "Author: Daniel E. Villavicencio Mena\n"
				+ "Object-Oriented Programming CSC133\n"
				+ "Version 3.3";
		Dialog.show("About The Game", info, "Ok", null);
	}
}

package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

/*
 * Exit command that displays a dialog box prompting for
 * confirmation, if the user says yes, then the codename1 application exits
 * if not, then it resumes running
 */
public class ExitCommand extends Command {
	
	// Call super constructor
	public ExitCommand() {
		super("Exit");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		Boolean bOk = Dialog.show("Quit", "Are you sure you want to quit?", "Ok", "Cancel");
		if(bOk) {
			Display.getInstance().exitApplication();
		}
	}
}

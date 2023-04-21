package com.mycompany.a3.Command;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/*
 * Sound command to switch the checkbox for sound either ON or OFF
 */
public class SoundCommand extends Command {

	// Private instance of GameWorld
	private GameWorld gw;
	
	// Call super constructor and initialize GameWorld
	public SoundCommand(GameWorld gw) {
		super("Sound");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		try {
			// If the ActionEvent object is of type checkBox, check if its selected
			// if it is, then set sound to true and play the background sound
			if(((CheckBox)evt.getComponent()).isSelected()) {
				gw.setGeneralSound(true);
				gw.setSound(true);
				gw.getBackgroundSound().play();
			}
			// Else, set sound to false
			else {
				gw.setGeneralSound(false);
				gw.setSound(false);
				gw.getBackgroundSound().pause();
			}
		}
		// (Debugging Purposes) If for any reason, the checkbox is null, throw an error message e1
		catch(NullPointerException e1) {
			System.err.println("Checkbox is null!");
		}
	}
}

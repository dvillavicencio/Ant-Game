package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/*
 * Command to simulate the ant colliding with a flag 
 */
public class FlagCollisionCommand extends Command{
	
	// Private instance of GameWorld
	private GameWorld gw;
	
	// Call super constructor and initialize GameWorld
	public FlagCollisionCommand(GameWorld gw) {
		super("Flag Collision");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Instantiate a textfield where the user inputs the numeric value
		// of the flag that the ant collided with, alongside a command for 'ok' option
		Command cOk = new Command("Ok");
		TextField myTextField = new TextField();
		Dialog.show("Enter a flag number:", myTextField, cOk);
		
		// Text response should be a numeric value, here we test if the user gave us 
		// a number or not, if its not a number, then catch the NumberFormatException
		// and display an error dialogue
		String textResponse = myTextField.getText();
		int numberResponse = 0;
		try {
			numberResponse = Integer.parseInt(textResponse);
		}
		catch(NumberFormatException e1) {
			Dialog.show("Invalid Input!", "The value you gave is not a number.\n"
					+ "Please enter a number between 0-1", new Command("Ok"));
			return;
		}
		// If the number is less than 0 (negative) or is greater than 9
		// then display an error window displaying what went wrong
		if(numberResponse <= 0 || numberResponse > 9) {
			Dialog.show("Invalid Input!", "The number you gave was not a number between 1 and 9.\n"
					+ "Please enter a valid number.", new Command("ok"));
		}
		// Else, call GameWorld's flagCollision method and pass in the user input
		else {
			gw.flagCollision(numberResponse);
		}
	}
}

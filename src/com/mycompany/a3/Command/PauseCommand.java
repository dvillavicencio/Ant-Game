package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameWorld;

public class PauseCommand extends Command {

	private boolean gamePaused;
	private GameWorld gameWorld;
	private Game game;
	private UITimer timer;
	
	public PauseCommand(Game game, UITimer timer, GameWorld gameWorld) {
		super("Pause");
		this.game = game;
		this.gameWorld = gameWorld;
		this.timer = timer;
		this.gamePaused = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		this.game.setPausedGameMode();
		
		if(gamePaused == false) {
			timer.cancel();
		}
		else {
			timer.schedule(20, true, game);
		}
		
		gamePaused = !gamePaused;
		
		if (gameWorld.isGeneralSound()){
			gameWorld.setSound(!gamePaused);
		}
		
		this.game.backgroundSound();
		
	}
	
	/*
	 * Getters
	 */
	public boolean isPauseFlag() {
		return this.gamePaused;
	}
}

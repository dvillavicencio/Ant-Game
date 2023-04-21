package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Command.AboutCommand;
import com.mycompany.a3.Command.AccelerateCommand;
import com.mycompany.a3.Command.BrakeCommand;
import com.mycompany.a3.Command.ExitCommand;
import com.mycompany.a3.Command.HelpCommand;
import com.mycompany.a3.Command.PauseCommand;
import com.mycompany.a3.Command.PositionCommand;
import com.mycompany.a3.Command.SoundCommand;
import com.mycompany.a3.Command.TurnLeftCommand;
import com.mycompany.a3.Command.TurnRightCommand;
import com.mycompany.a3.View.MapView;
import com.mycompany.a3.View.ScoreView;

public class Game extends Form implements Runnable{
	
	/*
	 * Private instance of a GameWorld object, MapView, and ScoreView
	 */
	private GameWorld gw;
	private MapView mapView;
	private ScoreView scoreView;
	private UITimer timer;
	private boolean pauseMode;
	
	/*
	 * Private instances of the CustomButton(s)
	 */
	private CustomButton positionButton;
	private CustomButton pauseButton;
	private CustomButton brakeButton;
	private CustomButton accelerateButton;
	private CustomButton rightButton;
	private CustomButton leftButton;
	private CheckBox soundCheckBox;
	
	/*
	 * Private instances of the Command(s)
	 */
	private AccelerateCommand accelerateCommand;
	private BrakeCommand brakeCommand;
	private TurnRightCommand turnRightCommand;
	private TurnLeftCommand turnLeftCommand;
	private SoundCommand soundCommand;
	private AboutCommand aboutCommand;
	private ExitCommand exitCommand;
	private HelpCommand helpCommand;
	private PauseCommand pauseCommand;
	private PositionCommand positionCommand;
	
	/*
	 * Constructor sets up the GUI for the game and 
	 * binds all buttons and side menu items to their corresponding
	 * commands, as well as keybinds in the keyboard.
	 */
	public Game() {
		
		// Initialize the GameWorld object
		gw = new GameWorld();
		
		
		// Initialize the UITimer object
		timer = new UITimer(this);
				
		// Initialize MapView and ScoreView
		pauseCommand = new PauseCommand(this, timer, gw);
		mapView = new MapView(gw, pauseCommand);
		scoreView = new ScoreView(gw);
		
		// Register both views as Obsevers 
		gw.addObserver(mapView);
		gw.addObserver(scoreView);
		
		// Initialize all the Commands
		accelerateCommand = new AccelerateCommand(gw);
		brakeCommand = new BrakeCommand(gw);
		turnRightCommand = new TurnRightCommand(gw);
		turnLeftCommand = new TurnLeftCommand(gw);
		soundCommand = new SoundCommand(gw);
		aboutCommand = new AboutCommand();
		exitCommand = new ExitCommand();
		helpCommand = new HelpCommand();
		positionCommand = new PositionCommand(mapView);
		
		// Set the pause mode of the game to false
		this.pauseMode = pauseCommand.isPauseFlag();
		
		// Set the inital border layout for the main form
		this.setLayout(new BorderLayout());
		
		// Initialize a Toolbar component
		Toolbar myToolbar = new Toolbar();
		soundCheckBox = new CheckBox("Sound");
		
		// Set the myToolbar as the toolbar for this form
		this.setToolbar(myToolbar);
		
		// Set the styles for the sound checkbox component
		soundCheckBox.getAllStyles().setBgTransparency(255);
		soundCheckBox.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		soundCheckBox.setCommand(soundCommand);
		
		// Add all the components and comands to each toolbar section
		myToolbar.addComponentToSideMenu(soundCheckBox);
		myToolbar.addCommandToSideMenu(accelerateCommand);
		myToolbar.addCommandToSideMenu(aboutCommand);
		myToolbar.addCommandToSideMenu(exitCommand);
		myToolbar.addCommandToRightBar(helpCommand);
		
		// North container elements
		this.add(BorderLayout.NORTH, scoreView);
		
		// South container elements
		// Instantiate all southern custom buttons and set their respective commands
		pauseButton = new CustomButton("Pause"); pauseButton.setCommand(pauseCommand);
		positionButton = new CustomButton("Position"); positionButton.setCommand(positionCommand);
		
		// Initialize a container and add all its buttons
		Container southContainer = new Container(new FlowLayout(Component.CENTER));
		southContainer.add(pauseButton);
		positionButton.setEnabled(pauseMode);
		southContainer.add(positionButton);
		southContainer.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
		
		// Add the southern container to the southern region of the GUI
		this.add(BorderLayout.SOUTH, southContainer);
		
		// Center container element
		mapView.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.rgb(255, 0, 0)));
		this.add(BorderLayout.CENTER, mapView);
		
		// East Container elements
		// Instantiate all eastern custom buttons and set their respective commands
		brakeButton = new CustomButton("Break"); brakeButton.setCommand(brakeCommand);
		rightButton = new CustomButton("Right"); rightButton.setCommand(turnRightCommand);
		
		// Initialize a container and add all its buttons
		Container eastContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		eastContainer.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
		eastContainer.add(brakeButton); eastContainer.add(rightButton);
		
		// Add the eastern container to the eastern region of the GUI
		this.add(BorderLayout.EAST, eastContainer);
		
		// West Container elements
		// Instantiate all the western custom buttons and set their respective commands
		accelerateButton = new CustomButton("Accelerate"); accelerateButton.setCommand(accelerateCommand);
		leftButton = new CustomButton("Left"); leftButton.setCommand(turnLeftCommand);
		
		// Initialize a container and add all its buttons
		Container westContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		westContainer.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
		westContainer.add(accelerateButton); westContainer.add(leftButton);
		
		// Add the container to the western region of the GUI
		this.add(BorderLayout.WEST, westContainer);
		
		// Assign all keybindings for all the required commands
		this.addKeyListener('a', accelerateCommand);
		this.addKeyListener('b', brakeCommand);
		this.addKeyListener('r', turnRightCommand);
		this.addKeyListener('l', turnLeftCommand);
		
		// Set the title for the game and show the GUI
		this.setTitle("FlagByFlag Game");
		
		// Show the GUI for the game
		this.show();
		
		// Pass the center container height and width to GameWorld for bound checking when the moveable bjects move
		gw.setHeight(mapView.getHeight());
		gw.setWidth(mapView.getWidth());
		
		// Initialize GameWorld
		gw.init();
		
		timer.schedule(20 , true, this);
		
		// Create the Sound objects in the GameWorld
		gw.CreateSounds();
		
		// Revaliate everything
		revalidate();
	}
	
	public boolean isPauseMode() {
		return this.pauseMode;
	}
	
	public void setPausedGameMode() {
		// PauseCommand has a flag that always alternates between true or false every time you click on the button
		// therefore, we just need to get that flag and set the enabled property of all the buttons equal to the flag
		pauseMode = pauseCommand.isPauseFlag();

		// If pauseMode = false
		if(!pauseMode) {
			this.pauseButton.setText("Play");
			this.removeKeyListener('a', accelerateCommand);
			this.removeKeyListener('b', brakeCommand);
			this.removeKeyListener('r', turnRightCommand);
			this.removeKeyListener('l', turnLeftCommand);
			//this.gw.setSound(pauseMode);
		}
		else {
			this.gw.getBackgroundSound().play();
			this.pauseButton.setText("Pause");
			this.addKeyListener('a', accelerateCommand);
			this.addKeyListener('b', brakeCommand);
			this.addKeyListener('r', turnRightCommand);
			this.addKeyListener('l', turnLeftCommand);
			//this.gw.setSound(pauseMode);
		}
		this.accelerateButton.setEnabled(pauseMode);
		this.brakeButton.setEnabled(pauseMode);
		this.rightButton.setEnabled(pauseMode);
		this.leftButton.setEnabled(pauseMode);
		this.soundCheckBox.setEnabled(pauseMode);
		this.accelerateCommand.setEnabled(pauseMode); // For the accelerate in the side menu
		this.positionButton.setEnabled(!pauseMode);
	}

	public void backgroundSound() {	
		if(gw.getSound()) {
			gw.getBackgroundSound().play();
		}
		else{
			gw.getBackgroundSound().pause();
		}

	}

	@Override
	public void run() {
		gw.gameTick(20);
	}
}

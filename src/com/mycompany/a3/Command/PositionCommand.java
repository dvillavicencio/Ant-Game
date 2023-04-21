package com.mycompany.a3.Command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.View.MapView;

public class PositionCommand extends Command {

	private MapView mapView;
	
	public PositionCommand(MapView mapView) {
		super("Position");
		this.mapView = mapView;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		mapView.setPositionChange(!mapView.isPositionChange());
	}

}

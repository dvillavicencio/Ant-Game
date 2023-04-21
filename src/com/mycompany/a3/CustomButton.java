package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.plaf.Border;

public class CustomButton extends Button {

	public CustomButton(String buttonName) {
		super(buttonName);
		this.getUnselectedStyle().setBgTransparency(255);
		this.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 0, 255));
		this.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		this.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
		this.getUnselectedStyle().setPadding(Component.TOP, 3);
		this.getUnselectedStyle().setPadding(Component.BOTTOM, 3);
		this.getUnselectedStyle().setPadding(Component.LEFT, 5);
		this.getUnselectedStyle().setPadding(Component.RIGHT, 5);
		this.getDisabledStyle().setPadding(Component.TOP, 3);
		this.getDisabledStyle().setPadding(Component.BOTTOM, 3);
		this.getDisabledStyle().setPadding(Component.LEFT, 5);
		this.getDisabledStyle().setPadding(Component.RIGHT, 5);
		this.getDisabledStyle().setBgTransparency(255);
		this.getDisabledStyle().setBgColor(ColorUtil.rgb(75, 75, 255));
		this.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		this.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
	}
}
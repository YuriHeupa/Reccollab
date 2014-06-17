package processing.app.screen.managers;

import processing.app.Jamcollab;
import processing.app.controls.GAlign;
import processing.app.controls.GCScheme;
import processing.app.controls.GOption;
import processing.app.controls.HotSpot;
import processing.app.controls.HotSpot.HSrect;
import processing.core.PImage;

public class GTab extends GOption {
	
	PImage tabImage;
	String text;

	public GTab(int x, int y, int width, String text, int id) {
		super(Jamcollab.app, x, y, width, 30);
		this.text = text;
		setText(text);
		setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
		setLocalColorScheme(GCScheme.SCHEME_15);
		tabImage = Jamcollab.app.loadImage("resources/sprites/tab"+ String.valueOf(id+1) +".png");
		tabImage.resize(width, 30);
		setIcon(tabImage, 2, GAlign.CENTER, GAlign.MIDDLE);
		hotspots = new HotSpot[] { new HSrect(1, 0, 0, width/2, height) // control
		// surface
		};
	}
	
	public void Modify(int position, int size) {
		tabImage.resize(size, 30);
		setIcon(tabImage, 2, GAlign.CENTER, GAlign.MIDDLE);
		this.setPositionAndSize(position, y, size, 30);
		setText(text);

		hotspots = new HotSpot[] { new HSrect(1, 0, 0, size/2, height) // control
																		// surface
		};
		setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
	}


	
}

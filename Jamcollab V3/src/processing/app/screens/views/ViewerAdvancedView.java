package processing.app.screens.views;

import processing.app.Application;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.screen.managers.View;
import processing.app.screen.managers.ViewHandler;

public class ViewerAdvancedView extends View {
	
	
	GButton EmptyButton; 
	GButton AdvancedButton; 
	GButton ResizeButton; 
	GButton PIPButton; 
	GButton VideoButton; 
	


	public ViewerAdvancedView() {
		super();
	}


	@Override
	public void Start() {

		
		VideoButton = new GButton(Application.app, 36, 320, 96, 30);
		VideoButton.setText("Video");
		VideoButton.setTextBold();
		VideoButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		VideoButton.addEventHandler(this, "VideoButtonClick");
		VideoButton.setVisible(false);
		PIPButton = new GButton(Application.app, 144, 320, 96, 30);
		PIPButton.setText("PIP");
		PIPButton.setTextBold();
		PIPButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		PIPButton.addEventHandler(this, "PIPButtonClick");
		PIPButton.setVisible(false);
		ResizeButton = new GButton(Application.app, 252, 320, 96, 30);
		ResizeButton.setText("Redimensionar");
		ResizeButton.setTextBold();
		ResizeButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		ResizeButton.addEventHandler(this, "ResizeButtonClick");
		ResizeButton.setVisible(false);
		AdvancedButton = new GButton(Application.app, 360, 320, 96, 30);
		AdvancedButton.setText("Avançado");
		AdvancedButton.setTextBold();
		AdvancedButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		AdvancedButton.addEventHandler(this, "AdvancedButtonClick");
		AdvancedButton.setVisible(false);
		EmptyButton = new GButton(Application.app, 468, 320, 96, 30);
		EmptyButton.setText("Vazio");
		EmptyButton.setTextBold();
		EmptyButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		EmptyButton.addEventHandler(this, "EmptyButtonClick");
		EmptyButton.setVisible(false);
	}

	@Override
	protected void Awake(boolean state) {
		VideoButton.setVisible(isVisible());
		PIPButton.setVisible(isVisible());
		ResizeButton.setVisible(isVisible());
		AdvancedButton.setVisible(isVisible());
		EmptyButton.setVisible(isVisible());
		
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}


	public void EmptyButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("Empty");
	}

	public void AdvancedButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("Advanced");
	}

	public void ResizeButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("Resize");
	} 

	public void PIPButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("PIP");
	}

	public void VideoButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("Video");
	}
	

}

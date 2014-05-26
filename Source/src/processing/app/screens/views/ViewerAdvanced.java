package processing.app.screens.views;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.screen.managers.ViewHandler;
import processing.event.MouseEvent;

public class ViewerAdvanced extends BaseObject {
	
	
	GButton EmptyButton; 
	GButton AdvancedButton; 
	GButton ResizeButton; 
	GButton PIPButton; 
	GButton VideoButton; 
	


	public ViewerAdvanced() {
		super();
	}


	@Override
	public void Init() {

		
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
	public void SetViewActive(boolean state) {
		VideoButton.setVisible(view.isVisible());
		PIPButton.setVisible(view.isVisible());
		ResizeButton.setVisible(view.isVisible());
		AdvancedButton.setVisible(view.isVisible());
		EmptyButton.setVisible(view.isVisible());
		
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


	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}
	

}

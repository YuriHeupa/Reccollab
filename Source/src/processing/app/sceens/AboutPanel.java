package processing.app.sceens;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.screen.managers.ViewHandler;
import processing.event.MouseEvent;

public class AboutPanel extends BaseObject {


	public AboutPanel () {
		super();
	}
	

	@Override
	public void Init() {
		//AddImage(30, 144, "./resources/sprites/logo.png");
		view.AddImage(0, 0, "./resources/sprites/border.png");
		view.AddLabel(160, 160, 260, 20, "", false, GCScheme.GREEN_SCHEME);
		view.AddButton((Application.app.width/2)-60, 
				Application.app.height-60, 
				120, 30, "Voltar", this,"ButtonBackClicked");
	}


	@Override
	public void SetViewActive(boolean state) {
	}

	public void ButtonBackClicked(GButton source, GEvent event) {
		ViewHandler.Enable("Home");
	}

	@Override
	public void Update() {
	}


	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}


}

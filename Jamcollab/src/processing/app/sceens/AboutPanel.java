package processing.app.sceens;

import processing.app.Application;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.screen.managers.View;
import processing.app.screen.managers.ViewHandler;

public class AboutPanel extends View {


	public AboutPanel () {
		super();
	}
	

	@Override
	public void Start() {
		//AddImage(30, 144, "./resources/sprites/logo.png");
		AddImage(0, 0, "./resources/sprites/border.png");
		AddLabel(160, 160, 260, 20, "", false, GCScheme.GREEN_SCHEME);
		AddButton((Application.app.width/2)-60, 
				Application.app.height-60, 
				120, 30, "Voltar", this,"ButtonBackClicked");
	}


	@Override
	public void Awake(boolean state) {
	}

	public void ButtonBackClicked(GButton source, GEvent event) {
		ViewHandler.Enable("Home");
	}

	@Override
	public void Update() {
	}


}

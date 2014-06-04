package processing.app.screens;

import java.awt.Font;

import processing.app.Jamcollab;
import processing.app.BaseObject;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.screen.managers.ViewHandler;
import processing.event.MouseEvent;

public class AboutPanel extends BaseObject {


	public AboutPanel () {
		super();
	}
	
	private GLabel aboutText;

	@Override
	public void Init() {
		//AddImage(30, 144, "./resources/sprites/logo.png");
		view.AddImage(0, 0, "./resources/sprites/border.png");
		aboutText = view.AddLabel(20, 50, 560, 500, "Criado como projeto de iniciação científica por Yuri Heupa sob orientação de Bruno Campagnolo, a partir de recursos da fundação Araucária. Versão 1.3", false);
		aboutText.setFont(new Font("Verdana", Font.PLAIN, 22));
		view.AddLabel(160, 160, 260, 20, "", false, GCScheme.GREEN_SCHEME);
		view.AddButton((Jamcollab.app.width/2)-60, 
				Jamcollab.app.height-60, 
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

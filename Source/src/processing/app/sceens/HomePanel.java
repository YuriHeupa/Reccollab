package processing.app.sceens;

import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.screen.managers.ViewHandler;
import processing.event.MouseEvent;

public class HomePanel extends BaseObject {


	public HomePanel () {
		super();
	}

	static GLabel User; 

	@Override
	public void Init() {
		view.AddLabel(160, 160, 260, 20, "Você está conectado como:", true, GCScheme.GREEN_SCHEME);
		view.AddButton(160, 240, 120, 30, "Trocar usuário", this, "ButtonChangeUserClicked");
		String user = Utils.AppDAO.getStringData("USERNAME", "");
		User = view.AddLabel(200, 180, 180, 20, 
				user.isEmpty() ? "Não há usuário selecionado" : user, true);
		User.setTextItalic();
		view.AddButton(300, 240, 120, 30, "Confirmar", this, "ButtonConfirmUserClicked");
		view.AddButton(230, 280, 120, 30, "Sobre", this, "ButtonAboutClicked");
		view.addControl(User, 200, 180);
	}

	@Override
	public void SetViewActive(boolean state) {
	}

	public static void SwitchUser(String user) {
		if(user.isEmpty()) {
			User.setText("Não há usuário selecionado");
			User.setTextBold();
			User.setTextItalic();
		} else {
			User.setText(user);
			User.setTextBold();
			User.setTextItalic();
		}
	}

	public void ButtonChangeUserClicked(GButton source, GEvent event) {
		ViewHandler.Enable("Login");
	}

	public void ButtonConfirmUserClicked(GButton source, GEvent event) {
		if(Utils.AppDAO.getStringData("USERNAME", "").isEmpty()) {
			ViewHandler.Enable("Login");
		} else {
			ViewHandler.Disable("Home");
			ViewHandler.EnableAlways("Main");
		}
	}
	public void ButtonAboutClicked(GButton source, GEvent event) {
		ViewHandler.Enable("About");
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

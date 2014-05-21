package processing.app.sceens;

import processing.app.Utils;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.screen.managers.View;
import processing.app.screen.managers.ViewHandler;

public class HomePanel extends View {


	public HomePanel () {
		super();
	}

	GLabel User; 

	@Override
	public void Start() {
		AddLabel(160, 160, 260, 20, "Você está conectado como:", true, GCScheme.GREEN_SCHEME);
		AddButton(160, 240, 120, 30, "Trocar usuário", this, "ButtonChangeUserClicked");
		String user = Utils.AppDAO.getStringData("USERNAME", "");
		User = AddLabel(200, 180, 180, 20, 
				user.isEmpty() ? "Não há usuário selecionado" : user, true);
		User.setTextItalic();
		AddButton(300, 240, 120, 30, "Confirmar", this, "ButtonConfirmUserClicked");
		AddButton(230, 280, 120, 30, "Sobre", this, "ButtonAboutClicked");
		addControl(User, 200, 180);
		setVisible(false);
	}

	@Override
	public void Awake(boolean state) {
	}

	public void SwitchUser(String user) {
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



}

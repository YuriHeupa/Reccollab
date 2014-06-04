package processing.app.screens;

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
	private GButton button1;
	private GButton button2;
	private GButton button3;
	

	@Override
	public void Init() {
		view.AddLabel(160, 160, 260, 20, "Você está conectado como:", true, GCScheme.GREEN_SCHEME);
		//view.AddButton(160, 240, 120, 30, "Trocar usuário", this, "ButtonChangeUserClicked");
		String user = Utils.AppDAO.getStringData("USERNAME", "");
		User = view.AddLabel(200, 180, 180, 20, 
				user.isEmpty() ? "Não há usuário selecionado" : user, true);
		User.setTextItalic();
		//view.AddButton(300, 240, 120, 30, "Confirmar", this, "ButtonConfirmUserClicked");
		//view.AddButton(230, 280, 120, 30, "Sobre", this, "ButtonAboutClicked");
		
		button1 = view.AddButton(230, 240, 120, 30, "Entrar", this, "ButtonConfirmUserClicked");
		button2 = view.AddButton(230, 280, 120, 30, "Trocar Usuário", this, "ButtonChangeUserClicked");
		button3 = view.AddButton(230, 320, 120, 30, "Sobre", this, "ButtonAboutClicked");
		
		view.addControl(User, 200, 180);
	}
	
	private boolean UserSelected() {
		return !Utils.AppDAO.getStringData("USERNAME", "").isEmpty();
	}
	
	@Override
	public void Update() {
		
	}
	
	@Override
	public void SetViewActive(boolean state) {
		if(state) {
			if(UserSelected()) {
				button1.setText("Entrar");
				button1.addEventHandler(this, "ButtonConfirmUserClicked");
				button2.setText("Trocar Usuário");
				button2.addEventHandler(this, "ButtonChangeUserClicked");
				button3.setVisible(true);
				button3.setText("Sobre");
				button3.addEventHandler(this, "ButtonAboutClicked");
			} else {
				button1.setText("Criar usuário");
				button1.addEventHandler(this, "ButtonChangeUserClicked");
				button2.setText("Sobre");
				button2.addEventHandler(this, "ButtonAboutClicked");
				button3.setVisible(false);
			}
		}
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
		if(!UserSelected()) {
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
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}



}

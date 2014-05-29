package processing.app.sceens;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.controls.GTextField;
import processing.app.screen.managers.ViewHandler;
import processing.event.MouseEvent;

public class LoginPanel extends BaseObject {


	GLabel StatusMessage; 
	GLabel LoginLabel; 
	GLabel PasswordLabel; 
	GTextField LoginInput; 
	GTextField PasswordInput; 
	GButton LoginButton; 
	String CurrentPassword;
	boolean needPass;
	
	public LoginPanel() {
		super();
	}

	public void SM(String status) {
		StatusMessage.setText(status);
	}

	@Override
	public void SetViewActive(boolean state) {
		StatusMessage.setVisible(view.isActive());
		LoginLabel.setVisible(view.isActive());
		LoginInput.setVisible(view.isActive());
		LoginButton.setVisible(view.isActive());
		LoginInput.setText("");
		PasswordInput.setVisible(needPass);
		PasswordLabel.setVisible(needPass);
		SM(needPass ? "Informe o seu nome e senha" : "Informe o seu nome");

	}

	@Override
	public void Init() {
		needPass = false;
		StatusMessage = new GLabel(Application.app, 180, 234, 220, 40);
		StatusMessage.setText("");
		StatusMessage.setLocalColorScheme(GCScheme.RED_SCHEME);
		StatusMessage.setOpaque(false);
		StatusMessage.setVisible(false);
		LoginLabel = new GLabel(Application.app, 140, (needPass ? 160 : 200), 60, 20);
		LoginLabel.setText("Login:");
		LoginLabel.setTextBold();
		LoginLabel.setOpaque(false);
		LoginLabel.setVisible(false);
		LoginLabel.setTextAlign(GAlign.LEFT, GAlign.CENTER);
		PasswordLabel = new GLabel(Application.app, 136, 200, 45, 20);
		PasswordLabel.setText("Senha:");
		PasswordLabel.setTextBold();
		PasswordLabel.setOpaque(false);
		PasswordLabel.setVisible(false);
		LoginInput = new GTextField(Application.app, 180, (needPass ? 160 : 200), 260, 20, G4P.SCROLLBARS_NONE);
		LoginInput.setDefaultText("Login...");
		LoginInput.setOpaque(true);
		LoginInput.setVisible(false);
		PasswordInput = new GTextField(Application.app, 180, 200, 260, 20, G4P.SCROLLBARS_NONE);
		PasswordInput.setDefaultText("Senha...");
		PasswordInput.setOpaque(true);
		PasswordInput.addEventHandler(this, "PasswordInputChanged");
		PasswordInput.setVisible(false);
		LoginButton = new GButton(Application.app, 250, 280, 80, 30);
		LoginButton.setText("Entrar");
		LoginButton.setTextBold();
		LoginButton.addEventHandler(this, "LoginButtonClick");
		LoginButton.setVisible(false);
	}

	public void PasswordInputChanged(GTextField source, GEvent event) {
		if(event == GEvent.LOST_FOCUS) {
			if(PasswordInput.getText().equals(" "))
				return;
			CurrentPassword = PasswordInput.getText();
			PasswordInput.setText("");
			for(int i = 0; i < CurrentPassword.length(); i++) {
				PasswordInput.appendText("*");
			}
		} else if(event == GEvent.GETS_FOCUS) {
			PasswordInput.setText("");
		}
	}

	public void LoginButtonClick(GButton source, GEvent event) {
		String user = LoginInput.getText();

		if(user.isEmpty()) {
			SM("Digite seu nome de usuário");
			return;
		} else {

			if(user.length() < 3) {
				SM("Utilize um mínimo de 3 caracteres");
				return;
			}
			if(!Utils.isAlphanumeric(user)) {
				SM("O nome de usuarios deve conter apenas letras e numeros");
				return;
			} else {
				if(needPass) {
					if(CurrentPassword.isEmpty()) {
						SM("Digite sua senha de usuário");
						return;
					} else {
						if(!Utils.DoLogin(user, CurrentPassword)) {
							SM("Nome de usuário e/ou senha inválidos");
							return;
						}
					}
				}
			}
		}

		// if validations is ok change user data
		HomePanel.SwitchUser(user);

		Utils.AppDAO.updateData("USERNAME", user);
		if(needPass)
			Utils.AppDAO.updateData("PASSWORD", CurrentPassword);
		
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

package processing.app.screens;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Lang;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.controls.GTextField;
import processing.event.MouseEvent;

public class Login extends BaseObject {


	GLabel StatusMessage; 
	GLabel LoginLabel; 
	GLabel PasswordLabel; 
	GTextField LoginInput; 
	GTextField PasswordInput; 
	GButton LoginButton; 
	String CurrentPassword;
	boolean needPass;
	
	public Login() {
		super();
	}

	public void SM(String status) {
		StatusMessage.setText(status);
	}

	@Override
	public void SetViewActive(boolean state) {
		StatusMessage.setVisible(state);
		LoginLabel.setVisible(state);
		LoginInput.setVisible(state);
		LoginButton.setVisible(state);
		LoginInput.setText("");
		PasswordInput.setVisible(needPass);
		PasswordLabel.setVisible(needPass);
		SM(needPass ? "Informe o seu nome e senha" : Lang.TYPE_USER);

	}

	@Override
	public void Awake() {
		needPass = false;
		StatusMessage = new GLabel(Jamcollab.app, 180, 234, 220, 40);
		StatusMessage.setText("");
		StatusMessage.setLocalColorScheme(GCScheme.RED_SCHEME);
		StatusMessage.setOpaque(false);
		StatusMessage.setVisible(false);
		LoginLabel = new GLabel(Jamcollab.app, 140, (needPass ? 160 : 200), 60, 20);
		LoginLabel.setText("Login:");
		LoginLabel.setTextBold();
		LoginLabel.setOpaque(false);
		LoginLabel.setVisible(false);
		LoginLabel.setTextAlign(GAlign.LEFT, GAlign.CENTER);
		PasswordLabel = new GLabel(Jamcollab.app, 136, 200, 45, 20);
		PasswordLabel.setText("Senha:");
		PasswordLabel.setTextBold();
		PasswordLabel.setOpaque(false);
		PasswordLabel.setVisible(false);
		LoginInput = new GTextField(Jamcollab.app, 180, (needPass ? 160 : 200), 260, 20, G4P.SCROLLBARS_NONE);
		LoginInput.setDefaultText("Login...");
		LoginInput.setOpaque(true);
		LoginInput.setVisible(false);
		PasswordInput = new GTextField(Jamcollab.app, 180, 200, 260, 20, G4P.SCROLLBARS_NONE);
		PasswordInput.setDefaultText("Senha...");
		PasswordInput.setOpaque(true);
		PasswordInput.addEventHandler(this, "PasswordInputChanged");
		PasswordInput.setVisible(false);
		LoginButton = new GButton(Jamcollab.app, 250, 280, 80, 30);
		LoginButton.setText(Lang.LOGIN);
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
			SM(Lang.TYPE_USER);
			return;
		} else {

			if(user.length() < 3) {
				SM(Lang.NEED_MIN_3_CHARS);
				return;
			}
			if(!Utils.isAlphanumeric(user)) {
				SM(Lang.ONLY_CHAR_AND_NUMBERS);
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
		Home.SwitchUser(user);

		Utils.AppDAO.updateData("USERNAME", user);
		if(needPass)
			Utils.AppDAO.updateData("PASSWORD", CurrentPassword);
		
		EnableView("Home");
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

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		
	}


}

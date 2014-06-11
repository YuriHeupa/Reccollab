package processing.app.screens;

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.Utils;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GDropList;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.event.MouseEvent;

public class Home extends BaseObject {

	static GLabel User; 
	private GButton button1;
	private GButton button2;
	private GButton button3;

	public Home () {
		super();
	}

	public void LanguageChanged(GDropList droplist, GEvent event) {
		Utils.AppDAO.updateData("LANGUAGE", droplist.getSelectedIndex());
		Utils.ShowWarningMessage(Lang.LANGUAGE_CHANGE_TITLE, Lang.LANGUAGE_CHANGE_WARNING);
	}

	@Override
	public void Init() {
		view.AddLabel(160, 160, 260, 20, Lang.YOU_ARE_CONNECTED_AS, true, GCScheme.GREEN_SCHEME);

		view.AddLabel(200, 20, 200, 20, Lang.SELECT_LANGUAGE, GAlign.RIGHT, GAlign.MIDDLE, true);

		GDropList langSelector = view.AddDropList(402, 22, 160, 80, 4, GCScheme.SCHEME_8, Lang.avaiableLangs(), 0, this, "LanguageChanged");
		langSelector.setSelected(Utils.AppDAO.getIntData("LANGUAGE", 0));

		//view.AddButton(160, 240, 120, 30, "Trocar usuário", this, "ButtonChangeUserClicked");
		String user = Utils.AppDAO.getStringData("USERNAME", "");
		User = view.AddLabel(200, 180, 180, 20, 
				user.isEmpty() ? Lang.THERES_NO_USER_SELECTED : user, true);
		User.setTextItalic();
		//view.AddButton(300, 240, 120, 30, "Confirmar", this, "ButtonConfirmUserClicked");
		//view.AddButton(230, 280, 120, 30, "Sobre", this, "ButtonAboutClicked");

		button1 = view.AddButton(230, 240, 120, 30, Lang.LOGIN, this, "ButtonConfirmUserClicked");
		button2 = view.AddButton(230, 280, 120, 30, Lang.CHANGE_USER, this, "ButtonChangeUserClicked");
		button3 = view.AddButton(230, 320, 120, 30, Lang.ABOUT, this, "ButtonAboutClicked");

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
				button1.setText(Lang.LOGIN);
				button1.addEventHandler(this, "ButtonConfirmUserClicked");
				button2.setText(Lang.CHANGE_USER);
				button2.addEventHandler(this, "ButtonChangeUserClicked");
				button3.setVisible(true);
				button3.setText(Lang.ABOUT);
				button3.addEventHandler(this, "ButtonAboutClicked");
			} else {
				button1.setText(Lang.CREATE_USER);
				button1.addEventHandler(this, "ButtonChangeUserClicked");
				button2.setText(Lang.ABOUT);
				button2.addEventHandler(this, "ButtonAboutClicked");
				button3.setVisible(false);
			}
		}
	}

	public static void SwitchUser(String user) {
		if(user.isEmpty()) {
			User.setText(Lang.THERES_NO_USER_SELECTED);
			User.setTextBold();
			User.setTextItalic();
		} else {
			User.setText(user);
			User.setTextBold();
			User.setTextItalic();
		}
	}

	public void ButtonChangeUserClicked(GButton source, GEvent event) {
		EnableView("Login");
	}

	public void ButtonConfirmUserClicked(GButton source, GEvent event) {
		if(!UserSelected()) {
			EnableView("Login");
		} else {
			DisableView("Home");
			EnableView("MainConfig");
		}
	}
	public void ButtonAboutClicked(GButton source, GEvent event) {
		EnableView("About");
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

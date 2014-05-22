package processing.app.screens.views;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GCheckbox;
import processing.app.controls.GEvent;
import processing.app.screen.managers.ViewHandler;
import processing.event.MouseEvent;

public class KeyboardConfigView extends BaseObject {

	static GCheckbox WordsTypedToggle; 
	GCheckbox WordsMinuteToggle; 
	static GCheckbox KeysTypedToggle; 
	GCheckbox KeysMinuteToggle; 
	GButton BackButton; 
	GButton SaveButton; 

	public KeyboardConfigView() {
		super();
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Init() {
		
		view.AddLabel(48, 32, 504, 20, "Estatísticas de Teclado", GAlign.LEFT, GAlign.MIDDLE, true);
		view.AddLabel(64, 88, 192, 16, "Palavras digitadas", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 112, 192, 16, "Palavras por minuto", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 136, 192, 16, "Teclas digitadas", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 160, 192, 16, "Teclas por minuto", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(100, 200, 400, 50, "ATENÇÃO! Para sua segurança desabilite sempre ao digitar senhas", GAlign.CENTER, GAlign.MIDDLE, false);

		WordsTypedToggle = new GCheckbox(Application.app, 256, 88, 24, 20);
		WordsTypedToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		WordsTypedToggle.setOpaque(false);
		WordsTypedToggle.addEventHandler(this, "WordsTypedToggleClicked");
		WordsTypedToggle.setVisible(false);
		WordsTypedToggle.setSelected(Utils.AppDAO.getBooleanData("WORDS_TYPED", false));
		WordsMinuteToggle = new GCheckbox(Application.app, 256, 112, 24, 20);
		WordsMinuteToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		WordsMinuteToggle.setOpaque(false);
		WordsMinuteToggle.addEventHandler(this, "WordsMinuteToggleClicked");
		WordsMinuteToggle.setVisible(false);
		WordsMinuteToggle.setSelected(Utils.AppDAO.getBooleanData("WORDS_PM", false));
		KeysTypedToggle = new GCheckbox(Application.app, 256, 136, 24, 20);
		KeysTypedToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		KeysTypedToggle.setOpaque(false);
		KeysTypedToggle.addEventHandler(this, "KeysTypedToggleClicked");
		KeysTypedToggle.setSelected(Utils.AppDAO.getBooleanData("KEYS_TYPED", false));
		KeysTypedToggle.setVisible(false);
		KeysMinuteToggle = new GCheckbox(Application.app, 256, 160, 24, 20);
		KeysMinuteToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		KeysMinuteToggle.setOpaque(false);
		KeysMinuteToggle.addEventHandler(this, "KeysMinuteToggleClicked");
		KeysMinuteToggle.setSelected(Utils.AppDAO.getBooleanData("KEYS_PM", false));
		KeysMinuteToggle.setVisible(false);
		BackButton = new GButton(Application.app, 480, 32, 80, 24);
		BackButton.setText("Voltar");
		BackButton.setTextBold();
		BackButton.setLocalColorScheme(GCScheme.SCHEME_15);
		BackButton.addEventHandler(this, "BackButtonClicked");
		BackButton.setVisible(false);
		SaveButton = new GButton(Application.app, 390, 32, 80, 24);
		SaveButton.setText("Salvar");
		SaveButton.setTextBold();
		SaveButton.setLocalColorScheme(GCScheme.SCHEME_15);
		SaveButton.addEventHandler(this, "SaveButtonClicked");
		SaveButton.setVisible(false);

	}

	public static boolean IsWordsTyped() {
		return WordsTypedToggle.isSelected();
	}
	public boolean IsWordsMinute() {
		return WordsMinuteToggle.isSelected();
	}
	public static boolean IsKeysTyped() {
		return KeysTypedToggle.isSelected();
	}
	public boolean IsKeysMinute() {
		return KeysMinuteToggle.isSelected();
	}

	@Override
	public void SetViewActive(boolean state) {
		WordsTypedToggle.setVisible(view.isActive());
		WordsMinuteToggle.setVisible(view.isActive());
		KeysTypedToggle.setVisible(view.isActive());
		KeysMinuteToggle.setVisible(view.isActive());
		BackButton.setVisible(view.isActive());
		SaveButton.setVisible(view.isActive());
	}
	
	public void SaveButtonClicked(GButton source, GEvent event) { 
		//Utils.AppDAO.saveSingleData("WORDS_TYPED");
		//Utils.AppDAO.saveSingleData("WORDS_PM");
		//Utils.AppDAO.saveSingleData("KEYS_TYPED");
		//Utils.AppDAO.saveSingleData("KEYS_PM");
		ViewHandler.Enable("MainConfig");
	} 

	public void BackButtonClicked(GButton source, GEvent event) { 
		ViewHandler.Enable("MainConfig");
	} 

	public void WordsTypedToggleClicked(GCheckbox source, GEvent event) { 
		Utils.AppDAO.updateData("WORDS_TYPED", Boolean.toString(source.isSelected()));
	} 

	public void WordsMinuteToggleClicked(GCheckbox source, GEvent event) { 
		Utils.AppDAO.updateData("WORDS_PM", Boolean.toString(source.isSelected()));
	} 

	public void KeysTypedToggleClicked(GCheckbox source, GEvent event) { 
		Utils.AppDAO.updateData("KEYS_TYPED", Boolean.toString(source.isSelected()));
	}

	public void KeysMinuteToggleClicked(GCheckbox source, GEvent event) { 
		Utils.AppDAO.updateData("KEYS_PM", Boolean.toString(source.isSelected()));
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

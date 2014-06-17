package processing.app.screens.configs;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Lang;
import processing.app.Utils;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GCheckbox;
import processing.app.controls.GEvent;
import processing.event.MouseEvent;

public class KeyboardConfig extends BaseObject {

	static GCheckbox WordsTypedToggle; 
	GCheckbox WordsMinuteToggle; 
	static GCheckbox KeysTypedToggle; 
	GCheckbox KeysMinuteToggle; 
	GButton BackButton; 
	GButton SaveButton; 

	public KeyboardConfig() {
		super();
		setParent("Master");
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Awake() {
		int y = 50;

		view.AddLabel(48, 32+y, 504, 20, Lang.KEYBOARD_STATICS, GAlign.LEFT, GAlign.MIDDLE, true);
		view.AddLabel(64, 88+y, 192, 16, Lang.WORDS_TYPED, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 112+y, 192, 16, Lang.WORDS_PM, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 136+y, 192, 16, Lang.KEYS_TYPED, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 160+y, 192, 16, Lang.KEYS_PM, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(100, 200+y, 400, 50, Lang.KEYBOARD_SECURITY_WARNING, GAlign.CENTER, GAlign.MIDDLE, false);

		WordsTypedToggle = new GCheckbox(Jamcollab.app, 256, 88+y, 24, 20);
		WordsTypedToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		WordsTypedToggle.setOpaque(false);
		WordsTypedToggle.setVisible(false);
		WordsTypedToggle.setSelected(Utils.AppDAO.getBooleanData("WORDS_TYPED", false));
		WordsMinuteToggle = new GCheckbox(Jamcollab.app, 256, 112+y, 24, 20);
		WordsMinuteToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		WordsMinuteToggle.setOpaque(false);
		WordsMinuteToggle.setVisible(false);
		WordsMinuteToggle.setSelected(Utils.AppDAO.getBooleanData("WORDS_PM", false));
		KeysTypedToggle = new GCheckbox(Jamcollab.app, 256, 136+y, 24, 20);
		KeysTypedToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		KeysTypedToggle.setOpaque(false);
		KeysTypedToggle.setSelected(Utils.AppDAO.getBooleanData("KEYS_TYPED", false));
		KeysTypedToggle.setVisible(false);
		KeysMinuteToggle = new GCheckbox(Jamcollab.app, 256, 160+y, 24, 20);
		KeysMinuteToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		KeysMinuteToggle.setOpaque(false);
		KeysMinuteToggle.setSelected(Utils.AppDAO.getBooleanData("KEYS_PM", false));
		KeysMinuteToggle.setVisible(false);
		BackButton = new GButton(Jamcollab.app, 480, 22+y, 80, 24);
		BackButton.setText(Lang.BACK);
		BackButton.setTextBold();
		BackButton.setLocalColorScheme(GCScheme.SCHEME_15);
		BackButton.addEventHandler(this, "BackButtonClicked");
		BackButton.setVisible(false);
		SaveButton = new GButton(Jamcollab.app, 390, 22+y, 80, 24);
		SaveButton.setText(Lang.SAVE);
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
		WordsTypedToggle.setVisible(state);
		WordsMinuteToggle.setVisible(state);
		KeysTypedToggle.setVisible(state);
		KeysMinuteToggle.setVisible(state);
		BackButton.setVisible(state);
		SaveButton.setVisible(state);

		WordsTypedToggle.setSelected(Utils.AppDAO.getBooleanData("WORDS_TYPED", false));
		WordsMinuteToggle.setSelected(Utils.AppDAO.getBooleanData("WORDS_PM", false));
		KeysTypedToggle.setSelected(Utils.AppDAO.getBooleanData("KEYS_TYPED", false));
		KeysMinuteToggle.setSelected(Utils.AppDAO.getBooleanData("KEYS_PM", false));
	}

	public void SaveButtonClicked(GButton source, GEvent event) { 
		saveChanges();
		EnableView("MainConfig");
	} 

	public void BackButtonClicked(GButton source, GEvent event) {
		if(WordsTypedToggle.isSelected() != Utils.AppDAO.getBooleanData("WORDS_TYPED", false) ||
				WordsMinuteToggle.isSelected() != Utils.AppDAO.getBooleanData("WORDS_PM", false) ||
				KeysTypedToggle.isSelected() != Utils.AppDAO.getBooleanData("KEYS_TYPED", false) ||
				KeysMinuteToggle.isSelected() != Utils.AppDAO.getBooleanData("KEYS_PM", false)) { 
			if(Utils.ShowQuestion(Lang.CONFIRM_CHANGES_TITLE, Lang.CONFIRM_CHANGES_MESSAGE)) {
				saveChanges();
			}
		}
		EnableView("MainConfig");
	} 

	private void saveChanges() {
		Utils.AppDAO.updateData("WORDS_TYPED", WordsTypedToggle.isSelected());
		Utils.AppDAO.updateData("WORDS_PM", WordsMinuteToggle.isSelected());
		Utils.AppDAO.updateData("KEYS_TYPED", KeysTypedToggle.isSelected());
		Utils.AppDAO.updateData("KEYS_PM", KeysMinuteToggle.isSelected());
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

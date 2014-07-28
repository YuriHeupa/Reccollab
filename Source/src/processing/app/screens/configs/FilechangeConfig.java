package processing.app.screens.configs;

import java.io.File;

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

public class FilechangeConfig extends BaseObject {

	GLabel Title; 
	GLabel Option1Label; 
	GTextField WatchFolderInput; 
	GLabel Option2Label; 
	GButton SearchPathButton1; 
	GTextField LogFolderInput; 
	GLabel Option4Label; 
	GButton SearchPathButton2; 
	GButton BackButton; 
	GButton SaveButton; 
	GTextField CaptureTimeInput;
	

	public FilechangeConfig() {
		super();
		setParent("Master");
	}


	@Override
	public void Awake() {
		int y = 50;
		Title = new GLabel(Jamcollab.app, 48, 32+y, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText(Lang.FILE_CHANGE);
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		Option1Label = new GLabel(Jamcollab.app, 64, 88+y, 192, 16);
		Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option1Label.setText(Lang.WATCH_FOLDER);
		Option1Label.setOpaque(false);
		Option1Label.setVisible(false);
		WatchFolderInput = new GTextField(Jamcollab.app, 256, 88+y, 216, 16, G4P.SCROLLBARS_NONE);
		WatchFolderInput.setOpaque(true);
		WatchFolderInput.setEnabled(false);
		WatchFolderInput.setVisible(false);
		WatchFolderInput.setText(Utils.AppDAO.getStringData("FILECHANGE_PATH", ""));
		

		view.AddLabel(64, 136+y, 192, 16, "Intervalo de backup (Minutos):", GAlign.RIGHT, GAlign.MIDDLE, false);
		
		CaptureTimeInput = view.AddTextField(256, 136+y, 60, 16, G4P.SCROLLBARS_NONE);
		CaptureTimeInput.addEventHandler(this, "CaptureTimeInputChanged");
		CaptureTimeInput.setText(Utils.AppDAO.getStringData("BACKUP_INTERVAL", "60"));

		SearchPathButton1 = new GButton(Jamcollab.app, 480, 88+y, 76, 16);
		SearchPathButton1.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton1.setText(Lang.SEARCH);
		SearchPathButton1.setTextBold();
		SearchPathButton1.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton1.addEventHandler(this, "SearchWatchPathButtonClick");
		SearchPathButton1.setVisible(false);
		
		Option2Label = new GLabel(Jamcollab.app, 64, 112+y, 192, 16);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText(Lang.SAVE_PATH);
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		LogFolderInput = new GTextField(Jamcollab.app, 256, 112+y, 216, 16, G4P.SCROLLBARS_NONE);
		LogFolderInput.setOpaque(true);
		LogFolderInput.setEnabled(false);
		LogFolderInput.setVisible(false);
		LogFolderInput.setText(Utils.AppDAO.getStringData("FILELOGS_PATH", ""));
		SearchPathButton2 = new GButton(Jamcollab.app, 480, 112+y, 76, 16);
		SearchPathButton2.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton2.setText(Lang.SEARCH);
		SearchPathButton2.setTextBold();
		SearchPathButton2.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton2.addEventHandler(this, "SearchLogPathButtonClick");
		SearchPathButton2.setVisible(false);
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

	public void CaptureTimeInputChanged(GTextField source, GEvent event) { 
		if(event == GEvent.LOST_FOCUS) {
			String str = CaptureTimeInput.getText();
			str = str.replaceAll("[^\\d.]", "");
			CaptureTimeInput.setText(str);
		}
	} 
	

	public void SaveButtonClicked(GButton source, GEvent event) { 
		saveChanges();
		EnableView("MainConfig");
	} 

	private void saveChanges() {
		Utils.AppDAO.updateData("FILECHANGE_PATH", WatchFolderInput.getText());

		if(!Utils.AppDAO.getStringData("FILELOGS_PATH", "").equals(LogFolderInput.getText())) {
			if(Utils.MoveFolder(Utils.AppDAO.getStringData("FILELOGS_PATH", ""), LogFolderInput.getText())) {
				Utils.ShowInfoMessage(Lang.FILES_MOVED_TITLE, Lang.FILES_MOVED_MESSAGE+" \n"+
						Utils.AppDAO.getStringData("FILELOGS_PATH", "")+ " "+ Lang.TO + "\n"+
						LogFolderInput.getText());
			}
		}
		Utils.AppDAO.updateData("FILELOGS_PATH", LogFolderInput.getText());
		Utils.AppDAO.updateData("BACKUP_INTERVAL", CaptureTimeInput.getText());
	}


	public void BackButtonClicked(GButton source, GEvent event) { 
		if(!Utils.AppDAO.getStringData("FILECHANGE_PATH", "").equals(WatchFolderInput.getText()) ||
				!Utils.AppDAO.getStringData("FILELOGS_PATH", "").equals(LogFolderInput.getText())) { 
			if(Utils.ShowQuestion(Lang.CONFIRM_CHANGES_TITLE, Lang.CONFIRM_CHANGES_MESSAGE)) {
				saveChanges();
			}
		}
		EnableView("MainConfig");
	} 

	public void SearchWatchPathButtonClick(GButton source, GEvent event) {
		Jamcollab.app.selectFolder(Lang.SELECT_WATCH_FOLDER, "selectWatchFolder", null, this);
	} 

	public void selectWatchFolder(File selection) {
		if(selection == null)
			return;
		WatchFolderInput.setText(selection.getAbsolutePath());
	}

	public void SearchLogPathButtonClick(GButton source, GEvent event) {
		Jamcollab.app.selectFolder(Lang.SELECT_SAVE_FOLDER, "selectLogFolder", null, this);
	} 

	public void selectLogFolder(File selection) {
		if(selection == null)
			return;
		LogFolderInput.setText(selection.getAbsolutePath());
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}


	@Override
	public void SetViewActive(boolean state) {
		Title.setVisible(state);
		Option1Label.setVisible(state);
		Option2Label.setVisible(state);
		WatchFolderInput.setVisible(state);
		LogFolderInput.setVisible(state);
		SearchPathButton1.setVisible(state);
		SearchPathButton2.setVisible(state);
		BackButton.setVisible(state);
		SaveButton.setVisible(state);

		WatchFolderInput.setText(Utils.AppDAO.getStringData("FILECHANGE_PATH", ""));
		LogFolderInput.setText(Utils.AppDAO.getStringData("FILELOGS_PATH", ""));
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

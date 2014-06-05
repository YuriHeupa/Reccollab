package processing.app.screens.configs;

import java.io.File;

import processing.app.BaseObject;
import processing.app.Jamcollab;
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

	public FilechangeConfig() {
		super();
	}


	@Override
	public void Init() {
		Title = new GLabel(Jamcollab.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("Alteração de Arquivos");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		Option1Label = new GLabel(Jamcollab.app, 64, 88, 192, 16);
		Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option1Label.setText("Pasta sendo monitorada:");
		Option1Label.setOpaque(false);
		Option1Label.setVisible(false);
		WatchFolderInput = new GTextField(Jamcollab.app, 256, 88, 216, 16, G4P.SCROLLBARS_NONE);
		WatchFolderInput.setOpaque(true);
		WatchFolderInput.setEnabled(false);
		WatchFolderInput.setVisible(false);
		WatchFolderInput.setText(Utils.AppDAO.getStringData("FILECHANGE_PATH", ""));

		SearchPathButton1 = new GButton(Jamcollab.app, 480, 88, 76, 16);
		SearchPathButton1.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton1.setText("Procurar");
		SearchPathButton1.setTextBold();
		SearchPathButton1.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton1.addEventHandler(this, "SearchWatchPathButtonClick");
		SearchPathButton1.setVisible(false);

		Option2Label = new GLabel(Jamcollab.app, 64, 112, 192, 16);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText("Pasta de Salvamento:");
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		LogFolderInput = new GTextField(Jamcollab.app, 256, 112, 216, 16, G4P.SCROLLBARS_NONE);
		LogFolderInput.setOpaque(true);
		LogFolderInput.setEnabled(false);
		LogFolderInput.setVisible(false);
		LogFolderInput.setText(Utils.AppDAO.getStringData("FILELOGS_PATH", ""));
		SearchPathButton2 = new GButton(Jamcollab.app, 480, 112, 76, 16);
		SearchPathButton2.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton2.setText("Procurar");
		SearchPathButton2.setTextBold();
		SearchPathButton2.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton2.addEventHandler(this, "SearchLogPathButtonClick");
		SearchPathButton2.setVisible(false);


		BackButton = new GButton(Jamcollab.app, 480, 32, 80, 24);
		BackButton.setText("Voltar");
		BackButton.setTextBold();
		BackButton.setLocalColorScheme(GCScheme.SCHEME_15);
		BackButton.addEventHandler(this, "BackButtonClicked");
		BackButton.setVisible(false);
		SaveButton = new GButton(Jamcollab.app, 390, 32, 80, 24);
		SaveButton.setText("Salvar");
		SaveButton.setTextBold();
		SaveButton.setLocalColorScheme(GCScheme.SCHEME_15);
		SaveButton.addEventHandler(this, "SaveButtonClicked");
		SaveButton.setVisible(false);
	}


	public void SaveButtonClicked(GButton source, GEvent event) { 
		saveChanges();
		ViewHandler.Enable("MainConfig");
	} 

	private void saveChanges() {
		Utils.AppDAO.updateData("FILECHANGE_PATH", WatchFolderInput.getText());

		if(!Utils.AppDAO.getStringData("FILELOGS_PATH", "").equals(LogFolderInput.getText())) {
			if(Utils.MoveFolder(Utils.AppDAO.getStringData("FILELOGS_PATH", ""), LogFolderInput.getText())) {
				Utils.ShowInfoMessage("Arquivos movidos", "Os arquivos foram movidos de \n"+
						Utils.AppDAO.getStringData("FILELOGS_PATH", "")+ " para\n"+
						LogFolderInput.getText());
			}
		}
		Utils.AppDAO.updateData("FILELOGS_PATH", LogFolderInput.getText());
	}


	public void BackButtonClicked(GButton source, GEvent event) { 
		if(!Utils.AppDAO.getStringData("FILECHANGE_PATH", "").equals(WatchFolderInput.getText()) ||
				!Utils.AppDAO.getStringData("FILELOGS_PATH", "").equals(LogFolderInput.getText())) { 
			if(Utils.ShowQuestion("Confirmar alterações", "Você tem alterações não salvas, deseja salvar?")) {
				saveChanges();
			}
		}
		ViewHandler.Enable("MainConfig");
	} 

	public void SearchWatchPathButtonClick(GButton source, GEvent event) {
		Jamcollab.app.selectFolder("Selecione uma pasta para ser monitorada:", "selectWatchFolder", null, this);
	} 

	public void selectWatchFolder(File selection) {
		if(selection == null)
			return;
		WatchFolderInput.setText(selection.getAbsolutePath());
	}

	public void SearchLogPathButtonClick(GButton source, GEvent event) {
		Jamcollab.app.selectFolder("Selecione uma pasta para salvar os logs:", "selectLogFolder", null, this);
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
		Title.setVisible(view.isActive());
		Option1Label.setVisible(view.isActive());
		Option2Label.setVisible(view.isActive());
		WatchFolderInput.setVisible(view.isActive());
		LogFolderInput.setVisible(view.isActive());
		SearchPathButton1.setVisible(view.isActive());
		SearchPathButton2.setVisible(view.isActive());
		BackButton.setVisible(view.isActive());
		SaveButton.setVisible(view.isActive());

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


}

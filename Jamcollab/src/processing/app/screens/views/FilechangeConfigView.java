package processing.app.screens.views;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import processing.app.Application;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.controls.GTextField;
import processing.app.screen.managers.View;
import processing.app.screen.managers.ViewHandler;

public class FilechangeConfigView extends View {

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

	public FilechangeConfigView() {
		super();
	}


	@Override
	public void Start() {
		Title = new GLabel(Application.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("Alteração de Arquivos");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		Option1Label = new GLabel(Application.app, 64, 88, 192, 16);
		Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option1Label.setText("Pasta sendo monitorada:");
		Option1Label.setOpaque(false);
		Option1Label.setVisible(false);
		WatchFolderInput = new GTextField(Application.app, 256, 88, 216, 16, G4P.SCROLLBARS_NONE);
		WatchFolderInput.setOpaque(true);
		WatchFolderInput.setEnabled(false);
		WatchFolderInput.setVisible(false);
		WatchFolderInput.setText(Utils.AppDAO.getStringData("WATCH_PATH", ""));

		SearchPathButton1 = new GButton(Application.app, 480, 88, 76, 16);
		SearchPathButton1.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton1.setText("Procurar");
		SearchPathButton1.setTextBold();
		SearchPathButton1.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton1.addEventHandler(this, "SearchWatchPathButtonClick");
		SearchPathButton1.setVisible(false);
		
		Option2Label = new GLabel(Application.app, 64, 112, 192, 16);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText("Pasta de Salvamento:");
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		LogFolderInput = new GTextField(Application.app, 256, 112, 216, 16, G4P.SCROLLBARS_NONE);
		LogFolderInput.setOpaque(true);
		LogFolderInput.setEnabled(false);
		LogFolderInput.setVisible(false);
		LogFolderInput.setText(Utils.AppDAO.getStringData("FILELOGS_PATH", ""));
		SearchPathButton2 = new GButton(Application.app, 480, 112, 76, 16);
		SearchPathButton2.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton2.setText("Procurar");
		SearchPathButton2.setTextBold();
		SearchPathButton2.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton2.addEventHandler(this, "SearchLogPathButtonClick");
		SearchPathButton2.setVisible(false);
		
		
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

	
	public void SaveButtonClicked(GButton source, GEvent event) { 
		//Utils.AppDAO.saveSingleData("WATCH_PATH");
		//Utils.AppDAO.saveSingleData("FILELOGS_PATH");
		ViewHandler.Enable("MainConfig");
	} 

	public void BackButtonClicked(GButton source, GEvent event) { 
		ViewHandler.Enable("MainConfig");
	} 

	public void SearchWatchPathButtonClick(GButton source, GEvent event) {
		Application.app.selectFolder("Selecione uma pasta para ser monitorada:", "selectWatchFolder", null, this);
	} 

	public void selectWatchFolder(File selection) {
		if(selection == null)
			return;
		String path = selection.getAbsolutePath();
		WatchFolderInput.setText(path);
		Utils.AppDAO.updateData("WATCH_PATH", path);
	}
	
	public void SearchLogPathButtonClick(GButton source, GEvent event) {
		Application.app.selectFolder("Selecione uma pasta para salvar os logs:", "selectLogFolder", null, this);
	} 

	public void selectLogFolder(File selection) {
		if(selection == null)
			return;
		String newPath = selection.getAbsolutePath();
		String olderPath = Utils.AppDAO.getStringData("FILELOGS_PATH", "");
		WatchFolderInput.setText(newPath);
		Utils.AppDAO.updateData("FILELOGS_PATH", newPath);
		File olderFile = new File(olderPath);
		Path olderFilePath = olderFile.toPath();		
		File newFile = new File(newPath);
		Path newFilePath = newFile.toPath();
		try {
			Files.move(olderFilePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e1) {
			System.out.println("An error ocurred while moving data from " + 
					olderPath + " to " + newPath);
			e1.printStackTrace();
		}
		
		
		
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void Awake(boolean state) {
		Title.setVisible(isActive());
		Option1Label.setVisible(isActive());
		Option2Label.setVisible(isActive());
		WatchFolderInput.setVisible(isActive());
		LogFolderInput.setVisible(isActive());
		SearchPathButton1.setVisible(isActive());
		SearchPathButton2.setVisible(isActive());
		BackButton.setVisible(isActive());
		SaveButton.setVisible(isActive());
		
	}



}

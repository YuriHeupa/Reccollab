package processing.app.screens.views;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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

public class MicConfig extends BaseObject {

	GLabel Title; 
	GLabel Option1Label; 
	GTextField SavePathInput; 
	GLabel Option2Label; 
	GButton SearchPathButton; 
	GButton BackButton; 
	GButton SaveButton; 

	public MicConfig() {
		super();
	}


	@Override
	public void Init() {
		Title = new GLabel(Application.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("Microfone");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		Option1Label = new GLabel(Application.app, 64, 88, 192, 16);
		Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option1Label.setText("Pasta de Salvamento:");
		Option1Label.setOpaque(false);
		Option1Label.setVisible(false);
		SavePathInput = new GTextField(Application.app, 256, 88, 216, 16, G4P.SCROLLBARS_NONE);
		SavePathInput.setOpaque(true);
		SavePathInput.setEnabled(false);
		SavePathInput.setVisible(false);
		SavePathInput.setText(Utils.AppDAO.getStringData("MIC_PATH", ""));
		Option2Label = new GLabel(Application.app, -400, 16, 192, 16);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText("Pasta de Salvamento:");
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		SearchPathButton = new GButton(Application.app, 480, 88, 76, 16);
		SearchPathButton.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton.setText("Procurar");
		SearchPathButton.setTextBold();
		SearchPathButton.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton.addEventHandler(this, "SearchPathButtonClick");
		SearchPathButton.setVisible(false);
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
		//Utils.AppDAO.saveSingleData("MIC_PATH");
		ViewHandler.Enable("MainConfig");
	} 

	public void BackButtonClicked(GButton source, GEvent event) { 
		ViewHandler.Enable("MainConfig");
	} 

	public void SearchPathButtonClick(GButton source, GEvent event) {
		Application.app.selectFolder("Selecione uma pasta para salvar O audio:", "selectFolder", null, this);
	} 

	public void selectFolder(File selection) {
		if(selection == null)
			return;
		String newPath = selection.getAbsolutePath();
		String olderPath = Utils.AppDAO.getStringData("MIC_PATH", "");
		SavePathInput.setText(newPath);
		Utils.AppDAO.updateData("MIC_PATH", newPath);
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
	public void SetViewActive(boolean state) {
		Title.setVisible(view.isActive());
		Option1Label.setVisible(view.isActive());
		Option2Label.setVisible(view.isActive());
		SavePathInput.setVisible(view.isActive());
		SearchPathButton.setVisible(view.isActive());
		BackButton.setVisible(view.isActive());
		SaveButton.setVisible(view.isActive());
		
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

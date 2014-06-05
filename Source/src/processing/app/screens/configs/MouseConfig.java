package processing.app.screens.configs;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Utils;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GCheckbox;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.screen.managers.ViewHandler;
import processing.event.MouseEvent;

public class MouseConfig extends BaseObject {

	GLabel Title; 
	GLabel Option1Label; 
	static GCheckbox SaveMouseClicksToggle; 
	static GCheckbox SaveMouseMovementsToggle; 
	GLabel Option2Label; 
	GButton BackButton; 
	GButton SaveButton; 

	public MouseConfig() {
		super();
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Init() {
		Title = new GLabel(Jamcollab.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("Rastro do Mouse");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		Option1Label = new GLabel(Jamcollab.app, 64, 88, 192, 16);
		Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option1Label.setText("Capturar movimento do mouse:");
		Option1Label.setOpaque(false);
		Option1Label.setVisible(false);
		SaveMouseMovementsToggle = new GCheckbox(Jamcollab.app, 256, 88, 32, 20);
		SaveMouseMovementsToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SaveMouseMovementsToggle.setOpaque(false);
		SaveMouseMovementsToggle.setVisible(false);
		SaveMouseMovementsToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_MOVEMENTS", false));
		Option2Label = new GLabel(Jamcollab.app, 64, 112, 192, 20);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText("Capturar cliques do mouse");
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		SaveMouseClicksToggle = new GCheckbox(Jamcollab.app, 256, 112, 32, 20);
		SaveMouseClicksToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SaveMouseClicksToggle.setOpaque(false);
		SaveMouseClicksToggle.setVisible(false);
		SaveMouseClicksToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_CLICKS", false));
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

	@Override
	public void SetViewActive(boolean state) {
		Title.setVisible(view.isActive());
		Option1Label.setVisible(view.isActive());
		Option2Label.setVisible(view.isActive());
		SaveMouseMovementsToggle.setVisible(view.isActive());
		SaveMouseClicksToggle.setVisible(view.isActive());
		BackButton.setVisible(view.isActive());
		SaveButton.setVisible(view.isActive());
		SaveMouseMovementsToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_MOVEMENTS", false));
		SaveMouseClicksToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_CLICKS", false));
	}
	
	public void SaveButtonClicked(GButton source, GEvent event) { 
		saveChanges();
		ViewHandler.Enable("MainConfig");
	} 

	public void BackButtonClicked(GButton source, GEvent event) { 
		if(SaveMouseMovementsToggle.isSelected() != Utils.AppDAO.getBooleanData("SAVE_MOUSE_MOVEMENTS", false) ||
				SaveMouseClicksToggle.isSelected() != Utils.AppDAO.getBooleanData("SAVE_MOUSE_CLICKS", false)) { 
			if(Utils.ShowQuestion("Confirmar alterações", "Você tem alterações não salvas, deseja salvar?")) {
				saveChanges();
			}
		}
		ViewHandler.Enable("MainConfig");
	} 
	
	private void saveChanges() {
		Utils.AppDAO.updateData("SAVE_MOUSE_MOVEMENTS", SaveMouseMovementsToggle.isSelected());
		Utils.AppDAO.updateData("SAVE_MOUSE_CLICKS", SaveMouseClicksToggle.isSelected());
	}

	public static boolean IsMouseClicks() {
		return SaveMouseClicksToggle.isSelected();
	}

	public static boolean IsMouseMovement() {
		return SaveMouseMovementsToggle.isSelected();
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

package processing.app.screens.views;

import processing.app.Application;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.controls.GTextArea;
import processing.app.sceens.MainPanel;
import processing.app.screen.managers.View;
import processing.app.screen.managers.ViewHandler;

public class WarningView extends View {

	GLabel Title; 
	GButton BackToConfigButton; 
	GTextArea WarningArea; 
	
	GLabel WarningLabel;
	
	public WarningView() {
		super();
	}


	@Override
	public void Start() {
		Title = new GLabel(Application.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("QUADRO DE AVISOS");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		BackToConfigButton = new GButton(Application.app, 480, 32, 80, 24);
		BackToConfigButton.setText("Voltar");
		BackToConfigButton.setTextBold();
		BackToConfigButton.setLocalColorScheme(GCScheme.SCHEME_15);
		BackToConfigButton.addEventHandler(this, "BackToConfigButtonClicked");
		BackToConfigButton.setVisible(false);
		WarningArea = new GTextArea(Application.app, 24, 80, 552, 300, G4P.SCROLLBARS_VERTICAL_ONLY | G4P.SCROLLBARS_AUTOHIDE);
		WarningArea.setLocalColorScheme(GCScheme.RED_SCHEME);
		WarningArea.setOpaque(true);
		WarningArea.addEventHandler(this, "WarningChanged");
		WarningArea.setVisible(false);
		WarningArea.setTextEditEnabled(false);
		WarningArea.setText("Programa inicializado com sucesso");
		WarningLabel = new GLabel(Application.app, 0, 348, 600, 18);
		WarningLabel.setText("QUADRO DE AVISOS: (Clique para expandir)");
		WarningLabel.setLocalColorScheme(GCScheme.RED_SCHEME);
		WarningLabel.setOpaque(false);
		WarningLabel.setVisible(false);
	}

	public void BackToConfigButtonClicked(GButton source, GEvent event) { 
		ViewHandler.DisableAll();
		ViewHandler.Enable("MainConfig");
		
	} 

	public void WarningChanged(GTextArea source, GEvent event) {
		System.out.println("WarningArea - GTextArea event occured " + System.currentTimeMillis()%10000000 );
	}

	
	public void Warn(String warning) {
		if(warning.isEmpty())
			return;
		MainPanel.WarningButton.setText(warning);
		String tmpStr = WarningArea.getText();
		WarningArea.setText(warning + "\n" + tmpStr);
	}


	@Override
	public void Update() {
		
	}


	@Override
	protected void Awake(boolean state) {
		Title.setVisible(isActive());
		//BackToConfigButton.setVisible(isActive());
		WarningArea.setVisible(isActive());
		WarningLabel.setVisible(isActive());
	}

}

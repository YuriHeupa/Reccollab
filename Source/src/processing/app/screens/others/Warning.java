package processing.app.screens.others;

import processing.app.Jamcollab;
import processing.app.BaseObject;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.controls.GTextArea;
import processing.app.screen.managers.ViewHandler;
import processing.app.screens.MainPanel;
import processing.event.MouseEvent;

public class Warning extends BaseObject {

	GLabel Title; 
	GButton BackToConfigButton; 
	GTextArea WarningArea; 
	
	GLabel WarningLabel;
	
	public Warning() {
		super();
	}


	@Override
	public void Init() {
		Title = new GLabel(Jamcollab.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("QUADRO DE AVISOS");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		BackToConfigButton = new GButton(Jamcollab.app, 480, 32, 80, 24);
		BackToConfigButton.setText("Voltar");
		BackToConfigButton.setTextBold();
		BackToConfigButton.setLocalColorScheme(GCScheme.SCHEME_15);
		BackToConfigButton.addEventHandler(this, "BackToConfigButtonClicked");
		BackToConfigButton.setVisible(false);
		WarningArea = new GTextArea(Jamcollab.app, 24, 80, 552, 316, G4P.SCROLLBARS_VERTICAL_ONLY | G4P.SCROLLBARS_AUTOHIDE);
		WarningArea.setLocalColorScheme(GCScheme.RED_SCHEME);
		WarningArea.setOpaque(true);
		WarningArea.addEventHandler(this, "WarningChanged");
		WarningArea.setVisible(false);
		WarningArea.setTextEditEnabled(false);
		WarningArea.setText("Programa inicializado com sucesso");
		WarningLabel = new GLabel(Jamcollab.app, 0, 348, 600, 18);
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
	public void SetViewActive(boolean state) {
		Title.setVisible(view.isActive());
		BackToConfigButton.setVisible(view.isActive());
		WarningArea.setVisible(view.isActive());
		WarningLabel.setVisible(view.isActive());
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

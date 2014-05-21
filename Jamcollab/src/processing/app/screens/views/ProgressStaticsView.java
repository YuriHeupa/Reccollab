package processing.app.screens.views;

import java.util.List;

import processing.app.Application;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.controls.GTextArea;
import processing.app.screen.managers.View;

public class ProgressStaticsView extends View {


	GLabel Title; 
	GLabel OptionLabel; 
	GLabel SubOptionLabel1; 
	static GTextArea SubOption1Text; 

	public ProgressStaticsView() {
		super();
	}

	@Override
	public void Start() {
		Title = new GLabel(Application.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("ESTATÍSTICAS");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		OptionLabel = new GLabel(Application.app, 64, 88, 72, 16);
		OptionLabel.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		OptionLabel.setText("Programas");
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Application.app, 64, 112, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText("Mais usados:");
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOption1Text = new GTextArea(Application.app, 204, 112, 350, 200, G4P.SCROLLBARS_NONE);
		SubOption1Text.setOpaque(true);
		SubOption1Text.setVisible(false);
		SubOption1Text.setEnabled(false);

	}

	@Override
	public void Update() {}
	
	public static void SetProcess(List<String> processes) {
		if(SubOption1Text == null)
			return;

		SubOption1Text.setText("");
		for(String word : processes) {
			String tmpStr = SubOption1Text.getText();
			SubOption1Text.setText(word + "\n" + tmpStr);
		}
		
	}

	@Override
	protected void Awake(boolean state) {
		Title.setVisible(isActive());  
		OptionLabel.setVisible(isActive()); 
		SubOptionLabel1.setVisible(isActive()); 
		SubOption1Text.setVisible(isActive()); 
		
	}


}

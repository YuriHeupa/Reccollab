package processing.app.screens.views;

import java.util.List;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.controls.GTextArea;
import processing.event.MouseEvent;

public class ProgressStaticsView extends BaseObject {


	GLabel Title; 
	GLabel OptionLabel; 
	GLabel SubOptionLabel1; 
	static GTextArea SubOption1Text; 

	public ProgressStaticsView() {
		super();
	}

	@Override
	public void Init() {
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
	public void SetViewActive(boolean state) {
		Title.setVisible(view.isActive());  
		OptionLabel.setVisible(view.isActive()); 
		SubOptionLabel1.setVisible(view.isActive()); 
		SubOption1Text.setVisible(view.isActive()); 
		
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

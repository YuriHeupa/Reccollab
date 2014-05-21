package processing.app.screens.views;

import processing.app.Application;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.screen.managers.View;
import processing.app.tools.io.IOHandler;

public class MouseStaticsView extends View {


	GLabel Title; 
	GLabel OptionLabel; 
	GLabel SubOptionLabel1; 
	GLabel SubOptionLabel2; 
	static GLabel SubOption1Text; 
	static GLabel SubOption2Text; 

	@Override
	public void Update() {
		SubOption1Text.setText(String.valueOf(IOHandler.getClickCount()));  
		SubOption2Text.setText(String.valueOf(IOHandler.getDistanceMouseTravel())+" px"); 
	}

	public MouseStaticsView() {
		super();
	}


	@Override
	public void Awake(boolean state) {

		Title.setVisible(isActive());  
		OptionLabel.setVisible(isActive()); 
		SubOptionLabel1.setVisible(isActive()); 
		SubOptionLabel2.setVisible(isActive()); 
		SubOption1Text.setVisible(isActive()); 
		SubOption2Text.setVisible(isActive()); 
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
		OptionLabel.setText("Mouse");
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Application.app, 64, 112, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText("Número cliques:");
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOptionLabel2 = new GLabel(Application.app, 64, 128, 136, 16);
		SubOptionLabel2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel2.setText("Distância percorrida:");
		SubOptionLabel2.setOpaque(false);
		SubOptionLabel2.setVisible(false);
		SubOption1Text = new GLabel(Application.app, 208, 112, 80, 16);
		SubOption1Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption1Text.setText("0");
		SubOption1Text.setOpaque(false);
		SubOption1Text.setVisible(false);
		SubOption2Text = new GLabel(Application.app, 208, 128, 80, 16);
		SubOption2Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption2Text.setText("0 px");
		SubOption2Text.setOpaque(false);
		SubOption2Text.setVisible(false);

	}


}

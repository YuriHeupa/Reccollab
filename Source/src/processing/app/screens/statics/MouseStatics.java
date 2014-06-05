package processing.app.screens.statics;

import processing.app.Jamcollab;
import processing.app.BaseObject;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.tools.io.IOHandler;
import processing.event.MouseEvent;

public class MouseStatics extends BaseObject {


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

	public MouseStatics() {
		super();
	}


	@Override
	public void SetViewActive(boolean state) {

		Title.setVisible(view.isActive());  
		OptionLabel.setVisible(view.isActive()); 
		SubOptionLabel1.setVisible(view.isActive()); 
		SubOptionLabel2.setVisible(view.isActive()); 
		SubOption1Text.setVisible(view.isActive()); 
		SubOption2Text.setVisible(view.isActive()); 
	}

	@Override
	public void Init() {
		Title = new GLabel(Jamcollab.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("ESTATÍSTICAS");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		OptionLabel = new GLabel(Jamcollab.app, 64, 88, 72, 16);
		OptionLabel.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		OptionLabel.setText("Mouse");
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Jamcollab.app, 64, 112, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText("Número cliques:");
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOptionLabel2 = new GLabel(Jamcollab.app, 64, 128, 136, 16);
		SubOptionLabel2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel2.setText("Distância percorrida:");
		SubOptionLabel2.setOpaque(false);
		SubOptionLabel2.setVisible(false);
		SubOption1Text = new GLabel(Jamcollab.app, 208, 112, 80, 16);
		SubOption1Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption1Text.setText("0");
		SubOption1Text.setOpaque(false);
		SubOption1Text.setVisible(false);
		SubOption2Text = new GLabel(Jamcollab.app, 208, 128, 80, 16);
		SubOption2Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption2Text.setText("0 px");
		SubOption2Text.setOpaque(false);
		SubOption2Text.setVisible(false);

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

package processing.app.screens.statics;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Lang;
import processing.app.Utils;
import processing.app.Vector2D;
import processing.app.controls.GAlign;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.screen.managers.TooltipHandler;
import processing.app.tools.webcam.WebcamHandler;
import processing.core.PConstants;
import processing.event.MouseEvent;

public class WebcamStatics extends BaseObject {



	GLabel OptionLabel; 
	GLabel SubOptionLabel1; 
	GLabel SubOptionLabel2; 
	GLabel SubOptionLabel3; 
	GLabel SubOptionLabel4; 
	static GLabel SubOption1Text; 
	static GLabel SubOption2Text; 
	static GLabel SubOption3Text; 

	@Override
	public void Update() {
		SubOption1Text.setText(String.valueOf(WebcamHandler.getImageTakenCount()));
		SubOption2Text.setText(WebcamHandler.getImageTakenResolution());  
		SubOption3Text.setText(Utils.AppDAO.getStringData("WEBCAM_PATH", ""));
		SubOption3Text.setTextUnderlined();

		if(SubOption3Text.isTooltip()) {
			TooltipHandler.Show(new Vector2D(Jamcollab.app.mouseX, Jamcollab.app.mouseY), Lang.CLICK_TO_GO);
		}
	}

	public WebcamStatics() {
		super();
		setParent("Master");
	}


	@Override
	public void SetViewActive(boolean state) {

		OptionLabel.setVisible(state); 
		SubOptionLabel1.setVisible(state); 
		SubOptionLabel2.setVisible(state); 
		SubOptionLabel3.setVisible(state); 
		SubOption1Text.setVisible(state); 
		SubOption2Text.setVisible(state); 
		SubOption3Text.setVisible(state); 
	}


	@Override
	public void Awake() {
		OptionLabel = new GLabel(Jamcollab.app, 64, 88, 72, 16);
		OptionLabel.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		OptionLabel.setText("Webcam");
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Jamcollab.app, 64, 112, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText(Lang.IMAGES_NUMBER);
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOptionLabel2 = new GLabel(Jamcollab.app, 64, 128, 136, 16);
		SubOptionLabel2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel2.setText(Lang.RESOLUTION);
		SubOptionLabel2.setOpaque(false);
		SubOptionLabel2.setVisible(false);
		SubOptionLabel3 = new GLabel(Jamcollab.app, 64, 144, 136, 16);
		SubOptionLabel3.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel3.setText(Lang.FOLDER);
		SubOptionLabel3.setOpaque(false);
		SubOptionLabel3.setVisible(false);
		SubOptionLabel4 = new GLabel(Jamcollab.app, 64, 160, 136, 16);
		SubOptionLabel4.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel4.setText(Lang.LAST_IMAGE);
		SubOptionLabel4.setOpaque(false);
		SubOptionLabel4.setVisible(false);
		SubOption1Text = new GLabel(Jamcollab.app, 208, 112, 80, 16);
		SubOption1Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption1Text.setText("0");
		SubOption1Text.setOpaque(false);
		SubOption1Text.setVisible(false);
		SubOption2Text = new GLabel(Jamcollab.app, 208, 128, 300, 16);
		SubOption2Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption2Text.setText(Lang.NO_IMAGE_CAPTURED);
		SubOption2Text.setOpaque(false);
		SubOption2Text.setVisible(false);
		SubOption3Text = new GLabel(Jamcollab.app, 208, 144, 350, 16);
		SubOption3Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption3Text.setText("null");
		SubOption3Text.setOpaque(false);
		SubOption3Text.setVisible(false);
		SubOption3Text.addEventHandler(this, "LinkClicked");
		SubOption3Text.setCursorOver(PConstants.HAND);

	}
	public void LinkClicked(GLabel source, GEvent event) {
		if(!Utils.OpenFile(SubOption3Text.getText()))
			Utils.OpenFile(Utils.getDefaultSavePath());
	}

	@Override
	public void Mouse(MouseEvent e) {
		
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		
	}

}

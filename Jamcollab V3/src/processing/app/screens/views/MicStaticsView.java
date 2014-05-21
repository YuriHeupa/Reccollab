package processing.app.screens.views;

import processing.app.Application;
import processing.app.Utils;
import processing.app.Vector2D;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.screen.managers.View;
import processing.app.screen.managers.TooltipHandler;
import processing.app.tools.audio.AudioHandler;
import processing.core.PConstants;

public class MicStaticsView extends View {


	GLabel Title; 
	GLabel OptionLabel; 
	GLabel SubOptionLabel1; 
	GLabel SubOptionLabel2; 
	static GLabel SubOption1Text; 
	static GLabel SubOption2Text; 


	@Override
	public void Update() {
		SubOption1Text.setText(AudioHandler.getTimeRecording());  
		SubOption2Text.setText(Utils.AppDAO.getStringData("MIC_PATH", ""));
		SubOption2Text.setTextUnderlined();
		
		

		if(Application.app.mousePressed) {
			if(Application.app.mouseButton == PConstants.LEFT) 
				if(SubOption2Text.isHover() && SubOption2Text.isVisible())
					if(!Utils.OpenFile(SubOption2Text.getText()))
						Utils.OpenFile(Utils.getDefaultSavePath());
		}

		if(SubOption2Text.isTooltip()) {
			TooltipHandler.Show(new Vector2D(Application.app.mouseX, Application.app.mouseY), "Clique para ir para a pasta");
		}
	}

	public MicStaticsView() {
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
		OptionLabel.setText("Microfone");
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Application.app, 64, 112, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText("Tempo gravação");
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOptionLabel2 = new GLabel(Application.app, 64, 128, 136, 16);
		SubOptionLabel2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel2.setText("Pasta:");
		SubOptionLabel2.setOpaque(false);
		SubOptionLabel2.setVisible(false);
		SubOption1Text = new GLabel(Application.app, 208, 112, 80, 16);
		SubOption1Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption1Text.setText("00:00");
		SubOption1Text.setOpaque(false);
		SubOption1Text.setVisible(false);
		SubOption2Text = new GLabel(Application.app, 208, 128, 350, 16);
		SubOption2Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption2Text.setText("null");
		SubOption2Text.setOpaque(false);
		SubOption2Text.setVisible(false);
		SubOption2Text.setCursorOver(PConstants.HAND);

	}

	@Override
	protected void Awake(boolean state) {
		Title.setVisible(isActive());  
		OptionLabel.setVisible(isActive()); 
		SubOptionLabel1.setVisible(isActive()); 
		SubOptionLabel2.setVisible(isActive()); 
		SubOption1Text.setVisible(isActive()); 
		SubOption2Text.setVisible(isActive()); 
		
	}


}

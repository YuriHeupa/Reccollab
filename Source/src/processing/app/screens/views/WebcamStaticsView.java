package processing.app.screens.views;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.Vector2D;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.screen.managers.TooltipHandler;
import processing.core.PConstants;
import processing.event.MouseEvent;

public class WebcamStaticsView extends BaseObject {



	GLabel Title; 
	GLabel OptionLabel; 
	GLabel SubOptionLabel1; 
	GLabel SubOptionLabel2; 
	GLabel SubOptionLabel3; 
	GLabel SubOptionLabel4; 
	static GLabel SubOption1Text; 
	static GLabel SubOption2Text; 
	static GLabel SubOption3Text; 
	int tickClicked = 0;

	@Override
	public void Update() {
		//SubOption1Text.setText(String.valueOf(WebCamController.getInstance().getImageTakenCount()));
		//SubOption2Text.setText(WebCamController.getInstance().getImageTakenResolution());  
		SubOption3Text.setText(Utils.AppDAO.getStringData("WEBCAM_PATH", ""));
		SubOption3Text.setTextUnderlined();

		if(SubOption3Text.isTooltip()) {
			TooltipHandler.Show(new Vector2D(Application.app.mouseX, Application.app.mouseY), "Clique para ir para a pasta");
		}
	}

	public WebcamStaticsView() {
		super();
	}


	@Override
	public void SetViewActive(boolean state) {

		Title.setVisible(view.isActive());  
		OptionLabel.setVisible(view.isActive()); 
		SubOptionLabel1.setVisible(view.isActive()); 
		SubOptionLabel2.setVisible(view.isActive()); 
		SubOptionLabel3.setVisible(view.isActive()); 
		SubOption1Text.setVisible(view.isActive()); 
		SubOption2Text.setVisible(view.isActive()); 
		SubOption3Text.setVisible(view.isActive()); 
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
		OptionLabel.setText("Webcam");
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Application.app, 64, 112, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText("Número imagens:");
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOptionLabel2 = new GLabel(Application.app, 64, 128, 136, 16);
		SubOptionLabel2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel2.setText("Resolução:");
		SubOptionLabel2.setOpaque(false);
		SubOptionLabel2.setVisible(false);
		SubOptionLabel3 = new GLabel(Application.app, 64, 144, 136, 16);
		SubOptionLabel3.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel3.setText("Pasta:");
		SubOptionLabel3.setOpaque(false);
		SubOptionLabel3.setVisible(false);
		SubOptionLabel4 = new GLabel(Application.app, 64, 160, 136, 16);
		SubOptionLabel4.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel4.setText("Última imagem:");
		SubOptionLabel4.setOpaque(false);
		SubOptionLabel4.setVisible(false);
		SubOption1Text = new GLabel(Application.app, 208, 112, 80, 16);
		SubOption1Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption1Text.setText("0");
		SubOption1Text.setOpaque(false);
		SubOption1Text.setVisible(false);
		SubOption2Text = new GLabel(Application.app, 208, 128, 80, 16);
		SubOption2Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption2Text.setText("?x?");
		SubOption2Text.setOpaque(false);
		SubOption2Text.setVisible(false);
		SubOption3Text = new GLabel(Application.app, 208, 144, 350, 16);
		SubOption3Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption3Text.setText("null");
		SubOption3Text.setOpaque(false);
		SubOption3Text.setVisible(false);
		SubOption3Text.setCursorOver(PConstants.HAND);

	}

	@Override
	public void Mouse(MouseEvent e) {
		if(e.getAction() == MouseEvent.RELEASE) {
			if(Application.app.mouseButton == PConstants.LEFT) 
				tickClicked++;
				if(SubOption3Text.isHover() && SubOption3Text.isVisible() &&
						tickClicked % 2 == 0) {
					if(!Utils.OpenFile(SubOption3Text.getText()))
						Utils.OpenFile(Utils.getDefaultSavePath());
				}
		}
		
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}

}

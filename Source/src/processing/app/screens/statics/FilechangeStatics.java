package processing.app.screens.statics;

import processing.app.Jamcollab;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.Vector2D;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.screen.managers.TooltipHandler;
import processing.app.tools.filechange.FileChangeHandler;
import processing.core.PConstants;
import processing.event.MouseEvent;

public class FilechangeStatics extends BaseObject {


	GLabel Title; 
	GLabel OptionLabel; 
	GLabel SubOptionLabel1; 
	GLabel SubOptionLabel2; 
	GLabel SubOptionLabel3; 
	GLabel SubOptionLabel4; 
	GLabel SubOptionLabel5; 
	public static GLabel CreatedText; 
	public static GLabel ModifiedText; 
	public static GLabel DeletedText; 
	public static GLabel DataSizeText; 
	public static GLabel FolderText; 
	int tickClicked = 0;

	@Override
	public void Update() {
		DataSizeText.setText(FileChangeHandler.getDataSize());  
		FolderText.setText(Utils.AppDAO.getStringData("FILECHANGE_PATH", "")); 
		FolderText.setTextUnderlined();

		if(FolderText.isTooltip()) {
			TooltipHandler.Show(new Vector2D(Jamcollab.app.mouseX, Jamcollab.app.mouseY), "Clique para ir para a pasta");
		}
	}

	public FilechangeStatics() {
		super();
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
		OptionLabel.setText("Arquivos");
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Jamcollab.app, 64, 112, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText("Criados:");
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOptionLabel2 = new GLabel(Jamcollab.app, 64, 128, 136, 16);
		SubOptionLabel2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel2.setText("Modificados:");
		SubOptionLabel2.setOpaque(false);
		SubOptionLabel2.setVisible(false);
		SubOptionLabel3 = new GLabel(Jamcollab.app, 64, 144, 136, 16);
		SubOptionLabel3.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel3.setText("Excluídos:");
		SubOptionLabel3.setOpaque(false);
		SubOptionLabel3.setVisible(false);
		SubOptionLabel4 = new GLabel(Jamcollab.app, 64, 160, 136, 16);
		SubOptionLabel4.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel4.setText("Quantidade dados:");
		SubOptionLabel4.setOpaque(false);
		SubOptionLabel4.setVisible(false);
		SubOptionLabel5 = new GLabel(Jamcollab.app, 64, 176, 136, 16);
		SubOptionLabel5.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel5.setText("Pasta:");
		SubOptionLabel5.setOpaque(false);
		SubOptionLabel5.setVisible(false);
		CreatedText = new GLabel(Jamcollab.app, 208, 112, 80, 16);
		CreatedText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		CreatedText.setText("0");
		CreatedText.setOpaque(false);
		CreatedText.setVisible(false);
		ModifiedText = new GLabel(Jamcollab.app, 208, 128, 80, 16);
		ModifiedText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		ModifiedText.setText("0");
		ModifiedText.setOpaque(false);
		ModifiedText.setVisible(false);
		DeletedText = new GLabel(Jamcollab.app, 208, 144, 80, 16);
		DeletedText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		DeletedText.setText("0");
		DeletedText.setOpaque(false);
		DeletedText.setVisible(false);
		DataSizeText = new GLabel(Jamcollab.app, 208, 160, 80, 16);
		DataSizeText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		DataSizeText.setText("0 MB");
		DataSizeText.setOpaque(false);
		DataSizeText.setVisible(false);
		FolderText = new GLabel(Jamcollab.app, 208, 176, 350, 16);
		FolderText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		FolderText.setText("null");
		FolderText.setOpaque(false);
		FolderText.setVisible(false);
		FolderText.setCursorOver(PConstants.HAND);
	}

	@Override
	public void SetViewActive(boolean state) {

		Title.setVisible(view.isActive());  
		OptionLabel.setVisible(view.isActive()); 
		SubOptionLabel1.setVisible(view.isActive()); 
		SubOptionLabel2.setVisible(view.isActive()); 
		SubOptionLabel3.setVisible(view.isActive()); 
		SubOptionLabel4.setVisible(view.isActive()); 
		SubOptionLabel5.setVisible(view.isActive()); 
		CreatedText.setVisible(view.isActive()); 
		ModifiedText.setVisible(view.isActive()); 
		DataSizeText.setVisible(view.isActive()); 
		DeletedText.setVisible(view.isActive()); 
		FolderText.setVisible(view.isActive()); 
		
	}

	@Override
	public void Mouse(MouseEvent e) {
		if(e.getAction() ==MouseEvent.RELEASE) {
			if(Jamcollab.app.mouseButton == PConstants.LEFT) 
				tickClicked++;
				if(FolderText.isHover() && FolderText.isVisible() &&
						tickClicked % 2 == 0) {
					if(!Utils.OpenFile(FolderText.getText()))
						Utils.OpenFile(Utils.getDefaultSavePath());
				}
		}
		
		
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}


}

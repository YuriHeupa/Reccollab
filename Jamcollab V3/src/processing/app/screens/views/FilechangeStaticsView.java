package processing.app.screens.views;

import processing.app.Application;
import processing.app.Utils;
import processing.app.Vector2D;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.screen.managers.View;
import processing.app.screen.managers.Screen;
import processing.app.screen.managers.TooltipHandler;
import processing.app.tools.filechange.FileChangeHandler;
import processing.core.PConstants;

public class FilechangeStaticsView extends View {


	GLabel Title; 
	GLabel OptionLabel; 
	GLabel SubOptionLabel1; 
	GLabel SubOptionLabel2; 
	GLabel SubOptionLabel3; 
	GLabel SubOptionLabel4; 
	GLabel SubOptionLabel5; 
	static GLabel CreatedText; 
	static GLabel InsertedText; 
	static GLabel DeletedText; 
	static GLabel DataSizeText; 
	static GLabel FolderText; 

	@Override
	public void Update() {
		DataSizeText.setText(FileChangeHandler.getDataSize());  
		FolderText.setText(Utils.AppDAO.getStringData("FILECHANGE_PATH", "")); 
		FolderText.setTextUnderlined();

		if(Application.app.mousePressed) {
			if(Application.app.mouseButton == PConstants.LEFT) 
				if(FolderText.isHover() && FolderText.isVisible())
					if(!Utils.OpenFile(FolderText.getText()))
						Utils.OpenFile(Utils.getDefaultSavePath());
		}

		if(FolderText.isTooltip()) {
			TooltipHandler.Show(new Vector2D(Application.app.mouseX, Application.app.mouseY), "Clique para ir para a pasta");
		}
	}

	public FilechangeStaticsView() {
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
		OptionLabel.setText("Arquivos");
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Application.app, 64, 112, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText("Criados:");
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOptionLabel2 = new GLabel(Application.app, 64, 128, 136, 16);
		SubOptionLabel2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel2.setText("Inseridos:");
		SubOptionLabel2.setOpaque(false);
		SubOptionLabel2.setVisible(false);
		SubOptionLabel3 = new GLabel(Application.app, 64, 144, 136, 16);
		SubOptionLabel3.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel3.setText("Excluídos:");
		SubOptionLabel3.setOpaque(false);
		SubOptionLabel3.setVisible(false);
		SubOptionLabel4 = new GLabel(Application.app, 64, 160, 136, 16);
		SubOptionLabel4.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel4.setText("Quantidade dados:");
		SubOptionLabel4.setOpaque(false);
		SubOptionLabel4.setVisible(false);
		SubOptionLabel5 = new GLabel(Application.app, 64, 176, 136, 16);
		SubOptionLabel5.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel5.setText("Pasta:");
		SubOptionLabel5.setOpaque(false);
		SubOptionLabel5.setVisible(false);
		CreatedText = new GLabel(Application.app, 208, 112, 80, 16);
		CreatedText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		CreatedText.setText("0");
		CreatedText.setOpaque(false);
		CreatedText.setVisible(false);
		InsertedText = new GLabel(Application.app, 208, 128, 80, 16);
		InsertedText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		InsertedText.setText("0");
		InsertedText.setOpaque(false);
		InsertedText.setVisible(false);
		DeletedText = new GLabel(Application.app, 208, 144, 80, 16);
		DeletedText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		DeletedText.setText("0");
		DeletedText.setOpaque(false);
		DeletedText.setVisible(false);
		DataSizeText = new GLabel(Application.app, 208, 160, 80, 16);
		DataSizeText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		DataSizeText.setText("0 MB");
		DataSizeText.setOpaque(false);
		DataSizeText.setVisible(false);
		FolderText = new GLabel(Application.app, 208, 176, 350, 16);
		FolderText.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		FolderText.setText("null");
		FolderText.setOpaque(false);
		FolderText.setVisible(false);
		FolderText.setCursorOver(PConstants.HAND);
	}

	@Override
	protected void Awake(boolean state) {

		Title.setVisible(isActive());  
		OptionLabel.setVisible(isActive()); 
		SubOptionLabel1.setVisible(isActive()); 
		SubOptionLabel2.setVisible(isActive()); 
		SubOptionLabel3.setVisible(isActive()); 
		SubOptionLabel4.setVisible(isActive()); 
		SubOptionLabel5.setVisible(isActive()); 
		CreatedText.setVisible(isActive()); 
		InsertedText.setVisible(isActive()); 
		DataSizeText.setVisible(isActive()); 
		DeletedText.setVisible(isActive()); 
		FolderText.setVisible(isActive()); 
		
	}


}

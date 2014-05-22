package processing.app.screens.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GDropList;
import processing.app.controls.GEvent;
import processing.app.controls.GTextField;
import processing.app.screen.managers.ViewHandler;
import processing.app.tools.encoder.PIPImage;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class ViewerPIPView extends BaseObject {

	GButton EmptyButton; 
	GButton AdvancedButton; 
	GButton ResizeButton; 
	GButton PIPButton; 
	GButton VideoButton; 
	
	GTextField MainImagePathInput; 
	GTextField OutputPathInput; 
	GTextField PIPImagePathInput; 

	GDropList SizeSelectionList; 
	GDropList PositionSelectionList; 

	Thread pipThread;
	JDialog pipDialog = new JDialog();  

	public ViewerPIPView() {
		super();
	}


	@Override
	public void Init() {
		view.AddLabel(48, 32, 504, 20, "PIP", GAlign.LEFT, GAlign.MIDDLE, true);

		view.AddLabel(4, 88, 192, 16, "Imagens:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 112, 192, 16, "PIP Imagens:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 136, 192, 16, "Destino:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 160, 192, 16, "Tamanho:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 184, 192, 16, "Posição:", GAlign.RIGHT, GAlign.MIDDLE, false);

		MainImagePathInput = new GTextField(Application.app, 196, 88, 216, 16, G4P.SCROLLBARS_NONE);
		MainImagePathInput.setOpaque(true);
		MainImagePathInput.setEnabled(false);
		MainImagePathInput.setVisible(false);
		PIPImagePathInput = new GTextField(Application.app, 196, 112, 216, 16, G4P.SCROLLBARS_NONE);
		PIPImagePathInput.setOpaque(true);
		PIPImagePathInput.setEnabled(false);
		PIPImagePathInput.setVisible(false);
		OutputPathInput = new GTextField(Application.app, 196, 136, 216, 16, G4P.SCROLLBARS_NONE);
		OutputPathInput.setOpaque(true);
		OutputPathInput.setEnabled(false);
		OutputPathInput.setVisible(false);

		view.AddButton(420, 88, 76, 16, "Procurar", GCScheme.SCHEME_15, this, 
				"SearchMainImagePathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(420, 112, 76, 16, "Procurar", GCScheme.SCHEME_15, this, 
				"SearchPIPImagePathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(420, 136, 76, 16, "Procurar", GCScheme.SCHEME_15, this, 
				"SearchOutputPathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);

		view.AddButton(480, 32, 80, 24, "Gerar", GCScheme.SCHEME_15, 
				this, "GenerateButtonClicked");
		String[] sizes = {"PEQUENO", "MEDIO", "GRANDE"};
		SizeSelectionList = new GDropList(Application.app, 196, 160, 216, 80, 4);
		SizeSelectionList.setItems(sizes, 0);
		SizeSelectionList.setLocalColorScheme(GCScheme.SCHEME_8);
		SizeSelectionList.setVisible(false);

		String[] positions = {"CIMA / ESQUERDA", "CIMA / DIREITA", 
				"BAIXO / ESQUERDA" , "BAIXO / DIREITA"};
		PositionSelectionList = new GDropList(Application.app, 196, 184, 216, 80, 4);
		PositionSelectionList.setItems(positions, 0);
		PositionSelectionList.setLocalColorScheme(GCScheme.SCHEME_8);
		PositionSelectionList.setVisible(false);

		VideoButton = new GButton(Application.app, 36, 320, 96, 30);
		VideoButton.setText("Video");
		VideoButton.setTextBold();
		VideoButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		VideoButton.addEventHandler(this, "VideoButtonClick");
		VideoButton.setVisible(false);
		PIPButton = new GButton(Application.app, 144, 320, 96, 30);
		PIPButton.setText("PIP");
		PIPButton.setTextBold();
		PIPButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		PIPButton.addEventHandler(this, "PIPButtonClick");
		PIPButton.setVisible(false);
		ResizeButton = new GButton(Application.app, 252, 320, 96, 30);
		ResizeButton.setText("Redimensionar");
		ResizeButton.setTextBold();
		ResizeButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		ResizeButton.addEventHandler(this, "ResizeButtonClick");
		ResizeButton.setVisible(false);
		AdvancedButton = new GButton(Application.app, 360, 320, 96, 30);
		AdvancedButton.setText("Avançado");
		AdvancedButton.setTextBold();
		AdvancedButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		AdvancedButton.addEventHandler(this, "AdvancedButtonClick");
		AdvancedButton.setVisible(false);
		EmptyButton = new GButton(Application.app, 468, 320, 96, 30);
		EmptyButton.setText("Vazio");
		EmptyButton.setTextBold();
		EmptyButton.setLocalColorScheme(GCScheme.RED_SCHEME);
		EmptyButton.addEventHandler(this, "EmptyButtonClick");
		EmptyButton.setVisible(false);
	}

	@Override
	public void SetViewActive(boolean state) {
		MainImagePathInput.setVisible(view.isVisible());
		PIPImagePathInput.setVisible(view.isVisible());
		OutputPathInput.setVisible(view.isVisible());
		SizeSelectionList.setVisible(view.isVisible());
		PositionSelectionList.setVisible(view.isVisible());
		VideoButton.setVisible(view.isVisible());
		PIPButton.setVisible(view.isVisible());
		ResizeButton.setVisible(view.isVisible());
		AdvancedButton.setVisible(view.isVisible());
		EmptyButton.setVisible(view.isVisible());
	}



	public void GenerateButtonClicked(GButton source, GEvent event) { 

		if(MainImagePathInput.getText().isEmpty() || MainImagePathInput.getText().equals(" ") ||
				PIPImagePathInput.getText().isEmpty() || PIPImagePathInput.getText().equals(" ")) 
			return;

		
		if(pipThread != null) {
			if(pipThread.isAlive()) {
				pipDialog.setVisible(true);
				pipDialog.setLocationRelativeTo(Application.jframe);  
				return;
			}
		}

		final JPanel p1 = new JPanel(new GridBagLayout());  
		final JLabel load = new JLabel("Aguarde, gerando 0%");
		p1.add(load, new GridBagConstraints());  
		pipDialog.setResizable(false);
		pipDialog.getContentPane().add(p1);  
		pipDialog.setSize(180, 60);  
		pipDialog.setLocationRelativeTo(Application.jframe);  
		pipDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		pipThread = new Thread() {  
			public void run() {  

				ArrayList<PIPImage> processPIP = new ArrayList<PIPImage>();

				File source = new File(MainImagePathInput.getText());
				File PIP = new File(PIPImagePathInput.getText());
				
				float percent = 0;
				float factorPercentLoad = 50.0f/source.listFiles(Utils.IMAGE_FILTER).length;
				
				if (source.isDirectory() && PIP.isDirectory()) { // make sure it's a directory
					for (File fs : source.listFiles(Utils.IMAGE_FILTER)) {
						if(percent+factorPercentLoad < 50)
							percent += factorPercentLoad;
						load.setText("Aguarde, gerando "+String.valueOf((int)(percent))+"%");
						for (File fp : PIP.listFiles(Utils.IMAGE_FILTER)) {
							if(fs.getName().substring(fs.getName().length()-25, fs.getName().length()).equals(
									fp.getName().substring(fp.getName().length()-25, fp.getName().length()))) {
								processPIP.add(new PIPImage(Application.app.loadImage(fs.getAbsolutePath()), 
										Application.app.loadImage(fp.getAbsolutePath()), fs.getName()){});
								break;
							}
						}
					}
				}
				
				load.setText("Aguarde, gerando 50%");
				

				float factorPercentSave = 50.0f/processPIP.size();
				
				final String output = (!OutputPathInput.getText().isEmpty() 
						&& !OutputPathInput.getText().equals(" ")) ? 
								OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Jamcollab" ;
				
				int sizeFactor = 0;
				
				switch (SizeSelectionList.getSelectedIndex()) {
				case 0: // PEQUENO
					sizeFactor = 20;
					break;
				case 1: // MEDIO
					sizeFactor = 30;
					break;
				case 2: // GRANDE
					sizeFactor = 40;
					break;
				default:
					sizeFactor = 0;
					break;
				}

				int paddingXFactor = 1;
				int paddingYFactor = 1;
				
				switch (PositionSelectionList.getSelectedIndex()) {
				case 0: // CIMA / ESQUERDA
					paddingXFactor = 1;
					paddingYFactor = 1;
					break;
				case 1: // CIMA / DIREITA
					paddingXFactor = -1;
					paddingYFactor = 1;
					break;
				case 2: // BAIXO / ESQUERDA
					paddingXFactor = 1;
					paddingYFactor = -1;
					break;
				case 3: // BAIXO / DIREITA
					paddingXFactor = -1;
					paddingYFactor = -1;
					break;
				default:
					
					break;
				}
				for (PIPImage img : processPIP) {
					if(percent+factorPercentSave < 100)
						percent += factorPercentSave;
					load.setText("Aguarde, gerando "+String.valueOf((int)(percent))+"%");
					float paddingX = (2.0f*img.getSource().width)/100.0f;
					float paddingY = (2.0f*img.getSource().height)/100.0f;
					PGraphics pg = Application.app.createGraphics(img.getSource().width, img.getSource().height);
					pg.beginDraw();
					pg.image(img.getSource(), 0, 0);
					pg.image(img.getPip(), 
							((paddingXFactor == 1) ? 0 : 1*img.getSource().width)+(paddingX*paddingXFactor)+
							((paddingXFactor == 1) ? 0 : ((sizeFactor*img.getSource().width)/100)*-1), 
							((paddingYFactor == 1) ? 0 : 1*img.getSource().height)+(paddingY*paddingYFactor)+
							((paddingYFactor == 1) ? 0 : ((sizeFactor*img.getSource().height)/100)*-1), 
							(sizeFactor*img.getSource().width)/100, 
							(sizeFactor*img.getSource().height)/100);
					pg.endDraw();
					pg.save(output + File.separator + img.getName());
				}
				
				load.setText("Aguarde, gerando 100%");
				
				 
				SwingUtilities.invokeLater(new Runnable(){ 
					public void run(){  
						p1.remove(load);
						pipDialog.dispose();
						JOptionPane.showMessageDialog(Application.jframe, 
								"PIP gerado com sucesso");
						Utils.OpenFile(output + File.separator);
					}  
				});  
			}  
		};  

		pipThread.start();  
		pipDialog.setVisible(true);  


	} 

	public void SearchMainImagePathButtonClick(GButton source, GEvent event) { 
		Application.app.selectFolder("Selecione uma pasta:", "selectMainImageFolder", null, this);
	} 


	public void SearchPIPImagePathButtonClick(GButton source, GEvent event) { 
		Application.app.selectFolder("Selecione uma pasta:", "selectPIPImageFolder", null, this);
	} 


	public void SearchOutputPathButtonClick(GButton source, GEvent event) { 
		Application.app.selectFolder("Selecione uma pasta:", "selectOutputFolder", null, this);
	} 

	public void selectMainImageFolder(File selection) {
		if(selection == null)
			return;
		String path = selection.getAbsolutePath();
		MainImagePathInput.setText(path);
	}

	public void selectPIPImageFolder(File selection) {
		if(selection == null)
			return;
		String path = selection.getAbsolutePath();
		PIPImagePathInput.setText(path);
	}

	public void selectOutputFolder(File selection) {
		if(selection == null)
			return;
		String path = selection.getAbsolutePath();
		OutputPathInput.setText(path);
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}


	public void EmptyButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("Empty");
	}

	public void AdvancedButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("Advanced");
	}

	public void ResizeButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("Resize");
	} 

	public void PIPButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("PIP");
	}

	public void VideoButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("Video");
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

package processing.app.screens.viewer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import processing.app.Jamcollab;
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

public class ViewerPIP extends BaseObject {

	GTextField SourcePathInput; 
	GTextField OutputPathInput; 
	GTextField PIPImagePathInput; 

	GDropList SizeSelectionList; 
	GDropList PositionSelectionList; 

	Thread pipThread;
	JDialog pipDialog = new JDialog();

	public ViewerPIP() {
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

		SourcePathInput = view.AddTextField(196, 88, 216, 16, G4P.SCROLLBARS_NONE);
		SourcePathInput.setEnabled(false);
		PIPImagePathInput = view.AddTextField(196, 112, 216, 16, G4P.SCROLLBARS_NONE);
		PIPImagePathInput.setEnabled(false);
		OutputPathInput = view.AddTextField(196, 136, 216, 16, G4P.SCROLLBARS_NONE);
		OutputPathInput.setEnabled(false);

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
		SizeSelectionList = new GDropList(Jamcollab.app, 196, 160, 216, 80, 4);
		SizeSelectionList.setItems(sizes, 0);
		SizeSelectionList.setLocalColorScheme(GCScheme.SCHEME_8);
		SizeSelectionList.setVisible(false);

		String[] positions = {"CIMA / ESQUERDA", "CIMA / DIREITA", 
				"BAIXO / ESQUERDA" , "BAIXO / DIREITA"};
		PositionSelectionList = new GDropList(Jamcollab.app, 196, 184, 216, 80, 4);
		PositionSelectionList.setItems(positions, 0);
		PositionSelectionList.setLocalColorScheme(GCScheme.SCHEME_8);
		PositionSelectionList.setVisible(false);

		view.AddButton(34, 308, 127, 22, "Video", this, "VideoButtonClick");
		view.AddButton(169, 308, 127, 22, "PIP", this, "PIPButtonClick");
		view.AddButton(304, 308, 127, 22, "Redimensionar", this, "ResizeButtonClick");
		view.AddButton(439, 308, 127, 22, "Mouse", this, "MouseButtonClick");
		view.AddButton(34, 336, 127, 22, "Teclado", this, "KeyboardButtonClick");
		view.AddButton(169, 336, 127, 22, "Arquivos", this, "FilesButtonClick");
		view.AddButton(304, 336, 127, 22, "Programas", this, "ProcessButtonClick");
		view.AddButton(439, 336, 127, 22, "Mapa", this, "MapButtonClick");
	}

	@Override
	public void SetViewActive(boolean state) {
		SizeSelectionList.setVisible(view.isVisible());
		PositionSelectionList.setVisible(view.isVisible());
	}



	public void GenerateButtonClicked(GButton source, GEvent event) { 

		if(SourcePathInput.getText().isEmpty() || SourcePathInput.getText().equals(" ") ||
				PIPImagePathInput.getText().isEmpty() || PIPImagePathInput.getText().equals(" ")) 
			return;

		
		if(pipThread != null) {
			if(pipThread.isAlive()) {
				pipDialog.setVisible(true);
				pipDialog.setLocationRelativeTo(Jamcollab.jframe);  
				return;
			}
		}

		final JPanel p1 = new JPanel(new GridBagLayout());  
		final JLabel load = new JLabel("Aguarde, gerando 0%");
		p1.add(load, new GridBagConstraints());  
		pipDialog.setResizable(false);
		pipDialog.getContentPane().add(p1);  
		pipDialog.setSize(180, 60);  
		pipDialog.setLocationRelativeTo(Jamcollab.jframe);  
		pipDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		pipThread = new Thread() {  
			public void run() {  

				ArrayList<PIPImage> processPIP = new ArrayList<PIPImage>();

				File source = new File(SourcePathInput.getText());
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
								processPIP.add(new PIPImage(Jamcollab.app.loadImage(fs.getAbsolutePath()), 
										Jamcollab.app.loadImage(fp.getAbsolutePath()), fs.getName()){});
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
					PGraphics pg = Jamcollab.app.createGraphics(img.getSource().width, img.getSource().height);
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
						JOptionPane.showMessageDialog(Jamcollab.jframe, 
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
		Jamcollab.app.selectFolder("Selecione uma pasta:", "selectMainImageFolder", null, this);
	} 


	public void SearchPIPImagePathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder("Selecione uma pasta:", "selectPIPImageFolder", null, this);
	} 


	public void SearchOutputPathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder("Selecione uma pasta:", "selectOutputFolder", null, this);
	} 

	public void selectMainImageFolder(File selection) {
		if(selection == null)
			return;
		String path = selection.getAbsolutePath();
		SourcePathInput.setText(path);
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

	public void ProcessButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("ProcessViewer");
	}

	public void MapButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("MapViewer");
	}
	
	public void FilesButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("FilesViewer");
	}

	public void KeyboardButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("KeyboardViewer");
	}

	public void MouseButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("MouseViewer");
	}

	public void ResizeButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("ResizeViewer");
	} 

	public void PIPButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("PIPViewer");
	}

	public void VideoButtonClick(GButton source, GEvent event) {
		ViewHandler.Enable("VideoViewer");
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

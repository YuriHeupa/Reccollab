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
import processing.app.controls.GEvent;
import processing.app.controls.GTextField;
import processing.app.screen.managers.ViewHandler;
import processing.app.tools.encoder.PIPImage;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class ViewerResizeView extends BaseObject {

	GButton EmptyButton; 
	GButton AdvancedButton; 
	GButton ResizeButton; 
	GButton PIPButton; 
	GButton VideoButton; 
	
	GTextField MainImagePathInput; 
	GTextField OutputPathInput; 

	GTextField WidthInput; 
	GTextField HeightInput; 

	Thread resizeThread;
	JDialog resizeDialog = new JDialog();  

	public ViewerResizeView() {
		super();
	}


	@Override
	public void Init() {

		view.AddLabel(48, 32, 504, 20, "Redimensionar", GAlign.LEFT, GAlign.MIDDLE, true);

		view.AddLabel(4, 88, 192, 16, "Imagens:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 112, 192, 16, "Destino:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 136, 192, 16, "Largura:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 160, 192, 16, "Altura:", GAlign.RIGHT, GAlign.MIDDLE, false);

		MainImagePathInput = view.AddTextField(196, 88, 216, 16, G4P.SCROLLBARS_NONE);
		MainImagePathInput.setEnabled(false);
		OutputPathInput = view.AddTextField(196, 112, 216, 16, G4P.SCROLLBARS_NONE);
		OutputPathInput.setEnabled(false);
		WidthInput = view.AddTextField(196, 136, 46, 16, G4P.SCROLLBARS_NONE);
		HeightInput = view.AddTextField(196, 160, 46, 16, G4P.SCROLLBARS_NONE);


		view.AddButton(420, 88, 76, 16, "Procurar", GCScheme.SCHEME_15, this, 
				"SearchMainImagePathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(420, 112, 76, 16, "Procurar", GCScheme.SCHEME_15, this, 
				"SearchOutputPathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);

		view.AddButton(470, 32, 100, 24, "Redimensionar", GCScheme.SCHEME_15, 
				this, "ResizeButtonClicked");

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
		VideoButton.setVisible(view.isVisible());
		PIPButton.setVisible(view.isVisible());
		ResizeButton.setVisible(view.isVisible());
		AdvancedButton.setVisible(view.isVisible());
		EmptyButton.setVisible(view.isVisible());
		
	}

	public void ResizeButtonClicked(GButton source, GEvent event) { 

		if(MainImagePathInput.getText().isEmpty() || MainImagePathInput.getText().equals(" ")) 
			return;


		if(resizeThread != null) {
			if(resizeThread.isAlive()) {
				resizeDialog.setVisible(true);
				resizeDialog.setLocationRelativeTo(Application.jframe);  
				return;
			}
		}

		final JPanel p1 = new JPanel(new GridBagLayout());  
		final JLabel load = new JLabel("Aguarde, gerando 0%");
		p1.add(load, new GridBagConstraints());  
		resizeDialog.setResizable(false);
		resizeDialog.getContentPane().add(p1);  
		resizeDialog.setSize(180, 60);  
		resizeDialog.setLocationRelativeTo(Application.jframe);  
		resizeDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		resizeThread = new Thread() {  
			public void run() {  

				ArrayList<PIPImage> processResize = new ArrayList<PIPImage>();

				File source = new File(MainImagePathInput.getText());

				float percent = 0;
				float factorPercentLoad = 50.0f/source.listFiles(Utils.IMAGE_FILTER).length;

				if (source.isDirectory()) { // make sure it's a directory
					for (File fs : source.listFiles(Utils.IMAGE_FILTER)) {
						if(percent+factorPercentLoad < 50)
							percent += factorPercentLoad;
						load.setText("Aguarde, gerando "+String.valueOf((int)(percent))+"%");
						processResize.add(new PIPImage(Application.app.loadImage(fs.getAbsolutePath()), null, fs.getName()) {});

					}
				}

				load.setText("Aguarde, gerando 50%");

				float factorPercentSave = 50.0f/processResize.size();

				final String output = (!OutputPathInput.getText().isEmpty() 
						&& !OutputPathInput.getText().equals(" ")) ? 
								OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Jamcollab";;

								for (PIPImage img : processResize) {
									if(percent+factorPercentSave < 100)
										percent += factorPercentSave;
									load.setText("Aguarde, gerando "+String.valueOf((int)(percent))+"%");
									PGraphics pg = Application.app.createGraphics(Integer.valueOf(WidthInput.getText()), Integer.valueOf(HeightInput.getText()));
									pg.beginDraw();
									pg.image(img.getSource(), 0, 0, Integer.valueOf(WidthInput.getText()), Integer.valueOf(HeightInput.getText()));
									pg.endDraw();
									pg.save(output + File.separator + img.getName());
								}

								load.setText("Aguarde, gerando 100%");


								SwingUtilities.invokeLater(new Runnable(){ 
									public void run(){  
										p1.remove(load);
										resizeDialog.dispose();
										JOptionPane.showMessageDialog(Application.jframe, 
												"Redimensionamento gerado com sucesso");
										Utils.OpenFile(output + File.separator);
									}  
								});  
			}  
		};  

		resizeThread.start();  
		resizeDialog.setVisible(true);  
	} 

	public void SearchMainImagePathButtonClick(GButton source, GEvent event) { 
		Application.app.selectFolder("Selecione uma pasta:", "selectMainImageFolder", null, this);
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

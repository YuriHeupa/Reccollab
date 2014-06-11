package processing.app.screens.viewer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Lang;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GTextField;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.event.MouseEvent;

public class ResizeViewer extends BaseObject {


	GTextField SourcePathInput; 
	GTextField OutputPathInput; 

	GTextField WidthInput; 
	GTextField HeightInput; 

	Thread resizeThread;
	JDialog resizeDialog = new JDialog();  

	public ResizeViewer() {
		super();
		setParent("TreatImages");
	}


	@Override
	public void Init() {
		int y = 70;
		
		view.AddLabel(4, 88+y, 192, 16, Lang.IMAGES, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 112+y, 192, 16, Lang.SAVE_PATH, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 136+y, 192, 16, Lang.WIDTH+":", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 160+y, 192, 16, Lang.HEIGHT+":", GAlign.RIGHT, GAlign.MIDDLE, false);

		SourcePathInput = view.AddTextField(196, 88+y, 216, 16, G4P.SCROLLBARS_NONE);
		SourcePathInput.setEnabled(false);
		OutputPathInput = view.AddTextField(196, 112+y, 216, 16, G4P.SCROLLBARS_NONE);
		OutputPathInput.setEnabled(false);
		WidthInput = view.AddTextField(196, 136+y, 46, 16, G4P.SCROLLBARS_NONE);
		HeightInput = view.AddTextField(196, 160+y, 46, 16, G4P.SCROLLBARS_NONE);


		view.AddButton(420, 88+y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this, 
				"SearchMainImagePathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(420, 112+y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this, 
				"SearchOutputPathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);

		view.AddButton(470, 22+y, 100, 24, Lang.RESIZE, GCScheme.SCHEME_15, 
				this, "ResizeButtonClicked");

	}


	@Override
	public void SetViewActive(boolean state) {

	}


	public void ResizeButtonClicked(GButton source, GEvent event) { 

		if(SourcePathInput.getText().isEmpty() || SourcePathInput.getText().equals(" ")) 
			return;


		if(resizeThread != null) {
			if(resizeThread.isAlive()) {
				resizeDialog.setVisible(true);
				resizeDialog.setLocationRelativeTo(Jamcollab.jframe);  
				return;
			}
		}

		final JPanel p1 = new JPanel(new GridBagLayout());  
		p1.add(new JLabel(Lang.GENERATING), new GridBagConstraints());
		final JLabel load = new JLabel("0%");
		p1.add(load, new GridBagConstraints());  
		resizeDialog.setResizable(false);
		resizeDialog.getContentPane().add(p1);  
		resizeDialog.setSize(180, 60);  
		resizeDialog.setLocationRelativeTo(Jamcollab.jframe);  
		resizeDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		resizeThread = new Thread() {  
			public void run() {  

				File source = new File(SourcePathInput.getText());
				File[] listImageFiles = source.listFiles(Utils.IMAGE_FILTER);

				float percent = 0;
				float factorPercentLoad = 100.0f/listImageFiles.length;

				final String output = (!OutputPathInput.getText().isEmpty() 
						&& !OutputPathInput.getText().equals(" ")) ? 
						OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Jamcollab";

				PImage buffer = null;
				if (source.isDirectory()) { // make sure it's a directory
					for (File f : listImageFiles) {
						percent += factorPercentLoad;
						load.setText(String.valueOf((int)(percent))+"%");

						buffer = Jamcollab.app.loadImage(f.getAbsolutePath());
						PGraphics pg = Jamcollab.app.createGraphics(Integer.valueOf(WidthInput.getText()), Integer.valueOf(HeightInput.getText()));
						pg.beginDraw();
						pg.image(buffer, 0, 0, Integer.valueOf(WidthInput.getText()), Integer.valueOf(HeightInput.getText()));
						pg.endDraw();
						pg.save(output + File.separator + f.getName());

					}
				}

				load.setText("100%");

				SwingUtilities.invokeLater(new Runnable(){ 
					public void run(){  
						p1.remove(load);
						resizeDialog.dispose();
						JOptionPane.showMessageDialog(Jamcollab.jframe, 
								Lang.RESIZE_SUCCESS);
						Utils.OpenFile(output + File.separator);
					}  
				});  
			}  
		};  

		resizeThread.start();  
		resizeDialog.setVisible(true);  
	} 

	public void SearchMainImagePathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder("Selecione uma pasta:", "selectMainImageFolder", null, this);
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

	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub

	}
}

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
import processing.event.MouseEvent;

public class ProcessViewer extends BaseObject {
	
	GTextField SourcePathInput; 
	GTextField OutputPathInput; 

	Thread generateThread;
	JDialog generatingDialog = new JDialog();  

	public ProcessViewer() {
		super();
		setParent("GenerateImages");
	}


	@Override
	public void Init() {
		int y = 70;

		view.AddLabel(4, 88+y, 192, 16, Lang.PROCESS_LOGS, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 112+y, 192, 16, Lang.SAVE_PATH, GAlign.RIGHT, GAlign.MIDDLE, false);
		
		SourcePathInput = view.AddTextField(196, 88+y, 216, 16, G4P.SCROLLBARS_NONE);
		SourcePathInput.setEnabled(false);
		OutputPathInput = view.AddTextField(196, 112+y, 216, 16, G4P.SCROLLBARS_NONE);
		OutputPathInput.setEnabled(false);
		
		view.AddButton(420, 88+y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this, 
				"SearchSourcePathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(420, 112+y, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this, 
				"SearchOutputPathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(480, 22+y, 80, 24, Lang.GENERATE, GCScheme.SCHEME_15, 
				this, "GenerateButtonClicked");

	}

	public void GenerateButtonClicked(GButton source, GEvent event) { 

		if(SourcePathInput.getText().isEmpty() || SourcePathInput.getText().equals(" ")) 
			return;


		if(generateThread != null) {
			if(generateThread.isAlive()) {
				generatingDialog.setVisible(true);
				generatingDialog.setLocationRelativeTo(Jamcollab.jframe);  
				return;
			}
		}

		final JPanel p1 = new JPanel(new GridBagLayout());  
		p1.add(new JLabel(Lang.GENERATING), new GridBagConstraints());
		final JLabel load = new JLabel("0%");
		p1.add(load, new GridBagConstraints());  
		generatingDialog.setResizable(false);
		generatingDialog.getContentPane().add(p1);  
		generatingDialog.setSize(180, 60);  
		generatingDialog.setLocationRelativeTo(Jamcollab.jframe);  
		generatingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		generateThread = new Thread() {  
			public void run() {  

				File source = new File(SourcePathInput.getText());
				File[] listTxtFiles = source.listFiles(Utils.TEXT_FILTER);
				
				float percent = 0;
				float factorPercentLoad = 100.0f/listTxtFiles.length;

				final String output = (!OutputPathInput.getText().isEmpty() 
						&& !OutputPathInput.getText().equals(" ")) ? 
								OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Jamcollab";

				if (source.isDirectory()) { // make sure it's a directory
					for (File f : source.listFiles(Utils.TEXT_FILTER)) {
						percent += factorPercentLoad;
						load.setText(String.valueOf((int)(percent))+"%");

						PGraphics pg = Jamcollab.app.createGraphics(0, 0);
						pg.beginDraw();
						pg.endDraw();
						pg.save(output + File.separator + f.getName());

					}
				}

				load.setText("100%");


				SwingUtilities.invokeLater(new Runnable(){ 
					public void run(){  
						p1.remove(load);
						generatingDialog.dispose();
						JOptionPane.showMessageDialog(Jamcollab.jframe, 
								Lang.GENERATE_SUCCES);
						
										
						Utils.OpenFile(output + File.separator);
					}  
				});  
			}  
		};  

		generateThread.start();  
		generatingDialog.setVisible(true);  

	} 
	public void SearchSourcePathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder(Lang.SELECT_FOLDER, "selectSourceFolder", null, this);
	} 


	public void SearchOutputPathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder(Lang.SELECT_FOLDER, "selectOutputFolder", null, this);
	} 

	public void selectSourceFolder(File selection) {
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
	public void SetViewActive(boolean state) { 
		
	}

	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}
	

}

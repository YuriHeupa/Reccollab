package processing.app.screens.viewer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
import processing.app.controls.GCheckbox;
import processing.app.controls.GEvent;
import processing.app.controls.GTextField;
import processing.app.tools.io.Keyword;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class KeyboardViewer extends BaseObject {


	GTextField SourcePathInput; 
	GTextField OutputPathInput; 

	Thread generateThread;
	JDialog generatingDialog = new JDialog();  
	

	GCheckbox tagCloud;
	GCheckbox keysSequence;
	GCheckbox wordSequence;
	GCheckbox generalInfo;
	
	public KeyboardViewer() {
		super();
		setParent("GenerateImages");
	}


	@Override
	public void Init() {
		int y = 70;


		view.AddLabel(4, 88+y, 192, 16, Lang.KEYBOARD_LOG, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 112+y, 192, 16, Lang.SAVE_PATH, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 136+y, 192, 16, "TagCloud:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 160+y, 192, 16, Lang.KEYS_SEQUENCE, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 184+y, 192, 16, Lang.WORDS_SEQUENCE, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 208+y, 192, 16, Lang.GENERAL_INFO, GAlign.RIGHT, GAlign.MIDDLE, false);

		
		tagCloud = new GCheckbox(Jamcollab.app, 196, 136+y, 24, 20);
		tagCloud.setOpaque(false);
		view.AddControl(tagCloud);

		keysSequence = new GCheckbox(Jamcollab.app, 196, 160+y, 24, 20);
		keysSequence.setOpaque(false);
		keysSequence.addEventHandler(this, "keysSequenceToggleClicked");
		view.AddControl(keysSequence);
		
		
		wordSequence = new GCheckbox(Jamcollab.app, 196, 184+y, 24, 20);
		wordSequence.setOpaque(false);
		wordSequence.addEventHandler(this, "wordSequenceToggleClicked");
		view.AddControl(wordSequence);
		
		generalInfo = new GCheckbox(Jamcollab.app, 196, 208+y, 24, 20);
		generalInfo.setOpaque(false);
		view.AddControl(generalInfo);

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
				
				ArrayList<Keyword> keywords = new ArrayList<Keyword>();
				
				float percent = 0;
				float factorPercentLoad = 50.0f/listTxtFiles.length;
				
				final String output = (!OutputPathInput.getText().isEmpty() 
						&& !OutputPathInput.getText().equals(" ")) ? 
								OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Jamcollab";


				if (source.isDirectory()) { // make sure it's a directory
					for (File f : source.listFiles(Utils.TEXT_FILTER)) {
						if(percent < 50)
							percent += factorPercentLoad;
						load.setText(String.valueOf((int)(percent))+"%");
						
						BufferedReader br = null;
						 
						try {
				 
							String currentLine;
				 
							br = new BufferedReader(new FileReader(f.getAbsolutePath()));
				 
							while ((currentLine = br.readLine()) != null) {
								String handleTime = currentLine.substring(0, 26);
								String keyword = currentLine.substring(29, currentLine.length());
								keywords.add(new Keyword(keyword, handleTime));
							}
				 
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								if (br != null)br.close();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					}
				}
				
				load.setText("50%");

				for (Keyword k : keywords) {
					if(percent < 100)
						percent += factorPercentLoad;
					load.setText(String.valueOf((int)(percent))+"%");
					
					PGraphics pg = Jamcollab.app.createGraphics(100, 100);
					pg.beginDraw();
					pg.text(k.getInfo(), 0, 0);
					pg.endDraw();
					pg.save(output + File.separator + "keyboard_"+Utils.dateFormat()+".jpg");
					
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
	public void Update() {
		// TODO Auto-generated method stub
		
	}
	
	public void keysSequenceToggleClicked(GCheckbox source, GEvent event) { 
		wordSequence.setSelected(false);
	} 
	
	public void wordSequenceToggleClicked(GCheckbox source, GEvent event) { 
		keysSequence.setSelected(false);
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

package processing.app.screens.viewer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import processing.app.BaseObject;
import processing.app.FileTime;
import processing.app.Jamcollab;
import processing.app.Lang;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GDropList;
import processing.app.controls.GEvent;
import processing.app.controls.GTextField;
import processing.app.tools.encoder.Encoder;
import processing.app.tools.encoder.TimeComparator;
import processing.event.MouseEvent;

public class VideoViewer extends BaseObject {


	GTextField MainImagePathInput; 
	GTextField OutputPathInput; 
	GTextField FrameRateInput; 

	GDropList CodecSelectionList; 
	GDropList FormatSelectionList; 

	Thread encodeThread;
	JDialog encodingDialog = new JDialog();  


	public VideoViewer() {
		super();
		setParent("Master");
	}

	@Override
	public void Init() {

		view.AddLabel(4, 138, 192, 16, Lang.IMAGES, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 162, 192, 16, Lang.SAVE_PATH, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 186, 192, 16, Lang.FORMAT, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 210, 192, 16, "Codec:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 234, 192, 16, "Frame rate:", GAlign.RIGHT, GAlign.MIDDLE, false);

		MainImagePathInput = view.AddTextField(196, 138, 216, 16, G4P.SCROLLBARS_NONE);
		MainImagePathInput.setEnabled(false);
		OutputPathInput = view.AddTextField(196, 162, 216, 16, G4P.SCROLLBARS_NONE);
		OutputPathInput.setEnabled(false);


		FrameRateInput = view.AddTextField(196, 234, 26, 16, G4P.SCROLLBARS_NONE);
		FrameRateInput.setText("25");


		view.AddButton(420, 138, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this, 
				"SearchMainImagePathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(420, 162, 76, 16, Lang.SEARCH, GCScheme.SCHEME_15, this, 
				"SearchOutputPathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(482, 52, 80, 24, Lang.GENERATE, GCScheme.SCHEME_15, 
				this, "GenerateButtonClicked");

		CodecSelectionList = view.AddDropList(196, 210, 216, 80, 4, 
				GCScheme.SCHEME_8, Encoder.codecList(), 3);

		FormatSelectionList = view.AddDropList(196, 186, 216, 80, 4, 
				GCScheme.SCHEME_8, Encoder.formatList(), 0);
	}

	@Override
	public void SetViewActive(boolean state) {

	}


	public void GenerateButtonClicked(GButton source, GEvent event) { 

		if(MainImagePathInput.getText().isEmpty() || MainImagePathInput.getText().equals(" ")) 
			return;

		if(encodeThread != null) {
			if(encodeThread.isAlive()) {
				encodingDialog.setVisible(true);
				encodingDialog.setLocationRelativeTo(Jamcollab.jframe);  
				return;
			}
		}

		JPanel p1 = new JPanel(new GridBagLayout());  
		p1.add(new JLabel(Lang.GENERATING), new GridBagConstraints());  
		final JLabel load = new JLabel("0%");
		p1.add(load, new GridBagConstraints());  
		encodingDialog.setResizable(false);
		encodingDialog.getContentPane().add(p1);  
		encodingDialog.setSize(180, 60);  
		encodingDialog.setLocationRelativeTo(Jamcollab.jframe);  
		encodingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		encodeThread = new Thread() {  
			public void run() {  

				ArrayList<FileTime> images = new ArrayList<FileTime>();


				// File representing the folder that you select using a FileChooser
				File dir = new File(MainImagePathInput.getText());

				if(dir.listFiles(Utils.IMAGE_FILTER).length <= 0)
					return;


				if (dir.isDirectory()) { // make sure it's a directory
					for (final File f : dir.listFiles(Utils.IMAGE_FILTER)) {
						images.add(new FileTime(f));
					}
				}

				Collections.sort(images, new TimeComparator());

				final String output = (!OutputPathInput.getText().isEmpty() 
						&& !OutputPathInput.getText().equals(" ")) ? 
								OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Jamcollab" ;



				int fps = 25;
				if(Utils.isAlphanumeric(FrameRateInput.getText()))
					fps = Integer.valueOf(FrameRateInput.getText());
				if(fps <= 0)
					fps = 1;
				final String fileName = output+File.separator+"Video_" +
						Utils.dateFormat()+"." + FormatSelectionList.getSelectedText();
				Encoder.Encode(load, images, fileName, fps,
						Encoder.GetCodecAt(CodecSelectionList.getSelectedIndex()));

				load.setText("100%");

				SwingUtilities.invokeLater(new Runnable(){ 
					public void run(){  
						encodingDialog.dispose();
						JOptionPane.showMessageDialog(Jamcollab.jframe, 
								Lang.VIDEO_SUCCESS);
						Utils.OpenFile(new File(fileName).getAbsolutePath());
					}  
				});  
			}  
		};  

		encodeThread.start();  
		encodingDialog.setVisible(true);  




	} 

	public void SearchMainImagePathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder(Lang.SELECT_FOLDER, "selectMainImageFolder", null, this);
	} 


	public void SearchOutputPathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder(Lang.SELECT_FOLDER, "selectOutputFolder", null, this);
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

	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}



}

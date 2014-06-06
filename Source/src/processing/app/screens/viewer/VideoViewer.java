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
import processing.app.Jamcollab;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GDropList;
import processing.app.controls.GEvent;
import processing.app.controls.GTextField;
import processing.app.tools.encoder.BufferedImagePlus;
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

		view.AddLabel(48, 32, 504, 20, "Video", GAlign.LEFT, GAlign.MIDDLE, true);
		view.AddLabel(4, 88, 192, 16, "Imagens:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 112, 192, 16, "Destino:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 136, 192, 16, "Formato:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 160, 192, 16, "Codec:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 184, 192, 16, "Frame rate:", GAlign.RIGHT, GAlign.MIDDLE, false);

		MainImagePathInput = view.AddTextField(196, 88, 216, 16, G4P.SCROLLBARS_NONE);
		MainImagePathInput.setEnabled(false);
		OutputPathInput = view.AddTextField(196, 112, 216, 16, G4P.SCROLLBARS_NONE);
		OutputPathInput.setEnabled(false);

		FrameRateInput = view.AddTextField(196, 184, 26, 16, G4P.SCROLLBARS_NONE);
		FrameRateInput.setText("25");


		view.AddButton(420, 88, 76, 16, "Procurar", GCScheme.SCHEME_15, this, 
				"SearchMainImagePathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(420, 112, 76, 16, "Procurar", GCScheme.SCHEME_15, this, 
				"SearchOutputPathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(480, 32, 80, 24, "Gerar", GCScheme.SCHEME_15, 
				this, "GenerateButtonClicked");

		CodecSelectionList = view.AddDropList(196, 160, 216, 80, 4, 
				GCScheme.SCHEME_8, Encoder.codecList(), 3);

		FormatSelectionList = view.AddDropList(196, 136, 216, 80, 4, 
				GCScheme.SCHEME_8, Encoder.formatList(), 0);

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
		p1.add(new JLabel("Gerando "), new GridBagConstraints());  
		final JLabel load = new JLabel("0%");
		p1.add(load, new GridBagConstraints());  
		encodingDialog.setResizable(false);
		encodingDialog.getContentPane().add(p1);  
		encodingDialog.setSize(180, 60);  
		encodingDialog.setLocationRelativeTo(Jamcollab.jframe);  
		encodingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		encodeThread = new Thread() {  
			public void run() {  

				ArrayList<BufferedImagePlus> images = new ArrayList<BufferedImagePlus>();


				// File representing the folder that you select using a FileChooser
				File dir = new File(MainImagePathInput.getText());

				if(dir.listFiles(Utils.IMAGE_FILTER).length <= 0)
					return;


				if (dir.isDirectory()) { // make sure it's a directory
					for (final File f : dir.listFiles(Utils.IMAGE_FILTER)) {
						images.add(new BufferedImagePlus(f, f.getName()){});
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
								"Video gerado com sucesso");
						Utils.OpenFile(new File(fileName).getAbsolutePath());
					}  
				});  
			}  
		};  

		encodeThread.start();  
		encodingDialog.setVisible(true);  




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
	public void ProcessButtonClick(GButton source, GEvent event) {
		EnableView("ProcessViewer");
	}

	public void MapButtonClick(GButton source, GEvent event) {
		EnableView("MapViewer");
	}
	
	public void FilesButtonClick(GButton source, GEvent event) {
		EnableView("FilesViewer");
	}

	public void KeyboardButtonClick(GButton source, GEvent event) {
		EnableView("KeyboardViewer");
	}

	public void MouseButtonClick(GButton source, GEvent event) {
		EnableView("MouseViewer");
	}

	public void ResizeButtonClick(GButton source, GEvent event) {
		EnableView("ResizeViewer");
	} 

	public void PIPButtonClick(GButton source, GEvent event) {
		EnableView("PIPViewer");
	}

	public void VideoButtonClick(GButton source, GEvent event) {
		EnableView("VideoViewer");
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

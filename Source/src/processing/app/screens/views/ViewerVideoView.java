package processing.app.screens.views;

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
import processing.app.tools.encoder.BufferedImagePlus;
import processing.app.tools.encoder.Encoder;
import processing.app.tools.encoder.TimeComparator;
import processing.event.MouseEvent;

public class ViewerVideoView extends BaseObject {

	GButton EmptyButton; 
	GButton AdvancedButton; 
	GButton ResizeButton; 
	GButton PIPButton; 
	GButton VideoButton; 

	GTextField MainImagePathInput; 
	GTextField OutputPathInput; 
	GTextField FrameRateInput; 

	GDropList CodecSelectionList; 
	GDropList FormatSelectionList; 

	Thread encodeThread;
	JDialog encodingDialog = new JDialog();  


	public ViewerVideoView() {
		super();
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


	public void GenerateButtonClicked(GButton source, GEvent event) { 

		if(MainImagePathInput.getText().isEmpty() || MainImagePathInput.getText().equals(" ")) 
			return;

		if(encodeThread != null) {
			if(encodeThread.isAlive()) {
				encodingDialog.setVisible(true);
				encodingDialog.setLocationRelativeTo(Application.jframe);  
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
		encodingDialog.setLocationRelativeTo(Application.jframe);  
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
						JOptionPane.showMessageDialog(Application.jframe, 
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

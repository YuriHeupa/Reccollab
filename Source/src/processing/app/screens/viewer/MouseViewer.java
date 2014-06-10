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
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GCheckbox;
import processing.app.controls.GEvent;
import processing.app.controls.GOption;
import processing.app.controls.GTextField;
import processing.app.controls.GToggleGroup;
import processing.app.tools.io.MouseInfo;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class MouseViewer extends BaseObject {


	GTextField SourcePathInput; 
	GTextField OutputPathInput; 
	Thread generateThread;
	JDialog generatingDialog = new JDialog();  

	GOption heatMapPos;
	GOption heatMapClicks;
	GCheckbox heatMap;
	GCheckbox accumulate;
	GCheckbox generalInfo;

	public MouseViewer() {
		super();
		setParent("Master");
	}


	@Override
	public void Init() {
		view.AddLabel(48, 32, 504, 20, "Mouse", GAlign.LEFT, GAlign.MIDDLE, true);

		view.AddLabel(4, 88, 192, 16, "Logs de mouse:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 112, 192, 16, "Destino:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 136, 192, 16, "Acumular rastro:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 160, 192, 16, "Heatmap:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(4, 184, 192, 16, "Informações gerais:", GAlign.RIGHT, GAlign.MIDDLE, false);

		accumulate = new GCheckbox(Jamcollab.app, 196, 136, 24, 20);
		accumulate.setOpaque(false);
		view.AddControl(accumulate);

		GToggleGroup group = new GToggleGroup();

		heatMap = new GCheckbox(Jamcollab.app, 196, 160, 24, 20);
		heatMap.setOpaque(false);
		view.AddControl(heatMap);

		heatMapPos = new GOption(Jamcollab.app, 220, 160, 80, 20);
		heatMapPos.setOpaque(false);
		heatMapPos.setText("Posição");
		view.AddControl(heatMapPos);
		group.addControl(heatMapPos);

		heatMapClicks = new GOption(Jamcollab.app, 290, 160, 80, 20);
		heatMapClicks.setOpaque(false);
		heatMapClicks.setText("Clicks");
		view.AddControl(heatMapClicks);
		group.addControl(heatMapClicks);

		generalInfo = new GCheckbox(Jamcollab.app, 196, 184, 24, 20);
		generalInfo.setOpaque(false);
		view.AddControl(generalInfo);


		SourcePathInput = view.AddTextField(196, 88, 216, 16, G4P.SCROLLBARS_NONE);
		SourcePathInput.setEnabled(false);
		OutputPathInput = view.AddTextField(196, 112, 216, 16, G4P.SCROLLBARS_NONE);
		OutputPathInput.setEnabled(false);

		view.AddButton(420, 88, 76, 16, "Procurar", GCScheme.SCHEME_15, this, 
				"SearchSourcePathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(420, 112, 76, 16, "Procurar", GCScheme.SCHEME_15, this, 
				"SearchOutputPathButtonClick", "resources/sprites/folderIcon.png", 
				1, GAlign.RIGHT, GAlign.MIDDLE);

		view.AddButton(480, 32, 80, 24, "Gerar", GCScheme.SCHEME_15, 
				this, "GenerateButtonClicked");


		view.AddButton(34, 308, 127, 22, "Video", this, "VideoButtonClick");
		view.AddButton(169, 308, 127, 22, "PIP", this, "PIPButtonClick");
		view.AddButton(304, 308, 127, 22, "Redimensionar", this, "ResizeButtonClick");
		view.AddButton(439, 308, 127, 22, "Mouse", this, "MouseButtonClick");
		view.AddButton(34, 336, 127, 22, "Teclado", this, "KeyboardButtonClick");
		view.AddButton(169, 336, 127, 22, "Arquivos", this, "FilesButtonClick");
		view.AddButton(304, 336, 127, 22, "Programas", this, "ProcessButtonClick");
		view.AddButton(439, 336, 127, 22, "Mapa", this, "MapButtonClick");
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
		final JLabel load = new JLabel("Aguarde, gerando 0%");
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
				
				ArrayList<MouseInfo> mouseInfos = new ArrayList<MouseInfo>();
				
				float percent = 0;
				float factorPercentLoad = 50.0f/listTxtFiles.length;
				
				final String output = (!OutputPathInput.getText().isEmpty() 
						&& !OutputPathInput.getText().equals(" ")) ? 
								OutputPathInput.getText() : Utils.getDefaultSavePath() + File.separator + "Jamcollab";


				if (source.isDirectory()) { // make sure it's a directory
					for (File f : source.listFiles(Utils.TEXT_FILTER)) {
						if(percent < 50)
							percent += factorPercentLoad;
						load.setText("Aguarde, gerando "+String.valueOf((int)(percent))+"%");
						
						BufferedReader br = null;
						 
						try {
				 
							String currentLine;
				 
							br = new BufferedReader(new FileReader(f.getAbsolutePath()));
				 
							while ((currentLine = br.readLine()) != null) {
								String handleTime = currentLine.substring(0, 26);
								int button = Integer.valueOf(currentLine.substring(29, 30));
								int x = Integer.valueOf(currentLine.substring(32, currentLine.indexOf("x")));
								int y = Integer.valueOf(currentLine.substring(currentLine.indexOf("x")+1, currentLine.indexOf("y")));
								int w = Integer.valueOf(currentLine.substring(currentLine.indexOf("R")+2, currentLine.indexOf("w")));
								int h = Integer.valueOf(currentLine.substring(currentLine.indexOf("w")+1, currentLine.length()-2));
								mouseInfos.add(new MouseInfo(x, y, w, h, button, handleTime));
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
				
				load.setText("Aguarde, gerando 50%");

				for (MouseInfo m : mouseInfos) {
					if(percent < 100)
						percent += factorPercentLoad;
					load.setText("Aguarde, gerando "+String.valueOf((int)(percent))+"%");
					
					PGraphics pg = Jamcollab.app.createGraphics(100, 100);
					pg.beginDraw();
					pg.text(m.getInfo(), 0, 0);
					pg.endDraw();
					pg.save(output + File.separator + "mouse_"+Utils.dateFormat()+".jpg");
					
				}

				
				
				load.setText("Aguarde, gerando 100%");


				SwingUtilities.invokeLater(new Runnable(){ 
					public void run(){  
						p1.remove(load);
						generatingDialog.dispose();
						JOptionPane.showMessageDialog(Jamcollab.jframe, 
								"Gerado com sucesso!");
						
										
						Utils.OpenFile(output + File.separator);
					}  
				});  
			}  
		};  

		generateThread.start();  
		generatingDialog.setVisible(true);  

	} 

	public void SearchSourcePathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder("Selecione uma pasta:", "selectSourceFolder", null, this);
	} 


	public void SearchOutputPathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder("Selecione uma pasta:", "selectOutputFolder", null, this);
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
